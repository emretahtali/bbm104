/**
 * The Road class represents a road in a graph, with its properties such as
 * start and end points, destination, distance, id, length, and priority.
 */
public class Road
{
    private final String A;
    private final String B;
    private final String destination;
    private int distance;
    private final int id;
    private final int length;
    private int priority;

    /**
     * Constructor for Road class.
     *
     * @param A           The starting point of the road.
     * @param B           The ending point of the road.
     * @param destination The destination point of the road.
     * @param distance    The distance of the road.
     * @param id          The unique identifier of the road as a string.
     * @param length      The length of the road as a string.
     * @param priority    The priority of the road.
     */
    public Road(String A, String B, String destination, int distance, String id, String length, int priority)
    {
        this.A = A;
        this.B = B;
        this.destination = destination;
        this.distance = distance;
        this.id = Integer.parseInt(id);
        this.length = Integer.parseInt(length);
        this.priority = priority;
    }

    public int getDistance ()
    {
        return distance;
    }

    public void setDistance ( int distance )
    {
        this.distance = distance;
    }

    public int getId ()
    {
        return id;
    }

    public String getA ()
    {
        return A;
    }

    public String getB ()
    {
        return B;
    }

    public String getDestination ()
    {
        return destination;
    }

    public int getLength ()
    {
        return length;
    }

    public int getPriority ()
    {
        return priority;
    }

    public void setPriority ( int priority )
    {
        this.priority = priority;
    }
}