import java.util.SortedMap;
import java.util.TreeMap;

public class Terminal
{
    private static String _outputFile = "";
    private static SortedMap<Integer, Bus> _voyageMap = new TreeMap<Integer, Bus>();

    /**
     * Executes the given command and writes the command to the output file.
     *
     * @param line    The command to execute.
     * @param newLine Indicates whether to append a new line after writing the command.
     */
    public static void runCommand (String line, boolean newLine)
    {
        FileOutput.writeToFile(getOutputFile(), "COMMAND: " + line, true, true);

        try
        {
            String[] command = line.split("\t");

            switch (command[0])
            {
                default: Exceptions.invalidCommand(command[0]); break;
                case "INIT_VOYAGE": initVoyage(command); break;
                case "SELL_TICKET": processTicket(command, "sell"); break;
                case "REFUND_TICKET": processTicket(command, "refund"); break;
                case "PRINT_VOYAGE": printVoyage(command); break;
                case "CANCEL_VOYAGE": cancelVoyage(command); break;
                case "Z_REPORT": zReport(command, newLine); break;
            }
        }

        catch (Exception e)
        {
            Exceptions.userError(line.split("\t")[0]);
        }
    }

    /**
     * Initializes a new bus voyage based on the given command.
     *
     * @param command The command specifying the details of the voyage.
     */
    private static void initVoyage(String[] command)
    {
        // check for valid command parameters and bus type
        int commandVarNum;
        switch (command[1])
        {
            default: Exceptions.invalidBusType(); return;
            case "Minibus": commandVarNum = 7; break;
            case "Standard": commandVarNum = 8; break;
            case "Premium": commandVarNum = 9; break;
        }

        // perform additional checks and create the appropriate type of bus voyage based on the command parameters
        if (Exceptions.checkCommand(command, commandVarNum)) return;
        if (Exceptions.checkId(command[2], true)) return;
        if (Exceptions.checkRows(command[5])) return;
        if (Exceptions.checkPrice(command[6])) return;
        if (Exceptions.checkVoyage(command[2])) return;

        switch (command[1])
        {
            case "Minibus":
            {
                addVoyage(command[2], new Minibus((command[2]), command[3], command[4], command[5], Float.parseFloat(command[6])));
                break;
            }
            case "Standard":
            {
                if (Exceptions.checkRefundCut(command[7])) return;

                addVoyage(command[2], new StandardBus((command[2]), command[3], command[4], command[5], Float.parseFloat(command[6]), command[7]));
                break;
            }
            case "Premium":
            {
                if (Exceptions.checkRefundCut(command[7])) return;
                if (Exceptions.checkPremiumFee(command[8])) return;

                addVoyage(command[2], new PremiumBus((command[2]), command[3], command[4], command[5], Float.parseFloat(command[6]), command[7], command[8]));
                break;
            }
        }
    }

    /**
     * Processes ticket sales or refunds based on the given command.
     *
     * @param command     The command specifying the ticket sale or refund details.
     * @param processType Indicates whether to process a ticket sale or refund.
     */
    private static void processTicket(String[] command, String processType)
    {
        // check for errors
        if (Exceptions.checkCommand(command, 3)) return;
        if (Exceptions.checkId(command[1], false)) return;

        String[] seats = command[2].split("_");
        Bus voyage = getVoyageMap().get(Integer.parseInt(command[1]));
        boolean[] seatList = voyage.getSeatList();

        if (Exceptions.checkRefund(processType, voyage.getType())) return;
        for (String seatStr : seats) { if (Exceptions.checkSeat(seatStr, seatList, processType)) return; }

        // process all the seats
        float revenue = 0f;
        if (processType.equals("sell"))
        {
            for (String seatStr : seats)
            {
                int seat = Integer.parseInt(seatStr) - 1;
                seatList[seat] = true;

                if (voyage.getType().equals("Premium") && seat % 3 == 0) revenue += ((PremiumBus) voyage).getPremiumPrice();
                else revenue += voyage.getPrice();
            }

            voyage.setRevenue(voyage.getRevenue() + revenue);
            FileOutput.writeToFile(getOutputFile(), String.format("Seat %s of the Voyage %d from %s to %s was successfully sold for " +
                    "%.2f TL.", String.join("-", seats), voyage.getId(), voyage.getFrom(), voyage.getTo(), revenue), true, true);
        }
        else
        {
            for (String seatStr : seats)
            {
                int seat = Integer.parseInt(seatStr) - 1;
                seatList[seat] = false;

                if (voyage.getType().equals("Premium") && seat % 3 == 0) revenue += ((PremiumBus) voyage).getPreRefundMoney();
                else revenue += voyage.getRefundMoney();
            }

            voyage.setRefundRevenue(voyage.getRefundRevenue() + revenue / voyage.getRefundCut() * (1 - voyage.getRefundCut()));
            voyage.setRevenue(voyage.getRevenue() - revenue);
            FileOutput.writeToFile(getOutputFile(), String.format("Seat %s of the Voyage %d from %s to %s was successfully refunded for " +
                    "%.2f TL.", String.join("-", seats), voyage.getId(), voyage.getFrom(), voyage.getTo(), revenue), true, true);
        }
    }


    /**
     * Prints details of the specified voyage.
     *
     * @param command The command specifying the ID of the voyage to print.
     */
    private static void printVoyage(String[] command)
    {
        if (Exceptions.checkCommand(command, 2)) return;
        if (Exceptions.checkId(command[1], false)) return;

        getVoyageMap().get(Integer.parseInt(command[1])).printVoyage();
    }

    /**
     * Cancels the specified voyage.
     *
     * @param command The command specifying the ID of the voyage to cancel.
     */
    private static void cancelVoyage(String[] command)
    {
        if (Exceptions.checkCommand(command, 2)) return;
        if (Exceptions.checkId(command[1], false)) return;

        int voyageId = Integer.parseInt(command[1]);
        Bus voyage = getVoyageMap().get(voyageId);

        FileOutput.writeToFile(getOutputFile(), String.format("Voyage %d was successfully cancelled!", voyageId), true, true);

        voyage.setRevenue(voyage.getRefundRevenue());
        FileOutput.writeToFile(getOutputFile(), "Voyage details can be found below:", true, true);
        voyage.printVoyage();

        getVoyageMap().remove(voyageId);
    }

    /**
     * Generates a Z report for all active voyages.
     *
     * @param command The Z_REPORT command.
     * @param newLine Indicates whether to append a new line after writing the report.
     */
    public static void zReport(String[] command, boolean newLine)
    {
        if (Exceptions.checkCommand(command, 1)) return;

        FileOutput.writeToFile(getOutputFile(), "Z Report:", true, true);
        for (Bus bus : getVoyageMap().values())
        {
            FileOutput.writeToFile(getOutputFile(), "----------------", true, true);
            bus.printVoyage();
        }

        if (getVoyageMap().isEmpty())
        {
            FileOutput.writeToFile(getOutputFile(), "----------------", true, true);
            FileOutput.writeToFile(getOutputFile(), "No Voyages Available!", true, true);
        }

        FileOutput.writeToFile(getOutputFile(), "----------------", true, newLine);
    }

    public static String getOutputFile ()
    {
        return _outputFile;
    }

    public static void setOutputFile ( String outputFile )
    {
        _outputFile = outputFile;
    }

    public static SortedMap<Integer, Bus> getVoyageMap ()
    {
        return _voyageMap;
    }

    public static void setVoyageMap ( SortedMap<Integer, Bus> voyageMap )
    {
        _voyageMap = voyageMap;
    }
    public static void addVoyage ( String id, Bus voyage )
    {
        getVoyageMap().put(Integer.parseInt(id), voyage);
    }
}