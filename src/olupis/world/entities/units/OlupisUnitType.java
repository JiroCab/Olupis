package olupis.world.entities.units;

import arc.graphics.Color;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import olupis.content.OlupisItemsLiquid;

public class OlupisUnitType extends UnitType {

    public OlupisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("371404");
        ammoType = new ItemAmmoType(OlupisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
    }

}
