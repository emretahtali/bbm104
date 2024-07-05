public class StandardBus extends Bus
{
    /**
     * Constructs a new StandardBus object with the provided parameters.
     *
     * @param id         The ID of the bus.
     * @param from       The starting destination of the bus.
     * @param to         The destination of the bus.
     * @param rows       The number of rows for the bus.
     * @param price      The price of the bus tickets.
     * @param refundCut  The refund cut for the bus tickets.
     */
    public StandardBus(String id, String from, String to, String rows, Float price, String refundCut)
    {
        super("Standard", id, from, to, Integer.parseInt(rows) * 4, price);
        setRefundCut((100 - Integer.parseInt(refundCut)) / 100f);
        setRefundMoney(getPrice() * getRefundCut());

        String logMessage = String.format("Voyage %s was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. " +
                "Note that refunds will be %s%% less than the paid amount.", id, from, to, price, getSeats(), refundCut);
        FileOutput.writeToFile(Terminal.getOutputFile(), logMessage, true, true);
    }

    /**
     * Prints the details of the bus's voyage.
     */
    @Override
    public void printVoyage()
    {
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("Voyage %d", getId()), true, true);
        FileOutput.writeToFile(Terminal.getOutputFile(), getFrom() + "-" +  getTo(), true, true);

        StringBuilder busStr = new StringBuilder();
        for (int i = 0; i < getSeats(); i++)
        {
            busStr.append((getSeatList()[i]) ? "X" : "*");

            switch (i % 4)
            {
                default: busStr.append(" "); break;
                case 1: busStr.append(" | "); break;
                case 3: busStr.append("\n"); break;
            }
        }

        FileOutput.writeToFile(Terminal.getOutputFile(), busStr.toString(), true, false);
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("Revenue: %.2f", getRevenue()), true, true);
    }
}
