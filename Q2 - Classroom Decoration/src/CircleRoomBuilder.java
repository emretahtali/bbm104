public class CircleRoomBuilder implements Builder
{
    private double _wallCost = 0.0;

    /**
     * Applies tiles for flooring in the circular classroom.
     *
     * @param classroom   The classroom object for which tiles are being applied.
     * @param decoration  The decoration object representing the tiles.
     * @param target      The target area where the tiles are applied (e.g., "floor").
     */
    public void useTiles(Classroom classroom, Decoration decoration, String target)
    {
        if (target.equals("floor"))
        {
            // calculate the quantity of tiles required and the associated cost
            int tileQty = (int) Math.ceil(Math.pow(classroom.getWidth() / 2, 2) * Math.PI / decoration.getArea());
            int cost = (int) Math.ceil(_wallCost + tileQty * decoration.getPrice());

            // add the cost to the total cost and log the details of tile usage for flooring
            Director.addCost(cost);
            FileOutput.writeToFile(Director.getOutputFile(), String.format("and used %d Tiles for flooring, these costed %dTL.", tileQty, cost), true, true);
        }
    }

    /**
     * Applies paint for the walls in the circular classroom.
     *
     * @param classroom   The classroom object for which paint is being applied.
     * @param decoration  The decoration object representing the paint.
     */
    public void usePaint(Classroom classroom, Decoration decoration)
    {
        // calculate the area of walls and the associated cost
        double area = (classroom.getWidth() * Math.PI * classroom.getHeight());
        _wallCost = area * decoration.getPrice();

        // log the details of paint usage
        FileOutput.writeToFile(Director.getOutputFile(), String.format("Classroom %s used %dm2 of Paint for walls ", classroom.getName(), (int) Math.ceil(area)), true, false);
    }
}