package olupis.content;

import arc.graphics.Color;
import arc.struct.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
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
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import olupis.world.blocks.*;
import olupis.world.entities.bullets.NoBoilLiquidBulletType;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.oil;
import static mindustry.type.ItemStack.with;
import static olupis.content.NyfalisAttribute.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisUnits.*;

public class NyfalisBlocks {
    //TODO: Woof woof wants  different class for loading turrets content so this isn't 1400~ lines long, pain & debating if should do
    //region Blocks Variables
    public static Block
        //environment
        /*Ores / Overlays */
        oreIron, oreIronWall, oreCobalt, oreOxidizedCopper, oreOxidizedLead, oreQuartz,

        /*Floors*/
        redSand, lumaGrass, yellowGrass, pinkGrass, frozenGrass,
        cinderBloomGrass, cinderBloomy, cinderBloomier, cinderBloomiest, mossyStone, mossStone, mossierStone, mossiestStone,
        grassyVent, mossyVent, stoneVent, basaltVent,

        /*Liquid floors*/
        redSandWater, lumaGrassWater, brimstoneSlag, mossyWater, pinkGrassWater, yellowMossyWater,

        /*props*/
        yellowBush, lumaFlora, bush, mossyBoulder, infernalBloom,

        /*walls*/
        redDune, pinkShrubs, lightWall,
        greenShrubsIrregular, greenShrubsCrooked, yellowShrubs, yellowShrubsIrregular, yellowShrubsCrooked,
        mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStoneWall,

        /*Trees*/
        nyfalisTree, mossTree, pinkTree, yellowTree, yellowTreeBlooming, infernalMegaBloom,

        //Buildings, sorted by category
        corroder, dissolver, shredder, hive, escalation, blitz, shatter,

        rustyDrill, steamDrill, hydroElectricDrill,

        rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironDistributor ,ironJunction, ironBridge, ironOverflow, ironUnderflow, ironUnloader,

        leadPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump, fortifiedTank, fortifiedCanister,
         steamBoiler, steamAgitator, broiler, oilSeparator,

        wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator, quartzBattery, mirror, solarTower, steamTurbine,

        rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge, rustyScrapWall, rustyScrapWallLarge, rustyScrapWallHuge, rustyScrapWallGigantic,

        garden, bioMatterPress, rustElectrolyzer, hydrochloricGraphitePress, ironSieve, siliconArcSmelter,

        construct, unitReplicator, unitReplicatorSmall,

        coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer,
        mendFieldProjector, taurus,

        fortifiedMessageBlock, mechanicalProcessor, mechanicalSwitch, mechanical
    ; //endregion

    public static Color nyfalisBlockOutlineColour = Color.valueOf("371404");
    public static ObjectSet<Block> nyfalisBuildBlockSet = new ObjectSet<>(), sandBoxBlocks = new ObjectSet<>(), nyfalisCores = new ObjectSet<>();

    public static void LoadWorldTiles(){
        //region Ores / Overlays
        oreIron = new OreBlock("ore-iron", rustyIron);

        oreIronWall = new OreBlock("ore-iron-wall", rustyIron){{
            wallOre = true;
        }};

        oreCobalt = new OreBlock("ore-cobalt", cobalt);

        /*uses ore's item as a name block in editor*/
        oreOxidizedCopper = new OreBlock("ore-oxidized-copper", copper);
        oreOxidizedLead = new OreBlock("ore-oxidized-lead", lead);

        oreQuartz = new OreBlock("ore-quartz", quartz){{
            variants = 3;
        }};
        //endregion
        // region Floors
        redSand = new Floor("red-sand-floor"){{
            itemDrop = Items.sand;
            playerUnmineable = true;
            attributes.set(Attribute.oil, 1.5f);
        }};

        lumaGrass = new Floor("luma-grass"){{
            variants = 3;
            attributes.set(bio, 0.08f);
            attributes.set(Attribute.water, 0.15f);
        }};

        yellowGrass = new Floor("yellow-grass"){{
            variants = 4;
            attributes.set(bio, 0.08f);
            attributes.set(Attribute.water, 0.15f);
        }};

        pinkGrass = new Floor("pink-grass"){{
            variants = 4;
            attributes.set(bio, 0.08f);
            attributes.set(Attribute.water, 0.15f);
        }};

        frozenGrass = new Floor("frozen-grass"){{
            attributes.set(Attribute.water, 0.15f);
            attributes.set(bio, 0.08f);
            wall = shrubs;
        }};

        cinderBloomy = new Floor("cinder-bloomy"){{
            variants = 3;
            attributes.set(bio, 0.03f);
            attributes.set(Attribute.water, -0.15f);
        }};

        cinderBloomier = new Floor("cinder-bloomier"){{
            variants = 3;
            attributes.set(bio, 0.02f);
            attributes.set(Attribute.water, -0.05f);
        }};

        cinderBloomiest = new Floor("cinder-bloomiest"){{
            variants = 3;
            attributes.set(bio, 0.01f);
        }};

        cinderBloomGrass = new Floor("cinder-bloom"){{
            variants = 3;
            attributes.set(bio, 0.06f);
            attributes.set(Attribute.water, 0.25f);
        }};

        mossierStone = new Floor("mossier-stone"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        mossyStone = new Floor("mossy-stone"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        mossiestStone = new Floor("mossiest-stone"){{
            attributes.set(bio, 0.1f);
            mapColor = mossierStone.mapColor;
            attributes.set(Attribute.water, 0.1f);
        }};

        mossStone = new Floor("moss-stone"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        grassyVent = new SteamVent("grassy-vent"){{
            effectColor = Color.white;
            parent = blendGroup = grass;
            attributes.set(Attribute.steam, 1f);
        }};

        mossyVent = new SteamVent("mossy-vent"){{
            effectColor = Color.white;
            parent = blendGroup = mossStone;
            attributes.set(Attribute.steam, 1f);
        }};

        stoneVent = new SteamVent("stone-vent"){{
            parent = blendGroup = stone;
            attributes.set(Attribute.steam, 1f);
        }};

        basaltVent = new SteamVent("basalt-vent"){{
            parent = blendGroup = basalt;
            attributes.set(Attribute.steam, 1f);
        }};
        //endregion
        //region Liquid floor

        redSandWater = new Floor("red-sand-water"){{
            isLiquid = supportsOverlay = true;

            variants = 0;
            albedo = 0.9f;
            statusDuration = 50f;
            speedMultiplier = 0.8f;
            liquidDrop = Liquids.water;
            status = StatusEffects.wet;
            cacheLayer = CacheLayer.water;
        }};

        lumaGrassWater = new Floor("luma-grass-water"){{
            isLiquid = supportsOverlay = true;
            liquidDrop = Liquids.water;
            status = StatusEffects.wet;
            statusDuration = 50f;
            speedMultiplier = 0.8f;
            cacheLayer = CacheLayer.water;
            variants = 0;
            albedo = 0.9f;
        }};

        brimstoneSlag = new Floor("brimstone-slag"){{
            isLiquid = emitLight = true;

            variants = 0;
            drownTime = 30f;
            lightRadius = 40f;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            liquidDrop = Liquids.slag;
            damageTaken = 9999999f;
            status = StatusEffects.melting;
            cacheLayer = CacheLayer.slag;
            attributes.set(Attribute.heat, 0.90f);
            lightColor = Color.valueOf("D54B3B").a(0.38f);
        }};

        mossyWater = new Floor("mossy-water"){{
            isLiquid = supportsOverlay = true;

            variants = 0;
            albedo = 0.9f;
            statusDuration = 50f;
            speedMultiplier = 0.8f;
            status = StatusEffects.wet;
            liquidDrop = Liquids.water;
            cacheLayer = CacheLayer.water;
        }};

        pinkGrassWater = new Floor("pink-grass-water"){{
            isLiquid = supportsOverlay = true;

            statusDuration = 50f;
            speedMultiplier = 0.8f;
            variants = 0;
            albedo = 0.9f;
            liquidDrop = Liquids.water;
            status = StatusEffects.wet;
            cacheLayer = CacheLayer.water;
        }};

        yellowMossyWater = new Floor("yellow-mossy-water"){{
            isLiquid = supportsOverlay = true;

            variants = 0;
            albedo = 0.9f;
            statusDuration = 50f;
            speedMultiplier = 0.8f;
            status = StatusEffects.wet;
            liquidDrop = Liquids.water;
            cacheLayer = CacheLayer.water;
        }};

        //endregion
        //region Props
        yellowBush = new Prop("yellow-bush"){{
            variants = 2;
            frozenGrass.asFloor().decoration = this;
            yellowGrass.asFloor().decoration = this;
        }};

        lumaFlora = new Prop("luma-flora"){{
            variants = 2;
            breakSound = Sounds.plantBreak;
            lumaGrass.asFloor().decoration = this;
            pinkGrass.asFloor().decoration = this;
        }};

        bush = new Prop("bush"){{
            variants = 2;
            breakSound = Sounds.plantBreak;
            grass.asFloor().decoration = this;
        }};

        mossyBoulder = new Prop("mossy-boulder"){{
            variants = 2;
            frozenGrass.asFloor().decoration = this;
            mossierStone.asFloor().decoration = this;
            mossiestStone.asFloor().decoration = this;
        }};

        infernalBloom = new Prop("infernal-bloom"){{
            variants = 3;
            breakSound = Sounds.plantBreak;
            cinderBloomGrass.asFloor().decoration = this;
            cinderBloomier.asFloor().decoration = this;
            cinderBloomiest.asFloor().decoration = this;
        }};

        //endregion
        //region Walls

        redDune = new StaticWall("red-dune-wall"){{
            redSand.asFloor().wall = this;
            attributes.set(Attribute.sand, 2f);
        }};

        pinkShrubs = new StaticWall("pink-shrubs");

        greenShrubsIrregular = new TallBlock("green-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        /*Irregular varrients that don't show up on top of tress*/
        greenShrubsCrooked = new StaticTree("green-shrubs-crooked"){{
            variants = 2;
            clipSize = 128f;
        }};

        yellowShrubs = new StaticWall("yellow-shrubs");

        yellowShrubsIrregular = new TallBlock("yellow-shrubs-irregular"){{
            variants = 2;
            clipSize = 128f;
        }};

        yellowShrubsCrooked = new TallBlock("yellow-shrubs-crooked"){{
            variants = 2;
            clipSize = 128f;
        }};

        mossyStoneWall = new StaticWall("mossy-stone-wall"){{
            attributes.set(Attribute.sand, 1f);
            mossierStone.asFloor().wall = this;
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
        //endregion
        //region Trees
        nyfalisTree = new TreeBlock("olupis-tree");
        mossTree = new TreeBlock("moss-tree");
        pinkTree = new TreeBlock("pink-tree");
        yellowTree = new TreeBlock("yellow-tree");
        yellowTreeBlooming = new TreeBlock("yellow-tree-blooming");
        infernalMegaBloom = new TreeBlock("infernal-megabloom"){{
            variants = 4;
            clipSize = 128f;
        }};

        //endregion
    }
    public static void LoadBlocks(){
        //region Distribution
        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            health = 45;
            speed = 0.015f;
            displayedSpeed = 1f;
            buildCostMultiplier = 2f;
            researchCost = with(rustyIron, 25);
            requirements(Category.distribution, with(rustyIron, 1));
        }};

        ironConveyor = new PowerConveyor("iron-conveyor"){{
            hasPower = conductivePower = consumesPower = noUpdateDisabled = true;

            health = 70;
            speed = 0.03f;
            itemCapacity = 1;
            poweredSpeed = 0.03f;
            unpoweredSpeed = 0.005f;
            displayedSpeedPowered = 4.2f;
            displayedSpeed = buildCostMultiplier = 2f;

            researchCost = with(iron, 150);
            consumePower (1f/60).boost();
            requirements(Category.distribution, with(iron, 1, rustyIron, 5 ));
        }};

        cobaltConveyor = new PowerConveyor("cobalt-conveyor"){{
            hasPower = conductivePower = consumesPower = true;

            health = 70;
            speed = 0.06f;
            itemCapacity = 1;
            poweredSpeed = 0.06f;
            unpoweredSpeed = 0.015f;
            displayedSpeedPowered = 9f;
            displayedSpeed =buildCostMultiplier = 2f;

            consumePower (1f/60);
            researchCost = with(cobalt, 1500, lead, 1500);
            requirements(Category.distribution, with(cobalt, 1, lead, 5 ));
        }};

        ironRouter = new Router("iron-router"){{
            buildCostMultiplier = 4f;

            researchCost = with(rustyIron, 40);
            requirements(Category.distribution, with(rustyIron, 3, lead, 1));
        }};

        ironDistributor = new Router("iron-distributor"){{
            size = 2;
            buildCostMultiplier = 4f;
            researchCost = with(rustyIron, 40, lead, 40);
            requirements(Category.distribution, with(rustyIron, 16, lead, 4));
        }};

        ironJunction = new Junction("iron-junction"){{
            speed = 26;
            capacity = 6;
            health = 50;
            armor = 1f;
            buildCostMultiplier = 2f;

            researchCost = with(rustyIron, 400, lead, 400);
            ((Conveyor)rustyIronConveyor).junctionReplacement = this;
            ((PowerConveyor)ironConveyor).junctionReplacement = this;
            requirements(Category.distribution, with(lead, 20, rustyIron, 30));
        }};

        ironBridge = new BufferedItemBridge("iron-bridge"){{
            fadeIn = moveArrows = false;

            range = 4;
            armor = 1f;
            health = 50;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;

            researchCost = with(iron, 1000, rustyIron, 2500);
            ((Conveyor)rustyIronConveyor).bridgeReplacement = this;
            ((PowerConveyor)ironConveyor).bridgeReplacement = this;
            requirements(Category.distribution, with(iron, 10, rustyIron, 2));
        }};

        ironOverflow = new OverflowSorter("iron-overflow"){{
            buildCostMultiplier = 3f;
            researchCost = with(lead, 1000, iron, 1000);
            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnderflow = new OverflowSorter("iron-underflow"){{
            invert = true;
            buildCostMultiplier = 3f;
            researchCost = with(lead, 1000, iron, 1000);
            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnloader = new DirectionalUnloader("iron-unloader"){{
            solid = false;
            allowCoreUnload = true;

            speed = 2f;
            health = 120;
            regionRotated1 = 1;
            researchCost = with(lead, 1000, graphite, 1000, iron, 1000);
            requirements(Category.distribution, with(iron, 20, graphite, 20, lead, 35));
        }};

        //endregion
        //region Drills / crafting
        rustyDrill = new BoostableBurstDrill("rusty-drill"){{
            hasPower = true;
            squareSprite = false;

            tier = 1;
            size = 3;
            drillTime = 60f * 14f;

            drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
            researchCost = with(rustyIron,50);
            consumePower(10f/60f);
            consumeLiquid(Liquids.water, 0.02f).boost(); //TODO: Make it consume either steam or water
            requirements(Category.production, with(rustyIron, 25));
        }};


        steamDrill = new Drill("steam-drill"){{
            hasPower = true;
            tier = 2;
            size = 3;
            drillTime = 330;

            envEnabled ^= Env.space;
            consumePower(1f/60f);
            consumeLiquid(steam, 0.05f);
            researchCost = with(iron, 2000, lead, 4000);
            consumeLiquid(Liquids.slag, 0.06f).boost();
            requirements(Category.production, with( iron, 40, lead, 20));
        }};

        hydroElectricDrill = new Drill("hydro-electric-drill"){{
            tier = 3;
            size = 4;
            drillTime = 600;

            envEnabled ^= Env.space;
            consumeLiquid(steam, 0.05f);
            consumePower(0.3f);
            consumeLiquid(Liquids.slag, 0.06f).boost();
            researchCost = with(iron, 5000, graphite, 5000, lead, 7000);
            requirements(Category.production, with(iron, 60, graphite, 70, lead, 30));
        }};

        garden = new AttributeCrafter("garden"){{
            hasLiquids = hasPower = hasItems = legacyReadWarmup = true;
            size = 3;
            craftTime = 200;
            maxBoost = 2.5f;

            attribute = bio;
            craftEffect = Fx.none;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawLiquidTile(Liquids.water){{alpha = 0.5f;}},
                    new DrawDefault(),
                    new DrawRegion("-top")
            );
            consumePower(80f / 60f);
            consumeLiquid(Liquids.water, 18f / 60f);
            researchCost = with(iron, 2000, lead, 2500, rustyIron, 2500);
            outputItem = new ItemStack(condensedBiomatter, 1);
            requirements(Category.production, ItemStack.with(iron, 150, lead, 60, rustyIron, 30));
        }};

        //endregion
        //region liquid
        leadPipe = new Conduit("lead-pipe"){{
            leaks = underBullets = true;

            health = 60;
            liquidCapacity = 5f;
            liquidPressure = 0.95f;
            researchCostMultiplier = 0.5f;
            botColor = Color.valueOf("37323C");
            requirements(Category.liquid, with(lead, 5));
        }};

        ironPipe = new ArmoredConduit("iron-pipe"){{
            leaks = underBullets = true;

            liquidCapacity = 20f;
            researchCostMultiplier = 3;
            botColor = Color.valueOf("252731");
            requirements(Category.liquid, with(iron, 5, rustyIron, 5));
        }};

        rustyPump = new Pump("rusty-pump"){{
            size = 1;
            pumpAmount = 0.05f;
            liquidCapacity = 10f;
            requirements(Category.liquid, with(rustyIron, 5, lead, 5));
        }};

        ironPump = new Pump("iron-pump"){{
            size = 2;
            pumpAmount = 0.1f;
            liquidCapacity = 20f;
            requirements(Category.liquid, with(iron, 10));
        }};

        displacementPump = new BurstPump("displacement-pump"){{
            size = 3;
            pumpAmount = 120f;
            leakAmount = 0.02f;
            liquidCapacity = 150f;
            consumePower(0.3f);
            researchCost = with(iron, 5000, lead, 10000, graphite, 5000, rustyIron, 10000);
            requirements(Category.liquid, with(iron, 30, graphite, 30, lead, 60, rustyIron, 60));
        }};

        massDisplacementPump = new BurstPump("mass-displacement-pump"){{
            size = 4;
            leakAmount = 0.1f;
            pumpAmount = 180f;
            liquidCapacity = 200f;
            consumePower(0.6f);
            researchCost = with(iron, 10000, lead, 50000, graphite, 10000, silicon, 10000);
            requirements(Category.liquid, with(iron, 60, graphite, 60, lead,150, silicon, 60));
        }};

        pipeRouter = new LiquidRouter("pipe-router"){{
            solid = underBullets = true;
            liquidCapacity = 20f;
            liquidPressure = 0.85f; /* Nerfed so you can't bypass lead pipe being terrible */
            researchCost = with(lead,50);
            requirements(Category.liquid, with(lead, 10));
        }};

        fortifiedCanister = new LiquidRouter("pipe-canister"){{
            solid = true;
            size = 2;
            liquidCapacity = 800f;
            liquidPressure = 0.95f;
            researchCost = with(lead, 1000, iron, 1000);
            requirements(Category.liquid, with(lead, 50, iron, 20));
        }};

        fortifiedTank = new LiquidRouter("pipe-tank"){{
            solid = true;
            size = 3;
            liquidCapacity = 2300f;
            researchCost = with(lead, 2500, iron, 2500);
            requirements(Category.liquid, with(lead, 150, iron, 60));
        }};

        pipeJunction = new LiquidJunction("pipe-junction"){{
            solid = false;
            ((Conduit)ironPipe).junctionReplacement = this;
            ((Conduit)leadPipe).junctionReplacement = this;
            researchCost = with(lead,200, rustyIron,200);

            /*expensive, since you can cheese the terribleness of pipes with this*/
            requirements(Category.liquid, with(rustyIron, 50, lead, 50));
        }};

        pipeBridge = new LiquidBridge("pipe-bridge"){{
            fadeIn = moveArrows = hasPower = false;
            range = 4;
            arrowSpacing = 6f;
            ((Conduit)ironPipe).bridgeReplacement = this;
            ((Conduit)leadPipe).bridgeReplacement = this;
            researchCost = with(iron, 700, lead, 700);
            requirements(Category.liquid, with(iron, 10, lead, 20));
        }};

        oilSeparator = new GenericCrafter("oil-separator"){{
            rotate = invertFlip = true;
            size = 4;
            craftTime = 15f;
            regionRotated1 = 3;
            liquidCapacity = 60f;
            liquidOutputDirections = new int[]{1,3};

            outputLiquids = LiquidStack.with(lightOil, 4/20, heavyOil,  7/60);
            consumePower(1.2f);
            consumeLiquid(oil, 40/60f);
            researchCostMultiplier = 1.3f;
            requirements(Category.liquid, with(iron, 50));
        }};

        steamBoiler = new AttributeCrafter("steam-boiler"){{
            hasPower = hasLiquids = outputsLiquid = solid = true;
            rotate = false;

            size = 2;
            craftTime = 150f;
            boostScale = 0.1f;
            liquidCapacity = 30f;
            envEnabled = Env.any;
            attribute = Attribute.heat;

            outputLiquid = new LiquidStack(steam, 12/60f);
            consumePower(1f);
            consumeLiquid(Liquids.water, 20/60f);
            requirements(Category.liquid, with(rustyIron, 20));
        }};

        steamAgitator = new AttributeCrafter("steam-agitator"){{
            outputsLiquid = solid = true;
            displayEfficiency = rotate = false;

            size = 3;
            boostScale = 0.1f;
            craftTime = 150f;
            baseEfficiency = 0f;
            liquidCapacity = 30f;
            envEnabled = Env.any;
            attribute = Attribute.steam;
            minEfficiency = 9f - 0.0001f;

            researchCost = with(lead, 300, rustyIron, 300);
            outputLiquid = new LiquidStack(steam, 10/60f);
            requirements(Category.liquid, with(rustyIron, 30, lead, 10));
        }};

        broiler = new GenericCrafter("broiler"){{
            hasLiquids = hasPower = true;

            size = 2;
            health = 600;
            craftTime = 10f;

            consumePower(1f);
            consumeItem(rustyIron, 2);
            consumeItem(scrap, 1).boost();
            outputLiquid = new LiquidStack(Liquids.slag, 12f / 60f);
            drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion());
            requirements(Category.liquid, with(graphite, 50, iron, 40, lead, 80, silicon, 10));
        }};

        //endregion
        //region Production
        rustElectrolyzer = new GenericCrafter("rust-electrolyzer"){{
            hasPower = hasItems = hasLiquids = solid = outputsLiquid = true;
            rotate = false;

            size = 2;
            craftTime = 120;
            liquidCapacity = 24f;
            envEnabled = Env.any;
            lightLiquid = Liquids.cryofluid;

            consumePower(1f);
            outputItem = new ItemStack(iron, 1);
            consumeLiquid(Liquids.water, 12f / 60f);
            consumeItems(with(lead, 1, rustyIron,1));
            researchCost = with(rustyIron, 400, lead, 400);
            requirements(Category.crafting, with(rustyIron, 80, lead, 80));
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
        }};

        hydrochloricGraphitePress  = new GenericCrafter("hydro-graphite-press"){{
            hasItems = hasLiquids = hasPower = true;

            size = 3;
            craftTime = 30f;
            itemCapacity = 20;
            craftEffect = Fx.pulverizeMedium;

            consumePower(30f/60f);
            researchCost = with(rustyIron, 3500,  iron, 3000);
            outputItem = new ItemStack(Items.graphite, 1);
            requirements(Category.crafting, with(iron, 150, rustyIron, 300));
            consumeLiquids(LiquidStack.with(Liquids.oil, 5f / 60f, NyfalisItemsLiquid.steam, 10f/60f));
        }};

        siliconArcSmelter = new GenericCrafter("silicon-arc-smelters") {{
            hasPower= hasItems = true;
            size = 4;
            craftTime = 30f;
            itemCapacity = 20;

            consumePower(30f/60f);
            outputItem = new ItemStack(silicon, 1);
            consumeItems(with(quartz, 2, graphite, 1));
            requirements(Category.crafting, with(lead, 100, graphite, 60, iron, 30));
        }};

        bioMatterPress = new GenericCrafter("biomatter-press"){{
            hasLiquids = hasPower = true;
            size = 2;
            health = 320;
            craftTime = 20f;
            liquidCapacity = 60f;

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
            consumePower(0.7f);
            consumeItem(condensedBiomatter, 1);
            outputLiquid = new LiquidStack(oil, 18f / 60f);
            requirements(Category.crafting, with(iron, 100, lead, 30));
        }};

        ironSieve  = new GenericCrafter("iron-sieve"){{
            //not to be confused with iron shiv
            hasPower = hasItems = true;
            hasLiquids = false;

            size = 2;
            craftTime = 30f;
            itemCapacity = 20;

            craftEffect = Fx.pulverizeMedium;
            consumePower(1.8f);
            consumeItem(Items.sand, 2);
            outputItem = new ItemStack(rustyIron, 2);
            requirements(Category.crafting, with(rustyIron, 60));
        }};

        //endregion
        //region Units
        construct = new PowerUnitTurret("construct"){{
            alwaysShooting = true;

            size = 4;
            shootY = 0f;
            reload = 600f;
            shootSound = Sounds.respawn;
            displayUnits = Seq.with(spirit);

            consumePower(6f);
            shootType = new BasicBulletType(2.5f, -1){{
                shootEffect = Fx.unitLand;
                ammoMultiplier = 1f;
                spawnUnit = spirit;
            }};
            researchCost = with(lead, 3000, silicon, 3000, iron, 3000, rustyIron, 3000);
            requirements(Category.units, with(iron, 200, lead, 100, silicon, 60, rustyIron, 200));
        }};

        unitReplicator = new Replicator("unit-replicator"){{
            size = 5;
            delay = 5f;

            this.requirements(Category.units, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        unitReplicatorSmall = new Replicator("unit-replicator-small"){{
            size = 4;
            delay = 4f;

            this.requirements(Category.units, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        //endregion
        //region Turrets
        corroder = new LiquidTurret("corroder"){{ //architronito
            requirements(Category.turret, with(rustyIron, 50, lead, 10));
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
            researchCost = with(rustyIron, 100);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
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
            displayUnits = Seq.with(mite);
            shootSound = Sounds.respawn;

            ammo(
                silicon, new BasicBulletType(2.5f, 11){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    spawnUnit = mite;
                }}
            );
            researchCost = with(lead, 1500, silicon, 1500,  iron, 1500);
            requirements(Category.turret, with(iron, 100, lead, 30, silicon, 30));
        }};

        //TODO: Escalation - A early game rocket launcher that acts similarly to the scathe but with lower range and damage. (Decent rate of fire, weak against high health single targets, slow moving rocket, high cost but great AOE)
        //TODO: Blitz (Recursor) - A recursive mortar turret that shoots long ranged recursive shells at the enemy (Has Really low rate of fire, high range, shells explode into multiple more shells on impact)
        //TODO:Shatter - A weak turret that shoots a spray of glass shards at the enemy. (High rate of fire, low damage, has pierce, very low defense, low range)

        //endregion
        //region Power
        wire = new Wire("wire"){{
            floating = placeableLiquid = consumesPower = outputsPower = true;
            solid = false;
            baseExplosiveness = 0.5f;
            consumePower(1f/60f);
            researchCost = with(rustyIron,20);
            consumePowerBuffered(1f);
            requirements(Category.power, with(rustyIron, 5));
        }};

        superConductors = new Wire("super-conductor"){{
            floating = true;
            solid = false;
            health = 150;
            baseExplosiveness = 0.7f;
            researchCost = with(iron, 2000, cobalt, 2000);
            requirements(Category.power, with(cobalt, 20, iron, 10));
        }};

        wireBridge = new BeamNode("wire-bridge"){{
            consumesPower = outputsPower = floating = true;
            range = 5;
            health = 100;
            pulseMag = 0f;
            laserWidth = 0.4f;
            baseExplosiveness = 0.6f;
            consumePower(10f/ 60f);
            laserColor2 = Color.valueOf("65717E");
            laserColor1 = Color.valueOf("ACB5BA");
            researchCost = with(lead, 1000, iron, 1000);
            requirements(Category.power, with(iron, 30, Items.lead, 15));
        }};

        windMills = new WindMill("wind-mill"){{
            size = 3;
            powerProduction = 20f/60f;
            displayEfficiencyScale = 1.1f;
            attribute = Attribute.steam;
            consumeLiquid(oil, 0.2f).boost();
            researchCost = with(rustyIron, 75);
            requirements(Category.power, with(rustyIron, 30));
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh =  0.01f;
            }});
        }};

        hydroMill = new ThermalGeneratorNoLight("hydro-mill"){{
            floating = true;

            size = 3;
            effectChance = 0.011f;
            powerProduction = 17f/60f;
            ambientSoundVolume = 0.06f;

            attribute = hydro;
            generateEffect = Fx.steam;
            ambientSound = Sounds.hum;
            researchCost = with(iron, 2000, rustyIron, 2000);
            requirements(Category.power, with(iron, 30, rustyIron, 50));
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        hydroElectricGenerator = new ThermalGeneratorNoLight("hydro-electric-generator"){{
            placeableLiquid = floating = true;

            size = 5;
            effectChance = 0.011f;
            powerProduction = 23f/60f;
            ambientSoundVolume = 0.06f;

            attribute = hydro;
            generateEffect = Fx.steam;
            ambientSound = Sounds.hum;
            researchCost = with(iron, 7000, rustyIron, 7000, lead, 7000, silicon, 7000);
            requirements(Category.power, with(iron, 30, rustyIron, 50, lead, 50, silicon, 30));
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.6f * 9f){{
                blurThresh = 0.01f;
            }});
        }};

        quartzBattery = new Battery("quartz-battery"){{
            size = 2;
            baseExplosiveness = 1f;
            consumePowerBuffered(1000f);
            researchCost = with(quartz, 5000, lead, 5000, silicon, 5000);
            requirements(Category.power, with(quartz, 50, lead, 50, silicon, 50));
        }};

        //TODO: Solar receiver & Mirror -> Super structure `Mirror(s)->Redirector->Solar tower+water=steam->steam turbine(s)`
        // Mirror -> SolarTower -> Heat + water-> SteamTurbine -> power

        //endregion
        //region Wall
        rustyWall = new Wall("rusty-wall"){{
            size = 1;
            health =  350;
            researchCost = with(rustyIron,80);
            requirements(Category.defense,with(rustyIron, 12));
        }};

        rustyWallLarge = new Wall("rusty-wall-large"){{
            size = 2;
            health =  1400;
            researchCost = with(rustyIron,200);
            requirements(Category.defense,with(rustyIron, 48));
        }};

        rustyWallHuge = new Wall("rusty-wall-huge"){{
            size = 3;
            health = 2690;
            researchCost = with(rustyIron,1000);
            requirements(Category.defense,with(rustyIron, 620));
        }};

        rustyWallGigantic = new Wall("rusty-wall-gigantic"){{
            size = 4;
            health = 3600;
            researchCost = with(rustyIron,4200);
            requirements(Category.defense, BuildVisibility.editorOnly, with(rustyIron, 1500));
        }};

        ironWall = new Wall("iron-wall"){{
            size = 1;
            health = 700;
            researchCost = with(iron, 2000);
            requirements(Category.defense,with(iron, 15));
        }};

        ironWallLarge = new Wall("iron-wall-large"){{
            size = 2;
            health = 2800;
            researchCost = with(iron, 5000);
            requirements(Category.defense,with(iron, 200));
        }};

        rustyScrapWall = new Wall("rusty-scrap-wall"){{
            size = 1;
            variants = 1;
            health = 240;
            requirements(Category.defense, BuildVisibility.editorOnly, with(rustyIron, 6, scrap, 3));
        }};

        rustyScrapWallLarge = new Wall("rusty-scrap-wall-large"){{
            size = 2;
            variants = 3;
            health = 960;
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.mult(rustyScrapWall.requirements, 4));
        }};

        rustyScrapWallHuge = new Wall("rusty-scrap-wall-huge"){{
            size = 3;
            variants  = 2;
            health = 3840;
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.mult(rustyScrapWall.requirements, 9));
        }};

        rustyScrapWallGigantic = new Wall("rusty-scrap-wall-gigantic"){{
            size = 4;
            health = 2530;
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.mult(rustyScrapWall.requirements, 16));
        }};

        //endregion
        //region Effect
        //TODO: Keep?
        mendFieldProjector = new DirectionalMendProjector ("mend-field-projector"){{
            size = 2;
            health = 80;
            range = 40f;
            reload = 200f;
            phaseRangeBoost = 20f;
            phaseBoost = healPercent = 4f;

            consumePower(0.3f);
            consumeItem(Items.silicon).boost();
            requirements(Category.effect, with(Items.lead, 30, iron, 25));
        }};

        taurus = new MendTurret("taurus"){{
            size = 3;
            recoils = 2;
            reload = 10f;
            shootEffect = Fx.shootHeal;
            outlineColor = nyfalisBlockOutlineColour;

            drawer = new DrawTurret("iron-"){{
                for(int i = 0; i < 2; i ++){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        under = true;
                        moveY = -1.5f;
                        recoilIndex = f;
                        progress = PartProgress.recoil;
                    }});
                }
            }};
            shootType = new BasicBulletType(5.2f, -5, "olupis-diamond-bullet"){{
                collidesTeam = true;
                collidesAir =  false;

                width = 10f;
                height = 16f;
                lifetime = 30f;
                healPercent = 7f;
                backColor = Pal.heal;
                /*added slight homing so it can hit 1x1 blocks better or at all*/
                homingPower = 0.02f;
                frontColor = Color.white;
                shootSound = Sounds.sap;
            }};
            limitRange(2);
            consumePower(3.3f);
            shoot = new ShootAlternate(9f);
            researchCost = with(iron, 1100, lead, 1100);
            flags = EnumSet.of(BlockFlag.repair, BlockFlag.turret);
            requirements(Category.effect, with(iron, 30, Items.lead, 40));
        }};

        fortifiedContainer = new StorageBlock("fortified-container"){{
            coreMerge = false;
            size = 2;
            health =  740;
            scaledHealth = 150;
            itemCapacity = 1400;
            researchCost = with(iron, 2000, rustyIron, 2000);
            requirements(Category.effect, with(rustyIron, 75, iron, 50));
        }};

        fortifiedVault = new StorageBlock("fortified-vault"){{
            coreMerge = false;
            size = 3;
            health =  1500;
            scaledHealth = 120;
            itemCapacity = 2700;
            researchCost = with(iron, 4000, rustyIron, 4000, silicon, 4000);
            requirements(Category.effect, with(rustyIron, 150, iron, 100, silicon, 100));
        }};

        coreRemnant = new PropellerCoreBlock("core-remnant"){{
            alwaysUnlocked = isFirstTier = true;
            size = 2;
            health = 3500;
            itemCapacity = 1500;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, lead, 1500));
        }};

        coreVestige = new PropellerCoreBlock("core-vestige"){{
            size = 3;
            health = 7000;
            itemCapacity = 3000;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, iron, 1000));
        }};

        coreRelic = new CoreBlock("core-relic"){{
            size = 4;
            health = 140000;
            itemCapacity = 4500;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, iron, 1000));
        }};

        coreShrine = new CoreBlock("core-shrine"){{
            size = 5;
            health = 280000;
            itemCapacity = 6000;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, iron, 1000));
        }};

        coreTemple = new CoreBlock("core-temple"){{
            size = 6;
            health = 560000;
            itemCapacity = 7500;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, iron, 1000));
        }};

        lightWall = new PrivilegedLightBlock("light-wall"){{
            alwaysUnlocked = true;
            brightness = 0.75f;
            radius = 140f;
            requirements(Category.effect, BuildVisibility.editorOnly, with());
        }};
        //endregion
        //region Logic
        fortifiedMessageBlock = new MessageBlock("fortified-message-block"){{
            health = 100;
            researchCost = with(iron, 1000, graphite, 1000);
            requirements(Category.logic, with(Items.graphite, 10, iron, 5));
        }};
        //endregion
    }
    public static void AddAttributes(){
        ice.attributes.set(bio, 0.01f);
        grass.attributes.set(bio, 0.1f);
        dirt.attributes.set(bio, 0.03f);
        mud.attributes.set(bio, 0.03f);
        stone.attributes.set(bio, 0.03f);
        charr.attributes.set(bio, 0.03f);
        snow.attributes.set(bio, 0.01f);
        craters.attributes.set(bio, 0.5f);

        water.attributes.set(hydro, 0.3f);
        deepwater.attributes.set(hydro, 0.5f);
        sandWater.attributes.set(hydro, 0.3f);
        taintedWater.attributes.set(hydro, 0.3f);
        darksandWater.attributes.set(hydro, 0.3f);
        deepTaintedWater.attributes.set(hydro, 0.3f);
        darksandTaintedWater.attributes.set(hydro, 0.3f);

        mossyWater.attributes.set(hydro, 0.3f);
        redSandWater.attributes.set(hydro, 0.3f);
        pinkGrassWater.attributes.set(hydro, 0.3f);
        lumaGrassWater.attributes.set(hydro, 0.3f);
        yellowMossyWater.attributes.set(hydro, 0.3f);

        mossyStone.asFloor().decoration = bush;
        mossyStone.asFloor().decoration = boulder;
        cinderBloomy.asFloor().decoration = basaltBoulder;
    }

    public static void NyfalisBlocksPlacementFix(){
        nyfalisBuildBlockSet.clear();
        Vars.content.blocks().each(b->{
            if(b.name.startsWith("olupis-") && b.isVisible()) nyfalisBuildBlockSet.add(b);
        });

        nyfalisCores.addAll(coreRemnant, coreRelic, coreShrine, coreTemple, coreVestige);

        sandBoxBlocks.addAll(
            /*just to make it easier for testing and/or sandbox*/
            itemSource, itemVoid, liquidSource, liquidVoid, payloadSource, payloadVoid, powerSource, powerVoid, heatSource,
            worldProcessor, logicProcessor, microProcessor, hyperProcessor, message, worldMessage, reinforcedMessage,
            logicDisplay, largeLogicDisplay, canvas, payloadConveyor, payloadRouter
        );
    }
}
