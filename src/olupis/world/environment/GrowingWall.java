package olupis.world.environment;

import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class GrowingWall extends StaticWall{
    /** The amount of times the chance must be rolled */
    public int growTries = 3;
    /** Base chance for the tile to try to grow, updated every second */
    public double growChance = 0.02;
    /** Block this will grow into */
    public Block next = null;
    /** Block to replace this with when removed */
    public Block replacement = Blocks.stoneWall;

    public GrowingWall(String name, int variants){
        super(name);
        this.variants = variants;
    }
}
