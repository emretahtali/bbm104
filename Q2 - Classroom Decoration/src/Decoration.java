public class Decoration
{
    private final String _name;
    private final String _type;
    private final int _price;
    private double _area;

    /**
     * Constructs a new Decoration object with the specified parameters.
     *
     * @param name   The name of the decoration.
     * @param type   The type of the decoration.
     * @param price  The price of the decoration.
     */
    public Decoration(String name, String type, String price)
    {
        _name = name;
        _type = type;
        _price = Integer.parseInt(price);
    }

    /**
     * Constructs a new Decoration object with the specified parameters, including area.
     *
     * @param name   The name of the decoration.
     * @param type   The type of the decoration.
     * @param price  The price of the decoration.
     * @param area   The area of a tile.
     */
    public Decoration(String name, String type, String price, String area)
    {
        _name = name;
        _type = type;
        _price = Integer.parseInt(price);
        _area = Double.parseDouble(area);
    }

    /**
     * @return The name of the decoration.
     */
    public String getName ()
    {
        return _name;
    }

    /**
     * @return The type of the decoration.
     */
    public String getType ()
    {
        return _type;
    }

    /**
     * @return The price of the decoration.
     */
    public int getPrice ()
    {
        return _price;
    }

    /**
     * @return The area covered by the decoration.
     */
    public double getArea ()
    {
        return _area;
    }
}
