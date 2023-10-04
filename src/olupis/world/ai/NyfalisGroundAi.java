package olupis.world.ai;

import mindustry.ai.types.CommandAI;
import mindustry.ai.types.GroundAI;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.gen.Teamc;
import olupis.world.entities.units.NyfalisUnitType;

public class NyfalisGroundAi extends GroundAI {
    public boolean shouldCircle = false;
    public float circleDistance = 75f;
    private int lastPathId = 0;
    private float lastMoveX, lastMoveY;

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
            if(shouldCircle) {
                if (ai.attackTarget != null) {
                    target = ai.attackTarget;
                    circleAttack(circleDistance);
                }
            }
        } else super.updateUnit();
    }
/* TODO: Implement
    @Override
    public void circleAttack(float circleLength){
        if(!Mathf.equal(target.getX(), lastMoveX, 0.1f) || !Mathf.equal(target.getY(), lastMoveY, 0.1f)){
            lastPathId ++;
            lastMoveX = target.getX();
            lastMoveY = target.getY();
        }
        Tmp.v2.set(target);
        float ang = unit.angleTo(target),
                diff = Angles.angleDist(ang, unit.rotation());

        if(diff > 70f && Tmp.v2.len() < circleLength){
            Tmp.v2.setAngle(unit.vel().angle());
        }else{
            Tmp.v2.setAngle(Angles.moveToward(unit.vel().angle(), Tmp.v2.angle(), 6f));
        }


        if (Vars.controlPath.getPathPosition(unit, lastPathId, Tmp.v2, Tmp.v1, null)) {
            unit.lookAt(Tmp.v1);
            moveTo(Tmp.v1, 1f, vec.epsilonEquals(Tmp.v1, 4.1f) ? 30f : 0f, false, null);
        } else {
            unit.lookAt(unit.prefRotation());
        }

    }*/
}

