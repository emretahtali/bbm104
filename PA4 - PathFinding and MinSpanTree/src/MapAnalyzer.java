import java.util.List;
import java.util.Locale;
import java.util.SortedMap;

public class MapAnalyzer
{
    public static void main ( String[] args )
    {
        Locale.setDefault(Locale.US);
        FileOutput.writeToFile(args[1], "", false, false);

        // Reading the input
        Input input = new Input(args[0]);
        String[] route = input.getRoute();
        SortedMap<String, List<Road>> graphMap = input.getGraphMap();

        // Finding the shortest path
        PathFinder pathFinder = new PathFinder(route, graphMap);
        float length1 = pathFinder.findPath(args[1], false);

        // Building a new path
        PathBuilder pathBuilder = new PathBuilder(graphMap);
        String newPath = pathBuilder.buildPath(args[1]);

        // Finding the shortest path in barely connected map
        SortedMap<String, List<Road>> barelyGraphMap = input.getGraphMap(newPath);
        PathFinder barelyPathFinder = new PathFinder(route, barelyGraphMap);
        float length2 = barelyPathFinder.findPath(args[1], true);

        // Analysis
        FileOutput.writeToFile(args[1], "Analysis:", true, true);
        FileOutput.writeToFile(args[1], String.format("Ratio of Construction Material Usage Between Barely Connected " +
                "and Original Map: %.2f", input.getTotalLength(newPath) / input.getTotalLength()), true, true);
        FileOutput.writeToFile(args[1], String.format("Ratio of Fastest Route Between Barely Connected and Original " +
                "Map: %.2f", length2 / length1), true, false);
    }
}