package olupis.world.blocks;

import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.Sorter;

public class OverflowSorter extends Sorter {


    public OverflowSorter(String name){
        super(name);
        hasItems = true;
    }

    public class OverflowSorterBuild extends SorterBuild{

        public Building getTileTarget(Item item, Building source, boolean flip){
            int dir = source.relativeTo(tile.x, tile.y);
            if(dir == -1) return null;
            Building to = nearby((dir + 2) % 4);
            boolean
                    fromInst = source.block.instantTransfer,
                    canForward = to != null && to.team == team && !(fromInst && to.block.instantTransfer) && to.acceptItem(this, item),
                    inv = invert == enabled,
                    sorter = sortItem != null;
            ;

            if(((item == sortItem) != invert) == enabled) {
                //prevent 3-chains
                if (isSame(source) && isSame(nearby(dir))) {
                    return null;
                }
                to = nearby(dir);
            } else if(sorter || (!canForward || inv)){
                Building a = nearby(Mathf.mod(dir - 1, 4));
                Building b = nearby(Mathf.mod(dir + 1, 4));
                boolean ac = a != null && !(fromInst && a.block.instantTransfer) && a.team == team && a.acceptItem(this, item);
                boolean bc = b != null && !(fromInst && b.block.instantTransfer) && b.team == team && b.acceptItem(this, item);

                if(!ac && !bc && !sorter){
                    return inv && canForward ? to : null;
                }

                if(ac && !bc){
                    to = a;
                }else if(bc && !ac){
                    to = b;
                }else if(!bc && sorter){
                    return null;
                }else{
                    to = (rotation & (1 << dir)) == 0 ? a : b;
                    if(flip) rotation ^= (1 << dir);
                }
            }

            return to;
        }
    }
}