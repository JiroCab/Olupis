package olupis.world.ai;

import arc.math.Angles;
import arc.util.Tmp;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.FlyingAI;
import mindustry.gen.Flyingc;
import mindustry.gen.Teamc;
import olupis.input.NyfalisUnitCommands;
import olupis.world.entities.units.NyfalisUnitType;

public class DeployedAi extends FlyingAI {

    @Override
    public void updateUnit(){
        if(unit.controller() instanceof CommandAI ai){
            if(unit.isCommandable() && unit.command().command != NyfalisUnitCommands.nyfalisDeployCommand){
                ai.defaultBehavior();
                if(ai.attackTarget != null && unit.within(ai.attackTarget, unit.range() - 10f) && shouldLand(ai.attackTarget) && unit.isCommandable()) unit.command().command(NyfalisUnitCommands.nyfalisDeployCommand);
            }

        }
        super.updateUnit();
    }

    @Override
    public Teamc findMainTarget(float x, float y, float range, boolean air, boolean ground){
        if(unit.controller() instanceof CommandAI ai && ai.attackTarget != null) return ai.attackTarget;
        return super.findMainTarget(x, y, range, air, ground);
    }

    @Override
    public void updateMovement(){
        if(unit.isFlying()) {
            if (!unit.type.flying && unit.controller() instanceof CommandAI ai) {
                ai.defaultBehavior();

                if(ai.attackTarget != null && unit.within(ai.attackTarget, unit.type.range) && shouldLand(ai.attackTarget)){
                    if (!unit.canLand() || (unit.elevation < 1)) circleAttack(unit.type.range + 5f, ai.attackTarget);
                    else moveTo(Tmp.v3.set(unit.x, unit.y), 5f, 100, false, null);
                }
            }
        } else {
            moveTo(Tmp.v3.set(unit.x, unit.y), 5f, 100, false, null);
        }

        if (unit.type.canBoost && unit.type instanceof NyfalisUnitType nyf && nyf.deployLands ) {
            if(unit.isCommandable()) unit.updateBoosting(unit.command().command != NyfalisUnitCommands.nyfalisDeployCommand);
            else unit.updateBoosting(shouldLand(target));
        }

    }

    public boolean shouldLand(Teamc target){
        boolean out = target instanceof Flyingc f && f.isFlying();
        if(unit.type instanceof NyfalisUnitType nyf && !nyf.inverseLanding) out = !out;
        return out;
    }

    public void circleAttack(float circleLength, Teamc tar){
        vec.set(tar).sub(unit);

        float ang = unit.angleTo(tar);
        float diff = Angles.angleDist(ang, unit.rotation());

        if(diff > 70f && vec.len() < circleLength){
            vec.setAngle(unit.vel().angle());
        }else{
            vec.setAngle(Angles.moveToward(unit.vel().angle(), vec.angle(), 6f));
        }

        vec.setLength(unit.speed() / 2f);

        unit.moveAt(vec);
    }
}