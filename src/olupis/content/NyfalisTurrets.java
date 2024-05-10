package olupis.content;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.*;
import olupis.world.blocks.defence.ItemUnitTurret;
import olupis.world.blocks.defence.UnstablePowerTurret;
import olupis.world.consumer.ConsumeLubricant;
import olupis.world.entities.NyfalisStats;
import olupis.world.entities.bullets.*;
import olupis.world.entities.parts.DrawUnstableTurret;
import olupis.world.entities.parts.UnstableRegionPart;

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
            targetAir = true;

            size = 2;
            recoil = 1;
            shootY = 7f;
            range = 15 * 8f;
            health = 1500;
            fogRadius = 13;
            shootCone = 50f;
            inaccuracy = 8.5f;
            rotateSpeed = 3f;
            coolantMultiplier = 2.5f;
            liquidCapacity = reload = 5f;

            ammo(
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    status = StatusEffects.corroded;
                    layer = Layer.bullet -2f;
                    trailColor = hitColor;

                    speed = 5.5f;
                    drag = 0.008f;
                    damage = 10f;
                    pierceCap = 1;
                    lifetime = 9999f;
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
                        lifetime = 9999f;
                        damage = 15f;
                        pierceCap = 3;
                        ammoMultiplier = 2.5f;
                        statusDuration = 60f * 5;
                        trailInterval = trailParam = 1.5f;
                        buildingDamageMultiplier = 0.5f;
                }},
                Liquids.slag, new LiquidBulletType(Liquids.slag){{
                    speed = 5.8f;
                    damage = 17;
                    pierceCap = 1;
                    drag = 0.0009f;
                    lifetime = 9999f;
                    rangeChange = 20f;
                    ammoMultiplier = 3f;
                    statusDuration = 60f * 2;
                    layer = Layer.bullet -2f;
                    hitSize = puddleSize = 7f;
                    trailInterval = trailParam = 1.5f;
                    buildingDamageMultiplier = 0.4f;

                    trailColor = hitColor;
                    status = StatusEffects.melting;
                }},
                emulsiveSlop, new LiquidBulletType(emulsiveSlop){{
                    speed = 5.8f;
                    damage = 13;
                    pierceCap = 1;
                    drag = 0.0009f;
                    lifetime = 9999f;
                    rangeChange = 20f;
                    ammoMultiplier = 3f;
                    statusDuration = 60f * 2;
                    layer = Layer.bullet -2f;
                    hitSize = puddleSize = 7f;
                    trailInterval = trailParam = 1.5f;
                    buildingDamageMultiplier = 0.4f;

                    trailColor = hitColor;
                    status = NyfalisStatusEffects.sloppy;
                }}
            );
            drawer = new DrawTurret("iron-"){{
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
            limitRange(0f);
            loopSound = Sounds.steam;
            consumePower(1f);
            outlineColor = nyfalisBlockOutlineColour;
            researchCost = with(rustyIron, 100, lead, 100);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            requirements(Category.turret, with(rustyIron, 40, lead, 20));

        }
            public void limitRange(float margin){
                for(var entry : ammoTypes.entries()){
                    limitRange(entry.value, margin);
                }
            }
        };

        avenger = new ItemTurret("avenger"){{
                targetAir = true;
                size = 3;
                reload = 42f;
                range = 34 * 8;
                health = 300;
                minWarmup = 0.96f;
                shootY = shootX= 0f;
                coolantMultiplier = 2.5f;
                warmupMaintainTime = 1f;
                fogRadiusMultiplier = 0.75f;
                shootWarmupSpeed = 0.11f;
                shootCone = 360;
                rotateSpeed = 0;
                inaccuracy = 360;

                final float groundPenalty = 0.1f;
                ammo(
                    copper, new EffectivenessMissleType(4.6f, 20f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 120f;
                        homingPower = 0.1f;
                        homingRange = 34 * 8;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = copper.color;
                        collidesAir = collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.shocked;
                        groundDamageMultiplier = groundPenalty;
                    }},
                    lead, new EffectivenessMissleType(4.6f, 60f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 60f;
                        homingPower = 0.4f;
                        homingRange = 34 * 8;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = lead.color;
                        collidesAir = collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.sapped;
                        groundDamageMultiplier = groundPenalty;
                    }},
                    iron, new EffectivenessMissleType(5f, 80f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        statusDuration = 60f;
                        homingPower = 0.04f;
                        homingRange = 34 * 8;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = lead.color;
                        collidesAir = collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = StatusEffects.slow;
                        groundDamageMultiplier = groundPenalty;
                    }},
                    cobalt, new EffectivenessMissleType(4.6f, 20f){{
                        width = 6f;
                        shrinkX = 0;
                        lifetime = 60f;
                        height = 10.5f;
                        knockback = 0.4f;
                        splashDamage = 10f;
                        absorbable = false;
                        statusDuration = 20f;
                        homingPower = 0.2f;
                        homingRange = 34 * 8;
                        splashDamageRadius = 25f * 0.75f;
                        backColor = trailColor = cobalt.color;
                        collidesAir =  collidesGround = true;
                        shootEffect = Fx.shootBigColor;
                        hitEffect = NyfalisFxs.hollowPointHit;
                        status = NyfalisStatusEffects.glitch;
                        groundDamageMultiplier = groundPenalty;
                    }}
                );
                limitRange(1.5f);
                shootSound = Sounds.missile;
                shootEffect = Fx.blastsmoke;
                recoil = 0;
                drawer = new DrawTurret(){{
                    parts.add(
                        new RegionPart("-door-bl"){{
                            progress = PartProgress.warmup;
                            moveX = moveY = -2.8f;
                            layerOffset = -2.8f;
                            mirror = outline = false;
                        }},
                        new RegionPart("-door-tl"){{
                            progress = PartProgress.warmup;
                            moveX = -2.8f;
                            moveY = 2.8f;
                            layerOffset = -2;
                            mirror = outline = false;
                        }},
                        new RegionPart("-door-br"){{
                            progress = PartProgress.warmup;
                            moveX = 2.8f;
                            moveY = -2.8f;
                            layerOffset = -2;
                            mirror = outline = false;
                        }},
                        new RegionPart("-door-tr"){{
                            progress = PartProgress.warmup;
                            moveX = moveY = 2.8f;
                            layerOffset = -2;
                            mirror = outline = false;
                        }}
                    );

                }};
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
            shootSound = NyfalisSounds.cncZhBattleMasterWeapon;
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
            researchCost = with(iron, 500, lead, 800, quartz, 500, copper, 800);
            requirements(Category.turret, with(iron, 50, lead, 50, quartz, 25, copper, 50));
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
                    fragLifeMin = primaryMinL;
                    fragLifeMax = primaryMaxL;
                    buildingDamageMultiplier = 0.7f;
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
                    fragLifeMin = primaryMinL;
                    fragLifeMax = primaryMaxL;
                    buildingDamageMultiplier = 0.7f;
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
                    fragLifeMin = primaryMinL;
                    fragLifeMax = primaryMaxL;
                    buildingDamageMultiplier = 0.7f;
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
                targetAir = true;
                targetGround = true;
                size = 3;
                reload = 120;
                range = 50f * 8f;
                shootY = 5;
                shootX = 0f;
                fogRadiusMultiplier = 0.75f;
                shootWarmupSpeed = 0.05f;
                minWarmup = 0.8f;

                ammo(
                        copper, new EffectivenessMissleType(6f, 1f) {{
                            width = 6f;
                            reloadMultiplier = 1.6f;
                            shrinkX = 0;
                            lifetime = 70f;
                            height = 10.5f;
                            knockback = 0.8f;
                            splashDamage = 2f;
                            statusDuration = 120f;
                            homingPower = 0.4f;
                            homingRange = 150f;
                            homingDelay = 20;
                            splashDamageRadius = 25f * 0.75f;
                            backColor = trailColor = copper.color;
                            collidesAir = collidesGround = true;
                            shootEffect = Fx.shootBigColor;
                            hitEffect = NyfalisFxs.hollowPointHit;
                            status = StatusEffects.shocked;
                        }},
                        lead, new EffectivenessMissleType(3f, 5f) {{
                            width = 6f;
                            reloadMultiplier = 2f;
                            shrinkX = 0;
                            lifetime = 140f;
                            height = 10.5f;
                            knockback = 0.8f;
                            splashDamage = 5f;
                            statusDuration = 120f;
                            homingPower = 0.4f;
                            homingRange = 150f;
                            homingDelay = 20;
                            splashDamageRadius = 25f * 0.75f;
                            backColor = trailColor = lead.color;
                            collidesAir = collidesGround = true;
                            shootEffect = Fx.shootBigColor;
                            hitEffect = NyfalisFxs.hollowPointHit;
                            status = StatusEffects.sapped;
                            groundDamageMultiplier = 0.5f;
                            buildingDamageMultiplier = 0.8f;
                        }},

                        rustyIron, new EffectivenessMissleType(3f, 2.5f) {{
                            width = 6f;
                            reloadMultiplier = 2f;
                            shrinkX = 0;
                            lifetime = 140f;
                            height = 10.5f;
                            knockback = 0.8f;
                            splashDamage = 2.5f;
                            statusDuration = 120f;
                            homingPower = 0.4f;
                            homingRange = 150f;
                            homingDelay = 20;
                            splashDamageRadius = 25f * 0.75f;
                            backColor = trailColor = rustyIron.color;
                            collidesAir = collidesGround = true;
                            shootEffect = Fx.shootBigColor;
                            hitEffect = NyfalisFxs.hollowPointHit;
                            status = StatusEffects.corroded;
                            groundDamageMultiplier = 2f;
                            buildingDamageMultiplier = 0.8f;
                        }}
                );
                drawer = new DrawTurret("iron-"){{
                    parts.add(new RegionPart("-core"){{
                        heatProgress = PartProgress.recoil;
                        recoilIndex = 1;
                        mirror = false;
                        under = true;
                        layerOffset = 1;
                        outlineLayerOffset = -1;
                        children.addAll(
                                new RegionPart("-core-barrel"){{
                                    progress = PartProgress.recoil;
                                    recoilIndex = 1;
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveX = 0f;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                }},
                                new RegionPart("-frame"){{
                                    mirror = false;
                                    under = true;
                                    growProgress = PartProgress.warmup.delay(0.3f);
                                    growX = 0.5f;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                    moveY = 0f;
                                    moveX = 0f;
                                }},
                                new RegionPart("-side-l"){{
                                    recoilIndex = 0;
                                    progress = PartProgress.warmup.delay(0.6f);
                                    mirror = false;
                                    under = false;
                                    moveY = 0f;
                                    moveRot = -2.5f;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                    outlineLayerOffset = -0.4f;
                                    moveX = 3f;

                                    moves.add(new PartMove(PartProgress.recoil, 0.25f, -0.5f, 0f));
                                    children.add(new RegionPart("-side-barrel-l"){{
                                        recoilIndex = 0;
                                        progress = PartProgress.recoil;
                                        mirror = false;
                                        under = false;
                                        layerOffset = 0.5f;
                                        heatLayerOffset = 3f;
                                        moveY = -1f;
                                    }},
                                    new RegionPart("-side-heatsink-l"){{
                                        recoilIndex = 0;
                                        progress = PartProgress.warmup.delay(0.2f).add(p -> Mathf.sin(9f, 0.2f) * p.warmup);
                                        mirror = false;
                                        under = false;
                                        layerOffset = 0.3f;
                                        heatLayerOffset = 3f;
                                        moveY = -0.9f;
                                        moveX = 0.9f;
                                        moveRot = 0;
                                    }});
                                }},
                                new RegionPart("-side-r"){{
                                    recoilIndex = 2;
                                    progress = PartProgress.warmup.delay(0.6f);
                                    mirror = false;
                                    under = false;
                                    moveY = 0f;
                                    moveRot = 2.5f;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                    outlineLayerOffset = -0.4f;
                                    moveX = -3f;

                                    moves.add(new PartMove(PartProgress.recoil, -0.25f, -0.5f, 0f));
                                    children.add(new RegionPart("-side-barrel-r"){{
                                        recoilIndex = 2;
                                        recoilIndex = 1;
                                        progress = PartProgress.recoil;
                                        mirror = false;
                                        under = false;
                                        layerOffset = 0.5f;
                                        heatLayerOffset = 3f;
                                        moveY = -1f;
                                        moveRot = 0;
                                    }},
                                    new RegionPart("-side-heatsink-r"){{
                                        recoilIndex = 2;
                                        progress = PartProgress.warmup.delay(0.2f).add(p -> Mathf.sin(9f, 0.2f) * p.warmup);
                                        mirror = false;
                                        under = false;
                                        layerOffset = 0.3f;
                                        heatLayerOffset = 3f;
                                        moveY = -0.9f;
                                        moveX = -0.9f;
                                        moveRot = 0;
                                    }});
                                }},
                                new RegionPart("-core-heatsink-l"){{
                                    recoilIndex = 1;
                                    progress = PartProgress.warmup.delay(0.2f).add(p -> Mathf.sin(9f, 0.2f) * p.warmup);
                                    mirror = false;
                                    under = false;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                    moveY = -0.75f;
                                    moveX = -0.75f;
                                    moveRot = 0;
                                }},
                                new RegionPart("-core-heatsink-r"){{
                                    recoilIndex = 1;
                                    progress = PartProgress.warmup.delay(0.2f).add(p -> Mathf.sin(9f, 0.2f) * p.warmup);
                                    mirror = false;
                                    under = false;
                                    layerOffset = 0.5f;
                                    heatLayerOffset = 3f;
                                    moveY = -0.75f;
                                    moveX = 0.75f;
                                    moveRot = 0;
                                }});
                    }});
                }};
                recoils = 3;
                shoot = new ShootBarrel(){{
                    shots = 6;
                    shotDelay = 5;
                    barrels = new float[]{
                            -9, 1, 0,
                            0, 3, 0,
                            9, 1, 0
                    };
                }};
                ammoPerShot = 20;
                maxAmmo = 220;
                shootSound = Sounds.missile;
                outlineColor = nyfalisBlockOutlineColour;
                shootEffect = Fx.shootSmallSmoke;
                researchCost = with(lead, 1500, iron, 700, alcoAlloy, 700);
                coolant = consume(new ConsumeLubricant(30f / 60f));
                coolantMultiplier = 2.4f;
                requirements(Category.turret, with(iron, 100, lead, 200, alcoAlloy, 60));
            }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, NyfalisStats.ammoWithInfo(ammoTypes, this));
            }
        };
        var a = "";
        var b = "";
        var c = "";
        var d = "";
        var color = Color.blue;
        var m = 0f;
        var details = "";
        if(Core.settings.getBool("nyfalis-bread-gun")){
            a = "PH-cascade";
            b = "-bladed";
            c = "-blade";
            d = "-bladed";
            color = Color.brown;
            m = 4f;
            details = """
            Bullet Math
            Main
                amount created per shot: [stat]1[]
            2cds
                amount created per shot: [stat]96[]
            3rds
                amount created per shot: [stat]2304[]
            4ths
                amount created per shot: [stat]55296[]
            5ths
                amount created per shot: [stat]1327104[]
            6ths
                amount created per shot: [stat]31850496[]
                
            Total Bullets per shot: [stat]33235297[]
            
            Max Damage Possible: [stat]497975540[]
             
            """;

        } else {
            a = "cascade";
            b = "-crest";
            c = "-blade";
            d = "-clask";
            color = Color.valueOf("8ca9e8");
            m = 1f;
            details = """
            Bullet Math
            Main
                amount created per shot: [stat]1[]
            2cds
                amount created per shot: [stat]24[]
            3rds
                amount created per shot: [stat]576[]
                
            Total Bullets per shot: [stat]601[]
            
            Max Damage Possible: [stat]8780[]
             
            """;
        }
        String finalB = b;
        String finalC = c;
        String finalD = d;
        Color finalColor = color;
        Float finalM = m;
        String finalDetails = details;
        cascade = new UnstablePowerTurret(a){{
            outlineColor = Color.valueOf("371404");
            details = finalDetails;
            hideDetails = false;
            targetAir = true;
            targetGround = true;
            size = 4;
            reload = 120;
            range = (50f * 8f) * finalM;
            shootY = 2;
            shootX = 0f;
            fogRadiusMultiplier = 0.75f;
            shootWarmupSpeed = 0.05f;
            minWarmup = 0.8f;
            explosionRadius = 25 * finalM;
            explosionDamage = 1000 * finalM;
            consumePower(17f * finalM);

            shootType = new BasicBulletType(3f, 20f) {{
                sprite = "large-orb";
                width = 10f;
                height = 20f;
                hitSize = 8f;

                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                    colorFrom = colorTo = finalColor;
                    lifetime = 6f;
                    sizeTo = 10f;
                    strokeFrom = 3f;
                    strokeTo = 0.3f;
                }});
                smokeEffect = Fx.shootSmokeSmite;
                pierceCap = 4;
                pierce = true;
                pierceBuilding = true;
                hitColor = backColor = trailColor = finalColor;
                frontColor = Color.white;
                trailWidth = 2f;
                trailLength = 8;
                hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                    sizeTo = 15f;
                    colorFrom = colorTo = finalColor;
                    lifetime = 6f;
                }});
                trailRotation = true;
                trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                trailInterval = 3f;
                lifetime = 75f * finalM;
                knockback = 0.8f;
                collidesAir = collidesGround = true;
                buildingDamageMultiplier = 0.1f;
                intervalBullets = 2;
                intervalSpread = -30;
                intervalRandomSpread = 0;
                bulletInterval = 6;
                intervalBullet = new BasicBulletType(3f, 5f) {{
                    sprite = "large-orb";
                    width = 5f;
                    height = 10f;
                    hitSize = 4f;

                    shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                        colorFrom = colorTo = finalColor;
                        lifetime = 4f;
                        sizeTo = 5f;
                        strokeFrom = 3f;
                        strokeTo = 0.3f;
                    }});
                    smokeEffect = Fx.shootSmokeSmite;
                    pierceCap = 4;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = finalColor;
                    frontColor = Color.white;
                    trailWidth = 1.5f;
                    trailLength = 6;
                    hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                    despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                        sizeTo = 7.5f;
                        colorFrom = colorTo = finalColor;
                        lifetime = 4f;
                    }});
                    trailRotation = true;
                    trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                    trailInterval = 3f;
                    lifetime = 50f;
                    knockback = 0.6f;
                    collidesAir = collidesGround = true;
                    buildingDamageMultiplier = 0.1f;
                    intervalBullets = 2;
                    intervalSpread = -30;
                    intervalRandomSpread = 0;
                    bulletInterval = 4;
                    intervalBullet = new BasicBulletType(3f, 15f) {{
                        sprite = "large-orb";
                        width = 2.5f;
                        height = 5f;
                        hitSize = 2f;
                        homingPower = 0.4f;
                        homingRange = 150f;

                        shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                            colorFrom = colorTo = finalColor;
                            lifetime = 2f;
                            sizeTo = 2.5f;
                            strokeFrom = 3f;
                            strokeTo = 0.3f;
                        }});
                        smokeEffect = Fx.shootSmokeSmite;
                        pierceCap = 4;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = finalColor;
                        frontColor = Color.white;
                        trailWidth = 1f;
                        trailLength = 4;
                        hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                        despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                            sizeTo = 3.75f;
                            colorFrom = colorTo = finalColor;
                            lifetime = 2f;
                        }});
                        trailRotation = true;
                        trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                        trailInterval = 3f;
                        lifetime = 25f;
                        knockback = 0.6f;
                        collidesAir = collidesGround = true;
                        buildingDamageMultiplier = 0.1f;
                        if(Core.settings.getBool("nyfalis-bread-gun")){
                            intervalBullets = 2;
                            intervalSpread = -30;
                            intervalRandomSpread = 0;
                            bulletInterval = 6;
                            intervalBullet = new BasicBulletType(3f, 5f) {{
                                sprite = "large-orb";
                                width = 5f;
                                height = 10f;
                                hitSize = 4f;

                                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                    colorFrom = colorTo = finalColor;
                                    lifetime = 4f;
                                    sizeTo = 5f;
                                    strokeFrom = 3f;
                                    strokeTo = 0.3f;
                                }});
                                smokeEffect = Fx.shootSmokeSmite;
                                pierceCap = 4;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = finalColor;
                                frontColor = Color.white;
                                trailWidth = 1.5f;
                                trailLength = 6;
                                hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                                despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                    sizeTo = 7.5f;
                                    colorFrom = colorTo = finalColor;
                                    lifetime = 4f;
                                }});
                                trailRotation = true;
                                trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                                trailInterval = 3f;
                                lifetime = 50f;
                                knockback = 0.6f;
                                collidesAir = collidesGround = true;
                                buildingDamageMultiplier = 0.1f;
                                intervalBullets = 2;
                                intervalSpread = -30;
                                intervalRandomSpread = 0;
                                bulletInterval = 4;
                                intervalBullet = new BasicBulletType(3f, 15f) {{
                                    sprite = "large-orb";
                                    width = 2.5f;
                                    height = 5f;
                                    hitSize = 2f;
                                    homingPower = 0.4f;
                                    homingRange = 150f;

                                    shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                        colorFrom = colorTo = finalColor;
                                        lifetime = 2f;
                                        sizeTo = 2.5f;
                                        strokeFrom = 3f;
                                        strokeTo = 0.3f;
                                    }});
                                    smokeEffect = Fx.shootSmokeSmite;
                                    pierceCap = 4;
                                    pierce = true;
                                    pierceBuilding = true;
                                    hitColor = backColor = trailColor = finalColor;
                                    frontColor = Color.white;
                                    trailWidth = 1f;
                                    trailLength = 4;
                                    hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                                    despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                        sizeTo = 3.75f;
                                        colorFrom = colorTo = finalColor;
                                        lifetime = 2f;
                                    }});
                                    trailRotation = true;
                                    trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                                    trailInterval = 3f;
                                    lifetime = 25f;
                                    knockback = 0.6f;
                                    collidesAir = collidesGround = true;
                                    buildingDamageMultiplier = 0.1f;
                                    intervalBullets = 2;
                                    intervalSpread = -30;
                                    intervalRandomSpread = 0;
                                    bulletInterval = 4;
                                    intervalBullet = new BasicBulletType(3f, 15f) {{
                                        sprite = "large-orb";
                                        width = 2.5f;
                                        height = 5f;
                                        hitSize = 2f;
                                        homingPower = 0.4f;
                                        homingRange = 150f;

                                        shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                            colorFrom = colorTo = finalColor;
                                            lifetime = 2f;
                                            sizeTo = 2.5f;
                                            strokeFrom = 3f;
                                            strokeTo = 0.3f;
                                        }});
                                        smokeEffect = Fx.shootSmokeSmite;
                                        pierceCap = 4;
                                        pierce = true;
                                        pierceBuilding = true;
                                        hitColor = backColor = trailColor = finalColor;
                                        frontColor = Color.white;
                                        trailWidth = 1f;
                                        trailLength = 4;
                                        hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                                        despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                            sizeTo = 3.75f;
                                            colorFrom = colorTo = finalColor;
                                            lifetime = 2f;
                                        }});
                                        trailRotation = true;
                                        trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                                        trailInterval = 3f;
                                        lifetime = 25f;
                                        knockback = 0.6f;
                                        collidesAir = collidesGround = true;
                                        buildingDamageMultiplier = 0.1f;
                                    }};
                                }};
                            }};
                        }
                    }};
                }};
            }};

            drawer = new DrawUnstableTurret("iron-"){{
                parts.addAll(new UnstableRegionPart(""),
                new UnstableRegionPart(finalB + "-l"){{
                    mirror = false;
                    moveRot = 25f;
                    moveX = -6;
                    under = true;
                    moves.add(new PartMove(UnstablePartProgress.reload, 8f, 0f, -5f));
                    layerOffset  = -1;
                    heatLayerOffset = -0.95f;

                    heatColor = finalColor;
                }},

                new UnstableRegionPart(finalC +"-l"){{
                    outlineLayerOffset = -2.99f;
                    layerOffset = 2;
                    mirror = false;
                    moveRot = 10f;
                    moveY = -0.8f;
                    moves.add(new PartMove(UnstablePartProgress.reload.shorten(0.5f), -1f, 0f, -7f));
                    under = true;

                    heatColor = finalColor;
                }},
                new UnstableRegionPart(finalD +"-l"){{
                    mirror = false;
                    moveRot = -175f;
                    moveX = 2;
                    moveY = 2f;
                    x = -1.5f;
                    layerOffset  = -0.5f;
                    moves.add(new PartMove(UnstablePartProgress.reload.shorten(0.5f), 0.5f, 2f, -120f));
                    under = true;
                    heatColor = finalColor;
                }},
                new UnstableRegionPart(finalB + "-r"){{
                    mirror = false;
                    moveRot = -25f;
                    moveX = 6;
                    under = true;
                    moves.add(new PartMove(UnstablePartProgress.reload, -8f, 0f, 5f));
                    layerOffset  = -1;
                    heatLayerOffset = -0.95f;

                    heatColor = finalColor;
                }},
                new UnstableRegionPart(finalC+"-r"){{
                    outlineLayerOffset = -2.99f;
                    layerOffset = 2;
                    mirror = false;
                    moveRot = -10f;
                    moveY = -0.8f;
                    moves.add(new PartMove(UnstablePartProgress.reload.shorten(0.5f), 1f, 0f, 7));
                    under = true;
                    heatColor = finalColor;
                }},
                new UnstableRegionPart(finalD + "-r"){{
                    mirror = false;
                    moveRot = 175f;
                    moveY = 2f;
                    moveX = -2;
                    x = 1.5f;
                    layerOffset  = -0.5f;
                    moves.add(new PartMove(UnstablePartProgress.reload.shorten(0.5f), -0.5f, 2f, 120f));
                    under = true;
                    heatColor = finalColor;
                }});
            }};
            shootSound = Sounds.shootSmite;
            shootEffect = new MultiEffect(Fx.shootPayloadDriver, NyfalisFxs.fastSquareSmokeCloud);
            researchCost = with(iron, 500 * finalM, copper, 500 * finalM, cobalt, 350 * finalM, quartz, 100 * finalM);
            coolant = consumeCoolant(2 ,true,true);
            liquidCapacity = 120;
            coolantMultiplier = 0.4f;
            requirements(Category.turret, with(iron, 200 * finalM, copper, 200 * finalM, cobalt, 125 * finalM, quartz, 30 * finalM));
            heatColor = finalColor;
            cooldownTime = 240;
        }};

        slash = new PowerTurret("slash"){{
            inaccuracy = 0f;
            rotateSpeed = 3f;
            reload = 6;
            shootY = 12;
            minWarmup = 0.9f;
            smokeEffect = shootEffect =  Fx.none;

            shootType = new ExplosionBulletType(50, 10){{
                trailEffect = despawnEffect = smokeEffect = shootEffect = hitEffect =  Fx.none;
                killShooter = collidesAir = false;
                fragBullets = 8;
                fragRandomSpread = 0;
                fragSpread = 360;
                fragBullet = new BulletType(){{
                    damage = 0;
                    knockback = 0.5f;
                    speed = 3;
                    lifetime = 10;
                    trailEffect = despawnEffect = smokeEffect = shootEffect =  Fx.none;
                    hitEffect =  Fx.hitFlameSmall;
                    collidesAir = false;
                    hitSound = NyfalisSounds.sawCollision;
                }};
            }};
            drawer = new DrawTurret("iron-"){{
                targetAir = false;

                size = 2;
                recoil = 0;
                range = 20f;
                health = 4000;
                fogRadius = 13;
                shootCone = 180f;
                coolantMultiplier = 3f;
                liquidCapacity = 5f;

                parts.addAll(
                        new RegionPart("-buzzsaw"){{
                            mirror = false;
                            under = true;
                            progress = PartProgress.warmup;
                            y = 4;
                            moveY = 8;
                            moves.add(new PartMove(PartProgress.reload.sustain(0,10,20), 0, 0, 360f));
                        }}
                );
            }};
            loopSound = NyfalisSounds.sawActiveLoop;
            shootSound = Sounds.none;
            outlineColor = nyfalisBlockOutlineColour;
            coolant = consume(new ConsumeLubricant(15f / 60f));
            consumePower(0.3f);
            researchCost = with(rustyIron, 200, copper, 150);
            requirements(Category.turret, with(rustyIron, 60, copper, 50));

        }
            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.remove(Stat.inaccuracy);
                stats.remove(Stat.reload);
                stats.remove(Stat.booster);
                stats.add(new Stat("bullet.damage", StatCat.function),50 * (60 / reload) , StatUnit.perSecond);
                stats.add(Stat.booster, NyfalisStats.sawBoosters(reload, coolant.amount, coolantMultiplier, false, this::consumesLiquid));
            }
        };
        laceration = new PowerTurret("laceration"){{
            inaccuracy = 0f;
            rotateSpeed = 3f;
            reload = 8;
            shootY = 20;
            minWarmup = 0.9f;
            smokeEffect = shootEffect =  Fx.none;
            shoot = new ShootAlternate(){{
                shots = barrels = 3;
                spread = 12;
                shotDelay = 0;
            }};


            shootType = new ExplosionBulletType(200, 10){{
                trailEffect = despawnEffect = smokeEffect = shootEffect = hitEffect =  Fx.none;
                killShooter = collidesAir = false;
                fragBullets = 8;
                fragRandomSpread = 0;
                fragSpread = 360;
                fragBullet = new BulletType(){{
                    damage = 0;
                    knockback = 0.5f;
                    speed = 3;
                    lifetime = 10;
                    trailEffect = despawnEffect = smokeEffect = shootEffect = Fx.none;
                    hitEffect =  Fx.hitFlameSmall;
                    collidesAir = false;
                    hitSound = NyfalisSounds.sawCollision;
                }};
            }};
            drawer = new DrawTurret("iron-"){{
                targetAir = false;

                size = 4;
                recoil = 0;
                range = 40f;
                health = 6000;
                fogRadius = 13;
                shootCone = 180f;
                coolantMultiplier = 3.5f;
                liquidCapacity = 5f;

                parts.addAll(
                        new RegionPart("-buzzsaw"){{
                            mirror = false;
                            under = true;
                            progress = PartProgress.warmup;
                            layerOffset = -0.1f;
                            x = -12;
                            y = 6.5f;
                            moveY = 13.5f;
                            moves.add(new PartMove(PartProgress.reload.sustain(0,10,20), 0, 0, 360f));
                        }},
                        new RegionPart("-buzzsaw"){{
                            mirror = false;
                            under = true;
                            progress = PartProgress.warmup;
                            layerOffset = -0.001f;
                            y = 8;
                            moveY = 13;
                            moves.add(new PartMove(PartProgress.reload.sustain(0,10,20), 0, 0, 360f));
                        }},
                        new RegionPart("-buzzsaw"){{
                            mirror = false;
                            under = true;
                            progress = PartProgress.warmup;
                            layerOffset = -0.1f;
                            x = 12;
                            y = 6.5f;
                            moveY = 13.5f;
                            moves.add(new PartMove(PartProgress.reload.sustain(0,10,20), 0, 0, 360f));
                        }}

                );
            }};
            loopSound = NyfalisSounds.sawActiveLoop;
            shootSound = Sounds.none;
            outlineColor = nyfalisBlockOutlineColour;
            coolant = consume(new ConsumeLubricant(30f / 60f));
            consumePower(2.5f);
            researchCost = with(iron, 400, copper, 300, quartz, 100);
            requirements(Category.turret, with(iron, 230, copper, 160, quartz, 60));

        }
            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.remove(Stat.inaccuracy);
                stats.remove(Stat.reload);
                stats.remove(Stat.booster);
                stats.add(new Stat("bullet.damage", StatCat.function),(200 * 3) * (60 / reload) , StatUnit.perSecond);
                stats.add(Stat.booster, NyfalisStats.sawBoosters(reload, coolant.amount, coolantMultiplier, false, this::consumesLiquid));
            }
        };

        strata = new ItemTurret("strata"){{

            ammo(
                    iron, new BasicBulletType(0,0){{
                        lifetime = 0;
                        fragBullets = 8;
                        fragRandomSpread = 10;
                        fragSpread = 40;
                        fragBullet = new BulletType(){{
                            damage = 0;
                            lifetime = 250;
                            scaleLife = true;
                            collidesAir = collidesGround = false;
                            hitColor = trailColor = iron.color;
                            trailWidth = 1f;
                            trailLength = 4;
                            hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 3.75f;
                                colorFrom = colorTo = iron.color;
                                lifetime = 2f;
                            }});
                            trailRotation = true;
                            trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                            fragBullets = 1;
                            fragRandomSpread = 0;
                            fragSpread = 360;
                            fragBullet = new MineBulletType(NyfalisBlocks.heavyMine,Fx.ballfire, 0.80f);
                        }};
                    }},
                    cobalt, new BasicBulletType(0,0){{
                        lifetime = 0;
                        fragBullets = 12;
                        fragRandomSpread = 10;
                        fragSpread = 40;
                        fragBullet = new BulletType(){{
                            damage = 0;
                            lifetime = 250;
                            scaleLife = true;
                            collidesAir = collidesGround = false;
                            hitColor = trailColor = cobalt.color;
                            trailWidth = 1f;
                            trailLength = 4;
                            hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 3.75f;
                                colorFrom = colorTo = cobalt.color;
                                lifetime = 2f;
                            }});
                            trailRotation = true;
                            trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                            fragBullets = 1;
                            fragRandomSpread = 0;
                            fragSpread = 360;
                            fragBullet = new MineBulletType(NyfalisBlocks.glitchMine,Fx.ballfire, 0.55f);
                        }};
                    }},
                    quartz, new BasicBulletType(0,0){{
                        lifetime = 0;
                        fragBullets = 10;
                        fragRandomSpread = 10;
                        fragSpread = 40;
                        fragBullet = new BulletType(){{
                            damage = 0;
                            lifetime = 250;
                            scaleLife = true;
                            collidesAir = collidesGround = false;
                            hitColor = trailColor = quartz.color;
                            trailWidth = 1f;
                            trailLength = 4;
                            hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 3.75f;
                                colorFrom = colorTo = quartz.color;
                                lifetime = 2f;
                            }});
                            trailRotation = true;
                            trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                            fragBullets = 1;
                            fragRandomSpread = 0;
                            fragSpread = 360;
                            fragBullet = new MineBulletType(NyfalisBlocks.fragMine,Fx.ballfire, 0.65f);
                        }};
                    }},
                    condensedBiomatter, new BasicBulletType(0,0){{
                        lifetime = 0;
                        fragBullets = 20;
                        fragRandomSpread = 10;
                        fragSpread = 40;
                        fragBullet = new BulletType(){{
                            damage = 0;
                            lifetime = 250;
                            scaleLife = true;
                            collidesAir = collidesGround = false;
                            hitColor = trailColor = condensedBiomatter.color;
                            trailWidth = 1f;
                            trailLength = 4;
                            hitEffect = new MultiEffect(Fx.hitBulletColor,NyfalisFxs.scatterDebris);

                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 3.75f;
                                colorFrom = colorTo = condensedBiomatter.color;
                                lifetime = 2f;
                            }});
                            trailRotation = true;
                            trailEffect = new MultiEffect(Fx.disperseTrail,NyfalisFxs.hollowPointHit);
                            fragBullets = 1;
                            fragRandomSpread = 0;
                            fragSpread = 360;
                            fragBullet = new MineBulletType(NyfalisBlocks.mossMine,Fx.ballfire, 0.50f);
                        }};
                    }}
            );
            drawer = new DrawTurret(){{
                targetAir = false;
                shootCone = 360;
                inaccuracy = 0;
                size = 3;
                recoil = 0;
                lockRotation = true;
                rotateSpeed = 0;
                rotateDraw = false;
                shootY = 0;
                range = 320f;
                predictTarget = true;
                health = 1500;
                fogRadius = 13;
                coolantMultiplier = 7.5f;
                reload = 12*16;
                parts.addAll(
                        new RegionPart("-piston"){{
                            layerOffset = 3;
                            mirror = outline = false;
                            under = true;
                            growProgress = PartProgress.reload.inv();
                            growX = growY = 0.2f;
                        }}
                );
            }};
            ammoPerShot = 24;
            loopSound = Sounds.release;
            outlineColor = nyfalisBlockOutlineColour;
            researchCost = with(iron, 300, copper, 210, alcoAlloy, 130);
            coolant = consume(new ConsumeLubricant(35f / 60f));
            requirements(Category.turret, with(iron, 130, copper, 100, alcoAlloy, 50));

        }
            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, NyfalisStats.ammoBlocksOnly(ammoTypes, this));
            }
        };

        //region Env Hazzards
        atroposShroom = new PowerTurret("atropos-shroom"){{
            alwaysShooting = true;
            inaccuracy =  360;
            playerControllable = logicConfigurable = false;
            var T = this;

            shootType = new ExplosionBulletType(50,100){{
                shootEffect = NyfalisFxs.smolPorpolKaboom;
                killShooter = true;
                fragBullets = 8;
                fragRandomSpread = 0;
                fragSpread = 45;
                fragBullet = new BulletType(){{
                    damage = 0;
                    knockback = 2f;
                    speed = 3;
                    lifetime = 10;
                    trailEffect = despawnEffect = smokeEffect = shootEffect = Fx.none;
                    hitEffect =  Fx.none;
                    collidesAir = true;
                    status = StatusEffects.sapped;
                    statusDuration = 240;
                    fragBullets = 1;
                    fragRandomSpread = 0;
                    fragSpread = 45;
                    fragBullet = new BulletType(3,0){{

                        trailLength = 2;
                        trailColor = Items.sporePod.color;
                        trailEffect = Fx.sporeSlowed;
                        despawnEffect = smokeEffect = shootEffect = hitEffect =  Fx.none;
                        lifetime = 100;
                        collidesAir = collidesGround = false;
                        fragBullets = 1;
                        fragRandomSpread = fragSpread = 0;
                        fragBullet = new MineBulletType(T,Fx.sporeSlowed,0.40f);
                    }};
                }};
            }};

            solid = createRubble = rebuildable = false;

            breakEffect = Fx.breakProp;
            breakSound = Sounds.plantBreak;


            drawer = new DrawRegion("");
            hasShadow = false;
            drawTeamOverlay = false;
            size = 1;
            shootY = 0;
            range = 0;
            health = 1;
            reload = 60 * 20;
            shootSound = Sounds.none;

        }};
        //endregion


        //TODO: Escalation - A early game rocket launcher that acts similarly to the scathe but with lower range and damage. (Decent rate of fire, weak against high health single targets, slow moving rocket, high cost but great AOE)
        //TODO:Shatter - A weak turret that shoots a spray of glass shards at the enemy. (High rate of fire, low damage, has pierce, very low defense, low range)

        //endregion
    }
}
