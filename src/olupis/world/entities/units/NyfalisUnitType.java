package olupis.world.entities.units;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import olupis.content.NyfalisItemsLiquid;
import olupis.world.ai.NyfalisCommand;

public class NyfalisUnitType extends UnitType {
    public boolean canCircleTarget = false;

    public NyfalisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("371404");
        ammoType = new ItemAmmoType(NyfalisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
    }

    @Override
    public void init(){
        super.init();
        if(canCircleTarget){
            Seq<UnitCommand> cmds = Seq.with(commands);
            cmds.add(NyfalisCommand.circleCommand);
            commands = cmds.toArray();
        }
    }
}
