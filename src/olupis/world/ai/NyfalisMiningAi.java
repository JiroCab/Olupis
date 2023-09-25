package olupis.world.ai;

import arc.math.Mathf;
import arc.math.geom.Position;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.type.Item;
import mindustry.world.Tile;
import olupis.world.entities.units.AmmoLifeTimeUnitType;

import static mindustry.Vars.indexer;

/*Custom mining Ai that Respects the ammo lifetime gimmick*/
public class NyfalisMiningAi extends AIController {
    public boolean mining = true, dynamicItems = true, inoperable = false;
    public Item targetItem;
    public Tile ore, lastOre;
    /*0 = Cant mine, 1 = core, 2 = Floor, 3 = Wall, 4 = overlay*/
    public int mineType = 0;
    public Seq<Item> dynamicMineItems = new Seq<>(), dynamicBlackList = new Seq<>();
    private int lastPathId = 0;
    private float lastMoveX, lastMoveY;

    public void updateMineItems(){
        dynamicMineItems.clear();
        Vars.content.items().each(i -> {
            if(unit.type.mineTier >= i.hardness && !dynamicBlackList.contains(i)) dynamicMineItems.addUnique(i);
        });
        dynamicMineItems.sort(i -> i.hardness);
    }

    @Override
    public void updateMovement(){
        Building core = unit.closestCore();

        if(!(unit.canMine()) || core == null) return;

        if(dynamicItems)updateMineItems();

        if(unit.type instanceof AmmoLifeTimeUnitType && unit.stack.amount > 0 && ((AmmoLifeTimeUnitType) unit.type).deathThreshold * unit.mineTimer >= unit.ammo){
            unit.mineTile = ore = null;
            if(unit.within(core, unit.type.range)){
                if(core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0){
                    Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                }

                mineType = 0;
                unit.clearItem();
                unit.ammo = ((AmmoLifeTimeUnitType) unit.type).deathThreshold / 2;
                mining = false;
            }

            move(core, true);
            return;
        }

        if(unit.mineTile != null && !unit.mineTile.within(unit, unit.type.mineRange)){
            unit.mineTile(null);
        }

        if(mining){
            if(timer.get(timerTarget2, 60 * 4) || targetItem == null){
                if(dynamicItems) targetItem = dynamicMineItems.min(i -> indexer.hasOre(i) && unit.canMine(i), i -> core.items.get(i));
                else targetItem = unit.type.mineItems.min(i -> indexer.hasOre(i) && unit.canMine(i)  && !dynamicBlackList.contains(targetItem), i -> core.items.get(i));
            }

            //core full of the target item, do nothing
            if(targetItem != null && core.acceptStack(targetItem, 1, unit) == 0){
                unit.clearItem();
                unit.mineTile = null;
                return;
            }

            //if inventory is full, drop it off.
            if(unit.stack.amount >= unit.type.itemCapacity || (targetItem != null && !unit.acceptsItem(targetItem))){
                mining = false;
            }else{
                if(timer.get(timerTarget3, 60) && targetItem != null){
                        lastOre =ore = indexer.findClosestOre(unit, targetItem);
                        mineType = 0;

                        if(ore.floor().itemDrop == targetItem) mineType = 2;
                        else if (ore.block().itemDrop== targetItem) mineType = 3;
                        else if (ore.overlay().itemDrop == targetItem) mineType = 4;
                }

                if(ore != null){

                    move(ore, false, true);

                    if(ore.block() == Blocks.air && unit.within(ore, unit.type.mineRange)){
                        unit.mineTile = ore;
                        unit.lookAt(ore);
                    }

                    if(ore.block() != Blocks.air){
                        mining = false;
                    }
                }
            }
        }else{
            unit.mineTile = null;

            if(unit.stack.amount == 0){
                mining = true;
                return;
            }

            if(unit.within(core, Math.max(unit.type.range, unit.type.mineRange))){
                if(core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0){
                    Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                }

                unit.clearItem();
                mining = true;
            }

            mineType = 1;
            move(core, true);
        }
    }


    public void move(Position target, boolean nullDepletion){
        move(target, nullDepletion, false);
    }
    public void move(Position target, boolean nullDepletion, boolean notCore){
        if(unit.within(target, unit.type.mineRange / 2f)) return;

        if (unit.type.flying) circle(target, unit.type.range / 1.8f);
        else {
            if(!Mathf.equal(target.getX(), lastMoveX, 0.1f) || !Mathf.equal(target.getY(), lastMoveY, 0.1f)){
                lastPathId ++;
                lastMoveX = target.getX();
                lastMoveY = target.getY();
            }
            if (Vars.controlPath.getPathPosition(unit, lastPathId, Tmp.v2.set(target.getX(), target.getY()), Tmp.v1, null)) {
                unit.lookAt(Tmp.v1);
                moveTo(Tmp.v1, 1f, Tmp.v2.epsilonEquals(Tmp.v1, 4.1f) ? 30f : 0f, false, null);
            } else {
                /*Prevents getting stuck on an ore that unit can't reach*/
                if(notCore && targetItem != null && !unit.moving() && timer.get(timerTarget4, 25)) dynamicBlackList.add(targetItem);
                if(nullDepletion) inoperable = true;
                unit.lookAt(unit.prefRotation());
            }
        }
    }

}
