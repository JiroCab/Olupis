package olupis.world.environment;

import arc.struct.*;
import mindustry.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class SpreadingFloor extends Floor{
    /** The amount of times the chance must be rolled */
    public int spreadTries = 3;
    /** Base chance for the tile to try to spread, updated every second */
    public double spreadChance = 0.013;
    /** Max tile offset, leave at 0 for linear spread*/
    public int spreadOffset = 0;
    /** Spreading blacklist */
    public ObjectSet<Block> blacklist = new ObjectSet<>();
    /** Block this can "upgrade" into, upgrading takes just as long as spreading */
    public Block next = null;
    /** Block this can spread around, don't set custom unless necessary */
    public Block set = this;
    /** Whether this floor spreads while growing, spreading is always linear here */
    public boolean growSpread = false;

    public SpreadingFloor(String name) {
        super(name);
        variants = 0;
    }

    @Override
    public void init(){
        Vars.content.blocks().each(b -> {
            if(b instanceof SpreadingFloor
            || b.isFloor() && b.asFloor().isLiquid)
                blacklist.add(b);
        });

        if(next != null)
            blacklist.add(next);
        if(set != null)
            blacklist.add(set);

        handleBlacklist(blacklist);
    }

    public void handleBlacklist(ObjectSet<Block> list){
        if(set != this && set instanceof SpreadingFloor t){
            if(t.growSpread || t.next == null)
                t.blacklist.addAll(list);
            else t.handleBlacklist(list);
        }

        if(next instanceof SpreadingFloor f){
            if(f.growSpread || f.next == null)
                f.blacklist.addAll(list);
            else f.handleBlacklist(list);
        }
    }
}
