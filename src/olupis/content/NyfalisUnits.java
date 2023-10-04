package olupis.content;

import arc.graphics.Color;
import arc.math.geom.Rect;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.DefenderAI;
import mindustry.content.*;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.part.HoverPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.meta.BlockFlag;
import olupis.input.NyfalisUnitCommands;
import olupis.world.ai.*;
import olupis.world.entities.bullets.*;
import olupis.world.entities.units.*;

import static mindustry.content.Items.*;
import static olupis.content.NyfalisItemsLiquid.*;

public class NyfalisUnits {

    public static AmmoType lifeTimeDrill, lifeTimeWeapon, lifeTimeSupport;
    public static NyfalisUnitType
        /*Air units*/
        aero, striker, falcon, vortex, tempest,
        zoner, regioner, district, division, territory,

        /*segmented units*/
        venom, serpent, reaper, goliath,

        /*naval*/
        porter, essex, lexington, excess, nimitz,
        bay, blitz, crusader, torrent, vanguard,

        /*core units*/
        gnat, phorid,

        /*assistant core units*/
        embryo, larva, pupa,

        /*Misc/pending purpose units*/
        firefly;


    public static AmmoLifeTimeUnitType
        mite,
        //yes its just Phasmophobia ghost types
        spirit, phantom, banshee, revenant, poltergeist
    ;

    public static void LoadUnits(){
        LoadAmmoType();

        //region Air Units
        //Aero -> decently quick and shoot a tiny constant beam, make it fixed and do 10dps
        aero = new NyfalisUnitType("aero"){{
            hitSize = 9f;
            speed = 3.6f;
            health = 200f;
            engineSize = 3f;
            engineOffset = 7f;
            rotateSpeed = 30f;
            itemCapacity = 30;
            drag = accel = 0.08f;
            strafePenalty = 0.35f; //Aero Tree has lower strafe pen, something about they're deigned for it

            lowAltitude = flying = canCircleTarget = alwaysShootWhenMoving = true;
            aiController = AgressiveFlyingAi::new;
            constructor = UnitEntity::create;
            weapons.add(new Weapon(""){{
                top = mirror = false;
                continuous = alwaysContinuous = parentizeEffects  = true;
                shake = 0f;
                range = 32f;
                shootY = 9.1f;
                y = x = recoil = 0f;
                reload = shootCone = 30f;
                ejectEffect = Fx.none;
                outlineRegion = null;
                layerOffset = Layer.flyingUnit -1;
                shootSound = Sounds.electricHum;
                firstRequirements = ItemStack.with(lead, 1, silicon, 5);

                bullet = new ContinuousLaserBulletType(){{
                    shake = 0f;
                    width = 2f;
                    length = 20f;
                    lifetime = 32f;
                    pierceCap = 2;
                    lightStroke = 10;
                    frontLength = 10f;
                    damage = 10 / 12f;
                    homingPower = 0.06f;
                    buildingDamageMultiplier = 1.1f;
                    incendChance = incendSpread = 0f;
                    pierce = true;
                    removeAfterPierce = false;
                    smokeEffect = shootEffect = Fx.none;
                    chargeEffect = hitEffect = Fx.hitLancer;
                    colors = new Color[]{Pal.regen.cpy().a(.2f), Pal.regen.cpy().a(.5f), Pal.regen.cpy().mul(1.2f), Color.white};
                }};
            }});
        }};

        //Striker ->pretty quick, maybe twice as fast as a flare, and shoots arc shots, like the Javelin from v5
        striker = new NyfalisUnitType("striker"){{
            armor = 3f;
            hitSize = 13f;
            drag = 0.05f;
            speed = 5.5f;
            accel = 0.07f;
            health = 300f;
            engineSize = 4f;
            itemCapacity = 70;
            engineOffset = 13.5f;
            strafePenalty = 0.35f; //Aero Tree has lower strafe pen, something about they're deigned for it
            rotateSpeed = baseRotateSpeed = 30f;

            constructor = UnitEntity::create;
            aiController = AgressiveFlyingAi::new;
            flying = canCircleTarget = true;
            weapons.add(new Weapon(""){{
                x = 0;
                y = 1.5f;
                inaccuracy = 3f;
                soundPitchMin = 0.2f;
                soundPitchMax = 0.5f;
                reload = shootCone = 15f;

                top = alternate =  mirror = false;
                alwaysShootWhenMoving = true;
                shootSound = NyfalisSounds.as2PlasmaShot;
                bullet = new LightningBulletType(){{
                    shootY = 0f;
                    drawSize = 55f;
                    pierceCap = 5;
                    lightningLength = 7;
                    lightningLengthRand = 10;
                    pierce = true;
                    shootEffect = Fx.none;
                    lightningColor = hitColor = Pal.regen;
                    lightningType = new BulletType(0.0001f, 0f){{
                        pierceCap = 5;
                        statusDuration = 10f;
                        hittable = false;
                        pierce = true;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        lifetime = Fx.lightning.lifetime;
                    }};
                }};
            }});
            weapons.add(new Weapon(){{
                x = 0f;
                reload = 30;
                minShootVelocity = 5f;
                inaccuracy = shootCone = 180f;

                ejectEffect = Fx.none;
                shootSound = Sounds.spark;
                ignoreRotation = alwaysShooting=  true;
                bullet = new LightningBulletType(){{
                    damage = 10;
                    shoot.shots = 2;
                    shoot.firstShotDelay = 0.2f;
                    buildingDamageMultiplier = 1.1f;
                    lightningLength = lightningLengthRand = 8;

                    parentizeEffects = autoTarget = autoFindTarget = true;
                    top = alternate =  mirror =  aiControllable = controllable = false;
                    status = StatusEffects.none;
                    shootEffect = Fx.hitLancer;
                    lightningColor = hitColor = Color.valueOf("d1efff56"); //Pal.regen w/ custom alpha
                    lightningType = new BulletType(0.0001f, 0f){{
                        hittable = false;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.none;
                        lifetime = Fx.lightning.lifetime;
                    }};
                }};
            }});
        }};

        //Todo: keep?
        firefly = new NyfalisUnitType("firefly"){{
            constructor = UnitTypes.mono.constructor;
            defaultCommand = UnitCommand.mineCommand;
            ammoType = new PowerAmmoType(500);

            flying = true;
            isEnemy = false;

            range = 50f;
            health = 100;
            speed = 1.5f;
            drag = 0.06f;
            accel = 0.12f;
            mineTier = 1;
            engineSize = 1.8f;
            mineSpeed = 2.5f;
            engineOffset = 5.7f;
        }};

        zoner = new NyfalisUnitType("zoner"){{
            armor = 1f;
            hitSize = 11f;
            drag = 0.05f;
            accel = 0.11f;
            health = 200f;
            speed = 3.55f;
            engineSize = 1.6f;
            rotateSpeed = 19f;
            itemCapacity = 70;
            engineOffset = 4.6f;

            constructor = UnitEntity::create;
            aiController = DefenderAI::new;
            lowAltitude = flying = canGuardUnits = true;
            defaultCommand = NyfalisUnitCommands.nyfalisGuardCommand;
            weapons.add(new Weapon("olupis-zoner-weapon"){{
                top = alternate = false;
                y = -1f;
                x = -1.8f;
                inaccuracy = 3f;
                reload = shootCone = 15f;
                ejectEffect = Fx.casing1;

                showStatSprite = false;
                bullet = new BasicBulletType(2.5f, 5, "olupis-diamond-bullet"){{
                    width = 4;
                    height = 6f;
                    lifetime = 60f;
                    homingPower = 0.04f;
                    shootEffect = Fx.none;
                    smokeEffect = Fx.shootSmallSmoke;
                    frontColor = rustyIron.color.lerp(Pal.bulletYellow, 0.5f);
                    hitEffect = despawnEffect = NyfalisFxs.hollowPointHitSmall;
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.5f);
                }};
            }});
        }};

        //endregion
        //region Ground Units
        venom = new SnekUnitType("venom"){{
            constructor = CrawlUnit::create;
            armor = 2;
            hitSize = 11f;
            health = 350;
            segments = 8;
            speed = 2.25f;
            segmentScl = 7f;
            rotateSpeed = 10f;
            legMoveSpace = 1.1f;
            crushDamage = 0.25f;
            segmentMaxRot = 80f;
            segmentRotSpeed = 5f;
            crawlSlowdownFrac = 1f;
            drownTimeMultiplier = 4f;
            omniMovement = drawBody = false;
            allowLegStep = true;

           weapons.addAll(new SnekWeapon(""){{
                x = 0f;
                y = -11f;
                reload = 35f;
                weaponSegmentParent = 1;
                mirror = false;
                rotate = true;
                ejectEffect = Fx.casing1;
                shoot = new ShootAlternate(3f);
                bullet = new BasicBulletType(2.5f, 10f){{
                    width = 7f;
                    height = 9f;
                    fragBullets = 1;
                    lifetime = 100f;
                    fragVelocityMin = 1f;
                    fragRandomSpread = 0f;
                }};
            }});
        }};

        /*TODO: crab tree, bulky and close range, legged naval*/

        //endregion
        //region Naval Units
        porter = new NyfalisUnitType("porter"){{
            armor = 6f;
            hitSize = 12f;
            health = 850;
            speed = 0.75f;
            itemCapacity = 0;
            treadPullOffset = 3;
            rotateSpeed = 3.5f;
            researchCostMultiplier = 0f;

            rotateMoveFirst = true;
            constructor = UnitWaterMove::create;
            treadRects = new Rect[]{new Rect(12 - 32f, 7 - 32f, 14, 51)};
            abilities.add(new UnitSpawnAbility(zoner, 60f * 15f, 0, 2.5f));
        }};

        //Minigun turret mounted on the front, 10mm autocannon mounted on the back
        bay = new NyfalisUnitType("bay"){{
            armor = 4f;
            accel = 0.4f;
            drag = 0.14f;
            hitSize = 11f;
            health = 250;
            range = 100f;
            trailScl = 1.3f;
            speed = 1.15f;
            trailLength = 20;
            waveTrailX = rotateSpeed = 5f;

            faceTarget = customMoveCommand = idleFaceTargets = true;
            constructor = UnitWaterMove::create;
            ammoType = new PowerAmmoType(900);
            weapons.add(new Weapon("olupis-missiles-mount-teamed"){{
                x = 0f;
                y = -8;
                reload = 26f;
                rotate= true;
                mirror = false;
                ejectEffect = Fx.casing1;
                bullet = new ArtilleryBulletType(2.5f, 14){{
                    width = 7f;
                    height = 9f;
                    trailSize = 3f;
                    lifetime = 60f;
                    splashDamage = 2f;
                    splashDamageRadius = 25f * 0.75f;
                    collidesAir = false;
                    frontColor = rustyIron.color.lerp(Pal.bulletYellow, 0.8f);
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.8f);
                }};
            }});
            weapons.add(new Weapon(""){{
                x = 0f;
                y = 6.5f;
                reload = 7f;
                inaccuracy = 4f;
                shootCone = 30f;
                rotateSpeed = 10f;
                rotationLimit = 45f;
                mirror = controllable = false;
                autoTarget = rotate = true;
                bullet = new BasicBulletType(2.5f, 10){{
                    width = 5f;
                    height = 6f;
                    lifetime = 60f;
                    collidesAir = false;
                    frontColor = rustyIron.color.lerp(Pal.bulletYellow, 0.5f);
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.5f);
                    hitEffect = despawnEffect = NyfalisFxs.hollowPointHitSmall;
                }};
            }});
        }};

        blitz = new NyfalisUnitType("blitz"){{
            armor = 5f;
            accel = 0.6f;
            drag = 0.14f;
            range = 200f;
            trailScl = 1.9f;
            health = 550f;
            speed = 1.15f;
            trailLength = 22;
            waveTrailY = -4f;
            waveTrailX = 5.5f;
            rotateSpeed = 3.8f;

            idleFaceTargets = customMoveCommand = true;
            constructor = UnitWaterMove::create;
            weapons.add(new Weapon("olupis-twin-mount"){{
                x = 0;
                y = -9.5f;
                recoils = 2;
                recoil = 0.5f;
                reload = 13f;
                mirror = false;
                rotate= top = true;
                shoot = new ShootAlternate(3.6f);
                for(int i = 0; i < 2; i ++){ int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "r" : "l")){{
                        x = (f == 0) ? 1.8f : -1.8f;
                        y = 3f;
                        shootY = 6f;
                        recoilIndex = f;
                        outlineLayerOffset = 0f;
                        outlineColor = unitOutLine;
                        outline = drawRegion = under = true;
                        progress = PartProgress.recoil;
                        moves.add(new PartMove(PartProgress.recoil, 0, -3f, 0));
                }}); }

                bullet = new ArtilleryBulletType(3f, 14){{
                    width = 7f;
                    height = 9f;
                    trailSize = 3f;
                    lifetime = 65f;
                    splashDamage = 2f;
                    splashDamageRadius = 25f * 0.75f;
                    collidesAir = false;
                    frontColor = rustyIron.color.lerp(Pal.bulletYellow, 0.9f);
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.9f);
                    hitEffect = despawnEffect = Fx.hitBulletSmall;
                }};
            }});
            weapons.add(new Weapon("olupis-twin-auto-cannon"){{
                x = 0f;
                y = 10.5f;
                recoil = 1f;
                recoils = 2;
                reload = 4f;
                hitSize = 14f;
                trailScl = 1.9f;
                shootY = 5.3f;
                inaccuracy = 8f;
                trailLength = 22;
                waveTrailY = -4f;
                rotateSpeed = 3f;
                waveTrailX = 5.5f;
                rotate = true;
                mirror = false;
                shootSound = Sounds.shoot;
                shoot = new ShootAlternate(4.1f);
                for(int i = 0; i < 2; i ++){ int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        x = (f == 0) ? 1.6f : -1.6f;
                        y = 2f;
                        recoilIndex = f;
                        outlineLayerOffset = 0f;
                        outlineColor = unitOutLine;
                        outline = drawRegion = under = true;
                        progress = PartProgress.recoil;
                        moves.add(new PartMove(PartProgress.recoil, 0, -2f, 0));
                }}); }

                bullet = new BasicBulletType(2.5f, 14){{
                    width = 5f;
                    height = 6f;
                    lifetime = 78f;
                    collidesAir = false;
                    frontColor = rustyIron.color.lerp(Pal.bulletYellow, 0.5f);
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.5f);
                    hitEffect = despawnEffect = NyfalisFxs.hollowPointHitSmall;
                    shootEffect = Fx.shootSmallSmoke;
                }};
            }});
        }};

        crusader = new NyfalisUnitType("crusader"){{
            constructor = bay.constructor;
        }};

        //endregion Units
        //region Nyfalis Limited LifeTime / Support Units
        mite = new AmmoLifeTimeUnitType("mite"){{
            hitSize = 9;
            range = 45f;
            armor = 10f;
            speed = 2.7f;
            drag = 0.04f;
            accel = 0.08f;
            health = 100;
            lightRadius = 15f;
            itemCapacity = 0;
            lightOpacity = 50f;
            penaltyMultiplier = 1f;
            ammoDepletionAmount = 0.55f;

            flying = targetGround = targetAir = true;
            playerControllable  = logicControllable = useUnitCap = false;
            constructor = UnitEntity::create;
            targetFlags = new BlockFlag[]{BlockFlag.factory, null};
            controller = u -> new SearchAndDestroyFlyingAi(true);
            weapons.add(new Weapon(){{
                y = x = 0f;
                reload = 10f;
                shootCone = 15f;
                targetInterval = 30f;
                targetSwitchInterval = 60f;

                shootSound = Sounds.pew;
                ammoType = lifeTimeWeapon;
                /*Gave up using LiquidBulletType*/
                bullet = new NoBoilLiquidBulletType(steam){{
                    useAmmo = true;
                    pierce = true;

                    speed = 2f;
                    lifetime = 18f;
                    damage = 10f;
                    pierceCap = 1;
                    ammoMultiplier = 1.5f;
                    statusDuration = 1.5f *60f;
                    buildingDamageMultiplier = 0f;
                    status = StatusEffects.corroded;
                    shootEffect = Fx.shootLiquid;
                    despawnEffect = hitEffect = Fx.steam;
                }};
            }});
        }};

        spirit = new AmmoLifeTimeUnitType("spirit"){{
            range = 30f;
            mineTier = 1;
            mineSpeed = 3.5f;
            itemCapacity = 30;
            ammoCapacity = 120;
            passiveAmmoDepletion = 0.1f;
            ammoDepletionAmount = 0.15f;
            /*Sound is not important so lower the volume a bit*/

            ammoType = lifeTimeDrill;
            constructor = UnitEntity::create;
            timedOutFx = NyfalisFxs.failedMake;
            timedOutSound = Sounds.dullExplosion;
            controller = u -> new NyfalisMiningAi();
            flying = miningDepletesAmmo = depleteOnInteractionUsesPassive = true;
            isEnemy = useUnitCap = ammoDepletesOverTime = depleteOnInteraction =false;
        }};

        phantom = new AmmoLifeTimeUnitType("phantom"){{
            speed = 2.5f;
            ammoCapacity = 60;

            weapons.add(new RepairBeamWeapon("repair-beam-weapon-center"){{
                shootY = 6f;
                shootCone = 20f;
                beamWidth = 0.3f;
                repairSpeed = 0.3f;
                x =  y = shootX = 0;
                fractionRepairSpeed = 0.03f;

                targetBuildings = useAmmo = true;
                controllable = false;
                bullet = new BulletType(){{
                    maxRange = 120f;
                    healPercent = 1f;
                }};
            }});

            constructor = UnitEntity::create;
            defaultCommand = NyfalisUnitCommands.nyfalisMendCommand;
            isEnemy = useUnitCap = ammoDepletesOverTime = depleteOnInteraction = false;
            flying = miningDepletesAmmo = depleteOnInteractionUsesPassive = canMend = canHealUnits =  targetAir = targetGround = singleTarget  = true;
        }};

        banshee = new LeggedWaterUnit("banshee"){{
            hitSize = 8f;
            speed = 0.5f;
            legCount = 4;
            health = 150;
            mineTier = 3;
            legLength = 9f;
            mineSpeed = 4f;
            legForwardScl = 0.6f;
            legMoveSpace = 1.4f;
            ammoCapacity = 300;

            ammoType = lifeTimeDrill;
            groundLayer = Layer.legUnit;
            constructor = LegsUnit::create;
            timedOutFx = NyfalisFxs.failedMake;
            timedOutSound = Sounds.dullExplosion;
            controller = u -> new NyfalisMiningAi();
            hovering = miningDepletesAmmo = depleteOnInteractionUsesPassive = true;
            isEnemy = useUnitCap = ammoDepletesOverTime = depleteOnInteraction = false;
        }};


        embryo = new AmmoLifeTimeUnitType("embryo"){{
            /*(trans) Egg if chan-version is made >;3c */
            constructor = UnitEntity::create;
            controller = u -> new AgressiveFlyingAi(true);

            flying = alwaysShootWhenMoving = true;
            playerControllable = useUnitCap = false;
            speed = 3f;
            ammoCapacity = 100;

            weapons.add(new Weapon(){{
                top = false;
                reload = 25f;
                shootCone = 30f;
                shootSound = Sounds.lasershoot;
                x = y = shootX = inaccuracy = 0f;
                bullet = new LaserBoltBulletType(6f, 10){{
                    lifetime = 30f;
                    healPercent = 5f;
                    homingPower = 0.03f;
                    buildingDamageMultiplier = 0.01f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                }};
            }});
        }};
        //endregion
        //region Nyfalis Core Units
        gnat = new NyfalisUnitType("gnat"){{
            armor = 1f;
            hitSize = 9f;
            speed = 2.4f;
            drag = 0.11f;
            health = 420;
            mineTier = 1;
            legCount = 0;
            /*Corner Engines only*/
            engineSize = -1;
            mineSpeed = 8.5f;
            buildSpeed = 0.5f;
            itemCapacity = 70;
            rotateSpeed = 4.5f;
            range = mineRange;
            legMoveSpace = 1.1f; //Limits world tiles movement
            boostMultiplier = 0.75f;
            buildBeamOffset = 4.2f;
            shadowElevation = 0.1f;
            researchCostMultiplier = 0f;
            groundLayer = Layer.legUnit - 1f;

            legPhysicsLayer = false;
            canBoost = allowLegStep = hovering = alwaysBoostOnSolid= customMineAi = true;
            constructor = LegsUnit::create;
            spawnStatus = StatusEffects.disarmed;
            ammoType = new PowerAmmoType(1000);
            defaultCommand = NyfalisUnitCommands.nyfalisMineCommand;
            mineItems = Seq.with(rustyIron, lead, scrap);
            setEnginesMirror(
                new UnitEngine(20.5f / 4f, 22 / 4f, 2.2f, 65f), //front
                new UnitEngine(23 / 4f, -22 / 4f, 2.2f, 315f)
            );
            parts.add(new HoverPart(){{
                mirror = false;
                radius = 13f;
                phase = 320f;
                layerOffset = -0.001f;
                color = Color.valueOf("5C9F62");
            }});

            weapons.add(
                new Weapon() {{
                    reload = 60*10;
                    x = y = shootX = shootY = 0;
                    shootStatus = StatusEffects.unmoving;
                    shootStatusDuration = shoot.firstShotDelay = Fx.heal.lifetime-1;
                    bullet = new HealOnlyBulletType(0,0) {{
                        spin = 3.5f;
                        drag = 0.9f;
                        lifetime = 10*60;
                        shrinkX = 25f/60f;
                        shrinkY = 35f/60f;
                        intervalBullets = 2;
                        intervalSpread = 180;
                        intervalRandomSpread = 90;
                        height = width = bulletInterval = healAmount = 20;

                        collidesTeam = true;
                        keepVelocity = false;
                        hitEffect = despawnEffect = Fx.heal;
                        backColor = frontColor = trailColor = lightColor = Pal.heal;

                        intervalBullet = new HealOnlyBulletType(4,-5, "olupis-diamond-bullet") {{
                            lifetime = 60;
                            trailLength = 10;
                            trailWidth = 1.5f;
                            healAmount = 20;
                            bulletInterval = 10;
                            homingPower = 0.09f;

                            collidesTeam = true;
                            keepVelocity = false;
                            hitEffect = despawnEffect = Fx.heal;
                            backColor = frontColor = trailColor = lightColor = Pal.heal;
                        }};
                    }};
                }},
                new Weapon(){{
                    reload = 24f;
                    x = shootY = 0f;
                    shootCone = 180f;
                    shootOnDeath = true;
                    mirror = controllable = aiControllable = false;
                    ejectEffect = Fx.none;
                    shootSound = Sounds.explosion;
                    bullet = new BulletType() {{
                        collides = hittable = collidesTiles = false;
                        instantDisappear = killShooter = collidesAir = true;
                        hitSound = Sounds.explosion;
                        hitEffect = Fx.pulverize;

                        splashDamage = 90f;
                        rangeOverride = 30f;
                        splashDamageRadius = 55f;
                        buildingDamageMultiplier = speed = 0f;
                    }};
                }}
            );
        }};

        phorid = new NyfalisUnitType("phorid"){{
            armor = 3f;
            hitSize = 10f;
            speed = 2.6f;
            drag = 0.11f;
            health = 560;
            mineTier = 2;
            legCount = 0;
            /*Corner Engines only*/
            engineSize = -1;
            rotateSpeed = 6f;
            mineSpeed = 9.5f;
            buildSpeed = 0.7f;
            itemCapacity = 80;
            range = mineRange;
            legMoveSpace = 1.3f; //Limits world tiles movement
            shadowElevation = 0.1f;
            buildBeamOffset = 4.2f;
            boostMultiplier = 0.75f;
            researchCostMultiplier = 0f;
            groundLayer = Layer.legUnit - 1f;

            legPhysicsLayer = false;
            canBoost = allowLegStep = hovering = alwaysBoostOnSolid = customMineAi = true;
            spawnStatus = StatusEffects.disarmed;
            constructor = LegsUnit::create;
            ammoType = new PowerAmmoType(1000);
            mineItems = Seq.with(rustyIron, lead, scrap);
            defaultCommand = NyfalisUnitCommands.nyfalisMineCommand;
            setEnginesMirror(
                    new UnitEngine(23.5f / 4f, 15 / 4f, 2.3f, 45f), //front
                    new UnitEngine(23 / 4f, -22 / 4f, 2.3f, 315f)
            );
            parts.add(new HoverPart(){{
                mirror = false;

                radius = 13f;
                phase = 320f;
                layerOffset = -0.001f;
                color = Color.valueOf("5C9F62");
            }});

            weapons.addAll(
                new Weapon() {{
                    reload = 60*10;
                    x = y = shootX = shootY = 0;
                    shootStatus = StatusEffects.unmoving;
                    shootStatusDuration = shoot.firstShotDelay = Fx.heal.lifetime-1;
                    bullet = new HealOnlyBulletType(0,-5) {{
                        spin = 3.7f;
                        drag = 0.9f;
                        lifetime = 10*60;
                        shrinkX = 25f/60f;
                        shrinkY = 35f/60f;
                        bulletInterval = 25;
                        intervalBullets = 2;
                        intervalSpread = 180;
                        intervalRandomSpread = 90;
                        height = width = healAmount = 20;

                        keepVelocity = false;
                        hitEffect = despawnEffect = Fx.heal;
                        backColor = frontColor = trailColor = lightColor = Pal.heal;
                        intervalBullet = new HealOnlyBulletType(5,0, "olupis-diamond-bullet") {{
                            lifetime = 60;
                            trailLength = 11;
                            trailWidth = 1.5f;
                            healAmount = 30;
                            bulletInterval = 10;
                            homingPower = 0.11f;

                            keepVelocity = false;
                            hitEffect = despawnEffect = Fx.heal;
                            backColor = frontColor = trailColor = lightColor = Pal.heal;
                        }};

                    }};
                }},
                new Weapon(){{
                    x = y = 0;
                    reload = 60f * 10f;
                    flipSprite = false;
                    shootStatus = StatusEffects.unmoving;
                    shootStatusDuration = Fx.heal.lifetime;
                    shoot.firstShotDelay = Fx.heal.lifetime-1;
                    bullet = new SpawnHelperBulletType(){{
                        hasParent = true;
                        shootEffect = Fx.shootBig;
                        spawnUnit = embryo;
                        //rangeOverride = mineRange;
                    }};
                }},
                new Weapon(){{
                    reload = 24f;
                    x = shootY = 0f;
                    shootCone = 180f;
                    shootOnDeath = true;
                    mirror = controllable = aiControllable = false;
                    ejectEffect = Fx.none;
                    shootSound = Sounds.explosion;
                    bullet = new BulletType() {{
                        collidesTiles = false;
                        collides = false;
                        hitSound = Sounds.explosion;

                        rangeOverride = 30f;
                        hitEffect = Fx.pulverize;
                        speed = 0f;
                        splashDamageRadius = 55f;
                        instantDisappear = true;
                        splashDamage = 90f;
                        killShooter = true;
                        hittable = false;
                        collidesAir = true;
                    }};
                }}
            );
        }};

        //added a death weapon
        //endregion
    }

    /*Common custom ammo types for the lifetime units*/
    public static void LoadAmmoType(){
        //Make them last long

        lifeTimeDrill = new AmmoType() {
            @Override
            public String icon() {
                return Iconc.production + "";
            }

            @Override
            public Color color() {
                return Pal.ammo;
            }

            @Override
            public Color barColor() {
                return Color.green;
            }

            @Override
            public void resupply(Unit unit) {}
        };

        lifeTimeWeapon = new AmmoType() {
            @Override
            public String icon() {
                return Iconc.commandAttack + "";
            }

            @Override
            public Color color() {
                return Pal.accent;
            }

            @Override
            public Color barColor() {
                return Pal.ammo;
            }

            @Override
            public void resupply(Unit unit) {}
        };

        lifeTimeSupport = new AmmoType() {
            @Override
            public String icon() {
                return Iconc.add + "";
            }

            @Override
            public Color color() {
                return Pal.ammo;
            }

            @Override
            public Color barColor() {
                return Color.green;
            }

            @Override
            public void resupply(Unit unit) {}
        };
    }

    public static void PostLoadUnits(){
        /*Blocks are null while loading units, so this exists for as a work around*/
        mite.displayFactory = Seq.with(NyfalisBlocks.hive);
        spirit.displayFactory = Seq.with(NyfalisBlocks.construct);
        phantom.displayFactory = Seq.with(NyfalisBlocks.construct);
        banshee.displayFactory = Seq.with(NyfalisBlocks.construct);
        aero.displayFactory = Seq.with(NyfalisBlocks.arialConstruct);
        venom.displayFactory = Seq.with(NyfalisBlocks.groundConstruct);

        porter.displayFactory = Seq.with(zoner, NyfalisBlocks.groundConstruct);

        zoner.displayFactory = Seq.with(porter);
        embryo.displayFactory = Seq.with(phorid);
    }


}
