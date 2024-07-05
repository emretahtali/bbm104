public class Classroom
{
    private final String _name;
    private final String _type;
    private final double _width;
    private final double _length;
    private final double _height;

    /**
     * Constructs a new Classroom object with the specified parameters.
     *
     * @param name    The name of the classroom.
     * @param type    The type of the classroom.
     * @param width   The width of the classroom.
     * @param length  The length of the classroom.
     * @param height  The height of the classroom.
     */
    public Classroom(String name, String type, String width, String length, String height)
    {
        _name = name;
        _type = type;
        _width = Double.parseDouble(width);
        _length = Double.parseDouble(length);
        _height = Double.parseDouble(height);
    }

    /**
     * @return The name of the classroom.
     */
    public String getName ()
    {
        return _name;
    }

    /**
     * @return The type of the classroom.
     */
    public String getType ()
    {
        return _type;
    }

    /**
     * @return The width of the classroom.
     */
    public double getWidth ()
    {
        return _width;
    }

    /**
     * @return The length of the classroom.
     */
    public double getLength ()
    {
        return _length;
    }

    /**
     * @return The height of the classroom.
     */
    public double getHeight ()
    {
        return _height;
    }
}
