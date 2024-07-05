public class PremiumBus extends Bus
{
    private float _premiumPrice;

    /**
     * Constructs a new PremiumBus object with the provided parameters.
     *
     * @param id         The ID of the premium bus.
     * @param from       The starting destination of the premium bus.
     * @param to         The destination of the premium bus.
     * @param rows       The number of rows for the premium bus.
     * @param price      The price of the premium bus tickets.
     * @param refundCut  The refund cut for the premium bus tickets.
     * @param premiumFee The premium fee for the premium bus.
     */
    public PremiumBus(String id, String from, String to, String rows, Float price, String refundCut, String premiumFee)
    {
        super("Premium", id, from, to, Integer.parseInt(rows) * 3, price);
        setRefundCut((100 - Integer.parseInt(refundCut)) / 100f);
        setRefundMoney(getPrice() * getRefundCut());
        setPremiumPrice(getPrice() * (100 + Integer.parseInt(premiumFee)) / 100f);

        String logMessage = String.format("Voyage %s was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats" +
                " and %.2f TL priced %d premium seats. Note that refunds will be %s%% less than the paid amount.", id, from, to, price,
                getSeats()*2/3, getPremiumPrice(), getSeats() / 3, refundCut);
        FileOutput.writeToFile(Terminal.getOutputFile(), logMessage, true, true);
    }

    /**
     * Prints the details of the premium bus's voyage.
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

            switch (i % 3)
            {
                default: busStr.append(" "); break;
                case 0: busStr.append(" | "); break;
                case 2: busStr.append("\n"); break;
            }
        }

        FileOutput.writeToFile(Terminal.getOutputFile(), busStr.toString(), true, false);
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("Revenue: %.2f", getRevenue()), true, true);
    }

    public float getPreRefundMoney()
    {
        return getPremiumPrice() * getRefundCut();
    }

    public float getPremiumPrice ()
    {
        return _premiumPrice;
    }

    public void setPremiumPrice ( float premiumPrice )
    {
        _premiumPrice = premiumPrice;
    }
}