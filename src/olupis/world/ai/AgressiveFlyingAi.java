package olupis.world.ai;

import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.FlyingAI;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

import static mindustry.Vars.state;

public class AgressiveFlyingAi extends FlyingAI {
    public boolean shouldCircle = false;
    public float circleDistance = 150f;
    public Unit parent;
    public float parentCircle = 35f;

    @Override
    public void updateUnit(){
        if(unit.controller() instanceof CommandAI ai){
            ai.defaultBehavior();
            if(ai.attackTarget != null && shouldCircle){
                target = ai.attackTarget;
                circleAttack(circleDistance);
            }
        } else super.updateUnit();
    }

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(parent != null && !parent.dead()) {
            float speed =  unit.within(parent, parentCircle * 1.1f) ?Math.min(parent.speed(), unit.speed()) : unit.speed() ;
            Log.err(parent.speed() + " || " + unit.speed() + " = " + speed);
            circle(parent, parentCircle, speed);
        }else if(target != null && unit.hasWeapons()){
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
        if(parent != null && !parent.dead){

            Vec2 aimVec = new Vec2(parent.aimX, parent.aimY);
            if(!parent.isShooting) aimVec = Predict.intercept(vec, unit, unit.speed());
            /*I don't know which one worked so have all of them*/
            unit.aimLook(aimVec); unit.lookAt(aimVec); unit.aim(aimVec);
            unit.isShooting = parent.isShooting();

            for(var mount : unit.mounts) {
                Weapon weapon = mount.weapon;
                Vec2 to = Predict.intercept(unit, aimVec, weapon.bullet.speed);

                //let uncontrollable weapons do their own thing
                if (!weapon.controllable || weapon.noAttack) continue;

                if (!weapon.aiControllable) {
                    mount.rotate = false;
                    continue;
                }

                mount.aimX = to.x;
                mount.aimY = to.y;
                mount.shoot = parent.isShooting;
            }
        } else{
            super.updateWeapons();
            if(!unit.isShooting && unit().hasWeapons()){
                /*shoot range regardless if it's the target & there's enemies nearby*/
                Teamc check = Units.closestTarget(unit.team, unit.x, unit.y, unit.range(), u -> u.checkTarget(unit.type.targetAir, unit.type.targetGround));
                unit.isShooting = check != null;
            }
        }
    }
}
