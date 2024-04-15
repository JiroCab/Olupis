package olupis.content;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.*;
import olupis.world.blocks.defence.ItemUnitTurret;
import olupis.world.consumer.ConsumeLubricant;
import olupis.world.entities.NyfalisStats;
import olupis.world.entities.bullets.*;

import static mindustry.Vars.tilesize;
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
                    ammoMultiplier = 1.5f;
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
                        ammoMultiplier = 2.5f;
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
                size = 3;
                reload = 42f;
                range = 250f;
                health = 600;
                minWarmup = 0.96f;
                shootY = shootX= 0f;
                coolantMultiplier = 2.5f;
                warmupMaintainTime = 1f;
                fogRadiusMultiplier = 0.75f;
                shootWarmupSpeed = 0.11f;

                final float groundPenalty = 0.2f;
                ammo(
                    lead, new AirEffectiveMissleType(4.6f, 80f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 5f;
                        homingPower = 0.4f;
                        homingRange = 150f;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = lead.color;
                        collidesAir = collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.shocked;
                        groundDamageMultiplier = groundPenalty;
                    }},
                    iron, new AirEffectiveMissleType(5f, 95f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 25f;
                        homingPower = 0.4f;
                        homingRange = 150f;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = lead.color;
                        collidesAir = collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.slow;
                        groundDamageMultiplier = groundPenalty;
                    }},
                    cobalt, new AirEffectiveMissleType(4.6f, 80f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 25f;
                        homingPower = 0.4f;
                        homingRange = 150f;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = cobalt.color;
                        collidesAir =  collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.shocked;
                        groundDamageMultiplier = groundPenalty;
                    }}
                );
                limitRange(1.5f);
                shootSound = Sounds.missile;
                shootEffect = Fx.blastsmoke;
                drawer = new DrawRegion("");
                researchCost = with(lead, 100, rustyIron, 100);
                coolant = consume(new ConsumeLubricant(30f / 60f));
                requirements(Category.turret, with(rustyIron, 20, lead, 40));
            }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, NyfalisStats.ammoWithInfo(ammoTypes, this));
            }
        };

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
                rustyIron, new RollBulletType(3f, 31){{
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
                iron, new RollBulletType(3.5f, 38){{
                    status = StatusEffects.slow;
                    collidesAir = false;
                    width = 40f;
                    height = 11f;
                    lifetime = 50f;
                    pierceCap = 4;
                    knockback = 5f;
                    ammoMultiplier = 2;
                    homingPower = 0.2f;
                    homingRange = 100f;
                    statusDuration = 60f * 2f;
                    buildingDamageMultiplier = 0.4f;
                    shootEffect = smokeEffect = Fx.none;
                    frontColor = new Color().set(iron.color).lerp(Pal.bulletYellowBack, 0.1f);
                    backColor = new Color().set(iron.color).lerp(Pal.bulletYellow, 0.2f);
                }},
                quartz, new RollBulletType(3.5f, 42){{
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
                    backColor = new Color().set(quartz.color).lerp(Pal.bulletYellowBack, 0.1f);
                    frontColor = new Color().set(quartz.color).lerp(Pal.bulletYellow, 0.3f);
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
                    //TODO: CRAWER THAT FLIES
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
                        ammoMultiplier = 2f;
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
                        lifetime = 26.5f;
                        rangeChange = 20f;
                        ammoMultiplier = 3f;
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
            reload = 85f;
            range = 265f;
            health = 1000;
            inaccuracy = 2f;
            shootCone = 15f;
            coolantMultiplier = 4f;
            fogRadiusMultiplier = 0.6f;
            shootY = (size * tilesize / 2f) - 7f;

            float primaryLifeTime = 20f, primaryMinL = 0.8f, primaryMaxL = 1f, primaryHeight = 4f, primaryWidth = 7f,
                    secondaryHeight = 4f, secondaryWidth = 3f;
            ammo(
                Items.graphite, new ArtilleryBulletType(3.2f, 25){{
                    lifetime = 80f;
                    fragBullets = 2;
                    knockback = 0.8f;
                    splashDamage = 29f;
                    width = height = 14f;
                    fragLifeMax = primaryMaxL;
                    fragLifeMin = primaryMinL;
                    splashDamageRadius = 25f * 0.75f;

                    collidesTiles = false;
                    frontColor = new Color().set(graphite.color).lerp(Pal.bulletYellow, 0.25f).a(1f);
                    backColor = new Color().set(graphite.color).lerp(Pal.bulletYellowBack, 0.15f).a(1f);
                    hitEffect = despawnEffect = NyfalisFxs.obliteratorShockwave;

                    fragBullet = new ArtilleryBulletType(2f, 12, "bullet"){{
                        shrinkY = 1f;
                        fragBullets = 3;
                        width = primaryWidth;
                        height = primaryHeight;
                        lifetime = primaryLifeTime;

                        despawnEffect = Fx.none;
                        frontColor = new Color().set(graphite.color).lerp(Pal.bulletYellow, 0.55f);
                        backColor = new Color().set(graphite.color).lerp(Pal.bulletYellowBack, 0.45f);

                        fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                            shrinkY = 1f;
                            lifetime = 10f;
                            splashDamage = 7f;
                            width = secondaryWidth;
                            height = secondaryHeight;
                            splashDamageRadius = Vars.tilesize;

                            despawnEffect = Fx.none;
                            hitEffect = Fx.flakExplosion;
                            frontColor = new Color().set(graphite.color).lerp(Pal.bulletYellow, 0.75f);
                            backColor = new Color().set(graphite.color).lerp(Pal.bulletYellowBack, 0.65f);
                        }};
                    }};
                }},

                Items.silicon, new ArtilleryBulletType(3f, 25){{
                    lifetime = 80f;
                    fragBullets = 2;
                    knockback = 0.8f;
                    homingRange = 50f;
                    ammoMultiplier = 3f;
                    splashDamage = 29f;
                    width = height = 11f;
                    homingPower = 0.08f;
                    reloadMultiplier = 1.2f;
                    fragLifeMax = primaryMaxL;
                    fragLifeMin = primaryMinL;
                    splashDamageRadius = 25f * 0.75f;

                    collidesTiles = false;
                    frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.25f).a(1f);
                    backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.15f).a(1f);
                    hitEffect = despawnEffect = NyfalisFxs.obliteratorShockwave;

                    fragBullet = new ArtilleryBulletType(2f, 12, "bullet"){{
                        shrinkY = 1f;
                        fragBullets = 2;
                        homingPower = 0.06f;
                        width = primaryWidth;
                        height = primaryHeight;
                        lifetime = primaryLifeTime;
                        despawnEffect = Fx.none;

                        frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.55f);
                        backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.45f);

                        fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                            shrinkY = 1f;
                            lifetime = 10f;
                            splashDamage = 7f;
                            width = secondaryWidth;
                            height = secondaryHeight;
                            splashDamageRadius = Vars.tilesize;

                            despawnEffect = Fx.none;
                            hitEffect = Fx.flakExplosion;
                            frontColor = new Color().set(silicon.color).lerp(Pal.bulletYellow, 0.75f);
                            backColor = new Color().set(silicon.color).lerp(Pal.bulletYellowBack, 0.65f);
                        }};
                    }};
                }},

                alcoAlloy, new ArtilleryBulletType(3.2f, 30){{
                    lifetime = 80f;
                    fragBullets = 2;
                    knockback = 0.8f;
                    splashDamage = 31f;
                    width = height = 14f;
                    fragLifeMax = primaryMaxL;
                    fragLifeMin = primaryMinL;
                    splashDamageRadius = 25f * 0.75f;

                    collidesTiles = false;
                    frontColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellow, 0.25f).a(1f);
                    backColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellowBack, 0.15f).a(1f);
                    hitEffect = despawnEffect = NyfalisFxs.obliteratorShockwave;

                    fragBullet = new ArtilleryBulletType(2f, 20, "bullet"){{
                        shrinkY = 1f;
                        fragBullets = 2;
                        width = primaryWidth;
                        height = primaryHeight;
                        lifetime = primaryLifeTime;

                        despawnEffect = Fx.none;
                        frontColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellow, 0.55f);
                        backColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellowBack, 0.45f);

                        fragBullet = new BasicBulletType(2.7f, 20, "bullet"){{
                            shrinkY = 1f;
                            lifetime = 10f;
                            splashDamage = 16f;
                            width = secondaryWidth;
                            height = secondaryHeight;
                            splashDamageRadius = Vars.tilesize * 2f;

                            despawnEffect = Fx.none;
                            hitEffect = Fx.flakExplosion;
                            frontColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellow, 0.75f);
                            backColor = new Color().set(alcoAlloy.color).lerp(Pal.bulletYellowBack, 0.65f);
                        }};
                    }};
                }}
            );
            shootEffect = new MultiEffect( Fx.shootBigColor, Fx.shootSmokeSquare);
            targetAir = false;
            limitRange(10f);
            drawer = new DrawTurret("iron-");
            shootSound = NyfalisSounds.cncZhBattleMasterWeapon;
            coolant = consume(new ConsumeLubricant(30f / 60f));
            requirements(Category.turret, with(iron, 40, quartz, 20, cobalt, 20));
        }};

        aegis = new ItemTurret("aegis"){
            {
                //big boi rocket launcher with various ammo types that do different things
                targetAir = true;
                targetGround = true;
                size = 4;
                reload = 400f;
                range = 70f * 8f;
                shootY = shootX = 0f;
                fogRadiusMultiplier = 0.75f;
                shootWarmupSpeed = 0.05f;
                minWarmup = 0.8f;

                ammo(
                        lead, new BulletType(0f, 1) {{
                            instantDisappear = true;
                            shootEffect = Fx.shootBig;
                            ammoMultiplier = 5f;
                            fragBullets = 10;
                            fragSpread = 1;
                            fragRandomSpread = 2;
                            fragBullet = new AirEffectiveMissleType(4f, 5f) {{
                                width = 6f;
                                shrinkX = 0;
                                lifetime = 160f;
                                height = 10.5f;
                                knockback = 0.4f;
                                splashDamage = 10f;
                                homingPower = 0.4f;
                                homingRange = 150f;
                                homingDelay = 20;
                                splashDamageRadius = 25f * 0.75f;
                                backColor = trailColor = lead.color;
                                collidesAir = collidesGround = true;
                                shootEffect = Fx.shootBigColor;
                                hitEffect = NyfalisFxs.hollowPointHit;
                                groundDamageMultiplier = 0.4f;
                                buildingDamageMultiplier = 0.02f;
                            }};
                        }},

                        rustyIron, new AirEffectiveMissleType(3f, 10f) {{
                            width = 12f;
                            reloadMultiplier = 1.5f;
                            shrinkX = 0;
                            lifetime = 180f;
                            height = 21f;
                            knockback = 0.8f;
                            splashDamage = 10f;
                            statusDuration = 160f;
                            homingPower = 0.4f;
                            homingRange = 150f;
                            homingDelay = 20;
                            splashDamageRadius = 25f * 0.75f;
                            backColor = trailColor = rustyIron.color;
                            collidesAir = collidesGround = true;
                            shootEffect = Fx.shootBigColor;
                            hitEffect = NyfalisFxs.hollowPointHit;
                            status = StatusEffects.corroded;
                            groundDamageMultiplier = 1.8f;
                            buildingDamageMultiplier = 0.5f;
                            intervalBullets = 4;
                            intervalSpread = -30;
                            intervalRandomSpread = 60;
                            bulletInterval = 20;
                            intervalBullet = new AirEffectiveMissleType(3f, 5f) {{
                                width = 6f;
                                shrinkX = 0;
                                lifetime = 25;
                                height = 10.5f;
                                knockback = 0.8f;
                                splashDamage = 10f;
                                statusDuration = 160f;
                                homingPower = 0.4f;
                                homingRange = 150f;
                                homingDelay = 20;
                                splashDamageRadius = 25f * 0.75f;
                                backColor = trailColor = rustyIron.color;
                                collidesAir = collidesGround = true;
                                shootEffect = Fx.shootBigColor;
                                hitEffect = NyfalisFxs.hollowPointHit;
                                status = StatusEffects.corroded;
                                groundDamageMultiplier = 1.8f;
                                buildingDamageMultiplier = 0.5f;
                            }};
                        }},

                        quartz, new BulletType(0f, 1){{
                            reloadMultiplier = 0.5f;
                            shootEffect = Fx.shootBig;
                            ammoMultiplier = 8f;

                            spawnUnit = new MissileUnitType("aegis-quartz-missile"){{
                                speed = 8f;
                                maxRange = 6f;
                                lifetime = 100;
                                engineColor = trailColor = quartz.color;
                                engineLayer = Layer.effect;
                                engineSize = 3.1f;
                                engineOffset = 10f;
                                rotateSpeed = 0.25f;
                                trailLength = 18;
                                missileAccelTime = 50f;
                                lowAltitude = true;
                                loopSound = Sounds.missileTrail;
                                loopSoundVolume = 0.6f;
                                deathSound = Sounds.largeExplosion;
                                targetAir = true;

                                fogRadius = 6f;

                                health = 60;

                                weapons.add(new Weapon(){{
                                    shootCone = 360f;
                                    mirror = false;
                                    reload = 1f;
                                    deathExplosionEffect = Fx.massiveExplosion;
                                    shootOnDeath = true;
                                    shake = 10f;
                                    bullet = new ExplosionBulletType(25f, 65f){{
                                        hitColor = quartz.color;
                                        shootEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
                                            lifetime = 10f;
                                            strokeFrom = 4f;
                                            sizeTo = 130f;
                                        }});

                                        collidesAir = true;
                                        buildingDamageMultiplier = 0.02f;
                                    }};
                                }});
                            }};
                        }}
                );
                drawer = new DrawTurret("iron-"){{
                    parts.add(new RegionPart("-core"){{
                        progress = PartProgress.recoil;
                        heatProgress = PartProgress.warmup.add(-0.2f).add(p -> Mathf.sin(9f, 0.2f) * p.warmup);
                        mirror = false;
                        under = true;
                        children.add(new RegionPart("-core-barrel"){{
                                         progress = PartProgress.recoil.delay(0.5f);
                                         heatProgress = PartProgress.recoil;
                                         heatColor = NyfalisItemsLiquid.alcoAlloy.color;
                                         mirror = false;
                                         under = false;
                                         moveY = -2f;
                                         moveX = 0f;
                                     }},
                                new RegionPart("-side-l"){{
                                    progress = PartProgress.warmup.delay(0.6f);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = NyfalisItemsLiquid.alcoAlloy.color;
                                    mirror = false;
                                    under = false;
                                    moveY = 0f;
                                    moveRot = -5f;
                                    moveX = 4f;

                                    moves.add(new PartMove(PartProgress.recoil, 0.5f, -1f, 0f));
                                    children.add(new RegionPart("-side-barrel-l"){{
                                        progress = PartProgress.recoil.delay(0.8f);
                                        heatProgress = PartProgress.recoil;
                                        heatColor = NyfalisItemsLiquid.alcoAlloy.color;
                                        mirror = false;
                                        under = false;
                                        moveY = -2f;
                                    }});
                                }},
                                new RegionPart("-side-r"){{
                                    progress = PartProgress.warmup.delay(0.6f);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = NyfalisItemsLiquid.alcoAlloy.color;
                                    mirror = false;
                                    under = false;
                                    moveY = 0f;
                                    moveRot = 5f;
                                    moveX = -4f;

                                    moves.add(new PartMove(PartProgress.recoil, -0.5f, -1f, 0f));
                                    children.add(new RegionPart("-side-barrel-r"){{
                                        progress = PartProgress.recoil.delay(0.2f);
                                        heatProgress = PartProgress.recoil;
                                        heatColor = NyfalisItemsLiquid.alcoAlloy.color;
                                        mirror = false;
                                        under = false;
                                        moveY = -2f;
                                        moveRot = 0;
                                    }});
                                }});
                    }});
                }};
                shoot = new ShootAlternate(){{ //don't forget to adjust the y of where the missle spawns! -Rushie
                    shots = barrels = 3;
                    spread = 15;
                    shotDelay = 20;
                }};
                ammoPerShot = 40;
                maxAmmo = 120;
                shootSound = Sounds.missile;
                shootEffect = Fx.shootSmallSmoke;
                researchCost = with(lead, 1500, iron, 700, cobalt, 700);
                coolant = consume(new ConsumeLubricant(30f / 60f));
                requirements(Category.turret, with(iron, 100, lead, 200, cobalt, 60));
            }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, NyfalisStats.ammoWithInfo(ammoTypes, this));
            }
        };
        //TODO: Shockwave - funi wave go brr
        /*
                        new AirEffectiveMissleType(3f, 5f) {{
                            width = 12f;
                            reloadMultiplier = 1.5f;
                            shrinkX = 0;
                            lifetime = 225f;
                            height = 21f;
                            knockback = 0.8f;
                            splashDamage = 10f;
                            statusDuration = 160f;
                            homingPower = 0.4f;
                            homingRange = 150f;
                            homingDelay = 20;
                            splashDamageRadius = 25f * 0.75f;
                            backColor = trailColor = rustyIron.color;
                            collidesAir = collideTerrain = collidesGround = true;
                            shootEffect = Fx.shootBigColor;
                            hitEffect = NyfalisFxs.hollowPointHit;
                            status = StatusEffects.corroded;
                            groundDamageMultiplier = 1.8f;
                            buildingDamageMultiplier = 0.1f;
                            intervalBullets = 2;
                            intervalSpread = -30;
                            intervalRandomSpread = 0;
                            bulletInterval = 6;
                            intervalBullet = new AirEffectiveMissleType(3f, 10f) {{
                                width = 6f;
                                shrinkX = 0;
                                lifetime = 50;
                                height = 10.5f;
                                knockback = 0.8f;
                                splashDamage = 10f;
                                statusDuration = 160f;
                                homingPower = 0.4f;
                                homingRange = 150f;
                                homingDelay = 20;
                                splashDamageRadius = 25f * 0.75f;
                                backColor = trailColor = rustyIron.color;
                                collidesAir = collideTerrain =collidesGround = true;
                                shootEffect = Fx.shootBigColor;
                                hitEffect = NyfalisFxs.hollowPointHit;
                                status = StatusEffects.corroded;
                                groundDamageMultiplier = 1.8f;
                                buildingDamageMultiplier = 0.1f;
                                intervalBullets = 2;
                                intervalSpread = -30;
                                intervalRandomSpread = 0;
                                bulletInterval = 4;
                                intervalBullet = new AirEffectiveMissleType(3f, 15f) {{
                                    width = 3f;
                                    shrinkX = 0;
                                    lifetime = 25;
                                    height = 5.25f;
                                    knockback = 0.8f;
                                    splashDamage = 10f;
                                    statusDuration = 160f;
                                    homingPower = 0.4f;
                                    homingRange = 150f;
                                    homingDelay = 20;
                                    splashDamageRadius = 25f * 0.75f;
                                    backColor = trailColor = rustyIron.color;
                                    collidesAir = collideTerrain = collidesGround = true;
                                    shootEffect = Fx.shootBigColor;
                                    hitEffect = NyfalisFxs.hollowPointHit;
                                    status = StatusEffects.corroded;
                                    groundDamageMultiplier = 1.8f;
                                    buildingDamageMultiplier = 0.1f;
                                }};
                            }};
                        }},
         */
        //TODO: Escalation - A early game rocket launcher that acts similarly to the scathe but with lower range and damage. (Decent rate of fire, weak against high health single targets, slow moving rocket, high cost but great AOE)
        //TODO:Shatter - A weak turret that shoots a spray of glass shards at the enemy. (High rate of fire, low damage, has pierce, very low defense, low range)

        //endregion
    }
}
