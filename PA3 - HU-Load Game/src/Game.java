import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

import java.util.HashMap;

/**
 * Represents the main game class that extends JavaFX Application.
 */
public class Game extends Application
{
    /** Height of the sky portion of the canvas. */
    private static final int SKY_HEIGHT = 6;

    /** Size of each block on the canvas. */
    private static final int BLOCK_SIZE = 50;

    /** Width of the canvas. */
    private static final int CANVAS_WIDTH = 16 * BLOCK_SIZE;

    /** Height of the canvas. */
    private static final int CANVAS_HEIGHT = (73 + SKY_HEIGHT) * BLOCK_SIZE;

    /** Height of the scene. */
    private static final int SCENE_HEIGHT = 15 * BLOCK_SIZE;

    /** 2D array representing the grid of blocks in the game. */
    private static final Block[][] blockGrid = new Block[CANVAS_HEIGHT / BLOCK_SIZE][CANVAS_WIDTH / BLOCK_SIZE];

    /** Flags indicating the input keys' state. */
    private static final HashMap<String, Integer> inputKeys = new HashMap<String, Integer>();

    /** Enumeration representing the game state. */
    public enum GameState {playing, red, green}

    /** Current state of the game. */
    private static GameState gameState = GameState.playing;

    /** Offset of the camera. */
    private double camOffset = 0;

    /**
     * Entry point to launch the game.
     * @param args Command-line arguments
     */
    public static void run(String[] args)
    {
        Application.launch(args);
    }

    /**
     * Initializes and starts the game.
     * @param stage The primary stage for the application
     */
    @Override public void start(Stage stage)
    {
        // create the canvas, stackpane and scene
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, CANVAS_WIDTH, SCENE_HEIGHT);

        // create the player and fill the block grid
        Player player = new Player();
        fillGrid();

        // update the game
        eventListeners(scene);
        update(gc, player, root);

        // show the scene
        stage.setScene(scene);
        stage.setTitle("HU-Load");
        stage.show();
    }

    /**
     * Updates the game state and graphics.
     * @param gc Graphics context for rendering
     * @param player The player object
     * @param root The root StackPane
     */
    private void update(GraphicsContext gc, Player player, StackPane root)
    {
        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // check if the game has ended
                if (getGameState() != GameState.playing)
                {
                    drawEndScreen(player, gc);
                    return;
                }

                // update the player and the camera
                player.update();
                handleCamera(player, root);

                // clear the canvas
                gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                // draw the background
                gc.setFill(Color.MIDNIGHTBLUE);
                gc.fillRect(0, 0, CANVAS_WIDTH, BLOCK_SIZE * SKY_HEIGHT);
                gc.setFill(Color.SADDLEBROWN);
                gc.fillRect(0, BLOCK_SIZE * (SKY_HEIGHT - 1) + 3, CANVAS_WIDTH, CANVAS_HEIGHT);

                // draw the blocks
                drawBlocks(gc);

                // draw the player
                int playerOffsetX = 2;
                int playerOffsetY = 2;

                if (player.getSprite() == Assets.getPlayer(Assets.PlayerAsset.up)) playerOffsetY = 5;
                else if (player.getSprite() == Assets.getPlayer(Assets.PlayerAsset.down)) playerOffsetY = 8;
                else if (player.getSprite() == Assets.getPlayer(Assets.PlayerAsset.right)) playerOffsetX = 22;

                gc.drawImage(player.getSprite(), player.getX() - player.getWidth() / 2.0 + playerOffsetX, player.getY() - player.getHeight() / 2.0 + playerOffsetY);

                // draw the gui
                drawGui(player, gc);
            }
        };
        timer.start();
    }

    /**
     * Draws the end screen based on the game state.
     * @param player The player object
     * @param gc Graphics context for rendering
     */
    private void drawEndScreen ( Player player, GraphicsContext gc )
    {
        if (getGameState() == GameState.green)
        {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(0, camOffset, CANVAS_WIDTH, SCENE_HEIGHT);

            gc.setFill(Color.WHITE);
            gc.setFont(new Font(50));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("GAME OVER", CANVAS_WIDTH / 2.0, SCENE_HEIGHT / 2.0 - 35 + camOffset);
            gc.fillText("Collected Money: " + player.getMoney(), CANVAS_WIDTH / 2.0, SCENE_HEIGHT / 2.0 + 35 + camOffset);
        }

        else
        {
            gc.setFill(Color.RED);
            gc.fillRect(0, camOffset, CANVAS_WIDTH, SCENE_HEIGHT);

            gc.setFill(Color.WHITE);
            gc.setFont(new Font(50));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("GAME OVER", CANVAS_WIDTH / 2.0, SCENE_HEIGHT / 2.0 + camOffset);
        }
    }

    /**
     * Handles the camera movement.
     * @param player The player object
     * @param root The root StackPane
     */
    private void handleCamera ( Player player, StackPane root )
    {
        camOffset = camOffset + .03 * (player.getCenterY() - SCENE_HEIGHT / 2.0 - camOffset);

        int camMargin = 200;
        int playerOnCenterY = player.getCenterY() - SCENE_HEIGHT / 2;
        camOffset = Math.max(camOffset, 0);
        camOffset = Math.min(camOffset, CANVAS_HEIGHT - SCENE_HEIGHT);
        root.setLayoutY(CANVAS_HEIGHT / 2.0 - SCENE_HEIGHT / 2.0 - camOffset);
    }

    /**
     * Draws the blocks within the visible region.
     * @param gc Graphics context for rendering
     */
    private void drawBlocks ( GraphicsContext gc )
    {
        int blockMargin = 50;
        int topRow = (int) Math.round((camOffset - blockMargin) / (double) BLOCK_SIZE);
        int bottomRow = (int) Math.round((camOffset + SCENE_HEIGHT + blockMargin) / (double) BLOCK_SIZE);
        topRow = Math.max(topRow, 0);
        bottomRow = Math.min(bottomRow, getBlockGrid().length - 1);

        for (int r = topRow; r < bottomRow + 1; r++)
        {
            for (int c = 0; c < getBlockGrid()[0].length; c++)
            {
                Block block = getBlockGrid()[r][c];
                if (block == null) continue;

                gc.drawImage(block.getSprite(), c * BLOCK_SIZE, r * BLOCK_SIZE);
            }
        }
    }

    /**
     * Draws the GUI elements.
     * @param player The player object
     * @param gc Graphics context for rendering
     */
    private void drawGui ( Player player, GraphicsContext gc )
    {
        int guiBGMargin = 10;

        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(.7);
        gc.fillRoundRect(guiBGMargin, guiBGMargin + camOffset, 220, 105, 10, 10);
        gc.setGlobalAlpha(1.0);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(25));
        gc.setTextAlign(TextAlignment.LEFT);

        gc.fillText(String.format("fuel: %.3f", player.getFuel()), 10 + guiBGMargin, 30 + guiBGMargin + camOffset);
        gc.fillText("haul: " + player.getHaul(), 10 + guiBGMargin, 60 + guiBGMargin + camOffset);
        gc.fillText("money: " + player.getMoney(), 10 + guiBGMargin, 90 + guiBGMargin + camOffset);
    }

    /**
     * Fills the grid with block objects representing the game environment.
     */
    private void fillGrid()
    {
        for (int c = 0; c < 16; c++) { getBlockGrid()[SKY_HEIGHT - 1][c] = new Block("grass"); }
        for (int r = SKY_HEIGHT; r < getBlockGrid().length; r++)
        {
            for (int c = 1; c < 15; c++) { getBlockGrid()[r][c] = new Block(r); }
        }
        for (int c = 1; c < 15; c++) { getBlockGrid()[getBlockGrid().length - 1][c] = new Block("boulder"); }
        for (int r = SKY_HEIGHT; r < getBlockGrid().length; r++)
        {
            getBlockGrid()[r][0] = new Block("boulder");
            getBlockGrid()[r][15] = new Block("boulder");
        }
    }

    /**
     * Sets up event listeners for keyboard input.
     * @param scene The scene where events will be listened to
     */
    private void eventListeners(Scene scene)
    {
        scene.setOnKeyPressed(event -> {
            // set the appropriate flag to 1 when a key is pressed
            switch (event.getCode()) {
                case UP: inputKeys.put("up", 1); break;
                case LEFT: inputKeys.put("left", 1); break;
                case DOWN: inputKeys.put("down", 1); break;
                case RIGHT: inputKeys.put("right", 1); break;
            }
        });

        scene.setOnKeyReleased(event -> {
            // set the appropriate flag to 0 when a key is released
            switch (event.getCode()) {
                case UP: inputKeys.put("up", 0); break;
                case LEFT: inputKeys.put("left", 0); break;
                case DOWN: inputKeys.put("down", 0); break;
                case RIGHT: inputKeys.put("right", 0); break;
            }
        });
    }

    public static Block[][] getBlockGrid ()
    {
        return blockGrid;
    }

    public static int getInput (String key)
    {
        if (!inputKeys.containsKey(key)) return 0;
        return inputKeys.get(key);
    }

    public static int getBlockSize ()
    {
        return BLOCK_SIZE;
    }

    public static int getCanvasWidth ()
    {
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight ()
    {
        return CANVAS_HEIGHT;
    }
    public static int getSkyHeight ()
    {
        return SKY_HEIGHT;
    }

    public static GameState getGameState ()
    {
        return gameState;
    }

    public static void setGameState ( GameState gameState )
    {
        Game.gameState = gameState;
    }
}