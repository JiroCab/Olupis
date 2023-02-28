package olupis.content;

import arc.graphics.Color;
import arc.math.geom.Rect;
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

            outlineColor = olupisUnitOutLineColour;

            speed = 2.4f;
            canBoost = true;
            boostMultiplier = 0.75f;
            rotateSpeed = 4f;

            hitSize = 9f;
            health = 420;
            armor = 1f;
            legCount = 0;
            buildBeamOffset = 4.2f;

            drag = 0.11f;
            buildSpeed = 0.5f;

            legMoveSpace = 1.1f; //Limits world tiles movement
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;

            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            researchCostMultiplier = 0f;

            itemCapacity = 70;
            mineTier = 1;
            mineSpeed = 5.5f;

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
        }};

        //endregion
        //region Olupis Units

        mite = new UnitType("mite"){{
        constructor = UnitTypes.flare.constructor;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.04f;
            flying = true;
            health = 50;
            engineOffset = 5.75f;
            //targetAir = false;
            targetFlags = new BlockFlag[]{BlockFlag.generator, null};
            hitSize = 9;
            itemCapacity = 10;
            range = 35f;

            outlineColor = olupisUnitOutLineColour;

            weapons.add(new Weapon(){{
                y = 0f;
                x = 0f;
                reload = 10f;

                shootSound = Sounds.pew;
                /*Gave up using LiquidBulletType*/
                bullet = new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                    shootEffect = Fx.shootLiquid;
                    despawnEffect = Fx.steam;
                    hitEffect = Fx.steam;

                    ammoMultiplier = 2;
                    status = StatusEffects.corroded;
                    statusDuration = 2f *60f;
                    pierce = true;
                    pierceCap = 1;
                    speed = 2f;
                    lifetime = 18f;
                }};
            }});
        }};

        zoner = new UnitType("zoner"){{
            constructor = UnitTypes.flare.constructor;

            lowAltitude = true;
            flying = true;
            drag = 0.05f;
            speed = 3.55f;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 70;
            health = 220f;
            engineOffset = 5.5f;
            engineSize = 2f;
            hitSize = 11f;

            outlineColor = olupisUnitOutLineColour;

            weapons.add(new Weapon("olupis-zoner-weapon"){{
                top = false;
                reload = 15f;
                x = -1.8f;
                y = -1f;
                inaccuracy = 3f;
                ejectEffect = Fx.casing1;
                alternate = false;

                bullet = new BasicBulletType(2.5f, 5){{
                    width = 7;
                    height = 9f;
                    lifetime = 60f;
                    smokeEffect = Fx.shootSmallSmoke;
                    shootEffect = Fx.none;
                    homingPower = 0.04f;
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
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            range = 50f;
            isEnemy = false;

            ammoType = new PowerAmmoType(500);

            outlineColor = olupisUnitOutLineColour;

            mineTier = 1;
            mineSpeed = 2.5f;
        }};

        porter = new TankUnitType("porter"){{
            constructor = UnitTypes.risso.constructor;
            hitSize = 12f;
            treadPullOffset = 3;
            speed = 0.75f;
            rotateSpeed = 3.5f;
            health = 850;
            armor = 6f;
            itemCapacity = 0;
            treadRects = new Rect[]{new Rect(12 - 32f, 7 - 32f, 14, 51)};
            researchCostMultiplier = 0f;
            float spawnTime = 60f * 15f;
            abilities.add(new UnitSpawnAbility(zoner, spawnTime, 1, 0));
            outlineColor = olupisUnitOutLineColour;
        }};
        //endregion
    }


}
