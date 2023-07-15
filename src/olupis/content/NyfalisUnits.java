package olupis.content;

import arc.graphics.Color;
import arc.math.geom.Rect;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.content.*;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.HoverPart;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.TankUnitType;
import mindustry.world.meta.BlockFlag;
import olupis.world.ai.NyfalisMiningAi;
import olupis.world.ai.SearchAndDestroyFlyingAi;
import olupis.world.entities.bullets.HealOnlyBulletType;
import olupis.world.entities.bullets.NoBoilLiquidBulletType;
import olupis.world.entities.units.AmmoLifeTimeUnitType;
import olupis.world.entities.units.NyfalisUnitType;

import static mindustry.content.Items.*;
import static olupis.content.NyfalisItemsLiquid.*;

public class NyfalisUnits {

    public static AmmoType lifeTimeDrill, lifeTimeWeapon;
    public static UnitType
        /*Air units*/
        aero, striker, falcon, vortex, tempest,
        zoner,

        /*segmented units*/
        venom, serpent, reaper, goliath,

        /*core units*/
        gnat,

        /*Misc/pending purpse units*/
        porter, firefly
    ;
    public static AmmoLifeTimeUnitType
        mite,
        spirit
    ;
    //TODO: Aero -> decently quick and shoot a tiny constant beam, make it fixed and do 10dps
    //TODO: Striker ->pretty quick, maybe twice as fast as a flare, and shoots arc shots, like the Javelin from v5

    public static void LoadUnits(){
        LoadAmmoType();

        //region Nyfalis Regular Units
        zoner = new NyfalisUnitType("zoner"){{
            constructor = UnitEntity::create;
            lowAltitude = flying = true;

            health = 220f;
            hitSize = 11f;
            speed = 3.55f;
            rotateSpeed = 19f;
            drag = 0.05f;
            accel = 0.11f;
            itemCapacity = 70;

            engineOffset = 5.5f;
            engineSize = 2f;

            weapons.add(new Weapon("olupis-zoner-weapon"){{
                top = alternate = false;

                reload = shootCone = 15f;
                x = -1.8f;
                y = -1f;
                inaccuracy = 3f;

                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(2.5f, 5){{
                    lifetime = 60f;
                    homingPower = 0.04f;
                    width = 7;
                    height = 9f;

                    smokeEffect = Fx.shootSmallSmoke;
                    shootEffect = Fx.none;
                }};
            }});

        }};

        firefly = new NyfalisUnitType("firefly"){{
            constructor = UnitTypes.mono.constructor;
            //there's no reason to command monos anywhere. it's just annoying.
            //haha, no
            //controller = u -> new MinerAI();
            defaultCommand = UnitCommand.mineCommand;

            flying = true;
            isEnemy = false;

            health = 100;
            speed = 1.5f;
            drag = 0.06f;
            accel = 0.12f;
            mineTier = 1;
            mineSpeed = 2.5f;
            range = 50f;

            engineSize = 1.8f;
            engineOffset = 5.7f;

            ammoType = new PowerAmmoType(500);
        }};

        porter = new TankUnitType("porter"){{
            constructor = UnitTypes.risso.constructor;

            health = 850;
            armor = 6f;
            hitSize = 12f;
            speed = 0.75f;
            rotateSpeed = 3.5f;
            itemCapacity = 0;
            researchCostMultiplier = 0f;

            treadPullOffset = 3;
            treadRects = new Rect[]{new Rect(12 - 32f, 7 - 32f, 14, 51)};

            abilities.add(new UnitSpawnAbility(zoner, 60f * 15f, 1, 0));
        }};
        //endregion

        //region Nyfalis Limited LifeTime Units
        mite = new AmmoLifeTimeUnitType("mite"){{
            constructor = UnitEntity::create;
            controller = u -> new SearchAndDestroyFlyingAi();
            ammoDepleteAmount = 0.55f;
            lightRadius = 15f;
            lightOpacity = 50f;

            flying = targetGround = targetAir = true;
            playerControllable  = logicControllable = useUnitCap = false;

            health = 80;
            armor = 5;
            hitSize = 9;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.04f;
            range = 45f;
            itemCapacity = 10;


            targetFlags = new BlockFlag[]{BlockFlag.factory, null};
            weapons.add(new Weapon(){{
                y = x = 0f;
                reload = 10f;
                shootCone = 15f;
                targetSwitchInterval = 60f;
                targetInterval = 30f;

                shootSound = Sounds.pew;
                ammoType = lifeTimeWeapon;
                /*Gave up using LiquidBulletType*/
                bullet = new NoBoilLiquidBulletType(steam){{
                    useAmmo = true;
                    pierce = true;
                    damage = 10f;

                    speed = 2f;
                    lifetime = 18f;
                    pierceCap = 1;
                    ammoMultiplier = 1.5f;
                    status = StatusEffects.corroded;
                    statusDuration = 1.5f *60f;

                    shootEffect = Fx.shootLiquid;
                    despawnEffect = hitEffect = Fx.steam;
                }};
            }});
        }};

        spirit = new AmmoLifeTimeUnitType("spirit"){{
            constructor = UnitEntity::create;
            controller = u -> new NyfalisMiningAi();

            flying = miningDepletesAmmo = true;
            isEnemy = useUnitCap = ammoDepletesOverTime = false;
            mineTier = 1;
            mineSpeed = 3.5f;
            range = 30f;
            ammoCapacity = 100;
            itemCapacity = 30;
            ammoDepleteAmount = 0.2f;


            ammoType = lifeTimeDrill;
        }};
        //endregion

        //region Nyfalis Core Units

        gnat = new NyfalisUnitType("gnat"){{
            constructor = LegsUnit::create;
            controller = u -> new NyfalisMiningAi();
            ammoType = new PowerAmmoType(1000);
            mineItems = Seq.with(rustyIron, lead, Items.scrap);

            legPhysicsLayer = false;
            canBoost = allowLegStep = hovering = true;

            armor = 1f;
            hitSize = 9f;
            speed = 2.4f;
            drag = 0.11f;
            health = 420;
            mineTier = 1;
            rotateSpeed = 4.5f;
            mineSpeed = 8f;
            buildSpeed = 0.5f;
            itemCapacity = 70;
            range = mineRange;
            boostMultiplier = 0.75f;
            researchCostMultiplier = 0f;
            groundLayer = Layer.legUnit - 1f;

            legCount = 0;
            legMoveSpace = 1.1f; //Limits world tiles movement
            shadowElevation = 0.1f;
            buildBeamOffset = 4.2f;
            /*Corner Engines only*/
            engineSize = -1;
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
                        reload = 60*10;
                        x = y = 0;
                        shootStatus = StatusEffects.unmoving;
                        shootStatusDuration = Fx.heal.lifetime;
                        shoot.firstShotDelay = Fx.heal.lifetime-1;
                        bullet = new BasicBulletType(0,-5) {{
                            collidesTeam = true;
                            keepVelocity = false;
                            backColor = frontColor = trailColor = lightColor = Pal.heal;
                            chargeEffect = hitEffect = despawnEffect = Fx.heal;

                            lifetime = 10*60;
                            spin = 3.5f;
                            drag = 0.9f;
                            shrinkX = 25f/60f;
                            shrinkY = 35f/60f;
                            intervalBullets = 2;
                            intervalSpread = 180;
                            intervalRandomSpread = 90;
                            height = width = bulletInterval = healAmount = 20;

                            intervalBullet = new HealOnlyBulletType(4,-5, "olupis-diamond-bullet") {{
                                collidesTeam = true;
                                keepVelocity = false;
                                hitEffect = despawnEffect = Fx.heal;
                                backColor = frontColor = trailColor = lightColor = Pal.heal;

                                lifetime = 60;
                                trailLength = 15;
                                trailWidth = 1.5f;
                                healAmount = 20;
                                bulletInterval = 10;
                                homingPower = 0.09f;
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
        mite.displayedBlocks = Seq.with(NyfalisBlocks.hive);
        spirit.displayedBlocks = Seq.with(NyfalisBlocks.construct);
    }


}
