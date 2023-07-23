package olupis.world.entities.units;

import arc.graphics.Color;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import olupis.content.NyfalisItemsLiquid;

public class NyfalisUnitType extends UnitType {

    public NyfalisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("371404");
        ammoType = new ItemAmmoType(NyfalisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
    }

}
