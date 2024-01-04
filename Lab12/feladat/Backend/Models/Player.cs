using System.Net.WebSockets;

namespace GuessWS.Models;

public class Player
{
    public WebSocket Socket { get; set; }
    public string Name { get; set; }
    public int CurrentGuesses { get; set; }
}
