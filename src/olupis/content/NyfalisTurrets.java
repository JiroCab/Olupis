package olupis.content;

import arc.graphics.Color;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSummon;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BlockFlag;
import olupis.world.blocks.ItemUnitTurret;
import olupis.world.entities.bullets.NoBoilLiquidBulletType;
import olupis.world.entities.bullets.SpawnHelperBulletType;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisUnits.mite;

public class NyfalisTurrets {

    public static void LoadTurrets(){
        //region Turrets
        corroder = new LiquidTurret("corroder"){{ //architronito
            ammo(
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    status = StatusEffects.corroded;
                    layer = Layer.bullet -2f;

                    speed = 5.5f;
                    damage = 15;
                    drag = 0.008f;
                    lifetime = 19.5f;
                    rangeChange = 15f;
                    ammoMultiplier = 5f;
                    statusDuration = 60f * 2;
                }},
                steam, new NoBoilLiquidBulletType(steam){{
                        evaporatePuddles = pierce = true;
                        status = StatusEffects.corroded;

                        speed = 8f;
                        drag = 0.009f;
                        lifetime = 12f;
                        damage = 18f;
                        pierceCap = 1;
                        ammoMultiplier = 3f;
                        statusDuration = 60f * 5;
                    }}
            );
            drawer = new DrawTurret("iron-"){{
                targetAir = true;

                size = 2;
                recoil = 1;
                range = 90f;
                fogRadius = 15;
                health = 1500;
                shootCone = 50f;
                liquidCapacity = 5f;
                shootY = reload = 10f;

                parts.addAll(
                    new RegionPart("-barrel"){{
                        mirror = false;
                        y = 1f;
                        layerOffset = -0.1f;
                        progress = PartProgress.recoil;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                    }},  new RegionPart("-front-wing"){{
                        mirror = true;
                        layerOffset = -0.1f;
                        progress = PartProgress.warmup;
                        moves.add(new PartMove(PartProgress.recoil, 0f, 0, -12f));
                    }}, new RegionPart("-back-wing"){{
                        mirror = true;
                        layerOffset = -0.1f;
                        progress = PartProgress.smoothReload;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -1f, 12f));
                    }}
                );
            }};
            loopSound = Sounds.steam;
            consumePower(1f);
            outlineColor = nyfalisBlockOutlineColour;
            researchCost = with(rustyIron, 100, lead, 100);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            requirements(Category.turret, with(rustyIron, 50, lead, 10));
        }};

        avenger = new ItemTurret("avenger"){{
            targetAir = true;
            targetGround = false;
            size = 3;
            reload = 25f;
            range = 250f;
            minWarmup = 0.96f;
            shootY = shootX= 0f;
            shootWarmupSpeed = 0.11f;
            warmupMaintainTime = 1f;

            ammo(
                lead, new MissileBulletType(4.6f, 32f){{
                    width = 6f;
                    height = 10.5f;
                    shrinkX = 0;
                    lifetime = 60f;
                    homingPower = 0.4f;
                    homingRange = 150f;
                    backColor = trailColor = lead.color;
                    collidesAir = true;
                    collidesGround = false;
                    hitEffect = NyfalisFxs.hollowPointHit;
                    knockback = 0.5f;
                    status = StatusEffects.slow;
                    statusDuration = 25f;
                    splashDamage = 10f;
                    splashDamageRadius = 25f * 0.75f;
                }}
            );
            limitRange(1.5f);
            shootSound = Sounds.missile;
            shootEffect = Fx.shootSmallSmoke;
            drawer = new DrawRegion("");
            requirements(Category.turret, with(rustyIron, 10, lead, 50));
        }};

        dissolver = new LiquidTurret("dissolver"){{ //architonnerre
            targetAir = true;

            recoil = 0.2f;
            reload = 5f;
            range = 130f;
            shootCone = 50f;
            health = 2500;
            size = 3;

            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        size = 3;
                        speed = 5.8f;
                        damage = 24;
                        drag = 0.0009f;
                        lifetime = 28.5f;
                        rangeChange = 40f;
                        ammoMultiplier = 4f;
                        statusDuration = 60f * 2;
                        hitSize = puddleSize = 7f;

                        status = StatusEffects.corroded;
                        layer = Layer.bullet -2f;
                    }},
                    steam, new NoBoilLiquidBulletType(steam){{
                        collidesAir = pierce = evaporatePuddles = true;

                        hitSize = 7f;
                        speed = 8.8f;
                        damage = 22f;
                        drag = 0.0009f;
                        lifetime = 14.5f;
                        ammoMultiplier = 3f;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                    }}
            );
            loopSound = Sounds.steam;
            consumePower(1.5f);
            outlineColor = nyfalisBlockOutlineColour;
            drawer = new DrawTurret("iron-");
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            requirements(Category.turret, with(iron, 50, lead, 50));
        }};

        shredder = new ItemTurret("shredder"){{
            //TODO: rework, shotgun/tri shot, home/ricochets  on hit, BTD6 quincy style
            targetAir = false;

            size = 3;
            armor = 5;
            health = 750;
            reload = 60f;
            range = 160;
            shootCone = 15f;
            rotateSpeed = 10f;
            shootY = Vars.tilesize * size;
            ammoUseEffect = Fx.casing1;
            researchCostMultiplier = 0.05f;
            outlineColor = nyfalisBlockOutlineColour;

            limitRange(1f);
            coolant = consumeCoolant(0.1f);
            researchCost = with(lead, 3000, iron, 3000, graphite, 3000);
            shoot = new ShootSummon(0f, 0f, 0f, 0f);
            requirements(Category.turret, with(iron, 100, lead, 20, graphite, 20));
            ammo(
                    //TODO: Some how ignore Allied Non-Solids??? (ex: mines & conveyors)
                    rustyIron, new BasicBulletType(2.5f, 11){{
                        collidesTeam = true;
                        collideTerrain = collidesAir = false;
                        status = StatusEffects.slow;
                        statusDuration = 60f * 2f;
                        width = 40f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = pierceCap = 2;
                        knockback= 3f;
                        frontColor = backColor = Color.valueOf("ea8878");
                    }},
                    iron, new BasicBulletType(3f, 23){{
                        collidesTeam = collideTerrain = true;
                        collidesAir = false;
                        status = StatusEffects.slow;
                        statusDuration = 60f * 3f;
                        width = 40f;
                        height = 11f;
                        lifetime = 50f;
                        ammoMultiplier = 2;
                        pierceCap = 3;
                        knockback = 3f;
                        frontColor = backColor = Color.valueOf("ea8878");
                    }}
            );
        }};

        hive = new ItemUnitTurret("hive"){{
            size = 4;
            shootY = 0f;
            range = 650;
            reload = 600f;
            shootSound = Sounds.respawn;

            ammo(
                silicon, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    spawnUnit = mite;
                }}
            );
            commandable = false;
            playerControllable = true;
            researchCost = with(lead, 1500, silicon, 1500,  iron, 1500);
            requirements(Category.turret, with(iron, 100, lead, 30, silicon, 30));
        }};

        //TODO: Escalation - A early game rocket launcher that acts similarly to the scathe but with lower range and damage. (Decent rate of fire, weak against high health single targets, slow moving rocket, high cost but great AOE)
        //TODO: Blitz (Recursor) - A recursive mortar turret that shoots long ranged recursive shells at the enemy (Has Really low rate of fire, high range, shells explode into multiple more shells on impact)
        //TODO:Shatter - A weak turret that shoots a spray of glass shards at the enemy. (High rate of fire, low damage, has pierce, very low defense, low range)

        //TODO: Aegis AA SAMM Turrets (later game)

        //endregion
    }
}
