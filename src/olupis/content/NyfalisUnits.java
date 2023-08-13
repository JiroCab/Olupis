package olupis.content;

import arc.graphics.Color;
import arc.math.geom.Rect;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.HugAI;
import mindustry.content.*;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.part.HoverPart;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.world.meta.BlockFlag;
import olupis.world.ai.*;
import olupis.world.entities.bullets.HealOnlyBulletType;
import olupis.world.entities.bullets.NoBoilLiquidBulletType;
import olupis.world.entities.units.*;

import static mindustry.content.Items.*;
import static olupis.content.NyfalisItemsLiquid.*;

public class NyfalisUnits {

    public static AmmoType lifeTimeDrill, lifeTimeWeapon;
    public static NyfalisUnitType
        /*Air units*/
        aero, striker, falcon, vortex, tempest,
        zoner, region, district, division, territory,

        /*segmented units*/
        venom, serpent, reaper, goliath,

        /*naval*/
        porter, essex, lexington, excess, nimitz,
        bay, blitz, crusader, torrent, vanguard,

        /*core units*/
        gnat,

        /*Misc/pending purpose units*/
         firefly
    ;
    public static AmmoLifeTimeUnitType
        mite,
        //yes its just Phasmophobia ghost types
        spirit, phantom,banshee, revenant, poltergeist
    ;

    public static void LoadUnits(){
        LoadAmmoType();

        //region Air Units
        //Aero -> decently quick and shoot a tiny constant beam, make it fixed and do 10dps
        aero = new NyfalisUnitType("aero"){{
            hitSize = 9f;
            health = 250f;
            speed = 3.6f;
            engineSize = 3f;
            rotateSpeed = 30f;
            itemCapacity = 30;
            engineOffset = 7f;
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
            hitSize = 12f;
            drag = 0.05f;
            accel = 0.07f;
            health = 350f;
            speed = 5.5f;
            armor = 3f;
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
                reload = shootCone = 15f;

                shootSound = Sounds.laser;
                top = alternate =  mirror = false;
                alwaysShootWhenMoving = true;
                bullet = new LightningBulletType(){{
                    damage = 13f;
                    drawSize = 55f;
                    pierceCap = 5;
                    lightningLength = lightningLengthRand = 12;
                    pierce = true;
                    shootEffect = Fx.hitLancer;
                    lightningColor = hitColor = Pal.regen;
                    lightningType = new BulletType(0.0001f, 0f){{
                        pierceCap = 5;
                        statusDuration = 10f;
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        hittable = false;
                        status = StatusEffects.shocked;
                        pierce = true;
                    }};
                }};
            }});
            weapons.add(new Weapon(){{
                x = 0f;
                reload = 30;
                inaccuracy = 180f;
                shootCone = 180f;
                minShootVelocity = 5f;

                ejectEffect = Fx.none;
                shootSound = Sounds.spark;
                ignoreRotation = alwaysShooting=  true;
                bullet = new LightningBulletType(){{
                    damage = 10;
                    shoot.shots = 2;
                    shoot.firstShotDelay = 0.2f;
                    buildingDamageMultiplier = 1.1f;
                    lightningLength = lightningLengthRand = 8;

                    status = StatusEffects.none;
                    shootEffect = Fx.hitLancer;
                    lightningColor = hitColor = Pal.regen;
                    parentizeEffects = autoTarget = autoFindTarget = true;
                    top = alternate =  mirror =  aiControllable = controllable = false;
                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.none;
                        hittable = false;
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
            mineSpeed = 2.5f;
            engineSize = 1.8f;
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

            lowAltitude = flying = true;
            constructor = UnitEntity::create;
            weapons.add(new Weapon("olupis-zoner-weapon"){{
                top = alternate = false;
                x = -1.8f;
                y = -1f;
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
                    backColor = rustyIron.color.lerp(Pal.bulletYellowBack, 0.5f);
                    hitEffect = despawnEffect = NyfalisFxs.hollowPointHitSmall;
                }};
            }});
        }};

        //endregion
        //region Ground Units
        venom = new SnekUnitType("venom"){{
            constructor = CrawlUnit::create;
            health = 20000;
            armor = 12;
            hitSize = 9f;
            legMoveSpace = 1.1f;
            drownTimeMultiplier = 4f;
            segments = 5;
            omniMovement = drawBody = false;
            crushDamage = 2f;
            aiController = HugAI::new;
            allowLegStep = true;

            segmentScl = 8f;
            segmentPhase = 10f;
            crawlSlowdownFrac = 1f;
            speed = 2.5f;
            rotateSpeed = 8f;

            weapons.add(new SnekWeapon("olupis-missiles-mount-teamed"){{
                x = 0f;
                y = 8f;
                reload = 15f;
                weaponSegmentParent = 3;
                mirror = false;
                rotate = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 9){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                }};
            }}, new SnekWeapon("olupis-missiles-mount-teamed"){{
                x = 0f;
                y = -11f;
                reload = 15f;
                weaponSegmentParent = 1;
                mirror = false;
                rotate = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 9){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                }};
            }});
        }};

        //endregion
        //region Naval Units
        porter = new NyfalisUnitType("porter"){{
            health = 850;
            armor = 6f;
            hitSize = 12f;
            speed = 0.75f;
            itemCapacity = 0;
            treadPullOffset = 3;
            rotateSpeed = 3.5f;
            researchCostMultiplier = 0f;

            constructor = UnitWaterMove::create;
            treadRects = new Rect[]{new Rect(12 - 32f, 7 - 32f, 14, 51)};
            abilities.add(new UnitSpawnAbility(zoner, 60f * 15f, 0, 2.5f));
        }};
        //endregion Units
        //region Nyfalis Limited LifeTime Units
        mite = new AmmoLifeTimeUnitType("mite"){{
            armor = 10f;
            hitSize = 9;
            range = 45f;
            health = 100;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.04f;
            lightRadius = 15f;
            itemCapacity = 0;
            lightOpacity = 50f;
            ammoDepleteAmount = 0.55f;

            flying = targetGround = targetAir = true;
            playerControllable  = logicControllable = useUnitCap = false;
            constructor = UnitEntity::create;
            controller = u -> new SearchAndDestroyFlyingAi();
            targetFlags = new BlockFlag[]{BlockFlag.factory, null};
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
                    buildingDamageMultiplier = 0.01f;
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
            ammoDepleteAmount = 0.15f;
            ammoDepleteAmountPassive = 0.1f;

            ammoType = lifeTimeDrill;
            constructor = UnitEntity::create;
            controller = u -> new NyfalisMiningAi();
            flying = miningDepletesAmmo = true;
            isEnemy = useUnitCap = ammoDepletesOverTime = false;
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
            shadowElevation = 0.1f;
            buildBeamOffset = 4.2f;
            boostMultiplier = 0.75f;
            researchCostMultiplier = 0f;
            groundLayer = Layer.legUnit - 1f;
            spawnStatusDuration =  Fx.heal.lifetime - 1f;

            legPhysicsLayer = false;
            canBoost = allowLegStep = hovering = true;
            spawnStatus = StatusEffects.disarmed;
            constructor = LegsUnit::create;
            controller = u -> new NyfalisMiningAi();
            ammoType = new PowerAmmoType(1000);
            mineItems = Seq.with(rustyIron, lead, scrap);
            setEnginesMirror(
                new UnitEngine(21 / 4f, 19 / 4f, 2.2f, 45f),
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
                        x = y = 0;
                        reload = 60*10;
                        shootStatus = StatusEffects.unmoving;
                        shootStatusDuration = Fx.heal.lifetime;
                        shoot.firstShotDelay = Fx.heal.lifetime-1;
                        bullet = new BasicBulletType(0,-5) {{
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
                                trailLength = 15;
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
                    }}
            );
        }};
        //endregion
    }

    /*Common custom ammo types for the lifetime units*/
    public static void LoadAmmoType(){

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
    }

    public static void PostLoadUnits(){
        /*Blocks are null while loading units, so this exists for as a work around*/
        mite.displayFactory = Seq.with(NyfalisBlocks.hive);
        spirit.displayFactory = Seq.with(NyfalisBlocks.construct);
        aero.displayFactory = Seq.with(NyfalisBlocks.arialConstruct);
        zoner.displayFactory = Seq.with(porter);
    }


}
