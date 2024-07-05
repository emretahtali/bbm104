import java.util.Arrays;
import java.util.HashSet;

public class Main
{
    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main (String[] args)
    {
        Machine machine = new Machine();

        String outputFile = args[2];
        FileOutput.writeToFile(outputFile, "", false, false);
        machine.fill(args[0], outputFile);
        listMachineContent(machine, outputFile);
        purchase(machine, args[1], outputFile);
        listMachineContent(machine, outputFile);
    }

    /**
     * Purchases items from the machine based on input data.
     *
     * @param machine     The machine from which items are purchased.
     * @param fileName    The file containing purchase information.
     * @param outputFile  The output file to write logs.
     */
    public static void purchase(Machine machine, String fileName, String outputFile)
    {
        HashSet<String> moneyTypes = new HashSet<String>(Arrays.asList("1", "5", "10", "20", "50", "100", "200"));

        String[] purchaseInfo = FileInput.readFile(fileName, true, false);

        for (String line : purchaseInfo)
        {
            purchaseItem(machine, line, moneyTypes, outputFile);
        }
    }

    /**
     * Lists the content of the machine.
     *
     * @param machine    The machine whose content is listed.
     * @param outputFile The output file to write the machine content.
     */
    public static void listMachineContent(Machine machine, String outputFile)
    {
        String outputStr = "";

        outputStr += "-----Gym Meal Machine-----\n";

        for (Slot[] col : machine.get_slots())
        {
            for (Slot slot : col)
            {
                outputStr += String.format("%s(%d, %d)___", slot.getName(), Math.round(slot.getCals()), slot.getAmount());
            }

            outputStr += "\n";
        }

        outputStr += "----------";
        FileOutput.writeToFile(outputFile, outputStr, true, true);
    }

    /**
     * Purchases an item from the machine based on the given line of input.
     *
     * @param machine      The machine from which an item is purchased.
     * @param line         The line of input representing the purchase information.
     * @param moneyTypes   The set of accepted money types.
     * @param outputFile   The output file to write logs.
     * @return             Returns 0 if the purchase is successful, -1 otherwise.
     */
    public static int purchaseItem(Machine machine, String line, HashSet<String> moneyTypes, String outputFile)
    {
        FileOutput.writeToFile(outputFile, "INPUT: " + line, true, true);

        String[] lineBits = line.split("\t");

        // validate inserted money
        int balance = 0;
        String[] insertedMoney = lineBits[1].split(" ");

        for (String i : insertedMoney)
        {
            if (moneyTypes.contains(i)) balance += Integer.parseInt(i);
            else FileOutput.writeToFile(outputFile, String.format("INFO: %s TL is not accepted.", i), true, true);
        }

        // parse purchase type and amount
        String type = lineBits[2];
        float amount = Float.parseFloat(lineBits[3]);

        // handle special case for number type
        if (type.equals("NUMBER") && amount > 24)
        {
            FileOutput.writeToFile(outputFile, "INFO: Number cannot be accepted. Please try again with another number.", true, true);
            FileOutput.writeToFile(outputFile, String.format("RETURN: Returning your change: %d TL", balance), true, true);
            return -1;
        }

        Slot selectedSlot = null;

        // search for the selected slot
        boolean found = false;
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                Slot slot = machine.get_slots()[row][col];

                // special case for number type
                if (type.equals("NUMBER"))
                {
                    if (amount == (float) 4*row + col)
                    {
                        selectedSlot = slot;
                        found = true;
                        break;
                    }
                    else continue;
                }

                // handle other types of search
                float lookFor;
                switch (type)
                {
                    case "CALORIES": default: lookFor = slot.getCals(); break;
                    case "PROTEIN": lookFor = slot.getProt(); break;
                    case "CARB": lookFor = slot.getCarb(); break;
                    case "FAT": lookFor = slot.getFat(); break;
                }

                // check if the slot matches the criteria
                if (amount - 5 <= lookFor && lookFor <= amount + 5 && !slot.getName().equals("___"))
                {
                    selectedSlot = slot;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        // handle various cases of not finding the slot
        if (selectedSlot == null)
        {
            FileOutput.writeToFile(outputFile, "INFO: Product not found, your money will be returned.", true, true);
            FileOutput.writeToFile(outputFile, String.format("RETURN: Returning your change: %d TL", balance), true, true);
            return -1;
        }

        if (selectedSlot.getName() == "___")
        {
            FileOutput.writeToFile(outputFile, "INFO: This slot is empty, your money will be returned.", true, true);
            FileOutput.writeToFile(outputFile, String.format("RETURN: Returning your change: %d TL", balance), true, true);
            return -1;
        }

        if (balance < selectedSlot.getPrice())
        {
            FileOutput.writeToFile(outputFile, "INFO: Insufficient money, try again with more money.", true, true);
            FileOutput.writeToFile(outputFile, String.format("RETURN: Returning your change: %d TL", balance), true, true);
            return -1;
        }

        // handle successful purchase
        FileOutput.writeToFile(outputFile, "PURCHASE: You have bought one " + selectedSlot.getName(), true, true);
        FileOutput.writeToFile(outputFile, String.format("RETURN: Returning your change: %d TL", balance - selectedSlot.getPrice()), true, true);
        selectedSlot.takeItem();

        return 0;
    }
}