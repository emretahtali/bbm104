import javafx.scene.image.Image;

/**
 * Represents the player in the game.
 */
public class Player
{
    /** Width of the player sprite. */
    private final int PLAYER_WIDTH = 47;

    /** Height of the player sprite. */
    private final int PLAYER_HEIGHT = 42;

    /** Image representing the player sprite. */
    private Image sprite = Assets.getPlayer(Assets.PlayerAsset.right);

    /** X-coordinate of the player. */
    private int x = 350;

    /** Y-coordinate of the player. */
    private int y = Game.getBlockSize() * (Game.getSkyHeight() - 1) - PLAYER_HEIGHT;

    /** Horizontal speed of the player. */
    private int hSpeed = 0;

    /** Vertical speed of the player. */
    private double vSpeed = 0;

    /** Flag indicating whether the player is on the ground. */
    private boolean onGround = false;

    /** Flag indicating whether there is a block on the right side of the player. */
    private boolean blockOnRight = false;

    /** Flag indicating whether there is a block on the left side of the player. */
    private boolean blockOnLeft = false;

    /** Position of the block targeted for drilling. */
    private int[] drillPos = {0, 0};

    /** Enumeration representing the state of the drill. */
    private enum Drill { off, right, left, down }

    /** Current state of the drill. */
    private Drill drillState = Drill.off;

    /** Last direction of movement. */
    private int lastDir = 1;

    /** Amount of fuel remaining. */
    private double fuel = 10000.0;

    /** Weight of material hauled by the player. */
    private int haul = 0;

    /** Amount of money collected by the player. */
    private int money = 0;

    /**
     * Updates the player's state.
     */
    public void update ()
    {
        // constants
        final int moveSpeed = 3;
        final double gravity = .1;

        // if not drilling
        if (drillState == Drill.off)
        {
            // decrease fuel
            if (Game.getInput("up") + Game.getInput("left") + Game.getInput("right") == 0) setFuel(getFuel() - .001);
            else setFuel(getFuel() - 1.0);

            if (getFuel() <= 0.0) Game.setGameState(Game.GameState.green);

            // movements
            hSpeed = (Game.getInput("right") - Game.getInput("left")) * moveSpeed;
            if (Game.getInput("up") == 0) vSpeed += gravity;
            else if (vSpeed >= gravity * -50) vSpeed -= gravity * .75;

            handleCollisions();

            setX(getX() + hSpeed);
            setY(getY() + (int) vSpeed);
            setX(Math.max(0, Math.min(getX(), Game.getCanvasWidth() - PLAYER_WIDTH)));
            setY(Math.max(0, Math.min(getY(), Game.getCanvasHeight() - PLAYER_HEIGHT)));

            // drilling action
            breakBlocks();

            // change the sprite
            determineSprite(false);
        }
        else drillAnimation();
    }

    /**
     * Attempts to break blocks around the player based on keyboard input.
     */
    private void breakBlocks()
    {
        final int blockSize = Game.getBlockSize();

        if (onGround)
        {
            // check for drilling request from keyboard
            int drillX = 0;
            int drillY = 0;

            if (Game.getInput("right") == 1 && blockOnRight)
            {
                drillX = getCenterX() / blockSize + 1;
                drillY = getCenterY() / blockSize;

                drillState = Drill.right;
                drillPos = new int[] {drillX * blockSize, drillY * blockSize};
            }
            else if (Game.getInput("left") == 1 && blockOnLeft)
            {
                drillX = getCenterX() / blockSize - 1;
                drillY = getCenterY() / blockSize;

                drillState = Drill.left;
                drillPos = new int[] {drillX * blockSize, drillY * blockSize};
            }
            else if (Game.getInput("down") == 1)
            {
                drillX = getCenterX() / blockSize;
                drillY = getCenterY() / blockSize + 1;

                drillState = Drill.down;
                drillPos = new int[] {drillX * blockSize, drillY * blockSize};
            }

            // drilling action
            if (drillState != Drill.off)
            {
                Block block = Game.getBlockGrid()[drillY][drillX];

                if (block.isDrillable())
                {
                    setFuel(getFuel() - 100.0);
                    setHaul(getHaul() + block.getWeight());
                    setMoney(getMoney() + block.getWorth());

                    if (block.isLethal()) Game.setGameState(Game.GameState.red);
                    else if (getFuel() <= 0.0) Game.setGameState(Game.GameState.green);
                }
                else drillState = Drill.off;
            }
        }
    }

    /**
     * Animates the drilling action.
     */
    private void drillAnimation()
    {
        int[] spriteOffset = {1, 7};

        // move the player towards the target block's position
        setX(getX() + 2 * (int)(Math.signum(drillPos[0] + spriteOffset[0] - getX())));
        setY(getY() + 2 * (int)(Math.signum(drillPos[1] + spriteOffset[1] - getY())));

        // break the block when the player has reached the target block's position
        if (Math.abs(drillPos[0] + spriteOffset[0] - getX()) <= 1 && Math.abs(drillPos[1] + spriteOffset[1] - getY()) <= 1)
        {
            setX(drillPos[0] + spriteOffset[0]);
            setY(drillPos[1] + spriteOffset[1]);

            drillState = Drill.off;
            Game.getBlockGrid()[drillPos[1] / Game.getBlockSize()][drillPos[0] / Game.getBlockSize()] = null;
        }

        // change the sprite
        determineSprite(true);
    }

    /**
     * Determines the appropriate sprite based on player state.
     * @param drilling Flag indicating which direction the player is drilling
     */
    private void determineSprite(boolean drilling)
    {
        if (drilling)
        {
            // drilling sprites
            switch (drillState)
            {
                case right:
                {
                    setSprite(Assets.getPlayer(Assets.PlayerAsset.right));
                    lastDir = 1;
                    break;
                }
                case left:
                {
                    setSprite(Assets.getPlayer(Assets.PlayerAsset.left));
                    lastDir = -1;
                    break;
                }
                case down:
                {
                    setSprite(Assets.getPlayer(Assets.PlayerAsset.down));
                    lastDir = 0;
                    break;
                }
            }
        }
        else
        {
            // free movement sprites
            if (Math.signum(hSpeed) > 0) lastDir = 1;
            else if (Math.signum(hSpeed) < 0) lastDir = -1;

            if (!onGround) setSprite(Assets.getPlayer(Assets.PlayerAsset.up));
            else
            {
                if (lastDir == 1) setSprite(Assets.getPlayer(Assets.PlayerAsset.right));
                else if (lastDir == -1) setSprite(Assets.getPlayer(Assets.PlayerAsset.left));
                else setSprite(Assets.getPlayer(Assets.PlayerAsset.down));
            }
        }
    }

    /**
     * Handles collision detection and response.
     */
    private void handleCollisions()
    {
        // calculate the grid positions player is colliding with
        int vSpeedSign = (int)Math.signum(vSpeed);
        int hSpeedSign = (int)Math.signum(hSpeed);

        int boundingBoxY = getCenterY() + (getHeight() / 2 * vSpeedSign);
        int boundingBoxX = getCenterX() + (getWidth() / 2 * hSpeedSign);

        int collisionWidth = 10;
        int collisionHeight = 20;
        int rowIndex1 = (getCenterY() - collisionHeight) / Game.getBlockSize();
        int rowIndex2 = (getCenterY() + collisionHeight) / Game.getBlockSize();
        int colIndex1 = (getCenterX() - collisionWidth) / Game.getBlockSize();
        int colIndex2 = (getCenterX() + collisionWidth) / Game.getBlockSize();

        int nextRowIndex = (boundingBoxY + (int)vSpeed) / Game.getBlockSize();
        int nextColIndex = (boundingBoxX + hSpeed) / Game.getBlockSize();

        // horizontal collision
        if (nextColIndex >= 0 && nextColIndex < Game.getBlockGrid()[0].length)
        {
            if (Game.getBlockGrid()[rowIndex1][nextColIndex] != null || Game.getBlockGrid()[rowIndex2][nextColIndex] != null)
            {
                int dummyX = boundingBoxX + hSpeedSign;
                nextColIndex = dummyX / Game.getBlockSize();
                while (Game.getBlockGrid()[rowIndex1][nextColIndex] == null && Game.getBlockGrid()[rowIndex2][nextColIndex] == null)
                {
                    setX(getX() + hSpeedSign);
                    dummyX += hSpeedSign;
                    nextColIndex = dummyX / Game.getBlockSize();
                }
                hSpeed = 0;
            }
        }

        // vertical collision
        if (nextRowIndex >= 0 && nextRowIndex < Game.getBlockGrid().length)
        {
            if (Game.getBlockGrid()[nextRowIndex][colIndex1] != null || Game.getBlockGrid()[nextRowIndex][colIndex2] != null)
            {
                int dummyY = boundingBoxY + vSpeedSign;
                nextRowIndex = dummyY / Game.getBlockSize();
                while (Game.getBlockGrid()[nextRowIndex][colIndex1] == null && Game.getBlockGrid()[nextRowIndex][colIndex2] == null)
                {
                    setY(getY() + vSpeedSign);
                    dummyY += vSpeedSign;
                    nextRowIndex = dummyY / Game.getBlockSize();
                }
                vSpeed = 0;
            }
        }

        int rowIndex = getCenterY() / Game.getBlockSize();
        int colIndex = getCenterX() / Game.getBlockSize();

        // check if the player is standing on ground
        int checkRowIndex = (getY() + getHeight() + 3) / Game.getBlockSize();
        onGround = (Game.getBlockGrid()[checkRowIndex][colIndex] != null);

        //check for block on right side
        int checkColIndex = (getX() + getWidth() + 3) / Game.getBlockSize();
        if (checkColIndex < Game.getBlockGrid()[0].length) blockOnRight = (Game.getBlockGrid()[rowIndex][checkColIndex] != null);

        //check for block on left side
        checkColIndex = (getX() - 3) / Game.getBlockSize();
        if (checkColIndex >= 0) blockOnLeft = (Game.getBlockGrid()[rowIndex][checkColIndex] != null);
    }

    /** Retrieves the center x position of the player object on the canvas. */
    public int getCenterX()
    {
        return getX() + getWidth() / 2;
    }

    /** Retrieves the center y position of the player object on the canvas. */
    public int getCenterY()
    {
        return getY() + getHeight() / 2;
    }

    public Image getSprite ()
    {
        return sprite;
    }

    public void setSprite ( Image sprite )
    {
        this.sprite = sprite;
    }

    public int getX ()
    {
        return x;
    }

    public void setX ( int x )
    {
        this.x = x;
    }

    public int getY ()
    {
        return y;
    }

    public void setY ( int y )
    {
        this.y = y;
    }

    public int getWidth ()
    {
        return PLAYER_WIDTH;
    }

    public int getHeight ()
    {
        return PLAYER_HEIGHT;
    }

    public double getFuel ()
    {
        return fuel;
    }

    public void setFuel ( double fuel )
    {
        this.fuel = fuel;
    }

    public int getHaul ()
    {
        return haul;
    }

    public void setHaul ( int haul )
    {
        this.haul = haul;
    }

    public int getMoney ()
    {
        return money;
    }

    public void setMoney ( int money )
    {
        this.money = money;
    }
}
