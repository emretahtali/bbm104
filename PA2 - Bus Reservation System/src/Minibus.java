public class Minibus extends Bus
{
    /**
     * Constructs a new Minibus object with the provided parameters.
     *
     * @param id    The ID of the minibus.
     * @param from  The starting destination of the minibus.
     * @param to    The destination of the minibus.
     * @param rows  The number of rows for the minibus.
     * @param price The price of the minibus tickets.
     */
    public Minibus(String id, String from, String to, String rows, Float price)
    {
        super("Minibus", id, from, to, Integer.parseInt(rows) * 2, price);

        String logMessage = String.format("Voyage %s was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. " +
                "Note that minibus tickets are not refundable.", id, from, to, price, getSeats());
        FileOutput.writeToFile(Terminal.getOutputFile(), logMessage, true, true);
    }

    /**
     * Prints the details of the minibus's voyage.
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

            if (i % 2 == 1) busStr.append("\n");
            else busStr.append(" ");
        }

        FileOutput.writeToFile(Terminal.getOutputFile(), busStr.toString(), true, false);
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("Revenue: %.2f", getRevenue()), true, true);
    }
}
