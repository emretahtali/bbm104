/**
 * The Stationery class represents a stationery item in the inventory, extending the Item class.
 * It includes additional information specific to stationery items, such as the kind of stationery.
 */
public class Stationery extends Item
{
    private final String kind;

    /**
     * Constructs a Stationery item with the specified name, kind, barcode, and price.
     *
     * @param name    The name of the stationery item.
     * @param kind    The kind of the stationery item.
     * @param barcode The barcode of the stationery item.
     * @param price   The price of the stationery.
     */
    public Stationery ( String name, String kind, String barcode, String price)
    {
        super(name, barcode, price);
        this.kind = kind;
    }

    /**
     * Provides a description of the stationery item.
     *
     * @return A string description of the stationery item, including its kind, barcode, and price.
     */
    @Override
    public String getDescription()
    {
        return String.format("Kind of the %s is %s. Its barcode is %s and its price is %s", getName(), getKind(), getBarcode(), getPrice());
    }

    public String getKind ()
    {
        return kind;
    }
}
