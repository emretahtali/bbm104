/**
 * The abstract Item class represents a general item with a name, barcode, and price.
 */
public abstract class Item
{
    private final String name;
    private final String barcode;
    private final double price;

    /**
     * Constructs an Item with the specified name, barcode, and price.
     *
     * @param name    The name of the item.
     * @param barcode The barcode of the item.
     * @param price   The price of the item as a string, which is converted to a double.
     */
    public Item (String name, String barcode, String price)
    {
        this.name = name;
        this.barcode = barcode;
        this.price = Double.parseDouble(price);
    }

    /**
     * Provides a description of the item.
     *
     * @return A string description of the item.
     */
    public abstract String getDescription();

    public String getName ()
    {
        return name;
    }

    public String getBarcode ()
    {
        return barcode;
    }

    public double getPrice ()
    {
        return price;
    }
}
