import javafx.scene.image.Image;

import java.util.Random;

/**
 * The Assets class provides access to various game assets such as player sprites, block images, and valuable resources.
 * These assets are loaded and initialized when the game starts and can be retrieved statically throughout the game.
 */
public class Assets
{
    // player sprites
    public enum PlayerAsset {left, right, up, down}
    private final static Image[] playerSprites = {new Image("assets/drill/drill_01.png"),
                                                  new Image("assets/drill/drill_right/01.png"),
                                                  new Image("assets/drill/drill_24.png"),
                                                  new Image("assets/drill/drill_40.png")};

    // valuable block sprites
    public enum Valuable {bronzium, silverium, goldium, platinum, emerald, diamond}
    private final static Image[] valuables = {new Image("assets/underground/valuable_bronzium.png"),
            new Image("assets/underground/valuable_silverium.png"),
            new Image("assets/underground/valuable_goldium.png"),
            new Image("assets/underground/valuable_platinum.png"),
            new Image("assets/underground/valuable_emerald.png"),
            new Image("assets/underground/valuable_diamond.png")};

    // other block sprites
    private final static Image dirt = new Image("assets/underground/soil_01.png");
    private final static Image grass = new Image("assets/underground/top_02.png");
    private final static Image[] boulder = {new Image("assets/underground/obstacle_01.png"),
                                            new Image("assets/underground/obstacle_02.png"),
                                            new Image("assets/underground/obstacle_03.png")};
    private final static Image[] lava = {new Image("assets/underground/lava_01.png"),
                                         new Image("assets/underground/lava_02.png"),
                                         new Image("assets/underground/lava_03.png")};


    /**
     * Retrieves the image associated with the specified player direction.
     *
     * @param key The direction of the player.
     * @return The image representing the player facing the specified direction.
     */
    public static Image getPlayer (PlayerAsset key)
    {
        switch (key)
        {
            case left: default: return playerSprites[0];
            case right: return playerSprites[1];
            case up: return playerSprites[2];
            case down: return playerSprites[3];
        }
    }

    /**
     * Retrieves the image associated with the specified valuable item.
     * If the key is not recognized, returns the default image for dirt.
     *
     * @param key The type of valuable item.
     * @return The image representing the valuable item, or the default dirt image if the key is not recognized.
     */
    public static Image getValuable (Valuable key)
    {
        switch (key)
        {
            default: return getDirt();
            case bronzium: return valuables[0];
            case silverium: return valuables[1];
            case goldium: return valuables[2];
            case platinum: return valuables[3];
            case emerald: return valuables[4];
            case diamond: return valuables[5];
        }
    }

    public static Image getDirt ()
    {
        return dirt;
    }

    public static Image getGrass ()
    {
        return grass;
    }

    public static Image getBoulder ()
    {
        return boulder[new Random().nextInt(3)];
    }

    public static Image getLava ()
    {
        return lava[new Random().nextInt(3)];
    }
}
