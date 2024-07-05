/**
 * The Toy class represents a toy item in the inventory, extending the Item class.
 * It includes additional information specific to toys, such as the color.
 */
public class Toy extends Item
{
    private final String color;

    /**
     * Constructs a Toy with the specified name, color, barcode, and price.
     *
     * @param name    The name of the toy.
     * @param color   The color of the toy.
     * @param barcode The barcode of the toy.
     * @param price   The price of the toy.
     */
    public Toy ( String name, String color, String barcode, String price)
    {
        super(name, barcode, price);
        this.color = color;
    }

    /**
     * Provides a description of the toy.
     *
     * @return A string description of the toy, including its color, barcode, and price.
     */
    @Override
    public String getDescription()
    {
        return String.format("Color of the %s is %s. Its barcode is %s and its price is %s", getName(), getColor(), getBarcode(), getPrice());
    }

    public String getColor ()
    {
        return color;
    }
}
