package olupis.world.ai;

import mindustry.ai.types.CommandAI;
import mindustry.ai.types.GroundAI;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.gen.Teamc;
import olupis.world.entities.units.NyfalisUnitType;

public class NyfalisGroundAi extends GroundAI {

    @Override
    public void updateUnit() {
        if (unit.controller() instanceof CommandAI ai) {
            ai.defaultBehavior();
            if (unit.type instanceof NyfalisUnitType unt) {
                if (unt.idleFaceTargets) {
                    if (ai.targetPos != null && unit.within(ai.targetPos, (unit.type.range - 10f) * 0.9f))
                        unit.lookAt(ai.targetPos);
                    else if(ai.targetPos == null) {
                        if (target != null) unit.lookAt(target);
                        else {
                            Teamc enmy = Units.bestTarget(unit.team, unit.x, unit.y(), unt.range, e -> !e.dead() && (e.isGrounded() || unit.type.targetAir) && (!e.isGrounded() || unit.type.targetGround), b -> unit.type.targetGround, UnitSorts.closest);
                            if (enmy != null) unit.lookAt(enmy);
                        }
                    }
                }
            }


        } else super.updateUnit();
    }
}

