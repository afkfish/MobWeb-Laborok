using GuessWS.Middlewares;
using GuessWS.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddSingleton<GameStateService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}

app.UseDefaultFiles();
app.UseStaticFiles();

app.UseWebSockets();
app.UseMiddleware<WebSocketServerMiddleware>();

app.UseSpa(spa =>
{
    spa.UseProxyToSpaDevelopmentServer("http://localhost:4200");
});

app.Run();
