package com.jirocab.planets.content;

import arc.graphics.Color;
import arc.struct.EnumSet;
import com.jirocab.planets.Registry;
import com.jirocab.planets.blocks.*;
import mindustry.content.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class OlupisBlocks {
    //region Blocks Variables
    public static Block
    //environment
    olupisTree, bush, mossyBoulder, mossTree , treeStump,

    oreIron, oreIronWall, oreCobalt, OreCobaltWall,

    lightWall,
    redSand, redDune, redSandWater, greenShrubsIrregular,  mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStone,
    frozenGrass, yellowGrass, yellowTree, yellowBush, yellowShrubs, yellowShrubsIrregular,  mossyStone, mossierStone, mossiestStone, mossStoneWall,

    //Buildings
    garden, bioMatterPress, unitReplicator, unitReplicatorSmall, rustElectrolyzer, steamBoilder, steamHood, hydrochloricGraphitePress, ironSieve,
    rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter,
    rustyPipe, ironPipe, pipeRouter,
    wire, wireBridge, superConductors, windMills, hydroMill,
    steamDrill, hydroElectricDrill, displacementPump, massDisplacementPump, ironPump, oilSeparator,
    architonnerre, architronito,
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

        frozenGrass = new Floor("frozen-grass"){{
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
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

        lightWall = new LightBlock("light-wall"){{
            requirements(Category.effect, BuildVisibility.lightingOnly, with());
            brightness = 0.75f;
            radius = 140f;
        }};

        //endregion

    }
    public static void LoadBlocks(){
        //region Buildable Blocks

        ironConveyor = new PowerConveyor("iron-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            speed = 0.03f;
            itemCapacity = 1;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.iron, 100);
            hasPower = true;
            conductivePower = true;
            consumesPower = true;
            consumePower (1f/60);
            researchCost = with(OlupisItemsLiquid.iron, 20);
            baseEfficiency = 1f;

        }};

        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            requirements(Category.distribution, with(OlupisBlocks.rustyIronConveyor, 1));
            health = 45;
            speed = 0.015f;
            //displayedSpeed = 4.2f;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.rustyIron, 100);
            variants = 0;
        }};

        cobaltConveyor = new Conveyor("cobalt-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            speed = 0.03f;
            itemCapacity = 1;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.cobalt, 500);
            hasPower = true;
            conductivePower = true;
            consumesPower = true;
            consumePower (1f/60);
            researchCost = with(OlupisItemsLiquid.cobalt, 20);
        }};

        ironRouter = new Router("iron-router"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 3));
            buildCostMultiplier = 4f;
        }};

        rustyPipe = new ArmoredConduit("rusty-pipe"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 5));
            botColor = Color.valueOf("9D7F7F");
            leaks = true;
            liquidCapacity = 5f;
            researchCostMultiplier = 3;
            underBullets = true;
            health = 60;
            liquidPressure = 0.985f;
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
            requirements(Category.crafting, with(OlupisItemsLiquid.iron, 50));
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
            outputItem = new ItemStack(OlupisItemsLiquid.iron, 1);
            craftTime = 30f;
            itemCapacity = 20;
            size = 2;
            hasItems = true;
            hasLiquids = false;
            hasPower = true;

            consumePower(1.8f);
            consumeItem(Items.sand, 10/60);
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
            outputLiquid = new LiquidStack(OlupisItemsLiquid.steam, 20/60f);
            consumePower(1f);
            consumeLiquid(Liquids.water, 20/60f);

            boostScale = 0.1f;
            attribute = Attribute.heat;
        }};

        steamHood = new AttributeCrafter("steam-hood"){{
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

        architonnerre = new LiquidTurret("architonnerre"){{
            requirements(Category.turret, with(OlupisItemsLiquid.rustyIron, 20));
            ammo(
                    Liquids.water, new BulletType(3.33f, 20f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        shootEffect = OlupisFxs.shootSteamSmall;
                        hitEffect = Fx.steam;
                        despawnEffect = Fx.steam;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    OlupisItemsLiquid.steam, new BulletType(5.55f, 30f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        shootEffect = OlupisFxs.shootSteamSmall;
                        hitEffect = Fx.steam;
                        despawnEffect = Fx.steam;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }}
            );
            recoil = 0.2f;
            reload = 10f;
            range = 60f;
            shootCone = 50f;
            ammoUseEffect = OlupisFxs.shootSteamSmall;
            shootSound = Sounds.steam;
            health = 400;
            size = 2;
            targetAir = false;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            drawer = new DrawTurret("iron-");
        }};

        architronito = new LiquidTurret("architronito"){{
            requirements(Category.turret, with(OlupisItemsLiquid.rustyIron, 20));
            ammo(
                    Liquids.water, new BulletType(3.33f, 20f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 22f;
                        pierce = true;
                        collidesAir = true;
                        statusDuration = 60f * 4;
                        shootEffect = OlupisFxs.shootSteamSmall;
                        hitEffect = Fx.steam;
                        despawnEffect = Fx.steam;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    OlupisItemsLiquid.steam, new BulletType(5.55f, 30f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 22f;
                        pierce = true;
                        collidesAir = true;
                        statusDuration = 60f * 4;
                        shootEffect = OlupisFxs.shootSteamSmall;
                        hitEffect = Fx.steam;
                        despawnEffect = Fx.steam;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }}
            );
            recoil = 0.2f;
            reload = 10f;
            range = 90f;
            shootCone = 50f;
            drawer = new DrawTurret("iron-");
            shootSound = Sounds.steam;
            health = 700;
            size = 3;
            targetAir = true;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        steamDrill = new Drill("steam-drill"){{
            requirements(Category.production, with(OlupisItemsLiquid.rustyIron, 12));
            tier = 1;
            drillTime = 600;
            size = 3;
            envEnabled ^= Env.space;
            researchCost = with(OlupisItemsLiquid.rustyIron, 20);

            consumeLiquid(Liquids.water, 0.05f);
            consumeLiquid(Liquids.cryofluid, 0.06f).boost();
        }};

        hydroElectricDrill = new Drill("hydro-electric-drill"){{
            requirements(Category.production, with(OlupisItemsLiquid.iron, 12));
            tier = 2;
            drillTime = 600;
            size = 4;
            envEnabled ^= Env.space;
            researchCost = with(OlupisItemsLiquid.iron, 20);

            consumeLiquid(OlupisItemsLiquid.steam, 0.05f);
            consumePower(0.3f);
            consumeLiquid(Liquids.cryofluid, 0.06f).boost();
        }};

        massDisplacementPump = new Pump("mass-displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 30));
            pumpAmount = 0.3f;
            liquidCapacity = 50f;
            size = 4;
            hasPower = true;
            consumePower(0.6f);
        }};

        displacementPump = new Pump("displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 20));
            pumpAmount = 0.2f;
            liquidCapacity = 40f;
            size = 3;
            hasPower = true;
            consumePower(0.3f);
        }};

        ironPump = new Pump("iron-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10));
            pumpAmount = 0.1f;
            liquidCapacity = 20f;
            size = 2;
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

        windMills = new WindMill("wind-mill"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 30));
            size = 3;
            powerProduction = 1.3f;
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        hydroMill = new ThermalGenerator("hydro-mill"){{
            requirements(Category.power, with(OlupisItemsLiquid.iron, 30, OlupisItemsLiquid.rustyIron, 50));
            powerProduction = 1f;
            generateEffect = Fx.steam;
            effectChance = 0.011f;
            size = 5;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = Registry.hydro;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        wire = new Wire("wire"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 5));
            consumePowerBuffered(2000f);
            baseExplosiveness = 0.5f;
            consumesPower = true;
            solid = false;
            consumePower(0.016f);

        }};

        wireBridge = new BeamNode("wire-bridge"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 15));
            consumePowerBuffered(2000f);
            baseExplosiveness = 0.5f;
            consumesPower = outputsPower = true;
            range = 5;
            health = 100;
            laserColor1 = Color.valueOf("ACB5BA");
            laserColor2 = Color.valueOf("65717E");
            laserWidth = 0.3f;

            consumePower(0.16f);
        }};

        rustyWall = new Wall("rusty-wall"){{
            requirements(Category.defense,with(OlupisItemsLiquid.rustyIron, 50));
            size = 1;
            health =  440;
        }};

        rustyWallLarge = new Wall("rusty-wall-large"){{
            requirements(Category.defense,with(OlupisItemsLiquid.rustyIron, 200));
            size = 2;
            health =  1640;
        }};

        rustyWallHuge = new Wall("rusty-wall-huge"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(OlupisBlocks.rustyWall.requirements, 8));
            size = 3;
            health = 1940;
        }};

        rustyWallGigantic = new Wall("rusty-wall-gigantic"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(OlupisBlocks.rustyWall.requirements, 16));
            size = 4;
            health = 1940;
        }};

        ironWall = new Wall("iron-wall"){{
            requirements(Category.defense,with(OlupisItemsLiquid.iron, 50));
            size = 1;
            health = 510;
        }};

        ironWallLarge = new Wall("iron-wall-large"){{
            requirements(Category.defense,with(OlupisItemsLiquid.iron, 200));
            size = 2;
            health = 2050;
        }};

        coreRemnant = new CoreBlock("core-remnant"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1000));
            size = 2;
            unitType = UnitTypes.nova;
            itemCapacity = 1500;
        }};

        coreVestige = new CoreBlock("core-vestige"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 3;
            unitType = UnitTypes.nova;
            itemCapacity = 3000;
        }};

        coreRelic = new CoreBlock("core-relic"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 4;
            unitType = UnitTypes.pulsar;
            itemCapacity = 4500;
        }};

        coreShrine = new CoreBlock("core-shrine"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 5;
            unitType = UnitTypes.quasar;
            itemCapacity = 6000;
        }};

        coreTemple = new CoreBlock("core-temple"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 6;
            unitType = UnitTypes.vela;
            itemCapacity = 7500;
        }};

        fortifiedVault = new StorageBlock("fortified-vault"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 150, OlupisItemsLiquid.iron, 100));
            size = 3;
            itemCapacity = 1400;
            scaledHealth = 120;
            coreMerge = false;
        }};

        fortifiedContainer = new StorageBlock("fortified-container"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 75, OlupisItemsLiquid.iron, 50));
            size = 2;
            itemCapacity = 2700;
            scaledHealth = 150;
            coreMerge = false;
        }};

        fortifiedMessageBlock = new MessageBlock("fortifiedMessageBlock"){{
            requirements(Category.logic, with(Items.graphite, 10, OlupisItemsLiquid.iron, 5));
            health = 100;
        }};

        //endregion

    }
}
