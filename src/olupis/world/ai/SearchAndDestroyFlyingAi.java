package olupis.world.ai;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.ai.types.FlyingAI;
import mindustry.entities.Units;
import mindustry.gen.Teamc;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.*;

/*FlyingAi but really aggressive */
public class SearchAndDestroyFlyingAi extends FlyingAI {
    public float delay = 70f * 60f, idleAfter;
    /*avoids stuttering on trying to go to spawn after target is null*/
    public boolean suicideOnSuicideUnits = true, suicideOnTarget = false;

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null && unit.hasWeapons()){
            idleAfter = Time.delta + delay;
            if(suicideOnSuicideUnits && suicideOnTarget ){
                /*screw crawlers in particular*/
                moveTo(target, 0);
                unit.lookAt(target);
                if(unit.within(target.x(), target.y(), unit.type.range))unit.isShooting = true;
            } else if(unit.type.circleTarget){
                circleAttack(120f);
            }else{
                moveTo(target, unit.type.range * 0.6f);
                unit.lookAt(target);
            }
        }

        if(target == null){
            if( Time.time >= idleAfter) {
                if (unit.closestEnemyCore() != null) moveTo(unit.closestEnemyCore(), unit.range());
                else if (getClosestSpawner() != null) moveTo(getClosestSpawner(), state.rules.dropZoneRadius + (unit.range() * 0.5f));
                else moveTo(unit.closestCore(), unit.range());
            }
            else findMainTarget(unit.x, unit.y, unit.range(), unit.type().targetAir, unit.type().targetGround);
        }
    }

    @Override
    public Teamc findMainTarget(float x, float y, float range, boolean air, boolean ground){
        var core = targetFlag(x, y, BlockFlag.core, true);

        if(core != null && Mathf.within(x, y, core.getX(), core.getY(), range)){
            return core;
        }

        var search = Units.closestTarget(unit.team, x, y, Float.MAX_VALUE, u -> air, b -> ground) ;
        if(search != null){
            suicideOnTarget = Units.closestTarget(unit.team, x, y, Float.MAX_VALUE, u -> u.type().weapons.find(w->w.bullet.killShooter) != null, b -> ground) != null;
            return search;
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

        return core;
    }

}
