public class RectRoomBuilder implements Builder
{
    private int _wallCost = 0;

    /**
     * Applies tiles for flooring or walls in the rectangular classroom.
     *
     * @param classroom   The classroom object for which tiles are being applied.
     * @param decoration  The decoration object representing the tiles.
     * @param target      The target area where the tiles are applied (e.g., "floor" or "wall").
     */
    public void useTiles(Classroom classroom, Decoration decoration, String target)
    {
        if (target.equals("floor"))
        {
            // calculate the quantity of tiles required for flooring and the associated cost
            int tileQty = (int) Math.ceil(classroom.getWidth() * classroom.getLength() / decoration.getArea());
            int cost = _wallCost + tileQty * decoration.getPrice();

            // add the cost to the total cost and log the details of tile usage for flooring
            Director.addCost(cost);
            FileOutput.writeToFile(Director.getOutputFile(), String.format("and used %d Tiles for flooring, these costed %dTL.", tileQty, (int) cost), true, true);
        }
        else
        {
            // calculate the quantity of tiles required for walls and the associated cost
            int tileQty = (int) Math.ceil((2 * classroom.getHeight() * (classroom.getWidth() + classroom.getLength())) / decoration.getArea());
            _wallCost = tileQty * decoration.getPrice();

            // log the details of tile usage for walls
            FileOutput.writeToFile(Director.getOutputFile(), String.format("Classroom %s used %d Tiles for walls ", classroom.getName(), tileQty), true, false);
        }
    }

    /**
     * Applies paint for the walls in the rectangular classroom.
     *
     * @param classroom   The classroom object for which paint is being applied.
     * @param decoration  The decoration object representing the paint.
     */
    public void usePaint(Classroom classroom, Decoration decoration)
    {
        // calculate the area of walls and the associated cost
        int area = (int) (2 * classroom.getHeight() * (classroom.getWidth() + classroom.getLength()));
        _wallCost = area * decoration.getPrice();

        // log the details of paint usage for walls
        FileOutput.writeToFile(Director.getOutputFile(), String.format("Classroom %s used %dm2 of Paint for walls ", classroom.getName(), area), true, false);
    }

    /**
     * Applies wallpaper for the walls in the rectangular classroom.
     *
     * @param classroom   The classroom object for which wallpaper is being applied.
     * @param decoration  The decoration object representing the wallpaper.
     */
    public void useWallpaper(Classroom classroom, Decoration decoration)
    {
        // calculate the area of walls and the associated cost
        int area = (int) (2 * classroom.getHeight() * (classroom.getWidth() + classroom.getLength()));
        _wallCost = area * decoration.getPrice();

        // log the details of wallpaper usage for walls
        FileOutput.writeToFile(Director.getOutputFile(), String.format("Classroom %s used %dm2 of Wallpaper for walls ", classroom.getName(), area), true, false);
    }
}