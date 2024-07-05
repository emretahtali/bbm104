import java.util.*;


/**
 * The Input class handles the reading and processing of input files
 * to generate routes, graph maps, and calculate total lengths of roads.
 */
public class Input
{
    private final String inputFile;

    /**
     * Constructor for Input class.
     *
     * @param inputFile The path to the input file.
     */
    public Input (String inputFile)
    {
        this.inputFile = inputFile;
    }

    /**
     * Reads the route from the input file.
     *
     * @return An array of strings representing the route.
     */
    public String[] getRoute()
    {
        return FileInput.readFile(inputFile, true, true)[0].split("\t");
    }

    /**
     * Generates a graph map from the input file.
     *
     * @return A SortedMap where the keys are city names and the values are lists of Road objects.
     */
    public SortedMap<String, List<Road>> getGraphMap()
    {
        String[] lines = FileInput.readFile(inputFile, true, true);
        return generateGraphMap(lines);
    }

    /**
     * Generates a graph map from a given graph text.
     *
     * @param graphText The string containing the graph data.
     * @return A SortedMap where the keys are city names and the values are lists of Road objects.
     */
    public SortedMap<String, List<Road>> getGraphMap(String graphText)
    {
        String[] lines = graphText.split("\n");
        return generateGraphMap(lines);
    }

    /**
     * Helper method to generate a graph map from an array of strings.
     *
     * @param lines An array of strings where each string represents a line in the input file.
     * @return A SortedMap where the keys are city names and the values are lists of Road objects.
     */
    private SortedMap<String, List<Road>> generateGraphMap(String[] lines)
    {
        SortedMap<String, List<Road>> graphMap = new TreeMap<>();

        // Start from the second line, skipping the header
        for (int i = 1; i < lines.length; i++)
        {
            String[] line = lines[i].split("\t");

            // Ensure each city (line[0] and line[1]) has an entry in the graph map
            if (!graphMap.containsKey(line[0])) graphMap.put(line[0], new ArrayList<>());
            if (!graphMap.containsKey(line[1])) graphMap.put(line[1], new ArrayList<>());

            // Add road connections to both cities' lists
            graphMap.get(line[0]).add(new Road(line[0], line[1], line[1], 0, line[3], line[2], 0));
            graphMap.get(line[1]).add(new Road(line[0], line[1], line[0], 0, line[3], line[2], 0));
        }

        return graphMap;
    }

    /**
     * Calculates the total length of roads from the input file.
     *
     * @return The total length of roads.
     */
    public float getTotalLength()
    {
        String[] lines = FileInput.readFile(inputFile, true, true);
        return calculateTotalLength(lines);
    }

    /**
     * Calculates the total length of roads from a given graph text.
     *
     * @param graphText The string containing the graph data.
     * @return The total length of roads.
     */
    public float getTotalLength(String graphText)
    {
        String[] lines = graphText.split("\n");
        return calculateTotalLength(lines);
    }

    /**
     * Helper method to calculate the total length of roads from an array of strings.
     *
     * @param lines An array of strings where each string represents a line in the input file.
     * @return The total length of roads.
     */
    private float calculateTotalLength ( String[] lines )
    {
        float sum = 0f;

        // Sum the lengths of all roads, starting from the second line (skipping the header)
        for (int i = 1; i < lines.length; i++) { sum += Float.parseFloat(lines[i].split("\t")[2]); }
        return sum;
    }
}
