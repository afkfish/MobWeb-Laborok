namespace GuessWS.Models;

public class GuessResponse
{
    public string Name { get; set; }
    public int Guess { get; set; }
    public string Value { get; set; }
    public int TimeInSeconds { get; set; }
}
