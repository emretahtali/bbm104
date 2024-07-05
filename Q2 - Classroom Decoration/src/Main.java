public class Main
{
    public static void main ( String[] args )
    {
        FileOutput.writeToFile(args[2], "", false, false);

        Director.readDefinitions(args[0], args[2]);
        Director.buildRooms(args[1]);
    }
}