package olupis.world.ai;

import arc.struct.Seq;
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
public class OlupisMiningAi extends AIController {
    public boolean mining = true;
    public Item targetItem;
    public Tile ore;
    public Seq<Item> dynamicMineItems = new Seq<>();

    public void updateMineItems(){
        Vars.content.items().each(i -> {
            if(unit.type.mineTier >= i.hardness) dynamicMineItems.add(i);
        });
        dynamicMineItems.sort(i -> i.hardness);
    }

    @Override
    public void updateMovement(){
        Building core = unit.closestCore();

        if(!(unit.canMine()) || core == null) return;

        updateMineItems();

        if(unit.type instanceof AmmoLifeTimeUnitType && unit.stack.amount > 0 && ((AmmoLifeTimeUnitType) unit.type).minimumAmmoBeforeKill * unit.mineTimer >= unit.ammo){
            unit.mineTile = null;
            if(unit.within(core, unit.type.range)){
                if(core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0){
                    Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                }

                unit.clearItem();
                unit.ammo = ((AmmoLifeTimeUnitType) unit.type).minimumAmmoBeforeKill / 2;
                mining = false;
            }

            circle(core, unit.type.range / 1.8f);
            return;
        }

        if(unit.mineTile != null && !unit.mineTile.within(unit, unit.type.mineRange)){
            unit.mineTile(null);
        }

        if(mining){
            if(timer.get(timerTarget2, 60 * 4) || targetItem == null){
                targetItem = dynamicMineItems.min(i -> indexer.hasOre(i) && unit.canMine(i), i -> core.items.get(i));
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
                    ore = indexer.findClosestOre(unit, targetItem);
                }

                if(ore != null){
                    moveTo(ore, unit.type.mineRange / 2f, 20f);

                    if(ore.block() == Blocks.air && unit.within(ore, unit.type.mineRange)){
                        unit.mineTile = ore;
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

            if(unit.within(core, unit.type.range)){
                if(core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0){
                    Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                }

                unit.clearItem();
                mining = true;
            }

            circle(core, unit.type.range / 1.8f);
        }
    }
}
