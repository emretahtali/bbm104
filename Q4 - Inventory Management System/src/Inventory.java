import java.util.ArrayList;
import java.util.List;

/**
 * The Inventory class manages a collection of items, allowing adding, removing,
 * searching by barcode or name, and displaying the inventory.
 */
public class Inventory
{
    private final List<Item> inventory = new ArrayList<Item>();

    /**
     * Reads commands from a file and executes them to modify the inventory.
     *
     * @param args Command line arguments; args[0] is the input file path, args[1] is the output file path.
     */
    public void run(String[] args)
    {
        // Loop through each line in the input file
        for (String lineStr: FileInput.readFile(args[0], true, true))
        {
            String[] line = lineStr.split("\t");

            // Process the command based on the first part of the line
            switch (line[0])
            {
                case "ADD":
                {
                    // Determine the type of item and add it to the inventory
                    switch (line[1])
                    {
                        case "Book": addItem(new Book(line[2], line[3], line[4], line[5])); break;
                        case "Toy": addItem(new Toy(line[2], line[3], line[4], line[5])); break;
                        case "Stationery": addItem(new Stationery(line[2], line[3], line[4], line[5])); break;
                    }
                    break;
                }
                case "REMOVE":
                {
                    // Attempt to remove the item by barcode and write the result to the output file
                    String message = (removeItem(line[1]) == 0) ? "Item is removed." : "Item is not found.";
                    FileOutput.writeToFile(args[1], "REMOVE RESULTS:\n" + message, true, true);
                    break;
                }
                case "SEARCHBYBARCODE":
                {
                    // Search for the item by barcode and write the result to the output file
                    Item item = findByBarcode(line[1]);

                    String message = (item != null) ? item.getDescription() : "Item is not found.";
                    FileOutput.writeToFile(args[1], "SEARCH RESULTS:\n" + message, true, true);
                    break;
                }
                case "SEARCHBYNAME":
                {
                    // Search for the item by name and write the result to the output file
                    Item item = findByName(line[1]);

                    String message = (item != null) ? item.getDescription() : "Item is not found.";
                    FileOutput.writeToFile(args[1], "SEARCH RESULTS:\n" + message, true, true);
                    break;
                }
                case "DISPLAY":
                {
                    // Write the current inventory list to the output file
                    FileOutput.writeToFile(args[1], "INVENTORY:\n" + listInventory(), true, false);
                    break;
                }
            }
            // Add a separator in the output file for all commands except ADD
            if (!line[0].equals("ADD")) FileOutput.writeToFile(args[1], "------------------------------", true, true);
        }
    }

    /**
     * Adds an item to the inventory.
     *
     * @param item The item to be added.
     * @param <E>  The type of item, which extends the Item class.
     */
    public <E extends Item> void addItem(E item)
    {
        inventory.add(item);
    }

    /**
     * Removes an item from the inventory based on its barcode.
     *
     * @param barcode The barcode of the item to be removed.
     * @return 0 if the item was removed successfully, -1 if the item was not found.
     */
    public int removeItem(String barcode)
    {
        // Loop through the inventory to find the item by barcode
        for (Item item : inventory)
        {
            if (!item.getBarcode().equals(barcode)) continue;

            // Remove the item if the barcode matches
            inventory.remove(item);
            return 0;
        }
        return -1;
    }

    /**
     * Finds an item in the inventory by its name.
     *
     * @param name The name of the item to find.
     * @return The item if found, null otherwise.
     */
    public Item findByName(String name)
    {
        // Loop through the inventory to find the item by name
        for (Item item : inventory)
        {
            if (item.getName().equals(name)) return item;
        }
        return null;
    }

    /**
     * Finds an item in the inventory by its barcode.
     *
     * @param barcode The barcode of the item to find.
     * @return The item if found, null otherwise.
     */
    public Item findByBarcode(String barcode)
    {
        // Loop through the inventory to find the item by barcode
        for (Item item : inventory)
        {
            if (item.getBarcode().equals(barcode)) return item;
        }
        return null;
    }

    /**
     * Lists all items in the inventory categorized by their types.
     *
     * @return A string representation of the inventory.
     */
    public String listInventory()
    {
        StringBuilder returnStr = new StringBuilder();

        // Array of item types to categorize the inventory
        String[] types = {"Book", "Toy", "Stationery"};
        for (String type : types)
        {
            // Loop through the inventory to append items of the current type
            for (Item item : inventory)
            {
                if (!item.getClass().getSimpleName().equals(type)) continue;

                returnStr.append(item.getDescription() + "\n");
            }
        }

        // Return the inventory list as a string
        return returnStr.toString();
    }
}
