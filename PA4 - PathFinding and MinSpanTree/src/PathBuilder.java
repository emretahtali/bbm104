import java.util.*;

/**
 * The PathBuilder class is responsible for building a path through
 * all points in a given graph map and writing the path to an output file.
 */
public class PathBuilder
{
    private final SortedMap<String, List<Road>> graphMap;
    private final List<Road> pathList = new ArrayList<>();

    /**
     * Constructor for PathBuilder class.
     *
     * @param graphMap The graph map represented as a SortedMap.
     */
    public PathBuilder(SortedMap<String, List<Road>> graphMap)
    {
        this.graphMap = graphMap;
    }

    /**
     * Builds a path through all points in the graph map and writes it to the output file.
     *
     * @param outputFile The file to write the output to.
     * @return A string representation of the path built.
     */
    public String buildPath (String outputFile)
    {
        Set<String> unvisitedSet = new HashSet<>(graphMap.keySet());
        List<Road> roadList = new ArrayList<>();

        String point = graphMap.firstKey();
        unvisitedSet.remove(point);

        // While there are still unvisited points
        while (!unvisitedSet.isEmpty())
        {
            roadList.addAll(graphMap.get(point));

            // Sort the roads first by length, then by id
            roadList.sort(( r1, r2 ) -> {
                if (r1.getLength() != r2.getLength()) return Integer.compare(r1.getLength(), r2.getLength());
                else return Integer.compare(r1.getId(), r2.getId());
            });

            // Find the next road connecting to an unvisited point
            Road road;
            do { road = roadList.remove(0); }
            while (!unvisitedSet.contains(road.getA()) && !unvisitedSet.contains(road.getB()));

            point = (unvisitedSet.contains(road.getA())) ? road.getA() : road.getB();

            pathList.add(road);
            unvisitedSet.remove(road.getA());
            unvisitedSet.remove(road.getB());
        }

        // Sort the path list first by length, then by id
        pathList.sort(( r1, r2 ) -> {
            if (r1.getLength() != r2.getLength()) return Integer.compare(r1.getLength(), r2.getLength());
            else return Integer.compare(r1.getId(), r2.getId());
        });

        StringBuilder outputTxt = new StringBuilder();
        for (Road path : pathList)
        {
            outputTxt.append(String.format("%s\t%s\t%d\t%d\n", path.getA(), path.getB(), path.getLength(), path.getId()));
        }

        // Write the results to the output file
        FileOutput.writeToFile(outputFile, "Roads of Barely Connected Map is:", true, true);
        FileOutput.writeToFile(outputFile, outputTxt.toString(), true, false);
        return "\n" + outputTxt;
    }
}
