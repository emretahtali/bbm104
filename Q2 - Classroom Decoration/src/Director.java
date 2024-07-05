import java.util.HashMap;

public class Director
{
    private static final HashMap<String, Classroom> _classrooms = new HashMap<String, Classroom>();
    private static final HashMap<String, Decoration> _decorations = new HashMap<String, Decoration>();
    private static Builder _builder;
    private static String _outputFile;
    private static int _totalCost = 0;

    /**
     * Reads the definitions of classrooms and decorations from the input file.
     *
     * @param inputFile  The input file containing definitions.
     * @param outputFile The output file to write logs.
     */
    public static void readDefinitions(String inputFile, String outputFile)
    {
        _outputFile = outputFile;

        String[] lines = FileInput.readFile(inputFile, true, true);

        for (String l : lines)
        {
            String[] line = l.split("\t");

            if (line[0].equals("CLASSROOM")) addClassroom(line[1], new Classroom(line[1], line[2], line[3], line[4], line[5]));
            else if (line[0].equals("DECORATION"))
            {
                if (line.length == 4) addDecoration(line[1], new Decoration(line[1], line[2], line[3]));
                if (line.length == 5) addDecoration(line[1], new Decoration(line[1], line[2], line[3], line[4]));
            }
        }
    }

    /**
     * Reads the definitions of classrooms and decorations from the input file.
     *
     * @param inputFile  The input file containing definitions.
     * @param outputFile The output file to write logs.
     */
    public static void buildRooms(String inputFile)
    {
        String[] lines = FileInput.readFile(inputFile, true, true);

        for (String l : lines)
        {
            String[] line = l.split("\t");
            make(line);
        }

        FileOutput.writeToFile(getOutputFile(), String.format("Total price is: %dTL.", getTotalCost()), true, false);
    }

    /**
     * Constructs a room by applying decorations to the specified classroom.
     *
     * @param line An array containing the details of the room to be constructed.
     *             The array should have three elements: [classroomName, wallDecorationName, floorDecorationName].
     */
    private static void make(String[] line)
    {
        Classroom classroom = getClassrooms().get(line[0]);
        Decoration wallDeco = getDecorations().get(line[1]);
        Decoration floorDeco = getDecorations().get(line[2]);

        // determine the builder based on the type of classroom
        if (classroom.getType().equals("Circle")) setBuilder(new CircleRoomBuilder());
        else setBuilder(new RectRoomBuilder());

        // choose the appropriate method to apply the wall decoration
        switch (wallDeco.getType())
        {
            case "Paint": default: getBuilder().usePaint(classroom, wallDeco); break;
            case "Tile": getBuilder().useTiles(classroom, wallDeco, "wall"); break;
            case "Wallpaper": ((RectRoomBuilder) getBuilder()).useWallpaper(classroom, wallDeco); break;
        }

        // apply the floor decoration
        getBuilder().useTiles(classroom, floorDeco, "floor");
    }

    /**
     * @return The HashMap containing classrooms.
     */
    public static HashMap<String, Classroom> getClassrooms ()
    {
        return _classrooms;
    }

    /**
     * Adds a classroom to the classrooms HashMap.
     *
     * @param name       The name of the classroom.
     * @param classroom  The classroom object to add.
     */
    public static void addClassroom ( String name, Classroom classroom )
    {
        _classrooms.put(name, classroom);
    }

    /**
     * @return The HashMap containing decorations.
     */
    public static HashMap<String, Decoration> getDecorations ()
    {
        return _decorations;
    }

    /**
     * Adds a decoration to the decorations HashMap.
     *
     * @param name        The name of the decoration.
     * @param decoration  The decoration object to add.
     */
    public static void addDecoration ( String name, Decoration decoration )
    {
        _decorations.put(name, decoration);
    }

    /**
     * @return The current builder object.
     */
    public static Builder getBuilder ()
    {
        return _builder;
    }

    /**
     * @param builder The builder object to set.
     */
    public static void setBuilder ( Builder builder )
    {
        _builder = builder;
    }

    /**
     * @return The output file name.
     */
    public static String getOutputFile()
    {
        return _outputFile;
    }

    /**
     * @return The total cost.
     */
    public static int getTotalCost()
    {
        return _totalCost;
    }

    /**
     * Adds a cost to the total cost.
     *
     * @param cost The cost to add.
     */
    public static void addCost(int cost)
    {
        _totalCost += cost;
    }
}