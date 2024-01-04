using GuessWS.Models;
using GuessWS.Services;

using System.Diagnostics;
using System.Net.WebSockets;
using System.Text.Json;

namespace GuessWS.Middlewares;

public record WebSocketServerMiddleware(RequestDelegate Next, GameStateService GameState, ILogger<WebSocketServerMiddleware> Logger)
{
    private static JsonSerializerOptions JsonOptions { get; } = new JsonSerializerOptions(JsonSerializerDefaults.Web);

    public async Task Invoke(HttpContext context)
    {
        if (!context.WebSockets.IsWebSocketRequest)
        {
            await (Next?.Invoke(context) ?? Task.CompletedTask);
            return;
        }

        var socketId = Guid.NewGuid();
        var socket = await context.WebSockets.AcceptWebSocketAsync();
        var player = new Player { Socket = socket };
        GameState.Clients.TryAdd(socketId, player);

        var buffer = new byte[1024 * 4];
        var timer = new Stopwatch();

        while (socket.CloseStatus == null)
        {
            try
            {
                var result = await socket.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                var s = System.Text.Encoding.UTF8.GetString(buffer.AsSpan()[0..result.Count]);
                var message = JsonSerializer.Deserialize<Message>(buffer.AsSpan()[0..result.Count], JsonOptions);
                switch (message.Action)
                {
                    case "startGame":
                        GameState.Randomize();
                        timer.Restart();
                        break;
                    case "setName":
                        player.Name = message.Name;
                        break;
                    case "guess":
                        if (!timer.IsRunning)
                            break;

                        player.CurrentGuesses++;
                        var val = GameState.CurrentValue;
                        var respMessage = new GuessResponse
                        {
                            Name = player.Name,
                            Guess = message.Guess,
                            Value = val > message.Guess
                                ? "greater"
                                : val < message.Guess
                                    ? "less"
                                    : "correct",
                            TimeInSeconds = (int)timer.Elapsed.TotalSeconds
                        };

                        if (respMessage.Value == "correct")
                        {
                            timer.Stop();
                            GameState.Toplist.Add(new ToplistItem
                            {
                                Name = player.Name,
                                TimeInSeconds = (int)timer.Elapsed.TotalSeconds,
                                Guesses = player.CurrentGuesses
                            });
                            GameState.Toplist = GameState.Toplist.OrderBy(e => e.Guesses).ThenBy(e => e.TimeInSeconds).ToList();
                            player.CurrentGuesses = 0;
                        }

                        var response = new ArraySegment<byte>(JsonSerializer.SerializeToUtf8Bytes(respMessage, JsonOptions));
                        foreach (var client in GameState.Clients.ToList())
                        {
                            try
                            {
                                await client.Value.Socket.SendAsync(response, WebSocketMessageType.Text, true, CancellationToken.None);
                            }
                            catch (Exception ex)
                            {
                                GameState.Clients.TryRemove(client.Key, out _);
                                Logger.LogWarning(ex, "Error sending response to client.");
                            }
                        }
                        break;
                    case "toplist":
                        await socket.SendAsync(
                            new ArraySegment<byte>(JsonSerializer.SerializeToUtf8Bytes(GameState.Toplist, JsonOptions)),
                            WebSocketMessageType.Text,
                            true,
                            CancellationToken.None);
                        break;
                }
            }
            catch (Exception ex)
            {
                Logger.LogWarning(ex, "Error processing client request.");
            }
        }

        GameState.Clients.TryRemove(socketId, out var _);
    }
}
