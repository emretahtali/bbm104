import java.util.HashMap;

public class BNF
{
    /**
     * Creates a BNF mapping from the given file.
     *
     * @param fileName The name of the file containing BNF mappings.
     * @return A HashMap representing the BNF mapping.
     */
    public static HashMap<Character, String> createBnfMap(String fileName)
    {
        // i used hashmaps because the backus-naur form is a hashmap structure in the essence, there are key letters holding strings as value.
        HashMap<Character, String> bnfMap = new HashMap<Character, String>();

        String[] inputFile = FileInput.readFile(fileName, true, true);
        for (String line : inputFile)
        {
            String[] items = line.split("->");
            bnfMap.put(items[0].charAt(0), items[1]);
        }
        return bnfMap;
    }

    /**
     * Retrieves the BNF value associated with the given key from the BNF mapping.
     *
     * @param bnfMap The HashMap holding the BNF mapping.
     * @param key    The key for which to retrieve the BNF value.
     * @return The BNF value associated with the given key.
     */
    public static String getBnfValue(HashMap<Character, String> bnfMap, char key)
    {
        if (bnfMap.get(key).charAt(0) == '(') return bnfMap.get(key);

        StringBuilder str = new StringBuilder();
        for (char ch : bnfMap.get(key).toCharArray())
        {
            if (bnfMap.containsKey(ch)) str.append(getBnfValue(bnfMap, ch));
            else str.append(ch);
        }

        String finalStr = "(" + str.toString() + ")";
        bnfMap.put(key, finalStr);
        return finalStr;
    }

    /**
     * The main method of the program.
     *
     * @param args Command line arguments. args[0] should contain the input file name, and args[1] should contain the output file name.
     */
    public static void main ( String[] args )
    {
        HashMap<Character, String> bnfMap = createBnfMap(args[0]);
        FileOutput.writeToFile(args[1], getBnfValue(bnfMap, 'S'), false, false);
    }
}