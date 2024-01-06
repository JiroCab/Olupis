package olupis.world.entities.abilities;

import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.gen.Unit;

public class MicroWaveFieldAblity extends EnergyFieldAbility {
    public boolean boostShoot = true, groundShoot = true, ideRangeDisplay = true;

    public MicroWaveFieldAblity(float damage, float reload, float range){
        super(damage, reload, range);
    }

    @Override
    public void draw(Unit unit){
        if(!boostShoot && unit.isFlying()) return;
        if(!groundShoot && unit.isGrounded()) return;

        if(ideRangeDisplay || anyNearby)super.draw(unit);
    }

    @Override
    public void update(Unit unit){
        if(!boostShoot && unit.isFlying()) timer = 0f;
        if(!groundShoot && unit.isGrounded()) timer =  0f;

        super.update(unit);
    }
}
