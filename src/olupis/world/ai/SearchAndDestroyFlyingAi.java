package olupis.world.ai;

import arc.math.Mathf;
import mindustry.ai.types.FlyingAI;
import mindustry.entities.Units;
import mindustry.gen.Teamc;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.state;

/*Very fancy name for something really simple*/
public class SearchAndDestroyFlyingAi extends FlyingAI {

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null && unit.hasWeapons()){
            if(unit.type.circleTarget){
                circleAttack(120f);
            }else{
                moveTo(target, unit.type.range * 0.6f);
                unit.lookAt(target);
            }
        }

        if(target == null && state.rules.waves && unit.team == state.rules.defaultTeam){
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + (unit.range() * 0.5f));
        }
    }

    @Override
    public Teamc findMainTarget(float x, float y, float range, boolean air, boolean ground){
        var core = targetFlag(x, y, BlockFlag.core, true);

        if(core != null && Mathf.within(x, y, core.getX(), core.getY(), range)){
            return core;
        }

        for(var flag : unit.type.targetFlags){
            if(flag == null){
                Teamc result = target(x, y, range, air, ground);
                if(result != null) return result;
            }else if(ground){
                Teamc result = targetFlag(x, y, flag, true);
                if(result != null) return result;
            }
        }

        var search = Units.closestTarget(unit.team, x, y, Float.MAX_VALUE, u -> air, b -> ground);
        if(search != null) return search;

        return core;
    }

}
