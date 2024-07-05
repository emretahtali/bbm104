/**
 * The Book class represents a book item in the inventory, extending the Item class.
 * It includes additional information specific to books, such as the author.
 */
public class Book extends Item
{
    private final String author;

    /**
     * Constructs a Book with the specified name, author, barcode, and price.
     *
     * @param name    The name of the book.
     * @param author  The author of the book.
     * @param barcode The barcode of the book.
     * @param price   The price of the book.
     */
    public Book (String name, String author, String barcode, String price)
    {
        super(name, barcode, price);
        this.author = author;
    }

    /**
     * Provides a description of the book.
     *
     * @return A string description of the book, including its author, barcode, and price.
     */
    @Override
    public String getDescription()
    {
        return String.format("Author of the %s is %s. Its barcode is %s and its price is %s", getName(), getAuthor(), getBarcode(), getPrice());
    }

    public String getAuthor ()
    {
        return author;
    }
}
