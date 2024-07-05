import java.util.List;
import java.util.ArrayList;

public class Main
{
    public static void main ( String[] args )
    {
        String[] lines = FileInput.readFile(args[0], true, false);

        // creating a list of players
        List<Player> playerList = new ArrayList<Player>();
        for (String name : lines[1].split(","))
        {
            Player player = new Player(name);
            playerList.add(player);
        }

        String outputStr = game(playerList, lines);

        FileOutput.writeToFile(args[1], outputStr, false, false);
    }

    public static String game(List<Player> playerList, String[] lines)
    {
        String outputStr = "";

        int currentPlayer = 0;

        // going through each line in the input file
        for (int i = 2; i < lines.length; i++)
        {
            String[] line = lines[i].split("-");
            int dice1 = Integer.parseInt(line[0]);
            int dice2 = Integer.parseInt(line[1]);
            int diceSum = dice1 + dice2;

            Player player = playerList.get(currentPlayer);

            //the condition where both dices are 0
            if (diceSum == 0)
            {
                outputStr += String.format("%s skipped the turn and %s’s score is %d.\n", player.getName(), player.getName(), player.getPoints());
            }

            // the condition where both dices are 1
            else if (diceSum == 2)
            {
                player.deletePoints();
                playerList.remove(currentPlayer);
                currentPlayer -= 1;

                outputStr += String.format("%s threw %s. Game over %s!\n", player.getName(), lines[i], player.getName());
            }

            // the condition where one of the dices is 1
            else if (dice1 == 1 || dice2 == 1)
            {
                outputStr += String.format("%s threw %s and %s’s score is %d.\n", player.getName(), lines[i], player.getName(), player.getPoints());
            }

            // the condition where the dices aren't 1
            else
            {
                player.addPoints(diceSum);

                outputStr += String.format("%s threw %s and %s’s score is %d.\n", player.getName(), lines[i], player.getName(), player.getPoints());
            }

            // moving to the next player
            currentPlayer += 1;
            if (currentPlayer == playerList.size()) currentPlayer = 0;
        }

        // winner message
        Player player = playerList.get(0);
        outputStr += String.format("%s is the winner of the game with the score of %d. Congratulations %s!", player.getName(), player.getPoints(), player.getName());

        return outputStr;
    }
}