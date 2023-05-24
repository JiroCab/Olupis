package olupis.world.blocks;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.BlockGroup;

public class MendTurret extends PowerTurret {

    public MendTurret(String name){
        super(name);
        hasPower = true;
        group = BlockGroup.projectors;
    }

    public class MendTurretBuild extends PowerTurretBuild{

        @Override
        public void updateTile(){
            if(!validateMendTarget()) target = null;

            float warmupTarget = (isShooting() && canConsume()) || charging() ? 1f : 0f;
            if(warmupTarget > 0 && shootWarmup >= minWarmup && !isControlled()){
                warmupHold = 1f;
            }
            if(warmupHold > 0f){
                warmupHold -= Time.delta / warmupMaintainTime;
                warmupTarget = 1f;
            }

            if(linearWarmup){
                shootWarmup = Mathf.approachDelta(shootWarmup, warmupTarget, shootWarmupSpeed * (warmupTarget > 0 ? efficiency : 1f));
            }else{
                shootWarmup = Mathf.lerpDelta(shootWarmup, warmupTarget, shootWarmupSpeed * (warmupTarget > 0 ? efficiency : 1f));
            }

            wasShooting = false;

            curRecoil = Mathf.approachDelta(curRecoil, 0, 1 / recoilTime);
            heat = Mathf.approachDelta(heat, 0, 1 / cooldownTime);
            charge = charging() ? Mathf.approachDelta(charge, 1, 1 / shoot.firstShotDelay) : 0;

            unit.tile(this);
            unit.rotation(rotation);
            unit.team(team);
            recoilOffset.trns(rotation, -Mathf.pow(curRecoil, recoilPow) * recoil);

            if(logicControlTime > 0){
                logicControlTime -= Time.delta;
            }

            if(heatRequirement > 0){
                heatReq = calculateHeat(sideHeat);
            }

            //turret always reloads regardless of whether it's targeting something
            updateReload();

            if(hasAmmo()){
                if(Float.isNaN(reloadCounter)) reloadCounter = 0;

                if(timer(timerTarget, targetInterval)){
                    findDamaged();
                }
                if(validateMendTarget()){
                    boolean canShoot = true;

                    if(isControlled()){ //player behavior
                        targetPos.set(unit.aimX(), unit.aimY());
                        canShoot = unit.isShooting();
                    }else if(logicControlled()){ //logic behavior
                        canShoot = logicShooting;
                    }else{ //default AI behavior
                        //targetPosition(target);
                        targetPos.set(target.x(), target.y());

                        if(Float.isNaN(rotation)) rotation = 0;
                    }

                    if(!isControlled()){
                        unit.aimX(targetPos.x);
                        unit.aimY(targetPos.y);
                    }

                    float targetRot = angleTo(targetPos);

                    if(shouldTurn()){
                        turnToTarget(targetRot);
                    }

                    if(Angles.angleDist(rotation, targetRot) < shootCone && canShoot){
                        wasShooting = true;
                        updateShooting();
                    }
                }
            }

            if(coolant != null){
                updateCooling();
            }
        }

        protected void findDamaged(){
            float range = range();

            /*Heal Tiles only*/
            target = Units.findAllyTile(team, x, y, range, b -> b.damaged() && b != this);
        }

        protected boolean validateMendTarget(){
            return !Units.invalidateTarget(target, Team.derelict, x, y, range) || isControlled() || logicControlled();
        }
    }

}
