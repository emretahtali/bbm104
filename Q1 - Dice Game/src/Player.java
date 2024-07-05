public class Player
{
    private final String _name;
    private int _score = 0;

    public Player(String name) { _name = name; }

    public String getName() { return _name; }

    public int getPoints() { return _score; }

    public void addPoints(int points) { _score += points; }

    public void deletePoints() { _score = 0; }
}