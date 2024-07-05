public class Main
{
    public static void main ( String[] args )
    {
        FileOutput.writeToFile(args[1], "", false, false);

        Inventory inventory = new Inventory();
        inventory.run(args);
    }
}