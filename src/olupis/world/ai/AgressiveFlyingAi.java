package olupis.world.ai;

import mindustry.ai.types.FlyingAI;
import mindustry.entities.Units;
import mindustry.gen.Teamc;

public class AgressiveFlyingAi extends FlyingAI {
    //TODO: Selecting Circle attack mode or not in RTS command ie like boosting

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){
        return findMainTarget(x, y, range, air, ground);
    }
    public void updateWeapons(){
        super.updateWeapons();
        if(!unit.isShooting){
            /*shoot a enemy is range regardless if it's the target*/
            Teamc check = Units.closestTarget(unit.team, unit.x, unit.y, unit.range(), u -> u.checkTarget(unit.type.targetAir, unit.type.targetGround));
            unit.isShooting = check != null;
        }
    }

}
