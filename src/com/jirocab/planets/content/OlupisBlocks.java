package com.jirocab.planets.content;

import arc.graphics.Color;
import arc.struct.EnumSet;
import com.jirocab.planets.Registry;
import com.jirocab.planets.blocks.Wire;
import com.jirocab.planets.world.NoBoilLiquidBulletType;
import com.jirocab.planets.world.blocks.*;
import mindustry.content.*;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.units.DroneCenter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class OlupisBlocks {
    //region Blocks Variables
    public static Block
    //environment
    olupisTree, bush, mossyBoulder, mossTree,

    oreIron, oreIronWall, oreCobalt, OreCobaltWall,

    lightWall,
    redSand, redDune, redSandWater, greenShrubsIrregular,  mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStone,
    frozenGrass, yellowGrass, yellowTree, yellowBush, yellowShrubs, yellowShrubsIrregular,  mossyStone, mossierStone, mossiestStone,
    mossStoneWall, mossyWater, yellowMossyWater,

    //Buildings
    garden, bioMatterPress, unitReplicator, unitReplicatorSmall, rustElectrolyzer, steamBoilder, steamAgitator, hydrochloricGraphitePress, ironSieve,
    rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironJunction, ironBridge,
    rustyPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump,
    wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator,
    steamDrill, hydroElectricDrill, oilSeparator, rustyDrill,
    corroder, dissolver,
    rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge,
    coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer,
    fortifiedMessageBlock
    ;  //endregion

    public static void LoadWorldTiles(){
        //region World Tiles
        oreIron = new OreBlock("ore-iron", OlupisItemsLiquid.rustyIron);
        oreIronWall = new OreBlock("ore-iron-wall", OlupisItemsLiquid.rustyIron){{
            wallOre = true;
        }};
        oreCobalt = new OreBlock("ore-cobalt", OlupisItemsLiquid.cobalt);

        frozenGrass = new Floor("frozen-grass"){{
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
            asFloor().wall = Blocks.shrubs;
        }};

        olupisTree = new TreeBlock("olupis-tree");
        mossTree = new TreeBlock("moss-tree");
        yellowTree = new TreeBlock("yellow-tree");

        bush = new Prop("bush"){{
            variants = 2;
            breakSound = Sounds.plantBreak;
            mindustry.content.Blocks.grass.asFloor().decoration = this;
        }};

        yellowBush = new Prop("yellow-bush"){{
            variants = 2;
            frozenGrass.asFloor().decoration = this;
        }};

        mossyBoulder = new Prop("mossy-boulder"){{
            variants = 2;
            frozenGrass.asFloor().decoration = this;
        }};


        redSand = new Floor("red-sand-floor"){{
            itemDrop = Items.sand;
            playerUnmineable = true;
            attributes.set(Attribute.oil, 1.5f);
            decoration = mindustry.content.Blocks.redStoneBoulder;
        }};

        yellowGrass = new Floor("yellow-grass"){{
            variants = 4;
            playerUnmineable = true;
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
            decoration = OlupisBlocks.mossyBoulder;
        }};

        redDune = new StaticWall("red-dune-wall"){{
            redSand.asFloor().wall = this;
            attributes.set(Attribute.sand, 2f);
        }};

        redSandWater = new Floor("red-sand-water"){{
            speedMultiplier = 0.8f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 50f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        greenShrubsIrregular = new TallBlock("green-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        yellowShrubs = new StaticWall("yellow-shrubs");

        yellowShrubsIrregular = new TallBlock("yellow-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        mossyStone = new Floor("mossy-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
            decoration = Blocks.stone;
        }};

        mossierStone = new Floor("mossier-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
            decoration = OlupisBlocks.mossyBoulder;
        }};

        mossiestStone = new Floor("mossiest-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
            decoration = OlupisBlocks.mossyBoulder;
        }};

        mossStone = new Floor("moss-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
        }};

        mossyStoneWall = new StaticWall("mossy-stone-wall"){{
            mossierStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 1f);
        }};

        mossierStoneWall = new StaticWall("mossier-stone-wall"){{
            mossierStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.8f);
        }};

        mossiestStoneWall = new StaticWall("mossiest-stone-wall"){{
            mossiestStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.6f);
        }};
        mossStoneWall = new StaticWall("moss-stone-wall"){{
            mossStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.6f);
        }};

        mossyWater = new Floor("mossy-water"){{
            speedMultiplier = 0.8f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 50f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        yellowMossyWater = new Floor("yellow-mossy-water"){{
            speedMultiplier = 0.8f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 50f;
            liquidDrop = Liquids.water;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};


        lightWall = new LightBlock("light-wall"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            brightness = 0.75f;
            radius = 140f;
        }};

        //endregion

    }
    public static void LoadBlocks(){
        //region Distribution
        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 1));
            health = 45;
            speed = 0.015f;
            //displayedSpe  ed = 4.2f;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.rustyIron, 75);
            //generateIcons = true;
        }};

        ironConveyor = new PowerConveyor("iron-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            unpoweredSpeed = 0.015f;
            poweredSpeed = 0.03f;
            speed = 0.03f;
            itemCapacity = 1;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.iron, 100);
            hasPower = true;
            conductivePower = true;
            consumesPower = true;
            consumePower (1f/60);
        }};

        cobaltConveyor = new PowerConveyor("cobalt-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            unpoweredSpeed = 0.03f;
            poweredSpeed = 0.06f;
            speed = 0.06f;
            itemCapacity = 1;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.iron, 100);
            hasPower = true;
            conductivePower = true;
            consumesPower = true;
            consumePower (1f/60);
        }};

        ironRouter = new Router("iron-router"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 3));
            buildCostMultiplier = 4f;
            researchCost = with(OlupisItemsLiquid.rustyIron, 50);
        }};

        ironJunction = new Junction("iron-junction"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 10));
            speed = 26;
            capacity = 6;
            health = 30;
            buildCostMultiplier = 6f;
            ((PowerConveyor)ironConveyor).junctionReplacement = this;
            ((Conveyor)rustyIronConveyor).junctionReplacement = this;
        }};

        ironBridge = new BufferedItemBridge("iron-bridge"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 8));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;

            ((PowerConveyor)ironConveyor).bridgeReplacement = this;
            ((Conveyor)rustyIronConveyor).bridgeReplacement = this;
        }};

        //endregion
        //region Drills
        rustyDrill = new BurstDrill("rusty-drill"){{
            requirements(Category.production, with(OlupisItemsLiquid.rustyIron, 25));
            size = 3;
            drillTime = 60f * 10f;
            tier = 1;
            hasPower = true;
            drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
            consumePower(1f/60f);
            consumeLiquid(Liquids.water, 0.02f).boost();
        }};


        steamDrill = new Drill("steam-drill"){{
            //requirements(Category.production, with(OlupisItemsLiquid.iron, 25, OlupisItemsLiquid.rustyIron, 40));
            //temp untill rusty drill is fully implemented & balanced
            requirements(Category.production, with( OlupisItemsLiquid.rustyIron, 40));
            tier = 2;
            drillTime = 600;
            size = 3;
            envEnabled ^= Env.space;
            researchCost = with(OlupisItemsLiquid.rustyIron, 100);

            hasPower = true;
            consumePower(1f/60f);
            consumeLiquid(Liquids.water, 0.05f);
            consumeLiquid(Liquids.slag, 0.06f).boost();
        }};

        hydroElectricDrill = new Drill("hydro-electric-drill"){{
            requirements(Category.production, with(OlupisItemsLiquid.iron, 55, OlupisItemsLiquid.rustyIron, 70, Items.lead, 30));
            tier = 3;
            drillTime = 600;
            size = 4;
            envEnabled ^= Env.space;

            consumeLiquid(OlupisItemsLiquid.steam, 0.05f);
            consumePower(0.3f);
            consumeLiquid(Liquids.slag, 0.06f).boost();
        }};

        //endregion
        //region liquid
        rustyPipe = new Conduit("rusty-pipe"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 5));
            botColor = Color.valueOf("928181");
            leaks = true;
            liquidCapacity = 5f;
            researchCostMultiplier = 3;
            underBullets = true;
            health = 60;
            liquidPressure = 0.95f;
        }};

        ironPipe = new ArmoredConduit("iron-pipe"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 5, OlupisItemsLiquid.rustyIron, 5));
            botColor = Color.valueOf("9D7F7F");
            leaks = true;
            liquidCapacity = 20f;
            researchCostMultiplier = 3;
            underBullets = true;
        }};

        pipeRouter = new LiquidRouter("pipe-router"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10));
            liquidCapacity = 20f;
            underBullets = true;
            solid = false;
        }};

        pipeJunction = new LiquidJunction("pipe-junction"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10 ));
            solid = false;
            ((Conduit)ironPipe).junctionReplacement = this;
            ((Conduit)rustyPipe).junctionReplacement = this;
        }};

        pipeBridge = new LiquidBridge("pipe-bridge"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10 ));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 4;
            hasPower = false;
            ((Conduit)ironPipe).bridgeReplacement = this;
            ((Conduit)rustyPipe).bridgeReplacement = this;
        }};

        rustyPump = new Pump("rusty-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10));
            pumpAmount = 0.05f;
            liquidCapacity = 10f;
            size = 1;
        }};

        ironPump = new Pump("iron-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 10));
            pumpAmount = 0.1f;
            liquidCapacity = 20f;
            size = 2;
            researchCost = with(OlupisItemsLiquid.rustyIron, 100);
        }};
        displacementPump = new Pump("displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 20));
            pumpAmount = 0.2f;
            liquidCapacity = 40f;
            size = 3;
            hasPower = true;
            consumePower(0.3f);
        }};


        massDisplacementPump = new Pump("mass-displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 30));
            pumpAmount = 0.3f;
            liquidCapacity = 50f;
            size = 4;
            hasPower = true;
            consumePower(0.6f);
        }};


        oilSeparator = new GenericCrafter("oil-separator"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 50));
            size = 4;

            researchCostMultiplier = 1.3f;
            craftTime = 15f;
            rotate = true;
            invertFlip = true;

            liquidCapacity = 60f;

            consumeLiquid(Liquids.oil, 40/60f);
            consumePower(1.2f);
            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(OlupisItemsLiquid.lightOil, 4/20, OlupisItemsLiquid.heavyOil,  7/60);
            liquidOutputDirections = new int[]{1,3};
        }};

        steamBoilder = new AttributeCrafter("steam-boiler"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 20));
            size = 2;
            hasPower = true;
            hasLiquids = true;
            outputsLiquid = true;
            craftTime = 150f;
            liquidCapacity = 30f;

            envEnabled = Env.any;
            rotate = false;
            solid = true;
            outputLiquid = new LiquidStack(OlupisItemsLiquid.steam, 12/60f);
            consumePower(1f);
            consumeLiquid(Liquids.water, 20/60f);

            boostScale = 0.1f;
            attribute = Attribute.heat;
        }};

        steamAgitator = new AttributeCrafter("steam-agitator"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 30));
            size = 3;

            attribute = Attribute.steam;
            minEfficiency = 9f - 0.0001f;
            baseEfficiency = 0f;
            displayEfficiency = false;

            outputsLiquid = true;
            craftTime = 150f;
            liquidCapacity = 30f;

            envEnabled = Env.any;
            rotate = false;
            solid = true;
            outputLiquid = new LiquidStack(OlupisItemsLiquid.steam, 10/60f);

            boostScale = 0.1f;
        }};

        //endregion
        //region Production
        garden = new AttributeCrafter("garden"){{
            requirements(Category.production, ItemStack.with(OlupisItemsLiquid.rustyIron, 30));
            outputItem = new ItemStack(OlupisItemsLiquid.condensedBiomatter, 1);
            craftTime = 200;
            size = 3;
            hasLiquids = true;
            hasPower = true;
            hasItems = true;

            craftEffect = Fx.none;
            //envRequired |= Env.terrestrial;
            attribute = Registry.Bio;
            legacyReadWarmup = true;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawLiquidTile(Liquids.water){{alpha = 0.5f;}},
                    new DrawDefault(),
                    new DrawRegion("-top")
            );
            maxBoost = 2.5f;

            consumePower(80f / 60f);
            consumeLiquid(Liquids.water, 18f / 60f);
        }};

        bioMatterPress = new GenericCrafter("biomatter-press"){{
            requirements(Category.crafting, with(OlupisItemsLiquid.iron, 100));
            liquidCapacity = 60f;
            craftTime = 20f;
            outputLiquid = new LiquidStack(Liquids.oil, 18f / 60f);
            size = 2;
            health = 320;
            hasLiquids = true;
            hasPower = true;
            craftEffect = Fx.none;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawPistons(){{
                        sinMag = 1f;
                    }},
                    new DrawDefault(),
                    new DrawLiquidRegion(),
                    new DrawRegion("-top")
            );

            consumeItem(OlupisItemsLiquid.condensedBiomatter, 1);
            consumePower(0.7f);
        }};

        rustElectrolyzer = new GenericCrafter("rust-electrolyzer"){{
            requirements(Category.crafting, with(OlupisItemsLiquid.rustyIron, 80));
            researchCost = with(OlupisItemsLiquid.rustyIron, 160, Items.lead, 60);
            outputItem = new ItemStack(OlupisItemsLiquid.iron, 1);
            size = 2;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
            liquidCapacity = 24f;
            craftTime = 120;
            lightLiquid = Liquids.cryofluid;

            consumePower(1f);
            consumeItems(with(Items.lead, 1, OlupisItemsLiquid.rustyIron,1));
            consumeLiquid(Liquids.water, 12f / 60f);
        }};

        hydrochloricGraphitePress  = new GenericCrafter("hydro-graphite-press"){{
            requirements(Category.crafting, with(OlupisItemsLiquid.iron, 150, OlupisItemsLiquid.rustyIron, 300));

            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(Items.graphite, 1    );
            craftTime = 30f;
            itemCapacity = 20;
            size = 3;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            consumeLiquids(LiquidStack.with(Liquids.oil, 5f / 60f, OlupisItemsLiquid.steam, 10f/60f));
            consumePower(10f/60f);

        }};

        ironSieve  = new GenericCrafter("iron-sieve"){{
            //not to be confused with iron shiv
            requirements(Category.crafting, with(OlupisItemsLiquid.rustyIron, 60));

            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(OlupisItemsLiquid.rustyIron, 2);
            craftTime = 30f;
            itemCapacity = 20;
            size = 2;
            hasItems = true;
            hasLiquids = false;
            hasPower = true;

            consumePower(1.8f);
            consumeItem(Items.sand, 2);
        }};

        //endregion
        //region Units
        unitReplicator = new Replicator("unit-replicator"){{
            this.requirements(Category.units, BuildVisibility.editorOnly, ItemStack.with());
            size = 5;
            delay = 5;
        }};

        unitReplicatorSmall = new Replicator("unit-replicator-small"){{
            this.requirements(Category.units, BuildVisibility.editorOnly, ItemStack.with());
            size = 4;
            delay = 4;
        }};

        //endregion
        //region Turrets
        corroder = new LiquidTurret("corroder"){{ //architronito
            requirements(Category.turret, with(OlupisItemsLiquid.rustyIron, 50));
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        speed = 5.55f;
                        hitSize = 5f;
                        lifetime = 10.5f;
                        drag = 0.009f;

                        damage = 6;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 2;
                        ammoMultiplier = 5f;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    OlupisItemsLiquid.steam, new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                        speed = 7.7f;
                        hitSize = 7f;
                        lifetime = 8f;
                        pierce = true;

                        damage = 15f;
                        ammoMultiplier = 3f;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                        evaporatePuddles = true;
                    }}
            );
            recoil = 0.2f;
            reload = 10f;
            range = 60f;
            shootCone = 50f;
            ammoUseEffect = Fx.shootLiquid;
            health = 1000;
            size = 2;
            targetAir = false;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            drawer = new DrawTurret("iron-");
        }};

        dissolver = new LiquidTurret("dissolver"){{ //architonnerre
            requirements(Category.turret, with(OlupisItemsLiquid.rustyIron, 100));
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        speed = 5.8f;
                        hitSize = 7f;
                        lifetime = 15.2f;

                        damage = 6;
                        pierce = true;
                        collidesAir = true;
                        ammoMultiplier = 5f;
                        statusDuration = 60f * 2;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    OlupisItemsLiquid.steam, new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                        speed = 8.8f;
                        lifetime = 10.7f;
                        hitSize = 7f;

                        damage = 10f;
                        ammoMultiplier = 3f;
                        pierce = true;
                        collidesAir = true;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                        evaporatePuddles = true;
                    }}
            );
            recoil = 0.2f;
            reload = 5f;
            range = 90f;
            shootCone = 50f;
            drawer = new DrawTurret("iron-");
            shootSound = Sounds.steam;
            health = 2500;
            size = 3;
            targetAir = true;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            shootY = 3f;
             //ammoUseEffect = OlupisFxs.shootSteamLarge;
        }};

        //endregion
        //region Power
        wire = new BeamNode("wire"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 5));
            baseExplosiveness = 0.5f;
            solid = false;
            floating = true;
            placeableLiquid = true;
            range = 1;
            consumePowerBuffered(1f);
            consumesPower = outputsPower = true;
            consumePower(1f/60f);
        }};

        superConductors = new Wire("super-conductor"){{
            requirements(Category.power, with(OlupisItemsLiquid.cobalt, 20, OlupisItemsLiquid.iron, 10));
            baseExplosiveness = 0.7f;
            range = 1;
            health = 150;
            solid = false;
            floating = true;
        }};

        wireBridge = new BeamNode("wire-bridge"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 15));
            baseExplosiveness = 0.5f;
            consumesPower = outputsPower = true;
            range = 5;
            health = 100;
            laserColor1 = Color.valueOf("ACB5BA");
            laserColor2 = Color.valueOf("65717E");
            floating = true;
            laserWidth = 0.4f;
            pulseMag = 0f;
            consumePower(10f/ 60f);
        }};

        windMills = new WindMill("wind-mill"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 30));
            researchCost = with(OlupisItemsLiquid.rustyIron, 300);
            attribute = Attribute.steam;
            size = 3;
            powerProduction = 10f/60f;
            displayEfficiencyScale = 1.1f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh =  0.01f;
            }});
        }};

        hydroElectricGenerator = new ThermalGenerator("hydro-electric-generator"){{
            requirements(Category.power, with(OlupisItemsLiquid.iron, 30, OlupisItemsLiquid.rustyIron, 50));
            powerProduction = 23f/60f;
            generateEffect = Fx.steam;
            effectChance = 0.011f;
            size = 5;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = Registry.hydro;
            placeableLiquid = true;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        hydroMill = new ThermalGenerator("hydro-mill"){{
            requirements(Category.power, with(OlupisItemsLiquid.iron, 30, OlupisItemsLiquid.rustyIron, 50));
            powerProduction = 17f/60f;
            generateEffect = Fx.steam;
            effectChance = 0.011f;
            size = 3;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = Registry.hydro;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        //endregion
        //region Wall
        rustyWall = new Wall("rusty-wall"){{
            requirements(Category.defense,with(OlupisItemsLiquid.rustyIron, 12));
            size = 1;
            health =  320;
        }};

        rustyWallLarge = new Wall("rusty-wall-large"){{
            requirements(Category.defense,with(OlupisItemsLiquid.rustyIron, 48));
            size = 2;
            health =  1640;
        }};

        rustyWallHuge = new Wall("rusty-wall-huge"){{
            requirements(Category.defense, ItemStack.mult(OlupisBlocks.rustyWall.requirements, 166));
            size = 3;
            health = 1280;
        }};

        rustyWallGigantic = new Wall("rusty-wall-gigantic"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(OlupisBlocks.rustyWall.requirements, 196));
            size = 4;
            health = 1940;
        }};

        ironWall = new Wall("iron-wall"){{
            requirements(Category.defense,with(OlupisItemsLiquid.iron, 12));
            size = 1;
            health = 640;
        }};

        ironWallLarge = new Wall("iron-wall-large"){{
            requirements(Category.defense,with(OlupisItemsLiquid.iron, 196));
            size = 2;
            health = 2530;
        }};

        //endregion
        //region Effect
        coreRemnant = new CoreBlock("core-remnant"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1000));
            size = 2;
            unitType = OlupisUnits.gnat;
            itemCapacity = 1500;
            isFirstTier = true;
            alwaysUnlocked = true;
            health = 3500;
        }};

        coreVestige = new CoreBlock("core-vestige"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 3;
            unitType = UnitTypes.nova;
            itemCapacity = 3000;
            health = 7000;
        }};

        coreRelic = new CoreBlock("core-relic"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 4;
            unitType = UnitTypes.pulsar;
            itemCapacity = 4500;
            health = 140000;
        }};

        coreShrine = new CoreBlock("core-shrine"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 5;
            unitType = UnitTypes.quasar;
            itemCapacity = 6000;
            health = 280000;
        }};

        coreTemple = new CoreBlock("core-temple"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 6;
            unitType = UnitTypes.vela;
            itemCapacity = 7500;
            health = 560000;
        }};

        fortifiedVault = new StorageBlock("fortified-vault"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 150, OlupisItemsLiquid.iron, 100));
            size = 3;
            itemCapacity = 1400;
            scaledHealth = 120;
            coreMerge = false;
            health =  1500;
        }};

        fortifiedContainer = new StorageBlock("fortified-container"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 75, OlupisItemsLiquid.iron, 50));
            size = 2;
            itemCapacity = 2700;
            scaledHealth = 150;
            coreMerge = false;
            health =  740;
        }};

        //endregion
        //region Logic
        fortifiedMessageBlock = new MessageBlock("fortified-message-block"){{
            requirements(Category.logic, with(Items.graphite, 10, OlupisItemsLiquid.iron, 5));
            health = 100;
        }};

        //endregion

    }
    public static void AddAttributes(){

        Blocks.grass.attributes.set(Registry.Bio, 0.1f);
        Blocks.stone.attributes.set(Registry.Bio, 0.03f);
        Blocks.charr.attributes.set(Registry.Bio, 0.03f);
        Blocks.mud.attributes.set(Registry.Bio, 0.03f);
        Blocks.dirt.attributes.set(Registry.Bio, 0.03f);
        Blocks.snow.attributes.set(Registry.Bio, 0.01f);
        Blocks.ice.attributes.set(Registry.Bio, 0.01f);
        Blocks.craters.attributes.set(Registry.Bio, 0.5f);

        Blocks.deepwater.attributes.set(Registry.hydro, 0.5f);
        Blocks.deepTaintedWater.attributes.set(Registry.hydro, 0.3f);
        Blocks.water.attributes.set(Registry.hydro, 0.3f);
        Blocks.taintedWater.attributes.set(Registry.hydro, 0.3f);
        Blocks.sandWater.attributes.set(Registry.hydro, 0.3f);
        Blocks.darksandTaintedWater.attributes.set(Registry.hydro, 0.3f);
        Blocks.darksandWater.attributes.set(Registry.hydro, 0.3f);
        OlupisBlocks.redSandWater.attributes.set(Registry.hydro, 0.3f);
        OlupisBlocks.mossyWater.attributes.set(Registry.hydro, 0.3f);
        OlupisBlocks.yellowMossyWater.attributes.set(Registry.hydro, 0.3f);
    }
}
