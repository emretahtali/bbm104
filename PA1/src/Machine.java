import java.util.Objects;

public class Machine
{
    private final Slot[][] _slots;

    /**
     * Constructor to initialize the Machine with slots.
     */
    public Machine()
    {
        _slots = new Slot[6][4];

        // initialize all slots
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                _slots[row][col] = new Slot();
            }
        }
    }

    /**
     * Getter for the slots array.
     *
     * @return The slots array.
     */
    Slot[][] get_slots() { return _slots; }

    /**
     * Fills the machine with products based on the given file.
     *
     * @param fileName   The file containing product information.
     * @param outputFile The output file to write logs.
     * @return Returns 0 if successful, -1 otherwise.
     */

    int fill(String fileName, String outputFile)
    {
        String[] products = FileInput.readFile(fileName, true, false);

        // iterate through each product
        for (String line : products)
        {
            String[] props = line.split("\t");
            String name = props[0];
            int price = Integer.parseInt(props[1]);

            String[] cals = props[2].split(" ");
            float prot = Float.parseFloat(cals[0]);
            float carb = Float.parseFloat(cals[1]);
            float fat = Float.parseFloat(cals[2]);
            float calories = 4*prot + 4*carb + 9*fat;

            // try to place the product in an existing slot
            boolean full = true;
            boolean placed = false;
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 4; col++)
                {
                    Slot slot = _slots[row][col];

                    if (slot.getAmount() != 10) full = false;
                    if (!Objects.equals(slot.getName(), name) || slot.getAmount() == 10) continue;

                    slot.addItem();
                    placed = true;
                    break;
                }
                if (placed) break;
            }
            if (placed) continue;

            // if no existing slot found, try to place it in an empty slot
            placed = false;
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 4; col++)
                {
                    Slot slot = _slots[row][col];

                    if (slot.getName() == "___")
                    {
                        slot.set(name, price, calories, prot, carb, fat, 1);
                        placed = true;
                        break;
                    }
                }
                if (placed) break;
            }

            // if no empty slot and machine is not full, log the error
            if (!placed && (!full)) FileOutput.writeToFile(outputFile, "INFO: There is no available place to put " + name, true, true);

            // if machine is full, return -1
            if (full)
            {
                FileOutput.writeToFile(outputFile, "INFO: There is no available place to put " + name, true, true);
                FileOutput.writeToFile(outputFile, "INFO: The machine is full!", true, true);
                return -1;
            }
        }
        return 0;
    }
}