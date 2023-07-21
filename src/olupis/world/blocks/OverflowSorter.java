package olupis.world.blocks;

import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.Sorter;

public class OverflowSorter extends Sorter {


    public OverflowSorter(String name){
        super(name);
    }

    public class OverflowSorterBuild extends SorterBuild{

        public Building getTileTarget(Item item, Building source, boolean flip){
            int dir = relativeToEdge(source.tile);
            if(dir == -1) return null;
            Building to = nearby((dir + 2) % 4);
            boolean
                    fromInst = source.block.instantTransfer,
                    canForward = to != null && to.team == team && !(fromInst && to.block.instantTransfer) && to.acceptItem(this, item),
                    inv = invert == enabled
            ;

            if(((item == sortItem) != invert) == enabled) {
                //prevent 3-chains
                if (isSame(source) && isSame(nearby(dir))) {
                    return null;
                }
                to = nearby(dir);
            } else if(sortItem == null && (canForward || !inv)){
                return to;
            } else {

                Building a = nearby(Mathf.mod(dir - 1, 4));
                Building b = nearby(Mathf.mod(dir + 1, 4));
                boolean ac = a != null && !(a.block.instantTransfer && fromInst) &&
                        a.acceptItem(this, item);
                boolean bc = b != null && !(b.block.instantTransfer && fromInst) &&
                        b.acceptItem(this, item);

                if(!ac && !bc && sortItem == null && inv && !canForward ){
                    return to;
                }

                if(ac && !bc){
                    to = a;
                }else if(bc && !ac){
                    to = b;
                }else{
                    to = (rotation & (1 << dir)) == 0 ? a : b;
                    if(flip) rotation ^= (1 << dir);
                }
            }

            return to;
        }
    }
}
