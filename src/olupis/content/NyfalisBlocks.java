package olupis.content;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.struct.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.blocks.payloads.PayloadRouter;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import olupis.world.blocks.defence.*;
import olupis.world.blocks.misc.*;
import olupis.world.blocks.power.*;
import olupis.world.blocks.processing.*;
import olupis.world.entities.bullets.HealOnlyBulletType;
import olupis.world.entities.bullets.SpawnHelperBulletType;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.oil;
import static mindustry.type.ItemStack.with;
import static olupis.content.NyfalisAttributeWeather.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisUnits.*;

public class NyfalisBlocks {
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
        yellowBush, lumaFlora, bush, mossyBoulder, infernalBloom, redSandBoulder, glowBloom, luminiteBoulder,

        /*walls*/
        redDune, pinkShrubs, lightWall, lumaWall,
        greenShrubsIrregular, greenShrubsCrooked, yellowShrubs, yellowShrubsIrregular, yellowShrubsCrooked,
        mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStoneWall,

        /*Trees*/
        nyfalisTree, mossTree, pinkTree, yellowTree, yellowTreeBlooming, infernalMegaBloom, orangeTree,

        //Buildings, sorted by category
        corroder, dissolver, shredder, hive, escalation, shatter, avenger, aegis, obliterator,

        rustyDrill, steamDrill, hydroElectricDrill,

        rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironDistributor ,ironJunction, ironBridge, ironOverflow, ironUnderflow, ironUnloader, rustedBridge,

        leadPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump, fortifiedTank, fortifiedCanister,
        steamBoiler, steamAgitator, broiler, oilSeparator,

        wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator, quartzBattery, mirror, solarTower, steamTurbine,

        rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge, rustyScrapWall, rustyScrapWallLarge, rustyScrapWallHuge, rustyScrapWallGigantic,

        garden, bioMatterPress, rustElectrolyzer, hydrochloricGraphitePress, ironSieve, siliconArcSmelter, rustEngraver,

        construct, arialConstruct, groundConstruct, navalConstruct, alternateArticulator, fortifiePayloadConveyor, fortifiePayloadRouter, unitReplicator, unitReplicatorSmall,

        coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer,
        mendFieldProjector, taurus,

        fortifiedMessageBlock, mechanicalProcessor, analogProcessor, mechanicalSwitch, mechanicalRegistry
    ; //endregion

    public static Color nyfalisBlockOutlineColour = Color.valueOf("371404");
    public static ObjectSet<Block> nyfalisBuildBlockSet = new ObjectSet<>(), sandBoxBlocks = new ObjectSet<>(), nyfalisCores = new ObjectSet<>(), allNyfalisBlocks = new ObjectSet<>();

    //revamp early game
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

        mossyStone = new Floor("mossy-stone"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        /*TODO: diff gray-greens for each stage*/
        mossierStone = new Floor("mossier-stone"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        mossiestStone = new Floor("mossiest-stone"){{
            attributes.set(bio, 0.1f);
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
            breakSound = Sounds.plantBreak;
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

        redSandBoulder = new Prop("red-sand-boulder"){{
            variants = 2;
            redSand.asFloor().decoration = this;
        }};

        glowBloom = new Prop("glow-bloom"){{
            variants = 3;

            lightRadius = 10f;
            emitLight = true;
        }};

        //endregion
        //region Walls

        redDune = new StaticWall("red-dune-wall"){{
            redSand.asFloor().wall = this;
            attributes.set(Attribute.sand, 2f);
        }};

        pinkShrubs = new StaticWall("pink-shrubs"){{
            variants = 2;
        }};

        lumaWall = new StaticTree("luma-wall"){{
            variants = 2;
        }};

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
            layer = Layer.power + 0.9f;
        }};

        yellowShrubsCrooked = new TallBlock("yellow-shrubs-crooked"){{
            variants = 2;
            clipSize = 128f;
            layer = Layer.power + 0.9f;
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
        nyfalisTree = new TreeBlock("olupis-tree"){{
            variants = 3;
        }};
        mossTree = new TreeBlock("moss-tree"){{
            variants = 3;
        }};
        pinkTree = new TreeBlock("pink-tree");
        yellowTree = new TreeBlock("yellow-tree");
        yellowTreeBlooming = new TreeBlock("yellow-tree-blooming");
        infernalMegaBloom = new TreeBlock("infernal-megabloom"){{
            variants = 4;
            clipSize = 128f;
        }};
        orangeTree = new TreeBlock("orange-tree"){{
            variants = 3;
        }};

        //endregion
    }
    public static void LoadBlocks(){
        //region Distribution
        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            health = 45;
            speed = 0.025f;
            displayedSpeed = 3.8f;
            buildCostMultiplier = 2f;
            researchCost = with(rustyIron, 25);
            requirements(Category.distribution, with(rustyIron, 1));
        }};

        ironConveyor = new PowerConveyor("iron-conveyor"){{
            hasPower = conductivePower = consumesPower = noUpdateDisabled = true;

            health = 70;
            speed = 0.03f;
            itemCapacity = 1;
            displayedSpeed = 4.8f;
            poweredSpeed = 0.05f;
            buildCostMultiplier = 2f;
            unpoweredSpeed = 0.025f;
            displayedSpeedPowered = 7f;

            researchCost = with(iron, 50);
            consumePower (1f/60).boost();
            requirements(Category.distribution, with(iron, 1, rustyIron, 5 ));
        }};

        cobaltConveyor = new PowerConveyor("cobalt-conveyor"){{
            hasPower = conductivePower = consumesPower = noUpdateDisabled =true;


            health = 70;
            speed = 0.06f;
            itemCapacity = 1;
            displayedSpeed = 0f;
            poweredSpeed = 0.09f;
            unpoweredSpeed = 0.020f;
            displayedSpeedPowered = 9f;
            buildCostMultiplier = 2f;

            consumePower (1f/60);
            researchCost = with(cobalt, 500, lead, 500);
            requirements(Category.distribution, with(cobalt, 1, lead, 5 ));
        }};

        ironRouter = new Router("iron-router"){{
            buildCostMultiplier = 4f;

            researchCost = with(rustyIron, 40, lead, 40);
            requirements(Category.distribution, with(rustyIron, 3, lead, 1));
        }};

        ironDistributor = new Router("iron-distributor"){{
            size = 2;
            health = 200;
            buildCostMultiplier = 4f;
            researchCost = with(rustyIron, 500, lead, 500, iron, 10);
            requirements(Category.distribution, with(rustyIron, 3, lead, 3, iron, 1));
        }};

        ironJunction = new Junction("iron-junction"){{
            speed = 26;
            capacity = 6;
            health = 50;
            armor = 1f;
            buildCostMultiplier = 0.5f;

            researchCost = with(rustyIron, 400, lead, 400);
            ((Conveyor)rustyIronConveyor).junctionReplacement = this;
            ((PowerConveyor)ironConveyor).junctionReplacement = this;
            requirements(Category.distribution, with(lead, 20, rustyIron, 30));
        }};

        rustedBridge = new BufferedItemBridge("rusted-bridge") {{
            /*Same throughput as a rusty conv, slightly slower but insignificant*/
            fadeIn = moveArrows = false;

            range = 3;
            armor = 1f;
            health = 50;
            speed = 47.55f;
            arrowSpacing = 6f;
            itemCapacity =5;
            bufferCapacity = 3;

            researchCost = with(lead, 300, rustyIron, 300);
            ((Conveyor)rustyIronConveyor).bridgeReplacement = this;
            /*expensive, to put off bridge waving/stacking*/
            requirements(Category.distribution, with(rustyIron, 20, lead, 15));
        }};

        ironBridge = new BufferedItemBridge("iron-bridge"){{
            /*Same throughput as an iron conv*/
            fadeIn = moveArrows = false;

            range = 6;
            armor = 1f;
            health = 50;
            speed = 67.05f;
            arrowSpacing = 6f;
            itemCapacity = 10;
            bufferCapacity = 8;

            researchCost = with(iron, 100, rustyIron, 500, lead, 500);
            ((PowerConveyor)ironConveyor).bridgeReplacement = this;
            requirements(Category.distribution, with(iron, 10, rustyIron, 5, lead, 5));
        }};

        ironOverflow = new OverflowSorter("iron-overflow"){{
            buildCostMultiplier = 3f;
            researchCost = with(lead, 550, iron, 50);
            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnderflow = new OverflowSorter("iron-underflow"){{
            invert = true;
            buildCostMultiplier = 3f;
            researchCost = with(lead, 550, iron, 50);
            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnloader = new DirectionalUnloaderRotatable("iron-unloader"){{
            solid = false;
            allowCoreUnload = true;

            speed = 4f;
            health = 120;
            regionRotated1 = 1;
            researchCost = with(lead, 500, graphite, 100, iron, 100);
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
            consumeLiquid(Liquids.water, 0.05f).boost(); //TODO: Make it consume either steam or water
            requirements(Category.production, with(rustyIron, 25));
        }};


        steamDrill = new Drill("steam-drill"){{
            hasPower = true;
            tier = 2;
            size = 3;
            drillTime = 330;

            envEnabled ^= Env.space;
            consumePower(25f/60f);
            consumeLiquid(steam, 0.05f);
            researchCost = with(iron, 500, lead, 1500);
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
            researchCost = with(iron, 1000, graphite, 1000, silicon, 500);
            requirements(Category.production, with(iron, 60, graphite, 70, silicon, 30));
        }};

        garden = new AttributeCrafter("garden"){{
            hasLiquids = hasPower = hasItems = legacyReadWarmup = true;
            size = 3;
            craftTime = 185f;
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
            consumePower(40f / 60f);
            consumeLiquid(Liquids.water, 18f / 60f);
            researchCost = with(iron, 2000, lead, 2500, rustyIron, 2500);
            outputItem = new ItemStack(condensedBiomatter, 1);
            requirements(Category.production, ItemStack.with(iron, 150, lead, 60, rustyIron, 30));
        }};

        //endregion
        //region liquid
        rustyPump = new Pump("rusty-pump"){{
            size = 1;
            pumpAmount = 0.05f;
            liquidCapacity = 10f;
            researchCost = with(lead, 100, rustyIron, 100);
            requirements(Category.liquid, with(rustyIron, 3, lead, 6));
        }};

        ironPump = new Pump("iron-pump"){{
            size = 2;
            pumpAmount = 0.1f;
            liquidCapacity = 20f;
            researchCost = with(lead, 500, iron, 100);
            requirements(Category.liquid, with(iron, 10, lead, 10));
        }};

        displacementPump = new BurstPump("displacement-pump"){{
            size = 3;
            pumpAmount = 120f;
            leakAmount = 0.02f;
            liquidCapacity = 150f;
            consumePower(0.3f);
            researchCost = with(iron, 250, lead, 800, graphite, 250, rustyIron, 800);
            requirements(Category.liquid, with(iron, 30, graphite, 30, lead, 60, rustyIron, 60));
        }};

        massDisplacementPump = new BurstPump("mass-displacement-pump"){{
            size = 4;
            leakAmount = 0.1f;
            pumpAmount = 180f;
            liquidCapacity = 200f;
            consumePower(0.6f);
            researchCost = with(iron, 500, lead, 1000, graphite, 250, silicon, 250);
            requirements(Category.liquid, with(iron, 60, graphite, 60, lead,150, silicon, 60));
        }};


        leadPipe = new Conduit("lead-pipe"){{
            leaks = underBullets = true;

            //try no lost, but slow/low capcity
            health = 60;
            liquidCapacity = 2f;
            liquidPressure = 1.1f;
            researchCostMultiplier = 0.5f;
            botColor = Color.valueOf("37323C");
            requirements(Category.liquid, with(lead, 1, rustyIron, 1));
        }};

        ironPipe = new ArmoredConduit("iron-pipe"){{
            leaks = underBullets = true;

            liquidCapacity = 6f;
            liquidPressure = 1.3f;
            researchCostMultiplier = 3;
            botColor = Color.valueOf("252731");
            requirements(Category.liquid, with(iron, 5, lead, 5));
        }};

        pipeRouter = new LiquidRouter("pipe-router"){{
            solid = underBullets = true;
            liquidCapacity = 20f;
            liquidPressure = 0.85f; /* Nerfed so you can't bypass lead pipe being terrible */
            researchCost = with(lead,100, rustyIron, 100);
            requirements(Category.liquid, with(lead, 5, rustyIron, 5));
        }};

        fortifiedCanister = new LiquidRouter("pipe-canister"){{
            solid = true;
            size = 2;
            liquidCapacity = 850f;
            liquidPressure = 0.95f;
            researchCost = with(lead, 600, iron, 100);
            requirements(Category.liquid, with(lead, 50, iron, 20));
        }};

        fortifiedTank = new LiquidRouter("pipe-tank"){{
            solid = true;
            size = 3;
            liquidCapacity = 2300f;
            researchCost = with(lead, 800, iron, 250);
            requirements(Category.liquid, with(lead, 150, iron, 60));
        }};

        pipeJunction = new LiquidJunction("pipe-junction"){{
            solid = false;
            ((Conduit)ironPipe).junctionReplacement = this;
            ((Conduit)leadPipe).junctionReplacement = this;
            researchCost = with(lead,200, rustyIron,200);

            /*expensive, since you can cheese the terribleness of pipes with this*/
            requirements(Category.liquid, with(rustyIron, 50, lead, 50));
            researchCost = with(lead, 500, rustyIron, 500);
        }};

        pipeBridge = new LiquidBridge("pipe-bridge"){{
            fadeIn = moveArrows = hasPower = false;
            range = 6;
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

            consumePower(1f);
            consumeLiquid(Liquids.water, 20/60f);
            outputLiquid = new LiquidStack(steam, 12/60f);
            requirements(Category.liquid, with(rustyIron, 10, lead, 10));
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

            researchCost = with(lead, 1000, rustyIron, 1000);
            outputLiquid = new LiquidStack(steam, 10/60f);
            requirements(Category.liquid, with(rustyIron, 30, lead, 30));
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
            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(iron, 1);
            consumeLiquid(Liquids.water, 12f / 60f);
            consumeItems(with(lead, 1, rustyIron,1));
            researchCost = with(rustyIron, 400, lead, 700);
            requirements(Category.crafting, with(rustyIron, 70, lead, 100));
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
        }};

        hydrochloricGraphitePress  = new GenericCrafter("hydro-graphite-press"){{
            hasItems = hasLiquids = hasPower = true;

            size = 3;
            craftTime = 30f;
            itemCapacity = 20;
            craftEffect = Fx.pulverizeMedium;

            consumePower(30f/60f);
            researchCost = with(lead, 3500,  iron, 3000, rustyIron, 3000);
            outputItem = new ItemStack(Items.graphite, 1);
            requirements(Category.crafting, with(iron, 150, lead, 300, rustyIron, 100));
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
            consumePower(30f /60f);
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
            researchCost = with(lead, 3000, rustyIron, 1500);
            requirements(Category.crafting, with(rustyIron, 10, lead, 60));
        }};

        //endregion
        //region Units
        construct = new PowerUnitTurret("construct"){{
            alwaysShooting = true;

            size = 4;
            shootY = 0f;
            reload = 600f;
            shootSound = Sounds.respawn;

            consumePower(6f);
            shootType = new BasicBulletType(2.5f, -1){{
                shootEffect = Fx.unitLand;
                ammoMultiplier = 1f;
                spawnUnit = spirit;
            }};
            ammo(
                    quartz, new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 0.5f;
                        spawnUnit = banshee;
                    }},
                    graphite, new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 0.5f;
                        reloadMultiplier = 0.8f;
                        spawnUnit = phantom;
                    }}
            );
            researchCost = with(lead, 3000, graphite, 3000, iron, 3000, rustyIron, 3000);
            requirements(Category.units, with(iron, 350, lead, 500, graphite, 150, rustyIron, 250));
        }};

        // arialConstruct -> offensive air units
        arialConstruct = new ItemUnitTurret("arial-construct"){{
            size = 4;
            shootY = 0f;
            reload = 600f;
            itemCapacity = 20;
            failedMakeSoundPitch = 0.7f;

            ammo(
                lead, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 2f;
                    spawnUnit = aero;
                }}
            );
            alwaysShooting = true;
            requiredItems = Seq.with(silicon);
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            researchCost = with(lead, 100, silicon, 600,  iron, 600);
            requirements(Category.units, with(iron, 100, lead, 100, silicon, 50));
        }};

        // groundConstruct -> offensive ground units
        groundConstruct = new ItemUnitTurret("ground-construct"){{
            size = 4;
            shootY = 2.5f * Vars.tilesize;
            reload = 600f;
            itemCapacity = 20;
            failedMakeSoundPitch = 0.7f;

            ammo(
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = venom;
                }}
            );
            requiredItems = Seq.with(silicon);
            alwaysShooting = hoverShowsSpawn = true;
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            researchCost = with(rustyIron, 1000, silicon, 600,  iron, 600);
            requirements(Category.units, with(iron, 100, rustyIron, 100, silicon, 50));
        }};

        //navalConstruct -> offensive naval units
        navalConstruct = new ItemUnitTurret("naval-construct"){{
            size = 4;
            shootY = 2.5f * Vars.tilesize;
            reload = 600f;
            itemCapacity = 20;
            failedMakeSoundPitch = 0.7f;

            ammo(
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = bay;
                }},
                iron, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = porter;
                }}
            );
            requiredItems = Seq.with(silicon);
            alwaysShooting = hoverShowsSpawn = true;
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            researchCost = with(rustyIron, 1000, silicon, 600,  iron, 600);
            requirements(Category.units, with(iron, 100, rustyIron, 100, silicon, 50));
        }};

        fortifiePayloadConveyor = new PayloadConveyor("fortified-payload-conveyor"){{
            requirements(Category.units, with(Items.graphite, 10    , iron, 10));
            canOverdrive = false;
            payloadLimit = 4f;
            size =4;
        }};

        fortifiePayloadRouter = new PayloadRouter("fortified-payload-router"){{
            requirements(Category.units, with(Items.graphite, 15, iron, 10));
            canOverdrive = false;
            payloadLimit = 4f;
            size = 4;
        }};

        //TODO: payloadLifter -> payload into a air unit to drop off at configured location, air unit can be killed

        unitReplicator = new Replicator("unit-replicator"){{
            size = 5;
            delay = 5f;

            this.requirements(Category.units, BuildVisibility.editorOnly, ItemStack.with());
        }};

        unitReplicatorSmall = new Replicator("unit-replicator-small"){{
            size = 4;
            delay = 4f;

            this.requirements(Category.units, BuildVisibility.editorOnly, ItemStack.with());
        }};

        //endregion
        NyfalisTurrets.LoadTurrets();
        //region Power
        wire = new Wire("wire"){{
            floating = placeableLiquid = consumesPower = outputsPower = true;
            solid = false;
            baseExplosiveness = 0.5f;
            //TODO: if possible 1 capacity & power usage, this only does usage
            consume(new ConsumePower(1/60f, 1f, false));
            researchCost = with(rustyIron, 20, lead, 20);
            requirements(Category.power, with(rustyIron, 1, lead, 2));
        }};

        superConductors = new Wire("super-conductor"){{
            floating = true;
            solid = false;
            health = 150;
            baseExplosiveness = 0.7f;
            researchCost = with(iron, 500, cobalt, 100);
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
            researchCost = with(lead, 500, iron, 100);
            requirements(Category.power, with(iron, 30, Items.lead, 15));
        }};

        windMills = new WindMill("wind-mill"){{
            size = 3;
            boosterMultiplier = 3.8f;/* rationed for press + 2 gardens*/
            powerProduction = 20f/60f;
            displayEfficiencyScale = 1.1f;
            attribute = Attribute.steam;
            consumeLiquid(oil, 10f / 60f).boost();
            researchCost = with(rustyIron, 75);
            requirements(Category.power, with(rustyIron, 35));
        }};

        hydroMill = new ThermalGeneratorNoLight("hydro-mill"){{
            floating = true;

            size = 3;
            effectChance = 0.011f;
            powerProduction = 20f/60f;
            ambientSoundVolume = 0.06f;

            attribute = hydro;
            generateEffect = Fx.steam;
            ambientSound = Sounds.hum;
            researchCost = with(iron, 1500, silicon, 1000, lead, 3000, cobalt, 1000);
            requirements(Category.power, with(iron, 20, silicon, 20, lead, 50, cobalt, 20));
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
            researchCost = with(iron, 1500, silicon, 1000, lead, 3000, cobalt, 1000);
            requirements(Category.power, with(iron, 50, silicon, 50, lead, 100, cobalt, 50));
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
            buildCostMultiplier = 0.8f;
            researchCost = with(rustyIron, 100);
            requirements(Category.defense,with(rustyIron, 12));
        }};

        rustyWallLarge = new Wall("rusty-wall-large"){{
            size = 2;
            health =  1400;
            buildCostMultiplier = 0.7f;
            researchCost = with(rustyIron, 200);
            requirements(Category.defense,with(rustyIron, 48));
        }};

        rustyWallHuge = new Wall("rusty-wall-huge"){{
            size = 3;
            health = 2690;
            buildCostMultiplier = 0.7f;
            researchCost = with(rustyIron,550);
            requirements(Category.defense,with(rustyIron, 108));
        }};

        rustyWallGigantic = new Wall("rusty-wall-gigantic"){{
            size = 4;
            health = 3600;
            researchCost = with(rustyIron,4200, iron, 10);
            requirements(Category.defense, BuildVisibility.editorOnly, with(rustyIron, 1500));
        }};

        ironWall = new Wall("iron-wall"){{
            size = 1;
            health = 700;
            buildCostMultiplier = 0.7f;
            researchCost = with(iron, 500);
            requirements(Category.defense,with(iron, 15));
        }};

        ironWallLarge = new Wall("iron-wall-large"){{
            size = 2;
            health = 2800;
            buildCostMultiplier = 0.7f;
            researchCost = with(iron, 1000);
            requirements(Category.defense,with(iron, 48));
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
        mendFieldProjector = new DirectionalMendProjector("mend-field-projector");

        taurus = new PowerTurret("taurus"){{
            size = 3;
            recoils = 2;
            reload = 10f;
            inaccuracy = 10f;
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
            shootType = new HealOnlyBulletType(5.2f, -5, "olupis-diamond-bullet"){{
                collidesTeam = true;
                collidesAir = absorbable = false;
                width = 10f;
                height = 16f;
                lifetime = 30f;
                healPercent = 7f;
                homingRange = 10f;
                backColor = Pal.heal;
                /*added slight homing, so it can hit 1x1 blocks better or at all*/
                splashDamageRadius = 25f * 0.75f;
                splashDamage = -3f;
                homingPower = 0.07f;
                frontColor = Color.white;
                shootSound = Sounds.sap;
            }};
            hasPower = targetHealing = true;
            targetAir = targetGround  = false;
            group = BlockGroup.projectors;
            limitRange(3f);
            consumePower(2.5f);
            shoot = new ShootAlternate(9f);
            researchCost = with(iron, 200, lead, 600);
            flags = EnumSet.of(BlockFlag.repair, BlockFlag.turret);
            requirements(Category.effect, with(iron, 30, Items.lead, 40));
        }};


        //TODO: Mister -> phase fluid = to give units a temp shied
        //  -> Nanite Fluid = repair

        fortifiedContainer = new StorageBlock("fortified-container"){{
            coreMerge = false;
            size = 2;
            health =  790;
            scaledHealth = 150;
            itemCapacity = 150;
            researchCost = with(iron, 250, rustyIron, 550);
            requirements(Category.effect, with(rustyIron, 75, iron, 50));
        }};

        fortifiedVault = new StorageBlock("fortified-vault"){{
            coreMerge = false;
            size = 3;
            health =  1500;
            scaledHealth = 120;
            itemCapacity = 950;
            researchCost = with(iron, 550, rustyIron, 800, silicon, 550);
            requirements(Category.effect, with(rustyIron, 150, iron, 100, silicon, 100));
        }};

        coreRemnant = new PropellerCoreBlock("core-remnant"){{
            alwaysUnlocked = isFirstTier = true;
            size = 2;
            health = 1750;
            itemCapacity = 1500;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1500, lead, 1500));
        }};

        coreVestige = new PropellerCoreBlock("core-vestige"){{
            size = 3;
            health = 3500;
            itemCapacity = 3000;
            researchCostMultiplier = 0.5f;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 3000, lead, 3000, iron, 1500));
        }};

        coreRelic = new PropellerCoreTurret("core-relic"){{
            size = 4;
            reload = 72f;
            health = 70000;
            itemCapacity = 4500;
            shootX = shootY = 0f;
            range = 19.5f * Vars.tilesize;
            researchCostMultiplier = 0.5f;

            unitType = phorid;
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            requirements(Category.effect, with(rustyIron, 4500, lead, 4500, iron, 3000, graphite, 3000));
            shootType = new SapBulletType(){{
                damage = 10f;
                width = 0.8f;
                lifetime = 20f;
                sapStrength = 0f;
                length = 19.5f * Vars.tilesize;
                status = StatusEffects.none;
                despawnEffect = Fx.none;
                color = hitColor = rustyIron.color;
                collidesTiles = false;
                collidesAir = collidesGround = collidesTeam = true;
            }};
        }};

        coreShrine = new PropellerCoreTurret("core-shrine"){{
            size = 5;
            reload = 55f;
            health = 140000;
            itemCapacity = 6000;
            shootX = shootY = 0f;
            range = 22.5f * Vars.tilesize;
            researchCostMultiplier = 0.5f;

            unitType = phorid;
            limitRange(0);
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            requirements(Category.effect, with(rustyIron, 6000, lead, 6000, iron, 6000, silicon, 4500, graphite, 4500, quartz, 4500));
            shootType = new ArtilleryBulletType(3f, 25){{
                lifetime = 80f;
                knockback = 0.8f;
                homingRange = 50f;
                width = height = 9f;
                splashDamage = 10f;
                homingPower = 0.08f;
                reloadMultiplier = 1.2f;
                splashDamageRadius = 30f;

                collidesTiles = false;
                frontColor = iron.color;
                backColor = rustyIron.color;
                collidesAir = collidesGround;
            }};
        }};

        coreTemple = new PropellerCoreTurret("core-temple"){{
            size = 6;
            reload = 35f;
            health = 280000;
            itemCapacity = 7500;
            shootX = shootY = 0f;
            range = 32 * Vars.tilesize;
            researchCostMultiplier = 0.5f;

            unitType = phorid;
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            targetGround = targetHealing = targetAir = true;
            requirements(Category.effect, with(rustyIron, 7500, lead, 7500, iron, 7500, silicon, 7500, graphite, 7500, quartz, 6000, cobalt, 6000));
            shootType = new RailBulletType(){{
                length = 255f;
                damage = 48f;
                pierceDamageFactor = 0.5f;
                hitColor = iron.color;
                hitEffect = endEffect = Fx.hitBulletColor.wrap(iron.color);
                lineEffect = new Effect(20f, e -> {
                    if(!(e.data instanceof Vec2 v)) return;
                    color(e.color);
                    stroke(e.fout() * 0.9f + 0.6f);
                    Fx.rand.setSeed(e.id);
                    for(int i = 0; i < 7; i++){
                        Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                        Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                    }
                    e.scaled(14f, b -> {
                        stroke(b.fout() * 1.5f);
                        color(e.color);
                        Lines.line(e.x, e.y, v.x, v.y);
                    });
                });
            }};
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
            researchCost = with(iron, 500, graphite, 500);
            requirements(Category.logic, with(Items.graphite, 10, iron, 5));
        }};

        mechanicalProcessor = new NyfalisLogicBlock("mechanical-processor"){{
            requirements(Category.logic, with(lead, 300, iron, 100, graphite, 60, silicon, 60));
            researchCost = with(lead, 1000, iron, 700, graphite, 200, silicon, 200);

            instructionsPerTick = 25;
            range = 10 * 22;
            size = 2;
        }};

        mechanicalSwitch = new SwitchBlock("mechanical-switch"){{
            size = 2;
            requirements(Category.logic, with(Items.graphite, 5, iron, 10));
            researchCost = with(iron, 400, graphite, 200);
        }};

        mechanicalRegistry = new MemoryBlock("mechanical-registry"){{
            size = 2;
            requirements(Category.logic, with(Items.graphite, 20, iron, 20, silicon, 10));
            researchCost = with(iron, 400, graphite, 400, silicon, 200);
        }};


        //endregion
    }

    public static void NyfalisBlocksPlacementFix(){
        nyfalisBuildBlockSet.clear();
        Vars.content.blocks().each(b->{
            if(b.name.startsWith("olupis-")){
                if(b.isVisible()) nyfalisBuildBlockSet.add(b);
                allNyfalisBlocks.add(b);
            }
        });

        nyfalisCores.addAll(coreRemnant, coreRelic, coreShrine, coreTemple, coreVestige);

        sandBoxBlocks.addAll(
            /*just to make it easier for testing and/or sandbox*/
            itemSource, itemVoid, liquidSource, liquidVoid, payloadSource, payloadVoid, powerSource, powerVoid, heatSource,
            worldProcessor, worldMessage
        );
    }
}
