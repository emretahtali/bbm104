public abstract class Bus
{
    private String _type;
    private String _id;
    private String _from;
    private String _to;
    private int _seats;
    private float _price;
    private boolean[] _seatList;
    private float _refundCut;
    private float _refundMoney;
    private float _revenue = 0f;
    private float _refundRevenue = 0f;

    /**
     * Constructs a new Bus object with the provided parameters.
     *
     * @param type  The type of bus.
     * @param id    The ID of the bus.
     * @param from  The starting destination of the bus.
     * @param to    The destination of the bus.
     * @param seats The number of seats in the bus.
     * @param price The price of the bus.
     */
    public Bus(String type, String id, String from, String to, int seats, Float price)
    {
        setType(type);
        setId(id);
        setFrom(from);
        setTo(to);
        setSeats(seats);
        setPrice(price);
        setSeatList(new boolean[seats]);
    }

    /**
     * Prints the details of the bus's voyage.
     */
    public abstract void printVoyage();

    public String getType ()
    {
        return _type;
    }

    public void setType ( String type )
    {
        _type = type;
    }

    public int getId ()
    {
        return Integer.parseInt(_id);
    }

    public void setId ( String id )
    {
        _id = id;
    }

    public String getFrom ()
    {
        return _from;
    }

    public void setFrom ( String from )
    {
        _from = from;
    }

    public String getTo ()
    {
        return _to;
    }

    public void setTo ( String to )
    {
        _to = to;
    }

    public int getSeats ()
    {
        return _seats;
    }

    public void setSeats ( int seats )
    {
        _seats = seats;
    }

    public float getPrice ()
    {
        return _price;
    }

    public void setPrice ( float price )
    {
        _price = price;
    }

    public boolean[] getSeatList ()
    {
        return _seatList;
    }

    public void setSeatList ( boolean[] seatList )
    {
        _seatList = seatList;
    }

    public float getRefundCut ()
    {
        return _refundCut;
    }

    public void setRefundCut ( float refundCut )
    {
        _refundCut = refundCut;
    }

    public float getRefundMoney ()
    {
        return _refundMoney;
    }

    public void setRefundMoney ( float refundMoney )
    {
        _refundMoney = refundMoney;
    }

    public float getRevenue ()
    {
        return _revenue;
    }

    public void setRevenue ( float revenue )
    {
        _revenue = revenue;
    }

    public float getRefundRevenue ()
    {
        return _refundRevenue;
    }

    public void setRefundRevenue ( float refundRevenue )
    {
        _refundRevenue = refundRevenue;
    }
}
