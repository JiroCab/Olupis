package olupis.content;

import arc.graphics.Color;
import arc.math.geom.Rect;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.graphics.Pal;
import olupis.world.NoBoilLiquidBulletType;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.MinerAI;
import mindustry.content.*;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.HoverPart;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.TankUnitType;
import mindustry.world.meta.BlockFlag;

public class OlupisUnits {

    public static UnitType
        gnat,
        mite,
        porter,
        firefly,
        zoner
    ;

    public static final Color olupisUnitOutLineColour = Color.valueOf("371404");

    public static void LoadUnits(){
        //region Core Units
        gnat = new UnitType("gnat"){{
            constructor = UnitTypes.merui.constructor;
            controller = u -> new MinerAI();

            canBoost = allowLegStep = hovering = true;
            legPhysicsLayer = false;

            health = 420;
            armor = 1f;
            hitSize = 9f;
            speed = 2.4f;
            rotateSpeed = 4f;
            boostMultiplier = 0.75f;
            drag = 0.11f;
            buildSpeed = 0.5f;
            mineTier = 1;
            mineSpeed = 5.5f;
            groundLayer = Layer.legUnit - 1f;
            researchCostMultiplier = 0f;
            itemCapacity = 70;

            outlineColor = olupisUnitOutLineColour;
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

                             lifetime = 60*60;
                             height = width = 20;
                             spin = 4;
                             bulletInterval = 15;
                             healAmount = 10;
                             drag = 0.9f;

                             backColor = frontColor = trailColor = lightColor = Pal.heal;
                             chargeEffect = hitEffect = despawnEffect = Fx.heal;
                             shrinkX = 20f/60f;
                             shrinkY = 30f/60f;

                             intervalBullets = 2;
                             intervalSpread = 180;
                             intervalRandomSpread = 90;
                             intervalBullet = new BasicBulletType(4,-5) {{
                                 collidesTeam = true;
                                 keepVelocity = false;

                                 lifetime = 60;
                                 bulletInterval = healAmount = 10;

                                 backColor = frontColor = trailColor = lightColor = Pal.heal;
                                 trailWidth = 2;
                                 trailLength = 20;
                                 hitEffect = despawnEffect = Fx.heal;
                             }};

                         }};
                    }}
            );
        }};

        //endregion
        //region Olupis Units

        mite = new UnitType("mite"){{
            constructor = UnitTypes.flare.constructor;

            flying = true;

            health = 50;
            hitSize = 9;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.04f;
            range = 35f;
            itemCapacity = 10;
            //targetAir = false;

            engineOffset = 5.75f;
            outlineColor = olupisUnitOutLineColour;

            targetFlags = new BlockFlag[]{BlockFlag.generator, null};
            weapons.add(new Weapon(){{
                y = x = 0f;
                reload = 10f;

                shootSound = Sounds.pew;
                /*Gave up using LiquidBulletType*/
                bullet = new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                    pierce = true;

                    speed = 2f;
                    lifetime = 18f;
                    pierceCap = 1;
                    ammoMultiplier = 2;
                    status = StatusEffects.corroded;
                    statusDuration = 2f *60f;

                    shootEffect = Fx.shootLiquid;
                    despawnEffect = Fx.steam;
                    hitEffect = Fx.steam;
                }};
            }});
        }};

        zoner = new UnitType("zoner"){{
            constructor = UnitTypes.flare.constructor;

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
            outlineColor = olupisUnitOutLineColour;

            weapons.add(new Weapon("olupis-zoner-weapon"){{
                top = alternate = false;

                reload = 15f;
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

        firefly = new UnitType("firefly"){{
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
            outlineColor = olupisUnitOutLineColour;

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
            outlineColor = olupisUnitOutLineColour;

            abilities.add(new UnitSpawnAbility(zoner, 60f * 15f, 1, 0));
        }};
        //endregion
    }


}
