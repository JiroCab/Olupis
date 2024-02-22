package olupis.content;

import arc.graphics.Color;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BlockFlag;
import olupis.world.blocks.defence.ItemUnitTurret;
import olupis.world.consumer.ConsumeLubricant;
import olupis.world.entities.bullets.*;

import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisUnits.mite;

public class NyfalisTurrets {

    public static void LoadTurrets(){

        //region Turrets
        corroder = new LiquidTurret("corroder"){{ //architronito
            inaccuracy = 8.5f;
            rotateSpeed = 3f;

            ammo(
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    status = StatusEffects.corroded;
                    layer = Layer.bullet -2f;
                    trailColor = hitColor;

                    speed = 5.5f;
                    damage = 7.8f;
                    drag = 0.008f;
                    lifetime = 19.7f;
                    rangeChange = 15f;
                    ammoMultiplier = 3f;
                    trailInterval = trailParam = 1.5f;
                    buildingDamageMultiplier = 0.5f;

                    statusDuration = 60f * 2;
                }},
                steam, new NoBoilLiquidBulletType(steam){{
                        evaporatePuddles = pierce = true;
                        status = StatusEffects.corroded;
                        trailColor = hitColor;

                        speed = 8f;
                        drag = 0.009f;
                        lifetime = 12f;
                        damage = 10.3f;
                        pierceCap = 1;
                        ammoMultiplier = 3f;
                        statusDuration = 60f * 5;
                        trailInterval = trailParam = 1.5f;
                        buildingDamageMultiplier = 0.5f;
                    }}
            );
            drawer = new DrawTurret("iron-"){{
                targetAir = true;

                size = 2;
                recoil = 1;
                shootY = 7f;
                range = 90f;
                health = 1500;
                fogRadius = 13;
                shootCone = 50f;
                coolantMultiplier = 2.5f;
                liquidCapacity = reload = 5f;

                parts.addAll(
                    new RegionPart("-barrel"){{
                        mirror = false;
                        under = true;
                        progress = PartProgress.recoil;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                    }},  new RegionPart("-front-wing"){{
                        mirror = true;
                        under = true;
                        layerOffset = -0.1f;
                        progress = PartProgress.warmup;
                        moves.add(new PartMove(PartProgress.recoil, 0f, 0, -12f));
                    }}, new RegionPart("-back-wing"){{
                        mirror = true;
                        under = true;
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
            requirements(Category.turret, with(rustyIron, 40, lead, 20));

        }};

        avenger = new ItemTurret("avenger"){{
            targetAir = true;
            targetGround = false;
            size = 3;
            reload = 25f;
            range = 250f;
            minWarmup = 0.96f;
            shootY = shootX= 0f;
            warmupMaintainTime = 1f;
            fogRadiusMultiplier = 0.75f;
            shootWarmupSpeed = 0.11f;

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
            researchCost = with(lead, 100, rustyIron, 100);
            coolant = consume(new ConsumeLubricant(30f / 60f));
            requirements(Category.turret, with(rustyIron, 20, lead, 40));
        }};

        shredder = new ItemTurret("shredder"){{
            //TODO: check for clear path to unit
            targetAir = false;

            size = 3;
            armor = 5;
            health = 750;
            reload = 70f;
            range = 160;
            shootCone = 15f;
            rotateSpeed = 10f;
            coolantMultiplier = 6f;
            ammoUseEffect = Fx.casing1;
            researchCostMultiplier = 0.05f;
            shootY = (Vars.tilesize * size) - 10f;
            outlineColor = nyfalisBlockOutlineColour;

            limitRange(1f);
            shootSound = Sounds.shootBig;
            drawer = new DrawTurret("iron-");
            shoot = new ShootSpread(3, 15);
            smokeEffect = Fx.shootSmokeSquareSparse;
            researchCost = with(lead, 1000, iron, 850, graphite, 850);
            requirements(Category.turret, with(iron, 100, lead, 20, graphite, 20));
            coolant = consume(new ConsumeLubricant(15f / 60f));
            ammo(
                rustyIron, new RollBulletType(2.5f, 18){{
                    status = StatusEffects.slow;
                    collidesAir = false;
                    height = 9f;
                    width = 40f;
                    lifetime = 60f;
                    knockback= 3f;
                    homingRange = 100;
                    homingPower = 0.2f;
                    reloadMultiplier = 1.1f;
                    statusDuration = 60f * 1.1f;
                    buildingDamageMultiplier = 0.4f;
                    ammoMultiplier = pierceCap = 2;
                    shootEffect = smokeEffect = Fx.none;
                    frontColor = new Color().set(rustyIron.color).lerp(Pal.bulletYellowBack, 0.3f);
                    backColor = new Color().set(rustyIron.color).lerp(Pal.bulletYellow, 0.3f);
                }},
                iron, new RollBulletType(3f, 30){{
                    status = StatusEffects.slow;
                    collidesAir = false;
                    width = 40f;
                    height = 11f;
                    lifetime = 50f;
                    pierceCap = 3;
                    knockback = 3f;
                    ammoMultiplier = 2;
                    homingPower = 0.2f;
                    homingRange = 100f;
                    statusDuration = 60f * 2f;
                    buildingDamageMultiplier = 0.4f;
                    shootEffect = smokeEffect = Fx.none;
                    frontColor = new Color().set(iron.color).lerp(Pal.bulletYellowBack, 0.1f);
                    backColor = new Color().set(iron.color).lerp(Pal.bulletYellow, 0.2f);
                }},
                quartz, new RollBulletType(3f, 37){{
                    status = StatusEffects.slow;
                    collidesAir = false;
                    width = 40f;
                    height = 11f;
                    lifetime = 50f;
                    fragBullets = 4;
                    knockback = 3.5f;
                    ammoMultiplier = 2;
                    homingPower = 0.2f;
                    homingRange = 100f;
                    reloadMultiplier = 0.9f;
                    statusDuration = 60f * 2f;
                    buildingDamageMultiplier = 0.35f;
                    fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 20f;
                        despawnEffect = Fx.none;
                        frontColor = quartz.color;
                        backColor = Pal.gray;
                    }};
                    shootEffect = smokeEffect = Fx.none;
                    frontColor = new Color().set(quartz.color).lerp(Pal.bulletYellowBack, 0.1f);
                    backColor = new Color().set(quartz.color).lerp(Pal.bulletYellow, 0.3f);
                }}
            );
        }};

        hive = new ItemUnitTurret("hive"){{
            size = 4;
            shootY = 0f;
            range = 650;
            reload = 600f;
            maxAmmo  = 20;
            fogRadiusMultiplier = 0.5f;
            shootSound = Sounds.respawn;

            ammo(
                silicon, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    spawnUnit = mite;
                }}
            );
            requiredItems = requiredAlternate = with();
            commandable = configurable = rallyAim = false;
            playerControllable = drawOnTarget = true;
            researchCost = with(lead, 1500, silicon, 1500,  iron, 1500);
            requirements(Category.turret, with(iron, 100, lead, 30, silicon, 30));
        }};

        dissolver = new LiquidTurret("dissolver"){{ //architonnerre
            targetAir = true;

            size = 3;
            reload = 5f;
            recoil = 0.2f;
            range = 130f;
            health = 1700;
            inaccuracy = 6f;
            shootCone = 50f;

            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        size = 3;
                        speed = 5.4f;
                        damage = 28;
                        drag = 0.0009f;
                        lifetime = 29.5f;
                        rangeChange = 40f;
                        ammoMultiplier = 4f;
                        statusDuration = 60f * 2;
                        layer = Layer.bullet -2f;
                        hitSize = puddleSize = 7f;
                        trailInterval = trailParam = 1.5f;
                        buildingDamageMultiplier = 0.4f;

                        trailColor = hitColor;
                        status = StatusEffects.corroded;
                    }},
                    steam, new NoBoilLiquidBulletType(steam){{
                        collidesAir = pierce = evaporatePuddles = true;
                        trailColor = hitColor;

                        hitSize = 7f;
                        speed = 8.8f;
                        damage = 27f;
                        drag = 0.0009f;
                        lifetime = 14.5f;
                        ammoMultiplier = 3f;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                        trailInterval = trailParam = 1.5f;
                        buildingDamageMultiplier = 0.4f;
                    }},
                    Liquids.slag, new LiquidBulletType(Liquids.slag){{
                        size = 3;
                        speed = 5.8f;
                        damage = 17;
                        drag = 0.0009f;
                        lifetime = 27.5f;
                        rangeChange = 20f;
                        ammoMultiplier = 4f;
                        statusDuration = 60f * 2;
                        layer = Layer.bullet -2f;
                        hitSize = puddleSize = 7f;
                        trailInterval = trailParam = 1.5f;
                        buildingDamageMultiplier = 0.4f;

                        trailColor = hitColor;
                        status = StatusEffects.melting;
                    }}
            );
            loopSound = Sounds.steam;
            consumePower(100f / 60f);
            outlineColor = nyfalisBlockOutlineColour;
            drawer = new DrawTurret("iron-");
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            researchCost = with(iron, 500, lead, 800, quartz, 500);
            requirements(Category.turret, with(iron, 50, lead, 50, quartz, 25));
        }};

        //A recursive mortar turret that shoots long ranged recursive shells at the enemy (Has Really low rate of fire, high range, shells explode into multiple more shells on impact)
        obliterator = new ItemTurret("obliterator"){{
            size = 4;
            recoil = 2f;
            reload = 60f;
            range = 235f;
            health = 260;
            inaccuracy = 1f;
            shootCone = 10f;

            ammo(
                Items.graphite, new ArtilleryBulletType(3.2f, 20){{
                    lifetime = 80f;
                    fragBullets = 2;
                    knockback = 0.8f;
                    width = height = 11f;
                    splashDamage = 33f;
                    splashDamageRadius = 25f * 0.75f;
                    collidesTiles = false;
                    frontColor = new Color().set(graphite.color).lerp(Pal.bulletYellow, 0.25f);
                    backColor = new Color().set(graphite.color).lerp(Pal.bulletYellowBack, 0.15f);
                    fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 15f;
                        fragBullets = 2;
                        despawnEffect = Fx.none;
                        frontColor = new Color().set(graphite.color).lerp(Pal.bulletYellow, 0.55f);
                        backColor = new Color().set(graphite.color).lerp(Pal.bulletYellowBack, 0.45f);
                        fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                            width = 5f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 10f;
                            despawnEffect = Fx.none;
                            frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.75f);
                            backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.65f);
                        }};
                    }};
                }},
                Items.silicon, new ArtilleryBulletType(3f, 20){{
                    fragBullets = 2;
                    lifetime = 80f;
                    knockback = 0.8f;
                    homingRange = 50f;
                    ammoMultiplier = 3f;
                    splashDamage = 33f;
                    width = height = 11f;
                    homingPower = 0.08f;
                    reloadMultiplier = 1.2f;
                    splashDamageRadius = 25f * 0.75f;
                    collidesTiles = false;
                    frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.25f);
                    backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.15f);
                    fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 15f;
                        homingPower = 0.06f;
                        despawnEffect = Fx.none;
                        frontColor = quartz.color;
                        backColor = Pal.gray;
                        fragBullets = 2;
                        frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.55f);
                        backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.45f);
                        fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                            width = 5f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 10f;
                            despawnEffect = Fx.none;
                            frontColor = quartz.color;
                            backColor = Pal.gray;
                            frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.75f);
                            backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.65f);
                        }};
                    }};
                }}
            );
            targetAir = false;
            limitRange(0f);
            shootSound = Sounds.bang;
            coolant = consume(new ConsumeLubricant(30f / 60f));
            drawer = new DrawTurret("iron-");
            requirements(Category.turret, with(iron, 40, quartz, 20, cobalt, 20));
        }};

        //Aegis AA SAM Turrets (later game),  Inspired by bullet hell shoot'em ups... or btd's tack shooter
        aegis = new ItemTurret("aegis"){{
            targetAir = true;
            targetGround = false;
            size = 6;
            reload = 65f;
            range = 250f;
            shootY = shootX= 0f;
            fogRadiusMultiplier = 0.75f;
            shootWarmupSpeed = 0.11f;

            ammo(
                    lead, new MissileBulletType(2.8f, 68f){{
                        width = 7f;
                        shrinkX = 0;
                        height = 11.5f;
                        lifetime = 120f;
                        knockback = 1.2f;
                        homingDelay = 18f;
                        homingPower = 0.22f;
                        splashDamage = 10f;
                        statusDuration = 25f;
                        homingRange = 5f;
                        splashDamageRadius = 30f * 0.75f;
                        status = StatusEffects.slow;
                        backColor = trailColor = lead.color;
                        collidesAir = true;
                        collidesGround = false;
                        hitEffect = NyfalisFxs.hollowPointHit;
                    }}
            );
            limitRange(1.5f);
            shootSound = Sounds.missile;
            shootEffect = Fx.shootSmallSmoke;
            drawer = new DrawRegion("");
            shoot = new ShootSpread(9, 45);
            researchCost = with(lead, 100, rustyIron, 100);
            coolant = consume(new ConsumeLubricant(30f / 60f));
            requirements(Category.turret, with(iron, 20, lead, 40, alcoAlloy, 20));
        }};

        //TODO: Escalation - A early game rocket launcher that acts similarly to the scathe but with lower range and damage. (Decent rate of fire, weak against high health single targets, slow moving rocket, high cost but great AOE)
        //TODO:Shatter - A weak turret that shoots a spray of glass shards at the enemy. (High rate of fire, low damage, has pierce, very low defense, low range)

        //endregion
    }
}
