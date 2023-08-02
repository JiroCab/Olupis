package olupis.world.ai;

import mindustry.ai.types.CommandAI;
import mindustry.ai.types.FlyingAI;
import mindustry.entities.Units;
import mindustry.gen.Teamc;

import static mindustry.Vars.state;

public class AgressiveFlyingAi extends FlyingAI {
    public boolean shouldCircle = false;
    public float circleDistance = 150f;

    @Override
    public void updateUnit(){
        if(unit.controller() instanceof CommandAI ai){
            ai.defaultBehavior();
            if(ai.attackTarget != null && shouldCircle){
                target = ai.attackTarget;
                circleAttack(circleDistance);
            }
        } else updateMovement();
    }

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null && unit.hasWeapons()){
            if(unit.type.circleTarget){
                circleAttack(circleDistance);
            }else{
                moveTo(target, unit.type.range * 0.8f);
                unit.lookAt(target);
            }
        } else if(target == null && state.rules.waves && unit.team == state.rules.defaultTeam){
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 130f);
        }
    }

    public void updateWeapons(){
        super.updateWeapons();
        if(!unit.isShooting && unit().hasWeapons()){
            /*shoot range regardless if it's the target & there's enemies nearby*/
            Teamc check = Units.closestTarget(unit.team, unit.x, unit.y, unit.range(), u -> u.checkTarget(unit.type.targetAir, unit.type.targetGround));
            unit.isShooting = check != null;
        }
    }
}
