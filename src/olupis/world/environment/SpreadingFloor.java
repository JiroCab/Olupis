package olupis.world.environment;

import arc.struct.*;
import mindustry.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class SpreadingFloor extends Floor {
    /** The amount of times the chance must be rolled */
    public int spreadTries = 3;
    /** Base chance for the tile to try to spread, updated every second */
    public double spreadChance = 0.013;
    /** Max tile offset, leave at 0 for linear spread*/
    public int spreadOffset = 0;
    /** Spreading blacklist */
    public ObjectSet<Block> blacklist = new ObjectSet<>();

    public SpreadingFloor(String name) {
        super(name);
    }

    @Override
    public void init(){
        Vars.content.blocks().each(b -> {
            if(b instanceof SpreadingFloor
            || b.isFloor() && b.asFloor().isLiquid)
                blacklist.add(b);
        });
    }
}
