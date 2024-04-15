package olupis.world.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.LogicAI;
import mindustry.audio.SoundLoop;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Predict;
import mindustry.entities.Sized;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import olupis.content.NyfalisItemsLiquid;
import olupis.input.NyfalisUnitCommands;
import olupis.world.ai.NyfalisMiningAi;
import olupis.world.entities.bullets.SpawnHelperBulletType;
import olupis.world.entities.parts.NyfPartParms;

import static mindustry.Vars.*;

public class NyfalisUnitType extends UnitType {
    /*Custom RTS commands*/
    public boolean canCircleTarget = false, canHealUnits = false, canGuardUnits  = false, canMend = false, canDeploy = false, canDash = false,
                            constructHideDefault = false, customMineAi = false;
    /*Makes (legged) units boost automatically regardless of Ai*/
    public boolean alwaysBoostOnSolid = false;
    /*Replace Move Command to a custom one*/
    public boolean customMoveCommand = false;
    /*Face targets when idle/not moving, assumes `customMoveCommand` = true  */
    public boolean idleFaceTargets = false;
    /*forces the unit to be landed on deploy*/
    public boolean deployLands = false, alwaysBoosts = false, deployHasEffect = false;
    public StatusEffect deployEffect = StatusEffects.none;
    public float deployEffectTime = 20f;
    /*Effects that a unit spawns with, gnat cheese fix*/
    public StatusEffect spawnStatus = StatusEffects.none;
    public float spawnStatusDuration = 60f * 5f;
    public Seq<UnlockableContent> displayFactory = new Seq<>();
    public Color unitOutLine = Color.valueOf("371404");

    public NyfalisUnitType(String name){
        super(name);
        outlineColor = unitOutLine;
        ammoType = new ItemAmmoType(NyfalisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
        generateIcons = true;
        if(customMoveCommand) defaultCommand = NyfalisUnitCommands.nyfalisMoveCommand;
    }

    @Override
    public void init(){
        super.init();

        Seq<UnitCommand> cmds = Seq.with(commands);
            if (customMoveCommand){
                cmds.remove(UnitCommand.moveCommand);
                cmds.add(NyfalisUnitCommands.nyfalisMoveCommand);
            }
            if(canDeploy)cmds.add(NyfalisUnitCommands.nyfalisDeployCommand);
            if(canCircleTarget) cmds.add(NyfalisUnitCommands.circleCommand);
            if(canHealUnits) cmds.add(NyfalisUnitCommands.healCommand);
            if(canMend) cmds.add(NyfalisUnitCommands.nyfalisMendCommand);
            if (customMineAi) cmds.add(NyfalisUnitCommands.nyfalisMineCommand);
            if (canGuardUnits) cmds.add(NyfalisUnitCommands.nyfalisGuardCommand);
            if (canDash)cmds.add(NyfalisUnitCommands.nyfalisDashCommand);
            if (canBoost && alwaysBoosts) cmds.remove(UnitCommand.boostCommand);
        commands = cmds.toArray();
    }

    @Override
    public void display(Unit unit, Table table){
        super.display(unit, table);
        if(unit.controller() instanceof NyfalisMiningAi ai && ai.targetItem != null) table.table(t -> {
            table.row();
            t.table(i ->  {
                i.image(ai.ore != null ? ai.ore.overlay().fullIcon : ai.targetItem.fullIcon).scaling(Scaling.bounded).left().pad(5);
                i.add(ai.targetItem.localizedName).pad(5).wrap().center();
            }).left();
            if (ai.ore != null && (Core.settings.getBool("mouseposition") || Core.settings.getBool("position"))) {
                t.row();
                t.add("[lightgray](" + Math.round(ai.ore.x) + ", " + Math.round(ai.ore.y) + ")").growX().wrap().center();
            }
        }).grow();
    }

    @Override
    public void setStats(){
        super.setStats();

        /*We have a weird tech tree, this just makes it easier for the player's end*/
        if(displayFactory.size >= 1){
            stats.add(Stat.input, table -> displayFactory.each(fac -> {
                table.row();
                table.table(Styles.grayPanel, t -> {
                    boolean show = (fac instanceof Block b && b.isVisible()) || (fac instanceof  UnitType u && !u.isBanned());
                    if(!fac.unlocked() && (Vars.state.isCampaign() || !Vars.state.isPlaying())) t.image(Icon.lock.getRegion()).tooltip(fac.localizedName).size(25).pad(10f).left().scaling(Scaling.fit);
                    else {
                        if(show) t.image(fac.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        else t.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);
                        t.table(info -> {
                            info.add(fac.localizedName).left();
                            if (Core.settings.getBool("console")) {
                                info.row();
                                info.add(fac.name).left().color(Color.lightGray);
                            }
                        });
                        t.button("?", Styles.flatBordert, () -> ui.content.show(fac)).size(40f).pad(10).right().grow().visible(fac::unlockedNow);
                    }
                }).growX().pad(5).row();
            }));
        }

        if(weapons.any()){
            stats.remove(Stat.weapons);
            stats.add(Stat.weapons, table -> {
                for(Weapon w : weapons) {
                    if(!w.hasStats(this) || w.flipSprite) continue;

                    if (w.bullet instanceof SpawnHelperBulletType) {
                        UnitType spawn = w.bullet.spawnUnit;
                        table.row();
                        table.table(Styles.grayPanel, t -> {
                            boolean show = !spawn.isBanned();
                            if (!spawn.unlocked() && (Vars.state.isCampaign() || !Vars.state.isPlaying()))
                                t.image(Icon.lock.getRegion()).tooltip(spawn.localizedName).size(25).pad(10f).left().scaling(Scaling.fit);
                            else {
                                if (show) t.image(spawn.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                                else
                                    t.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);
                                t.table(info -> {
                                    info.add(spawn.localizedName).left();
                                    if (Core.settings.getBool("console")) {
                                        info.row();
                                        info.add(spawn.name).left().color(Color.lightGray);
                                    }
                                });
                                t.button("?", Styles.flatBordert, () -> ui.content.show(spawn)).size(40f).pad(10).right().grow().visible(spawn::unlockedNow);
                            }
                        }).growX().pad(5).row();
                        if(w.bullet.intervalBullet != null){
                            table.row();
                            table.table(Styles.grayPanel, t -> {
                                t.left().top().defaults().padRight(3).left();
                                StatValues.ammo(ObjectMap.of(this, w.bullet.intervalBullet)).display(t);
                            }).growX().pad(5).margin(10);
                        }
                    } else {
                        table.row();
                        TextureRegion region = !w.name.isEmpty() ? Core.atlas.find(w.name + "-preview", w.region) : null;
                        table.table(Styles.grayPanel, wt -> {
                            wt.left().top().defaults().padRight(3).left();
                            if (region != null && region.found() && w.showStatSprite)
                                wt.image(region).size(60).scaling(Scaling.bounded).left().top();
                            wt.row();
                            w.addStats(this, wt);
                        }).growX().pad(5).margin(10);
                        table.row();
                    }
                }
            });
        }

    }

    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        unit.ammo = ammoCapacity; //fill up on ammo upon creation
        unit.elevation = flying || alwaysBoosts ? 1f : 0;
        unit.heal();
        if(unit instanceof TimedKillc u){
            u.lifetime(lifetime);
        }
        unit.apply(spawnStatus, spawnStatusDuration);
        return unit;
    }

    @Override
    public void draw(Unit unit){
        if(parts.size > 0){
            NyfPartParms.nyfparams.set(unit.healthf(), unit.team.id, unit.elevation(), partAmmo(unit) );
        }
        super.draw(unit);
    }

    public float partAmmo(Unit unit){
        return unit.ammo/ ammoCapacity;
    }

    @Override
    public void update(Unit unit){
        super.update(unit);

        if(deployHasEffect && (!deployLands || unit.isGrounded())) unit.apply(deployEffect, deployEffectTime);
        if(unit.type instanceof  NyfalisUnitType nyf && nyf.canDeploy) {
            if (!unit.isPlayer() && !(unit.controller() instanceof LogicAI)) {
                boolean deployed = (unit.isCommandable() && unit.command().command == NyfalisUnitCommands.nyfalisDeployCommand);
                if(deployed && unit.isGrounded())unit.apply(deployEffect, deployEffectTime);

                if (!deployed && alwaysBoosts) {
                    unit.updateBoosting(true);
                    unit.unapply(deployEffect);
                } else if (deployLands) unit.updateBoosting(!(deployed && unit.canLand()));
                else if (alwaysBoosts) unit.updateBoosting(true);
            }
        }

        if(alwaysBoostOnSolid && canBoost && (unit.controller() instanceof CommandAI c && c.command != UnitCommand.boostCommand)){
            unit.updateBoosting(unit.onSolid());
        }
    }

    public  class NyfalisWeapon extends Weapon {
        public boolean
        /*Determines if the weapon can shoot while boosting or not*/
        boostShoot = true, groundShoot = true,
        /*Allows weapon to be shot by the player when Ai is not using it*/
        partialControl = false,
        idlePrefRot = true, alwaysRotate = false,
        /*Shoot while dash command is selected*/
        dashShoot = false, dashExclusive = false,
        /*Check for angle to target before shooting */
        strictAngle = true;
        /*Margin where when a weapon can fire while transition from ground to air*/
        float boostedEvaluation = 0.95f, groundedEvaluation = 0.05f;
        /*Snek weapon helper so I don't have to override anything else there*/
        float shootXf = shootX, shootYf = shootY;
        boolean altWeaponPos = false;

        public NyfalisWeapon(String name){super(name);}
        public NyfalisWeapon(String name, boolean boostShoot, boolean groundShoot ){
            super(name);
            this.boostShoot = boostShoot;
            this.groundShoot = groundShoot;
        }
        NyfalisWeapon(){}

        @Override
        public void draw(Unit unit, WeaponMount mount){
            if(parts.size > 0){
                NyfPartParms.nyfparams.set(unit.healthf(), unit.team.id, unit.elevation(), partAmmo(unit));
            }
            super.draw(unit, mount);
        }


        @Override
        public void update(Unit unit, WeaponMount mount){

            boolean can = (!unit.disarmed
                    && (!unit.type.canBoost ||
                    (unit.isFlying() && boostShoot  && unit.elevation >= boostedEvaluation || unit.isGrounded() && groundShoot  && unit.elevation <= groundedEvaluation)));
            float lastReload = mount.reload;
            mount.reload =Math.max(mount.reload -Time.delta *unit.reloadMultiplier,0);
            mount.recoil =Mathf.approachDelta(mount.recoil,0,unit.reloadMultiplier /recoilTime);
            if(recoils >0)

            {
                if (mount.recoils == null) mount.recoils = new float[recoils];
                for (int i = 0; i < recoils; i++) {
                    mount.recoils[i] = Mathf.approachDelta(mount.recoils[i], 0, unit.reloadMultiplier / recoilTime);
                }
            }

            mount.smoothReload =Mathf.lerpDelta(mount.smoothReload,mount.reload /reload,smoothReloadSpeed);
            mount.charge =mount.charging &&shoot.firstShotDelay >0?Mathf.approachDelta(mount.charge,1,1/shoot.firstShotDelay):0;

            float warmupTarget = (can && mount.shoot) || (continuous && mount.bullet != null) || mount.charging ? 1f : 0f;
            if(linearWarmup)mount.warmup = Mathf.approachDelta(mount.warmup, warmupTarget, shootWarmupSpeed);
            else mount.warmup = Mathf.lerpDelta(mount.warmup, warmupTarget, shootWarmupSpeed);

            //rotate if applicable
            if(rotate &&(mount.rotate ||mount.shoot)&&can){
                float axisX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                        axisY = unit.y + Angles.trnsy(unit.rotation - 90, x, y);

                mount.targetRotation = Angles.angle(axisX, axisY, mount.aimX, mount.aimY) - unit.rotation;
                mount.rotation = Angles.moveToward(mount.rotation, mount.targetRotation, rotateSpeed * Time.delta);
                if (rotationLimit < 360) {
                    float dst = Angles.angleDist(mount.rotation, baseRotation);
                    if (dst > rotationLimit / 2f) {
                        mount.rotation = Angles.moveToward(mount.rotation, baseRotation, dst - rotationLimit / 2f);
                    }
                }
            }else if(!rotate){
                mount.rotation = baseRotation;
                mount.targetRotation = unit.angleTo(mount.aimX, mount.aimY);
            } else if ( ( alwaysRotate || !mount.rotate || !mount.shoot) && idlePrefRot) {
                mount.targetRotation = baseRotation;
                mount.rotation = Angles.moveToward(mount.rotation, mount.targetRotation, rotateSpeed * Time.delta);
            }

            float weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation),
                    mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                    mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
                    bulletX = altWeaponPos  ? shootXf : mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY),
                    bulletY =  altWeaponPos  ? shootYf : mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY),
                    shootAngle = bulletRotation(unit, mount, bulletX, bulletY);

            //find a new target
            if(!controllable && autoTarget){
                if ((mount.retarget -= Time.delta) <= 0f) {
                    mount.target = findTarget(unit, mountX, mountY, bullet.range, bullet.collidesAir, bullet.collidesGround);
                    mount.retarget = mount.target == null ? targetInterval : targetSwitchInterval;
                }

                if (mount.target != null && checkTarget(unit, mount.target, mountX, mountY, bullet.range)) {
                    mount.target = null;
                }

                boolean shoot;

                if (mount.target != null) {
                    shoot = mount.target.within(mountX, mountY, bullet.range + Math.abs(shootY) + (mount.target instanceof Sized s ? s.hitSize() / 2f : 0f)) && can;

                    if (predictTarget) {
                        Vec2 to = Predict.intercept(unit, mount.target, bullet.speed);
                        mount.aimX = to.x;
                        mount.aimY = to.y;
                    } else {
                        mount.aimX = mount.target.x();
                        mount.aimY = mount.target.y();
                    }
                } else{
                    shoot = partialControl && unit.isShooting && can;
                    mount.aimX = partialControl ? unit.aimX : bulletX;
                    mount.aimY = partialControl ? unit.aimY : bulletY;
                }

                mount.shoot = mount.rotate = shoot;

                //note that shooting state is not affected, as these cannot be controlled
                //logic will return shooting as false even if these return true, which is fine
            }

            if(alwaysShooting)mount.shoot =true;
            // deploying units can shoot regardless of elevation && LogicAi 's shouldShoot checks for boosting and this is a work around
            if(unit.type instanceof NyfalisUnitType nyf && nyf.canDeploy && unit.controller() instanceof LogicAI ai && ai.shoot)mount.shoot = true;

            boolean isDashing = !unit.isPlayer() && unit.isCommandable() && unit.command().command == NyfalisUnitCommands.nyfalisDashCommand;
            if(dashShoot && isDashing) mount.shoot =true;
            else if(dashExclusive && !isDashing) mount.shoot =false;

            //update continuous state
            if(continuous &&mount.bullet !=null) {
                if (!mount.bullet.isAdded() || mount.bullet.time >= mount.bullet.lifetime || mount.bullet.type != bullet) {
                    mount.bullet = null;
                } else {
                    mount.bullet.rotation(weaponRotation + 90);
                    mount.bullet.set(bulletX, bulletY);
                    mount.reload = reload;
                    mount.recoil = 1f;
                    unit.vel.add(Tmp.v1.trns(unit.rotation + 180f, mount.bullet.type.recoil * Time.delta));
                    if (shootSound != Sounds.none && !headless) {
                        if (mount.sound == null) mount.sound = new SoundLoop(shootSound, 1f);
                        mount.sound.update(bulletX, bulletY, true);
                    }

                    if (alwaysContinuous && mount.shoot) {
                        mount.bullet.time = mount.bullet.lifetime * mount.bullet.type.optimalLifeFract * mount.warmup;
                        mount.bullet.keepAlive = true;

                        unit.apply(shootStatus, shootStatusDuration);
                    }
                }
            }else {
                //heat decreases when not firing
                mount.heat = Math.max(mount.heat - Time.delta * unit.reloadMultiplier / cooldownTime, 0);

                if (mount.sound != null) {
                    mount.sound.update(bulletX, bulletY, false);
                }
            }

            //flip weapon shoot side for alternating weapons
            boolean wasFlipped = mount.side;
            if(otherSide !=-1&&alternate &&mount.side ==flipSprite &&mount.reload <=reload /2f&&lastReload >reload /2f){
                unit.mounts[otherSide].side = !unit.mounts[otherSide].side;
                mount.side = !mount.side;
            }

            //shoot if applicable
            if((mount.shoot || partialControl && unit.isShooting && !controllable) && //must be shooting
                    can && //must be able to shoot
                    !(bullet.killShooter &&mount.totalShots >0)&& //if the bullet kills the shooter, you should only ever be able to shoot once
                    (!useAmmo ||unit.ammo >0||!state.rules.unitAmmo ||unit.team.rules().infiniteAmmo)&& //check ammo
                    (!alternate ||wasFlipped ==flipSprite)&&
                    mount.warmup >=minWarmup && //must be warmed up
                    unit.vel.len()>=minShootVelocity && //check velocity requirements
                    (mount.reload <=0.0001f||(alwaysContinuous &&mount.bullet ==null))&& //reload has to be 0, or it has to be an always-continuous weapon
                    (alwaysShooting || (!strictAngle || Angles.within(rotate ?mount.rotation :unit.rotation +baseRotation,mount.targetRotation,shootCone))) //has to be within the cone
            ) {
                shoot(unit, mount, bulletX, bulletY, shootAngle);

                mount.reload = reload;

                if (useAmmo) {
                    unit.ammo--;
                    if (unit.ammo < 0) unit.ammo = 0;
                }
            }
        }
    }

}
