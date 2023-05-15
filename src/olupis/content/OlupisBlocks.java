package olupis.content;

import arc.Core;
import arc.graphics.Color;
import arc.struct.EnumSet;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSummon;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import olupis.Registry;
import olupis.world.NoBoilLiquidBulletType;
import olupis.world.blocks.*;

import static mindustry.type.ItemStack.with;

public class OlupisBlocks {
    //region Blocks Variables
    public static Block
        //environment
        olupisTree, bush, mossyBoulder, mossTree, pinkTree, yellowTree, yellowTreeBlooming, infernalMegaBloom, infernalBloom,

        oreIron, oreIronWall, oreCobalt, oreOxidizedCopper, oreOxidizedLead,

        lightWall,
        redSand, redDune, redSandWater, greenShrubsIrregular,  mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStone,
        lumaGrass, cinderBloomGrass, cinderBloomy, cinderBloomer, cinderBloomiest,
        frozenGrass, yellowGrass, yellowBush, yellowShrubs, yellowShrubsIrregular,  mossyStone, mossierStone, mossiestStone,
        mossStoneWall, mossyWater, yellowMossyWater, brimstoneSlag, pinkGrass, pinkGrassWater, pinkShrubs, lumaFlora,

        //Buildings
        garden, bioMatterPress, unitReplicator, unitReplicatorSmall, rustElectrolyzer, steamBoiler, steamAgitator, hydrochloricGraphitePress, ironSieve,
        rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironJunction, ironBridge, ironOverflow, ironUnderflow,
        leadPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump, fortifiedTank, fortifiedCanister,
        wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator,
        steamDrill, hydroElectricDrill, oilSeparator, rustyDrill,
        corroder, dissolver, shredder,
        rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge, rustyScrapWall, rustyScrapWallLarge, rustyScrapWallHuge, rustyScrapWallGigantic,
        coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer,
        mendFieldProjector, taurus,
        fortifiedMessageBlock
    ; //endregion

    public static Color olupisBlockOutlineColour = Color.valueOf("371404");
    public static ObjectSet<Block> olupisBuildBlockSet = new ObjectSet<>(), sandBoxBlocks = new ObjectSet<>();

    public static void LoadWorldTiles(){
        //region World Tiles
        oreIron = new OreBlock("ore-iron", OlupisItemsLiquid.rustyIron){{
            placeableLiquid = true;
        }};
        oreIronWall = new OreBlock("ore-iron-wall", OlupisItemsLiquid.rustyIron){{
            wallOre = true;
        }};
        oreCobalt = new OreBlock("ore-cobalt", OlupisItemsLiquid.cobalt);

        /*uses ore's item as a name block in editor*/
        oreOxidizedCopper = new OreBlock("ore-oxidized-copper", Items.copper);
        oreOxidizedLead = new OreBlock("ore-oxidized-lead", Items.lead);


        frozenGrass = new Floor("frozen-grass"){{
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
            asFloor().wall = Blocks.shrubs;
        }};

        olupisTree = new TreeBlock("olupis-tree");
        mossTree = new TreeBlock("moss-tree");
        yellowTree = new TreeBlock("yellow-tree");
        yellowTreeBlooming = new TreeBlock("yellow-tree-blooming");
        pinkTree = new TreeBlock("pink-tree");
        infernalMegaBloom = new TreeBlock("infernal-megabloom"){{
            variants = 4;
            clipSize = 128f;
        }};

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

        lumaFlora = new Prop("luma-flora"){{
            variants = 2;
            breakSound = Sounds.plantBreak;
        }};

        infernalBloom = new Prop("infernal-bloom"){{
            variants = 3;
            breakSound = Sounds.plantBreak;
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
            decoration = OlupisBlocks.yellowBush;
        }};

        pinkGrass = new Floor("pink-grass"){{
            variants = 4;
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
            decoration = OlupisBlocks.mossyBoulder;
        }};

        lumaGrass = new Floor("luma-grass"){{
            variants = 3;
            attributes.set(Attribute.water, 0.15f);
            attributes.set(Registry.Bio, 0.08f);
            decoration = OlupisBlocks.lumaFlora;
        }};

        cinderBloomGrass = new Floor("cinder-bloom"){{
            variants = 3;
            attributes.set(Attribute.water, 0.25f);
            attributes.set(Registry.Bio, 0.06f);
            decoration = OlupisBlocks.infernalBloom;
        }};

        cinderBloomiest = new Floor("cinder-bloomiest"){{
            variants = 3;
            attributes.set(Registry.Bio, 0.01f);
            decoration = OlupisBlocks.infernalBloom;
        }};

        cinderBloomer = new Floor("cinder-bloomier"){{
            variants = 3;
            attributes.set(Attribute.water, -0.05f);
            attributes.set(Registry.Bio, 0.02f);
            decoration = OlupisBlocks.infernalBloom;
        }};

        cinderBloomy = new Floor("cinder-bloomy"){{
            variants = 3;
            attributes.set(Attribute.water, -0.15f);
            attributes.set(Registry.Bio, 0.03f);
            decoration = Blocks.basaltBoulder;
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

        pinkGrassWater = new Floor("pink-grass-water"){{
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

        brimstoneSlag = new Floor("brimstone-slag"){{
            drownTime = 30f;
            status = StatusEffects.melting;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            damageTaken = 9999999f;
            variants = 0;
            liquidDrop = Liquids.slag;
            isLiquid = true;
            cacheLayer = CacheLayer.slag;
            attributes.set(Attribute.heat, 0.90f);

            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.valueOf("D54B3B").a(0.38f);
        }};

        greenShrubsIrregular = new StaticTree("green-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        yellowShrubs = new StaticWall("yellow-shrubs");
        pinkShrubs = new StaticWall("pink-shrubs");

        yellowShrubsIrregular = new StaticTree("yellow-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        mossyStone = new Floor("mossy-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
            decoration = Blocks.boulder;
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
            mapColor = OlupisBlocks.mossierStone.mapColor;
        }};

        mossStone = new Floor("moss-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
            decoration =  OlupisBlocks.bush;
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

        pinkTree = new Floor("pink-mossy-water"){{
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
            alwaysUnlocked = true;
        }};

        //endregion
    }
    public static void LoadBlocks(){
        //region Distribution
        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.rustyIron, 1));
            health = 45;
            speed = 0.015f;
            displayedSpeed = 1f;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.rustyIron, 75);
            //ui bug fix
            uiIcon = Core.atlas.find("olupis-rusty-iron-conveyor-0-0");
        }};

        ironConveyor = new PowerConveyor("iron-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            unpoweredSpeed = 0.015f;
            poweredSpeed = 0.03f;
            speed = 0.03f;
            displayedSpeed = 2f;
            itemCapacity = 1;
            buildCostMultiplier = 2f;
            researchCost = with(OlupisItemsLiquid.iron, 100);
            hasPower = true;
            conductivePower = true;
            consumesPower = true;
            noUpdateDisabled = true;
            consumePower (1f/60);
        }};

        cobaltConveyor = new PowerConveyor("cobalt-conveyor"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron,1 ));
            health = 70;
            unpoweredSpeed = 0.03f;
            poweredSpeed = 0.06f;
            speed = 0.06f;
            displayedSpeed = 4f;
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
            requirements(Category.distribution, with(OlupisItemsLiquid.iron, 5, OlupisItemsLiquid.rustyIron, 2));
            speed = 26;
            capacity = 6;
            health = 50;
            armor = 1f;
            buildCostMultiplier = 6f;
            ((PowerConveyor)ironConveyor).junctionReplacement = this;
            ((Conveyor)rustyIronConveyor).junctionReplacement = this;
        }};

        ironBridge = new BufferedItemBridge("iron-bridge"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron, 10, OlupisItemsLiquid.rustyIron, 2));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
            armor = 1f;
            health = 50;

            ((PowerConveyor)ironConveyor).bridgeReplacement = this;
            ((Conveyor)rustyIronConveyor).bridgeReplacement = this;
        }};

        ironOverflow = new OverflowGate("iron-overflow"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron, 2, Items.lead, 5));
            buildCostMultiplier = 3f;
        }};

        ironUnderflow = new OverflowGate("iron-underflow"){{
            requirements(Category.distribution, with(OlupisItemsLiquid.iron, 2, Items.lead, 5));
            buildCostMultiplier = 3f;
            invert = true;
        }};

        //endregion
        //region Drills
        rustyDrill = new BurstDrill("rusty-drill"){{
            requirements(Category.production, with(OlupisItemsLiquid.rustyIron, 25));
            size = 3;
            drillTime = 60f * 10f;
            tier = 1;
            hasPower = true;
            squareSprite = false;
            drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
            consumePower(10f/60f);
            //TODO: Make it consume either steam or water
            consumeLiquid(Liquids.water, 0.02f);
        }};


        steamDrill = new Drill("steam-drill"){{
            //requirements(Category.production, with(OlupisItemsLiquid.iron, 25, OlupisItemsLiquid.rustyIron, 40));
            requirements(Category.production, with( OlupisItemsLiquid.iron, 40));
            tier = 2;
            drillTime = 600;
            size = 3;
            envEnabled ^= Env.space;
            researchCost = with(OlupisItemsLiquid.rustyIron, 100);

            hasPower = true;
            consumePower(1f/60f);
            consumeLiquid(OlupisItemsLiquid.steam, 0.05f);
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

        rustyPump = new Pump("rusty-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 5, Items.lead, 5));
            pumpAmount = 0.05f;
            liquidCapacity = 10f;
            size = 1;
        }};

        ironPump = new Pump("iron-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 10));
            pumpAmount = 0.1f;
            liquidCapacity = 20f;
            size = 2;
        }};

        displacementPump = new BurstPump("displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 20));
            pumpAmount = 120f;
            leakAmount = 0.02f;
            liquidCapacity = 150f;
            size = 3;

            consumePower(0.3f);
        }};

        massDisplacementPump = new BurstPump("mass-displacement-pump"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 30));
            pumpAmount = 180f;
            leakAmount = 0.1f;
            liquidCapacity = 200f;
            size = 4;

            consumePower(0.6f);
        }};

        leadPipe = new Conduit("lead-pipe"){{
            requirements(Category.liquid, with(Items.lead, 5));
            botColor = Color.valueOf("37323C");
            leaks = true;
            liquidCapacity = 5f;
            researchCostMultiplier = 3;
            underBullets = true;
            health = 60;
            liquidPressure = 0.95f;
        }};

        ironPipe = new ArmoredConduit("iron-pipe"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 5, OlupisItemsLiquid.rustyIron, 5));
            botColor = Color.valueOf("252731");
            leaks = true;
            liquidCapacity = 20f;
            researchCostMultiplier = 3;
            underBullets = true;
        }};

        pipeRouter = new LiquidRouter("pipe-router"){{
            requirements(Category.liquid, with(Items.lead, 10));
            liquidCapacity = 20f;
            underBullets = true;
            solid = true;
            liquidPressure = 0.85f; /* Nerfed so you can't bypass lead pipe being terrible */
        }};

        fortifiedCanister = new LiquidRouter("pipe-canister"){{
            requirements(Category.liquid, with(Items.lead, 50, OlupisItemsLiquid.iron, 20));
            liquidCapacity = 800f;
            solid = true;
            size = 2;
            liquidPressure = 0.95f;
        }};

        fortifiedTank = new LiquidRouter("pipe-tank"){{
            requirements(Category.liquid, with(Items.lead, 150, OlupisItemsLiquid.iron, 60));
            liquidCapacity = 2300f;
            solid = true;
            size = 3;
        }};

        pipeJunction = new LiquidJunction("pipe-junction"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 10, Items.lead, 10));
            solid = false;
            ((Conduit)ironPipe).junctionReplacement = this;
            ((Conduit)leadPipe).junctionReplacement = this;
        }};

        pipeBridge = new LiquidBridge("pipe-bridge"){{
            requirements(Category.liquid, with(OlupisItemsLiquid.iron, 10, Items.lead, 20 ));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 4;
            hasPower = false;
            ((Conduit)ironPipe).bridgeReplacement = this;
            ((Conduit)leadPipe).bridgeReplacement = this;
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

        steamBoiler = new AttributeCrafter("steam-boiler"){{
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
            requirements(Category.liquid, with(OlupisItemsLiquid.rustyIron, 30, Items.lead, 10));
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
            requirements(Category.production, ItemStack.with(OlupisItemsLiquid.iron, 30, Items.lead, 30));
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
            requirements(Category.crafting, with(OlupisItemsLiquid.iron, 100, Items.lead, 10));
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
            outputItem = new ItemStack(Items.graphite, 1);
            craftTime = 30f;
            itemCapacity = 20;
            size = 3;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            consumeLiquids(LiquidStack.with(Liquids.oil, 5f / 60f, OlupisItemsLiquid.steam, 10f/60f));
            consumePower(30f/60f);

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
            this.requirements(Category.units, BuildVisibility.sandboxOnly, ItemStack.with());
            size = 5;
            delay = 5;
        }};

        unitReplicatorSmall = new Replicator("unit-replicator-small"){{
            this.requirements(Category.units, BuildVisibility.sandboxOnly, ItemStack.with());
            size = 4;
            delay = 4;
        }};

        //endregion
        //region Turrets
        corroder = new LiquidTurret("corroder"){{ //architronito
            requirements(Category.turret, with(OlupisItemsLiquid.rustyIron, 50, Items.lead, 10));
            researchCost = with(OlupisItemsLiquid.rustyIron, 500);
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        lifetime = 13f;
                        speed = 5.55f;
                        drag = 0.008f;
                        damage = 6;
                        statusDuration = 60f * 2;
                        ammoMultiplier = 5f;
                        status = StatusEffects.corroded;
                        layer = Layer.bullet -2f;
                    }},
                    OlupisItemsLiquid.steam, new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                        lifetime = 10f;
                        speed = 7.7f;
                        damage = 15f;
                        drag = 0.009f;
                        ammoMultiplier = 3f;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                        evaporatePuddles = true;
                    }}
            );

            liquidCapacity = 5f;
            recoil = 1;
            shootY = 10f;
            reload = 10f;
            range = 75f;
            shootCone = 50f;
            health = 1000;
            size = 2;
            targetAir = true;
            consumePower(1f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            drawer = new DrawTurret("iron-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            layerOffset = -0.1f;
                            progress = PartProgress.recoil;
                            moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                            y = 1f;
                            mirror = false;
                        }},  new RegionPart("-front-wing"){{
                            layerOffset = -0.1f;
                            progress = PartProgress.warmup;
                            moves.add(new PartMove(PartProgress.recoil, 0f, 0, -12f));
                            mirror = true;
                        }}, new RegionPart("-back-wing"){{
                            layerOffset = -0.1f;
                            progress = PartProgress.smoothReload;
                            moves.add(new PartMove(PartProgress.recoil, 0f, -1f, 12f));
                            mirror = true;
                        }}
                );
            }};
            outlineColor = olupisBlockOutlineColour;
        }};

        dissolver = new LiquidTurret("dissolver"){{ //architonnerre
            requirements(Category.turret, with(OlupisItemsLiquid.iron, 50, Items.lead, 50));
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        lifetime = 21.5f;
                        speed = 5.8f;
                        hitSize = 7f;
                        damage = 6;
                        drag = 0.0009f;
                        ammoMultiplier = 5f;
                        statusDuration = 60f * 2;
                        status = StatusEffects.corroded;
                        layer = Layer.bullet -2f;
                        puddleSize = 7f;
                    }},
                    OlupisItemsLiquid.steam, new NoBoilLiquidBulletType(OlupisItemsLiquid.steam){{
                        lifetime = 14.5f;
                        speed = 8.8f;
                        hitSize = 7f;
                        damage = 10f;
                        drag = 0.0009f;
                        ammoMultiplier = 3f;
                        pierce = true;
                        collidesAir = true;
                        statusDuration = 60f * 4;
                        status = StatusEffects.corroded;
                        evaporatePuddles = true;
                    }}
            );
            recoil = 0.2f;
            reload = 5f;
            range = 130f;
            shootCone = 50f;
            drawer = new DrawTurret("iron-");
            shootSound = Sounds.steam;
            health = 2500;
            size = 3;
            targetAir = true;
            consumePower(1.5f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            outlineColor = olupisBlockOutlineColour;
             //ammoUseEffect = OlupisFxs.shootSteamLarge;
        }};

        shredder = new ItemTurret("shredder"){{
            requirements(Category.turret, with(OlupisItemsLiquid.iron, 100, Items.lead, 20, Items.graphite, 20));
            researchCostMultiplier = 0.05f;
            size = 3;
            health = 250;
            armor = 2;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);

            reload = 60f;
            shoot = new ShootSummon(0f, 0f, 0f, 0f);
            shootY = Vars.tilesize * size;
            range = 160;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            targetAir = false;
            outlineColor = olupisBlockOutlineColour;

            limitRange(1f);
            ammo(
            //TODO: Some how ignore Allied Non-Solids??? (ex: mines & conveyors)
                    OlupisItemsLiquid.rustyIron, new BasicBulletType(2.5f, 11){{
                        width = 40f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                        healPercent = 0f;
                        collidesTeam = true;
                        collideTerrain = false;
                        collidesAir = false;
                        pierceCap = 3;
                        knockback= 2f;
                        frontColor = Color.valueOf("ea8878");
                        backColor = Color.valueOf("ea8878");
                    }},
                    OlupisItemsLiquid.iron, new BasicBulletType(3f, 23){{
                        width = 40f;
                        height = 11f;
                        lifetime = 50f;
                        ammoMultiplier = 2;
                        collidesTeam = true;
                        healPercent = 0f;
                        collideTerrain = true;
                        collidesAir = false;
                        pierceCap = 4;
                        knockback= 2f;
                        frontColor = Color.valueOf("ea8878");
                        backColor = Color.valueOf("ea8878");
                    }}
            );
        }};

        //endregion
        //region Power
        wire = new Wire("wire"){{
            requirements(Category.power, with(OlupisItemsLiquid.rustyIron, 5));
            baseExplosiveness = 0.5f;
            solid = false;
            floating = true;
            placeableLiquid = true;
            consumePowerBuffered(1f);
            consumesPower = outputsPower = true;
            consumePower(1f/60f);
        }};

        superConductors = new Wire("super-conductor"){{
            requirements(Category.power, with(OlupisItemsLiquid.cobalt, 20, OlupisItemsLiquid.iron, 10));
            baseExplosiveness = 0.7f;
            health = 150;
            solid = false;
            floating = true;
        }};

        wireBridge = new BeamNode("wire-bridge"){{
            requirements(Category.power, with(OlupisItemsLiquid.iron, 30, Items.lead, 15));
            baseExplosiveness = 0.6f;
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

        hydroMill = new ThermalGeneratorNoLight("hydro-mill"){{
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

        hydroElectricGenerator = new ThermalGeneratorNoLight("hydro-electric-generator"){{
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

        rustyScrapWall = new Wall("rusty-scrap-wall"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, with(OlupisItemsLiquid.rustyIron, 6, Items.scrap, 3));
            size = 1;
            variants = 1;
            health = 240;
        }};

        rustyScrapWallLarge = new Wall("rusty-scrap-wall-large"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(rustyScrapWall.requirements, 4));
            size = 2;
            variants = 4;
            health = 960;
        }};

        rustyScrapWallHuge = new Wall("rusty-scrap-wall-huge"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(rustyScrapWall.requirements, 9));
            size = 3;
            variants  = 2;
            health = 3840;
        }};

        rustyScrapWallGigantic = new Wall("rusty-scrap-wall-gigantic"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, ItemStack.mult(rustyScrapWall.requirements, 16));
            size = 4;
            health = 2530;
        }};

        //endregion
        //region Effect
        mendFieldProjector = new DirectionalMendProjector ("mend-field-projector"){{
            requirements(Category.effect, with(Items.lead, 30, OlupisItemsLiquid.iron, 25));
            consumePower(0.3f);
            size = 2;
            reload = 200f;
            range = 40f;
            healPercent = 4f;
            phaseBoost = 4f;
            phaseRangeBoost = 20f;
            health = 80;
            consumeItem(Items.silicon).boost();
        }};

        taurus = new MendTurret("taurus"){{
            requirements(Category.effect, with(OlupisItemsLiquid.iron, 30, Items.lead, 40));
            size = 3;
            flags = EnumSet.of(BlockFlag.repair, BlockFlag.turret);

            shootType = new LaserBoltBulletType(5.2f, -5){{
                lifetime = 30f;
                healPercent = 5f;
                collidesTeam = true;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
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
            unitType = OlupisUnits.gnat;
            itemCapacity = 3000;
            health = 7000;
        }};

        coreRelic = new CoreBlock("core-relic"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 4;
            unitType = OlupisUnits.gnat;
            itemCapacity = 4500;
            health = 140000;
        }};

        coreShrine = new CoreBlock("core-shrine"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 5;
            unitType = OlupisUnits.gnat;
            itemCapacity = 6000;
            health = 280000;
        }};

        coreTemple = new CoreBlock("core-temple"){{
            requirements(Category.effect, with(OlupisItemsLiquid.rustyIron, 1500, OlupisItemsLiquid.iron, 1000));
            size = 6;
            unitType = OlupisUnits.gnat;
            itemCapacity = 7500;
            health = 560000;
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

    public static void NoIconFix(){
        /*I have no idea what I did to the game or what I did wrong, but they don't want to generate the ui icons
         so this is a workaround via manually assigning the uiIcon by hand -RushieWashie */
        rustyIronConveyor.uiIcon = Core.atlas.find("olupis-rusty-iron-conveyor-0-0");
        ironConveyor.uiIcon = Core.atlas.find("olupis-iron-conveyor-0-0");
        cobaltConveyor.uiIcon = Core.atlas.find("olupis-cobalt-conveyor-0-0");

        //wire.uiIcon = Core.atlas.find("olupis-wire-preview");
        //superConductors.uiIcon = Core.atlas.find("olupis-super-conductor-preview");
        wireBridge.uiIcon = Core.atlas.find("olupis-wire-bridge-preview");

        coreRemnant.fullIcon = Core.atlas.find("olupis-core-remnant-preview");
        fortifiedContainer.fullIcon = Core.atlas.find("olupis-fortified-container-preview");

    }

    public static void OlupisBlocksPlacementFix(){
        olupisBuildBlockSet.addAll(
                //TODO: find a dynamic way to add them? so less copy paste
                garden, bioMatterPress, unitReplicator, unitReplicatorSmall, rustElectrolyzer, steamBoiler, steamAgitator, hydrochloricGraphitePress, ironSieve,
                rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironJunction, ironBridge,
                leadPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump, fortifiedTank, fortifiedCanister,
                wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator,
                steamDrill, hydroElectricDrill, oilSeparator, rustyDrill,
                corroder, dissolver, shredder,
                rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge, rustyScrapWall, rustyScrapWallLarge, rustyScrapWallHuge, rustyScrapWallGigantic,
                coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer,
                mendFieldProjector, taurus,
                fortifiedMessageBlock,

                /* Legally required boulder*/
                mossyBoulder
        );

        sandBoxBlocks.addAll(
                /*just to make it easier for testing and/or sandbox*/
                Blocks.itemSource, Blocks.itemVoid, Blocks.liquidSource, Blocks.liquidVoid, Blocks.payloadSource, Blocks.payloadVoid,
                Blocks.worldProcessor, Blocks.logicProcessor, Blocks.microProcessor, Blocks.hyperProcessor, Blocks.message, Blocks.worldMessage, Blocks.reinforcedMessage,
                Blocks.logicDisplay, Blocks.largeLogicDisplay, Blocks.canvas, Blocks.payloadConveyor, Blocks.payloadRouter
        );
    }
}
