import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Random;

/**
 * The Block class represents a block in the game world.
 * Blocks can have various properties such as sprite, drillability, weight, worth, and lethality.
 */
public class Block
{
    /** The sprite image of the block. */
    private Image sprite = Assets.getDirt();

    /** Indicates whether the block is drillable. */
    private boolean drillable = true;

    /** Indicates whether the block is lethal. */
    private boolean lethal = false;

    /** The weight of the block. */
    private int weight = 0;

    /** The worth of the block. */
    private int worth = 0;

    /**
     * Constructs a block at a certain depth level.
     * @param depth The depth level of the block.
     */
    public Block(int depth)
    {
        //constants
        final int LEVEL_SIZE = 12;
        final int LEVEL = (depth - 7) / LEVEL_SIZE;

        // blocks get more valuable as depth grows
        HashMap<Integer, Integer[]> richnessMap = new HashMap<Integer, Integer[]>();
        richnessMap.put(0, new Integer[] {3, 3, 4, 3, 1, 1, 0, 0});
        richnessMap.put(1, new Integer[] {3, 3, 2, 4, 2, 1, 1, 0});
        richnessMap.put(2, new Integer[] {3, 3, 1, 1, 3, 2, 1, 0});
        richnessMap.put(3, new Integer[] {3, 3, 0, 1, 2, 4, 2, 0});
        richnessMap.put(4, new Integer[] {3, 3, 0, 0, 1, 2, 3, 1});
        richnessMap.put(5, new Integer[] {3, 3, 0, 0, 1, 2, 3, 3});

        // give every block a chance in 0-100 range
        int[] chanceList = new int[8];
        int sum = 0;
        for (int i = 0; i < 8; i++)
        {
            sum += richnessMap.get(LEVEL)[i];
            chanceList[i] = sum;
        }

        // choose the type of the block
        int randomNum = new Random().nextInt(100);

        // boulder
        if (randomNum < chanceList[0])
        {
            setSprite(Assets.getBoulder());
            setDrillable(false);
            return;
        }

        // lava
        if (randomNum < chanceList[1])
        {
            setSprite(Assets.getLava());
            setLethal(true);
            return;
        }

        // valuables
        int[] worthList = new int[] {30, 60, 250, 750, 5000, 100000};
        int[] weightList = new int[] {10, 10, 20, 30, 60, 100};
        Image[] spriteList = new Image[] {Assets.getValuable(Assets.Valuable.bronzium),
                                          Assets.getValuable(Assets.Valuable.silverium),
                                          Assets.getValuable(Assets.Valuable.goldium),
                                          Assets.getValuable(Assets.Valuable.platinum),
                                          Assets.getValuable(Assets.Valuable.emerald),
                                          Assets.getValuable(Assets.Valuable.diamond)};
        for (int i = 0; i < 6; i++)
        {
            if (randomNum >= chanceList[i + 2]) continue;

            setSprite(spriteList[i]);
            setWorth(worthList[i]);
            setWeight(weightList[i]);
            return;
        }
    }

    /**
     * Constructs a block of a specific type.
     * @param type The type of the block.
     */
    public Block (String type)
    {
        if (type.equals("grass")) setSprite(Assets.getGrass());
        else if (type.equals("boulder"))
        {
            setSprite(Assets.getBoulder());
            setDrillable(false);
        }
    }

    public Image getSprite ()
    {
        return sprite;
    }

    public void setSprite ( Image sprite )
    {
        this.sprite = sprite;
    }

    public boolean isDrillable ()
    {
        return drillable;
    }

    public void setDrillable ( boolean drillable )
    {
        this.drillable = drillable;
    }

    public int getWeight ()
    {
        return weight;
    }

    public void setWeight ( int weight )
    {
        this.weight = weight;
    }

    public int getWorth ()
    {
        return worth;
    }

    public void setWorth ( int worth )
    {
        this.worth = worth;
    }

    public boolean isLethal ()
    {
        return lethal;
    }

    public void setLethal ( boolean lethal )
    {
        this.lethal = lethal;
    }
}
