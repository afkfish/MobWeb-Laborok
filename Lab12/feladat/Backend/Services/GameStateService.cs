using GuessWS.Models;

using System.Collections.Concurrent;

namespace GuessWS.Services;

public class GameStateService
{
    private readonly Random _random = new Random();
    private int? _currentValue;

    public ConcurrentDictionary<Guid, Player> Clients { get; } = new ConcurrentDictionary<Guid, Player>();

    public List<ToplistItem> Toplist { get; set; } = new List<ToplistItem>();

    public int CurrentValue => (_currentValue = _currentValue ?? Randomize()).Value;

    public int Randomize() => _random.Next(1, 100);
}
