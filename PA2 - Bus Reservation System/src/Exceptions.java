public class Exceptions
{
    /**
     * Checks if the provided ID is valid and handles any errors.
     *
     * @param id           The ID to check.
     * @param onlyCheckSign Indicates whether to only check if the ID is negative.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkId(String id, boolean onlyCheckSign)
    {
        try
        {
            if (Integer.parseInt(id) < 0)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", id), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", id), true, true);
            return true;
        }

        if (!onlyCheckSign && !Terminal.getVoyageMap().containsKey(Integer.parseInt(id)))
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: There is no voyage with ID of %s!", id), true, true);
            return true;
        }

        return false;
    }

    /**
     * Handles invalid command errors.
     *
     * @param command The invalid command.
     */
    public static void invalidCommand(String command)
    {
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: There is no command namely %s!", command), true, true);
    }

    /**
     * Checks if the provided number of rows is valid.
     *
     * @param rows The number of rows to check.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkRows(String rows)
    {
        try
        {
            if (Integer.parseInt(rows) < 0)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!", rows), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!", rows), true, true);
            return true;
        }
        return false;
    }

    /**
     * Checks if the provided price is valid.
     *
     * @param price The price to check.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkPrice(String price)
    {
        try
        {
            if (Float.parseFloat(price) < 0)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive number, price must be a positive number!", price), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive number, price must be a positive number!", price), true, true);
            return true;
        }
        return false;
    }

    /**
     * Handles errors for invalid bus type.
     */
    public static void invalidBusType()
    {
        FileOutput.writeToFile(Terminal.getOutputFile(), "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
    }

    /**
     * Checks if the provided refund cut is valid.
     *
     * @param refundCut The refund cut to check.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkRefundCut(String refundCut)
    {
        try
        {
            int refundCutNum = Integer.parseInt(refundCut);
            if (refundCutNum < 0 || refundCutNum > 100)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", refundCut), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", refundCut), true, true);
            return true;
        }
        return false;
    }

    /**
     * Checks if the provided premium fee is valid.
     *
     * @param premiumFee The premium fee to check.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkPremiumFee(String premiumFee)
    {
        try
        {
            if (Integer.parseInt(premiumFee) < 0)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!", premiumFee), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, premium fee must be a positive integer!", premiumFee), true, true);
            return true;
        }
        return false;
    }

    /**
     * Checks if the provided seat is valid.
     *
     * @param seatStr    The seat number to check.
     * @param seatList   The list of seats.
     * @param processType The type of process ("sell" or "refund").
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkSeat(String seatStr, boolean[] seatList, String processType)
    {
        // check for negative seat numbers
        try
        {
            if (Integer.parseInt(seatStr) < 1)
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatStr), true, true);
                return true;
            }
        }
        catch (Exception e)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatStr), true, true);
            return true;
        }

        int seat = Integer.parseInt(seatStr) - 1;

        // check for nonexistent seat numbers
        if (seat >= seatList.length)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), "ERROR: There is no such a seat!", true, true);
            return true;
        }

        // check for unavailable seats
        if (processType.equals("sell"))
        {
            if (seatList[seat])
            {
                FileOutput.writeToFile(Terminal.getOutputFile(), "ERROR: One or more seats already sold!", true, true);
                return true;
            }
        }
        else if (!seatList[seat])
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), "ERROR: One or more seats are already empty!", true, true);
            return true;
        }

        return false;
    }

    /**
     * Checks if the provided command has the correct number of arguments.
     *
     * @param command The command to check.
     * @param num     The expected number of arguments.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkCommand(String[] command, int num)
    {
        if (command.length != num)
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: Erroneous usage of \"%s\" command!", command[0]), true, true);
            return true;
        }
        else return false;
    }

    /**
     * Checks if refund is applicable for the given process and voyage type.
     *
     * @param processType The type of process ("sell" or "refund").
     * @param voyageType  The type of voyage.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkRefund(String processType, String voyageType)
    {
        if (processType.equals("refund") && voyageType.equals("Minibus"))
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), "ERROR: Minibus tickets are not refundable!", true, true);
            return true;
        }
        return false;
    }

    /**
     * Checks if there is already a voyage with the provided ID.
     *
     * @param id The ID to check.
     * @return True if there is an error, false otherwise.
     */
    public static boolean checkVoyage(String id)
    {
        if (Terminal.getVoyageMap().containsKey(Integer.parseInt(id)))
        {
            FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: There is already a voyage with ID of %s!", id), true, true);
            return true;
        }
        return false;
    }

    /**
     * Handles errors related to user commands.
     *
     * @param command The erroneous command.
     */
    public static void userError(String command)
    {
        FileOutput.writeToFile(Terminal.getOutputFile(), String.format("ERROR: Erroneous usage of \"%s\" command!", command), true, true);
    }
}