import java.util.*;

/**
 * The PathFinder class is responsible for finding the shortest path
 * between two points in a given graph map.
 */
public class PathFinder
{
    private final String[] route;
    private final SortedMap<String, List<Road>> graphMap;

    /**
     * Constructor for PathFinder class.
     *
     * @param route    The route represented as an array of strings.
     * @param graphMap The graph map represented as a SortedMap.
     */
    public PathFinder(String[] route, SortedMap<String, List<Road>> graphMap)
    {
        this.route = route;
        this.graphMap = graphMap;
    }

    /**
     * Finds the shortest path between the start and end points of the route.
     *
     * @param outputFile         The file to write the output to.
     * @param barelyConnectedMap A boolean indicating if the map is barely connected.
     * @return The distance of the shortest path found.
     */
    public int findPath(String outputFile, boolean barelyConnectedMap)
    {
        List<Road> roadList = new ArrayList<>();
        Map<String, Road> roadMap = new HashMap<>();
        Set<String> visitSet = new HashSet<>();

        // Initialize with the starting point of the route
        roadMap.put(route[0], new Road(route[0], route[0], route[0], 0, "0", "0", 0));

        int iteration = 0;
        String currentLoc = route[0];

        // While the destination is not reached
        while (!currentLoc.equals(route[1]))
        {
            visitSet.add(currentLoc);
            int startDistance = roadMap.get(currentLoc).getDistance();

            // Update distances and priorities for neighboring roads
            for (Road road : graphMap.get(currentLoc))
            {
                road.setDistance(startDistance + road.getLength());
                road.setPriority(iteration);
                roadList.add(road);
            }

            // Sort roads by distance, priority, and id
            roadList.sort(( r1, r2 ) -> {
                if (r1.getDistance() != r2.getDistance()) return Integer.compare(r1.getDistance(), r2.getDistance());
                else if (r1.getPriority() != r2.getPriority()) return Integer.compare(r1.getPriority(), r2.getPriority());
                else return Integer.compare(r1.getId(), r2.getId());
            });

            // Find the next unvisited road with the shortest distance
            while (visitSet.contains(roadList.get(0).getDestination())) roadList.remove(0);

            Road shortestRoad = roadList.remove(0);
            roadMap.put(shortestRoad.getDestination(), shortestRoad);
            currentLoc = shortestRoad.getDestination();
            iteration ++;
        }

        // Write the results to the output file
        String addedText = (barelyConnectedMap) ? " on Barely Connected Map" : "";
        FileOutput.writeToFile(outputFile, String.format("Fastest Route from %s to %s%s (%d KM):", route[0], route[1],
                addedText, roadMap.get(route[1]).getDistance()), true, true);

        Stack<String> routeStack = new Stack<>();

        // Trace back the path from destination to start
        String point = route[1];
        while (!point.equals(route[0]))
        {
            Road road = roadMap.get(point);
            routeStack.add(String.format("%s\t%s\t%d\t%d", road.getA(), road.getB(), road.getLength(), road.getId()));
            point = (road.getB().equals(road.getDestination())) ? road.getA() : road.getB();
        }

        // Write the path to the output file
        while (!routeStack.isEmpty()) FileOutput.writeToFile(outputFile, routeStack.pop(), true, true);

        // Return the shortest distance from start to end
        return roadMap.get(route[1]).getDistance();
    }
}