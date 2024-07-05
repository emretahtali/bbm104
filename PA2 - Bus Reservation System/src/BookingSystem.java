import java.util.Locale;

public class BookingSystem
{
    public static void main ( String[] args )
    {
        // set the default locale to US in order to print the float numbers with dot instead of comma
        Locale.setDefault(Locale.US);

        // set the output file for the terminal and initialize it with an empty content
        Terminal.setOutputFile(args[1]);
        FileOutput.writeToFile(args[1], "", false, false);

        // iterate over each input command
        String[] input = FileInput.readFile(args[0], true, true);
        for (int i = 0; i < input.length; i++)
        {
            String line = input[i];

            // execute each command except the last one
            if (i != input.length - 1) Terminal.runCommand(line, true);
            else
            {
                // for the last command, if it's a Z_REPORT command, execute it without printing
                if (line.equals("Z_REPORT")) Terminal.runCommand(line, false);
                else
                {
                    // for other commands, execute and print, then generate a Z_REPORT
                    Terminal.runCommand(line, true);
                    Terminal.zReport(new String[] {"Z_REPORT"}, false);
                }
            }
        }
    }
}