package olupis.world.ai;

import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.*;
import mindustry.entities.units.AIController;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

import static mindustry.Vars.state;

public class ArmDefenderAi extends AIController {
    protected Teamc follow;

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null){
            Tmp.v1.set(target);
            if(target instanceof Unit unt &&unt.moving()) Tmp.v1.set(Predict.intercept(unit, target, ((Unit) target).speed()));
            moveTo(Tmp.v1, (target instanceof Sized s ? s.hitSize()/2f * 1.1f : 0f) + unit.hitSize/2f + (unit.range() * 0.85f), 50f);
            unit.aimLook(target);
        } else if (follow != null){
            moveTo(follow, (follow instanceof Sized s ? s.hitSize()/2f * 1.1f : 0f) + unit.hitSize/2f + 15f, 50f);
            unit.lookAt(follow);
        }
    }

    @Override
    public void updateTargeting(){
        if(follow == null) follow = findFollow(unit.x, unit.y, unit.range());
        super.updateTargeting();
    }

    @Override
    public void updateWeapons(){
        super.updateWeapons();
        if( target != null && unit.within(target, unit.range())){
            unit.isShooting = true;
            for(var mount : unit.mounts){
                if(!mount.weapon.controllable || mount.weapon.noAttack) continue;
                mount.shoot = true;
        }}
    }

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){

        if(follow != null){
            var followTarget = Units.closestTarget(unit.team, follow.x(), follow.y(), range, u -> u.checkTarget(air, ground), t -> ground);
            if(followTarget != null )return followTarget;
        }

        var close = Units.closestTarget(unit.team, x, y, range, u -> u.checkTarget(air, ground), t -> ground);
        if(close != null) return close;

        //for enemies, target the enemy core.
        if(state.rules.waves && unit.team == state.rules.waveTeam && follow == null){
            return unit.closestEnemyCore();
        }

        return null;
    }

    public Teamc findFollow(float x, float y, float range){
        //Sort by max health and closer target.
        Unit unt = Units.closest(unit.team, x, y, Math.max(range, 400f), u -> !u.dead() && u.type != unit.type && u.targetable(unit.team) && u.type.playerControllable,
                (u, tx, ty) -> -u.maxHealth + Mathf.dst2(u.x, u.y, tx, ty) / 6400f);
         if(unt != null) return unt;
         return unit.within(unit.closestCore(), range) ? unit().closestCore() :  null;

    }
}
