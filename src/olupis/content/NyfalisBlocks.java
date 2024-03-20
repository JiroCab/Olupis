package olupis.content;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
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
import mindustry.world.blocks.defense.Radar;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.legacy.LegacyBlock;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.blocks.payloads.PayloadRouter;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import olupis.world.blocks.defence.*;
import olupis.world.blocks.misc.*;
import olupis.world.blocks.power.*;
import olupis.world.blocks.processing.*;
import olupis.world.consumer.ConsumeLubricant;
import olupis.world.entities.bullets.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static mindustry.Vars.tilesize;
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
        oreIron, oreIronWall, oreCobalt, oreOxidizedCopper, oreOxidizedLead, oreQuartz, oreAlco,
        glowSprouts, lumaSprouts,

        /*Floors*/
        redSand, lumaGrass, yellowGrass, pinkGrass, frozenGrass, mossyDirt, frozenDirt, frozenMud, hardenMud, mossyhardenMud, crackedIce,
        cinderBloomGrass, cinderBloomy, cinderBloomier, cinderBloomiest, mossyStone, mossStone, mossierStone, mossiestStone,
        grassyVent, mossyVent, stoneVent, basaltVent, hardenMuddyVent, redSandvent, snowVent,

        /*Liquid floors*/
        redSandWater, lumaGrassWater, brimstoneSlag, mossyWater, pinkGrassWater, yellowMossyWater,

        /*props*/
        yellowBush, lumaFlora, bush, mossyBoulder, infernalBloom, redSandBoulder, glowBloom, luminiteBoulder, deadBush,

        /*walls*/
        redDune, pinkShrubs, lightWall, lumaWall,
        greenShrubsIrregular, greenShrubsCrooked, yellowShrubs, yellowShrubsIrregular, yellowShrubsCrooked,
        mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStoneWall,

        /*Trees*/
        nyfalisTree, mossTree, pinkTree, yellowTree, yellowTreeBlooming, infernalMegaBloom, orangeTree, deadTree, spruceTree,

        //Buildings, sorted by category
        corroder, dissolver, shredder, hive, escalation, shatter, avenger, aegis, obliterator,

        rustyDrill, steamDrill, hydroElectricDrill,

        rustyIronConveyor, ironConveyor, cobaltConveyor, ironRouter, ironDistributor ,ironJunction, ironBridge, ironOverflow, ironUnderflow, ironUnloader, rustedBridge,

        leadPipe, ironPipe, pipeRouter, pipeJunction, pipeBridge, displacementPump, massDisplacementPump, ironPump, rustyPump, fortifiedTank, fortifiedCanister,
        steamBoiler, steamAgitator, broiler, oilSeparator, lubricantMixer,

        wire, wireBridge, superConductors, windMills, hydroMill, hydroElectricGenerator, quartzBattery, mirror, solarTower, steamTurbine,

        rustyWall, rustyWallLarge, rustyWallHuge, rustyWallGigantic, ironWall, ironWallLarge, rustyScrapWall, rustyScrapWallLarge, rustyScrapWallHuge, rustyScrapWallGigantic, quartzWall, quartzWallLarge, cobaltWall, cobaltWallLarge,

        garden, bioMatterPress, rustElectrolyzer, hydrochloricGraphitePress, ironSieve, siliconArcSmelter, rustEngraver, pulverPress, discardDriver, siliconKiln, inductionSmelter,

        construct, arialConstruct, groundConstruct, navalConstruct, alternateArticulator, ultimateAssembler, fortifiePayloadConveyor, fortifiePayloadRouter, unitReplicator, unitReplicatorSmall, repairPin,

        coreRemnant, coreVestige, coreRelic, coreShrine, coreTemple, fortifiedVault, fortifiedContainer, deliveryCannon, deliveryTerminal,
        mendFieldProjector, taurus, ladar,

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

        oreAlco = new OreBlock("ore-alco", alcoAlloy){{
            variants = 1;
        }};

        glowSprouts = new OverlayFloor("glow-sprout"){{
            emitLight = true;
            variants = 1;
            lightRadius = 10f;
            //is sad that BlockRenderer.java does not render light from overlays, but ill keep it incase TnT
        }};

        lumaSprouts = new OverlayFloor("luma-sprout"){{
            variants = 1;
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

        mossyDirt = new Floor("mossy-dirt"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.1f);
        }};

        frozenDirt = new Floor("frozen-dirt"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.3f);
        }};

        frozenMud = new Floor("frozen-mud"){{
            attributes.set(bio, 0.1f);
            attributes.set(Attribute.water, 0.3f);
        }};

        hardenMud = new Floor("harden-mud"){{
            attributes.set(Attribute.water, 0.1f);
            variants = 3;
        }};

        mossyhardenMud = new Floor("mossy-harden-mud"){{
            attributes.set(bio, 0.2f);
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
            effectColor = Color.white;
            parent = blendGroup = stone;
            attributes.set(Attribute.steam, 1f);
        }};

        hardenMuddyVent = new SteamVent("harden-muddy-vent"){{
            effectColor = Color.white;
            parent = blendGroup = hardenMud;
            attributes.set(Attribute.steam, 1f);
        }};

        basaltVent = new SteamVent("basalt-vent"){{
            effectColor = Color.white;
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

        deadBush = new Prop("dead-bush"){{
            variants = 1;
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
        deadTree = new TreeBlock("dead-tree"){{
            variants = 1; //TODO: more
        }};

        //endregion
    }
    public static void LoadBlocks(){
        //region Distribution
        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            health = 45;
            speed = 0.025f;
            displayedSpeed = 3.8f;
            buildCostMultiplier = 1.5f;
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
            powerRequired = 15f/60f;
            buildCostMultiplier = 0.45f;
            unpoweredSpeed = 0.025f;
            displayedSpeedPowered = 7f;

            researchCost = with(iron, 10, rustyIron, 20);
            consumePower (2f/60).boost();
            requirements(Category.distribution, with(iron, 1, rustyIron, 5 ));
        }};

        cobaltConveyor = new PowerConveyor("cobalt-conveyor"){{
            hasPower = conductivePower = consumesPower = noUpdateDisabled =true;


            health = 70;
            speed = 0.06f;
            itemCapacity = 1;
            displayedSpeed = 0f;
            poweredSpeed = 0.095f;
            unpoweredSpeed = 0.023f;
            displayedSpeedPowered = 9f;
            buildCostMultiplier = 2f;

            consumePower (5f/60);
            researchCost = with(cobalt, 500, lead, 500);
            requirements(Category.distribution, with(cobalt, 1, lead, 5 ));
        }};

        ironRouter = new Router("iron-router"){{
            buildCostMultiplier = 1.5f;
            speed = 16;

            researchCost = with(rustyIron, 10, lead, 10);
            requirements(Category.distribution, with(rustyIron, 3, lead, 1));
        }};

        ironDistributor = new Router("iron-distributor"){{
            size = 2;
            health = 200;
            buildCostMultiplier = 2f;
            researchCost = with(rustyIron, 300, lead, 300, iron, 10);
            requirements(Category.distribution, with(rustyIron, 3, lead, 3, iron, 1));
        }};

        ironJunction = new Junction("iron-junction"){{
            speed = 45;
            armor = 1f;
            health = 50;
            capacity = 6;
            buildCostMultiplier = 0.5f;

            researchCost = with(rustyIron, 20, lead, 20);
            ((Conveyor)rustyIronConveyor).junctionReplacement = this;
            ((PowerConveyor)ironConveyor).junctionReplacement = this;
            requirements(Category.distribution, with(lead, 3, rustyIron, 3));
        }};

        rustedBridge = new BufferedItemBridge("rusted-bridge") {{
            /*Same throughput as a rusty conv, slightly slower but insignificant*/
            fadeIn = moveArrows = false;

            armor = 1f;
            health = 50;
            speed = 47.55f;
            itemCapacity =5;
            arrowSpacing = 6f;
            buildCostMultiplier = 0.3f;
            range = bufferCapacity = 3;

            researchCost = with(lead, 15, rustyIron, 15);
            ((Conveyor)rustyIronConveyor).bridgeReplacement = this;
            /*expensive, to put off bridge waving/stacking*/
            requirements(Category.distribution, with(rustyIron, 6, lead, 6));
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
            buildCostMultiplier = 0.4f;

            researchCost = with(iron, 100, rustyIron, 500, lead, 500);
            ((PowerConveyor)ironConveyor).bridgeReplacement = this;
            requirements(Category.distribution, with(iron, 5, rustyIron, 10, lead, 10));
        }};

        ironOverflow = new OverflowSorter("iron-overflow"){{
            hideDetails = false;
            buildCostMultiplier = 0.5f;
            researchCost = with(lead, 350, iron, 25);
            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnderflow = new OverflowSorter("iron-underflow"){{
            invert = true;
            hideDetails = false;
            buildCostMultiplier = 0.5f;
            researchCost = with(lead, 350, iron, 25);

            requirements(Category.distribution, with(iron, 2, lead, 5));
        }};

        ironUnloader = new DirectionalUnloaderRotatable("iron-unloader"){{
            solid = false;
            allowCoreUnload = true;

            speed = 4f;
            health = 120;
            regionRotated1 = 1;
            buildCostMultiplier = 0.3f;
            researchCost = with(lead, 500, graphite, 100, iron, 100);
            requirements(Category.distribution, with(iron, 15, graphite, 15, lead, 30));
        }};

        //endregion
        //region Drills / crafting
        rustyDrill = new BoostableBurstDrill("rusty-drill"){{
            hasPower = true;
            squareSprite = false;

            tier = 1;
            size = 3;
            drillTime = 60f * 8.5f;

            drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
            researchCost = with(rustyIron,50);
            consumePower(10f/60f);
            consumeLiquid(Liquids.water, 0.05f).boost();
            requirements(Category.production, with(rustyIron, 15    ));
        }};

        steamDrill = new Drill("steam-drill"){{
            hasPower = true;
            tier = 2;
            size = 3;
            drillTime = 60f * 5f;


            envEnabled ^= Env.space;
            consumePower(25f/60f);
            consumeLiquid(steam, 0.05f);
            researchCost = with(iron, 300, lead, 700);
            consumeLiquid(Liquids.slag, 0.06f).boost();
            requirements(Category.production, with( iron, 40, lead, 20));
        }};

        hydroElectricDrill = new Drill("hydro-electric-drill"){{
            tier = 3;
            size = 4;
            drillTime = 60f * 4.2f;
            liquidBoostIntensity = 1.7f;

            envEnabled ^= Env.space;
            consumeLiquid(steam, 0.1f);
            consumePower(30f/60f);
            consumeLiquid(Liquids.slag, 0.1f).boost();
            researchCost = with(iron, 1000, graphite, 1000, silicon, 500);
            requirements(Category.production, with(iron, 60, graphite, 70, silicon, 30));
        }};

        garden = new AttributeCrafter("garden"){{
            hasLiquids = hasPower = hasItems = legacyReadWarmup = true;
            size = 3;
            craftTime = 185f;
            maxBoost = 2.5f;
            buildCostMultiplier = 0.6f;

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
            researchCost = with(iron, 700, lead, 1500, rustyIron, 1500, condensedBiomatter, 500);
            outputItem = new ItemStack(condensedBiomatter, 1);
            requirements(Category.production, ItemStack.with(iron, 30, lead, 60, rustyIron, 60));
        }};

        //endregion
        //region liquid
        rustyPump = new Pump("rusty-pump"){{
            size = 1;
            liquidCapacity = 10f;
            pumpAmount = 0.06f;
            buildCostMultiplier = 1.5f;
            researchCost = with(lead, 20, rustyIron, 20);
            requirements(Category.liquid, with(rustyIron, 1, lead, 3));
        }};

        ironPump = new Pump("iron-pump"){{
            size = 2;
            liquidCapacity = 15f;
            pumpAmount = 0.075f;
            buildCostMultiplier = 2.1f;
            researchCost = with(lead, 500, iron, 100);
            requirements(Category.liquid, with(iron, 12, lead, 12));
        }};

        displacementPump = new BurstPump("displacement-pump"){{
            size = 3;
            pumpAmount = 140f;
            leakAmount = 0.02f;
            liquidCapacity = 300f;
            consumePower(25f/60f);
            researchCost = with(iron, 250, lead, 800, graphite, 250, rustyIron, 800);
            requirements(Category.liquid, with(iron, 15, graphite, 15, lead, 30, rustyIron, 30));
        }};

        massDisplacementPump = new BurstPump("mass-displacement-pump"){{
            size = 4;
            leakAmount = 0.1f;
            pumpAmount = 200f;
            liquidCapacity = 400f;
            consumePower(0.6f);
            researchCost = with(iron, 500, lead, 1000, graphite, 250, silicon, 250);
            requirements(Category.liquid, with(iron, 30, graphite, 30, lead, 75, silicon, 30));
        }};


        leadPipe = new Conduit("lead-pipe"){{
            leaks = underBullets = true;

            health = 70;
            liquidCapacity = 2f;
            liquidPressure = 1.05f;
            researchCostMultiplier = 0.5f;
            botColor = Color.valueOf("37323C");
            researchCost = with(rustyIron, 10, lead, 10);
            requirements(Category.liquid, with(lead, 1, rustyIron, 1));
        }};

        ironPipe = new ArmoredConduit("iron-pipe"){{
            leaks = underBullets = true;

            liquidCapacity = 6f;
            liquidPressure = 1.25f;
            researchCostMultiplier = 3;
            botColor = Color.valueOf("252731");
            researchCost = with(lead, 300, iron, 50);
            requirements(Category.liquid, with(iron, 2, lead, 5));
        }};

        pipeRouter = new LiquidRouter("pipe-router"){{
            solid = underBullets = true;
            liquidCapacity = 15f;
            liquidPressure = 0.90f; /* Nerfed so you can't bypass lead pipe being terrible */
            researchCost = with(lead, 10, rustyIron, 10);
            requirements(Category.liquid, with(lead, 5, rustyIron, 5));
        }};

        fortifiedCanister = new LiquidRouter("pipe-canister"){{
            solid = true;
            size = 2;
            liquidPadding = 2f;
            liquidCapacity = 850f;
            liquidPressure = 0.95f;
            researchCost = with(lead, 300, iron, 50);
            requirements(Category.liquid, with(lead, 50, iron, 20));
        }};

        fortifiedTank = new LiquidRouter("pipe-tank"){{
            solid = true;
            size = 3;
            liquidPadding = 2f;
            liquidCapacity = 2300f;
            researchCost = with(lead, 800, iron, 250);
            requirements(Category.liquid, with(lead, 75, iron, 30));
        }};

        pipeJunction = new LiquidJunction("pipe-junction"){{
            solid = hideDetails = false;
            ((Conduit)ironPipe).junctionReplacement = this;
            ((Conduit)leadPipe).junctionReplacement = this;
            researchCost = with(lead,200, rustyIron,200);
            buildCostMultiplier = 0.5f;

            /*expensive, since you can cheese the terribleness of pipes with this*/
            requirements(Category.liquid, with(rustyIron, 50, lead, 50));
            researchCost = with(lead, 40, rustyIron, 40);
        }};

        pipeBridge = new LiquidBridge("pipe-bridge"){{
            fadeIn = moveArrows = hasPower = false;
            range = 6;
            arrowSpacing = 6f;
            buildCostMultiplier = 0.5f;
            ((Conduit)ironPipe).bridgeReplacement = this;
            ((Conduit)leadPipe).bridgeReplacement = this;
            researchCost = with(iron, 150, lead, 150);
            requirements(Category.liquid, with(iron, 5, lead, 10));
        }};

        oilSeparator = new LegacyBlock("oil-separator"){{
        //TODO: test
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
            researchCost = with(rustyIron, 50, lead, 50);
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

            researchCost = with(lead, 750, rustyIron, 750);
            outputLiquid = new LiquidStack(steam, 10/60f);
            requirements(Category.production, with(rustyIron, 30, lead, 30));

        }};

        broiler = new BoostableGenericCrafter("broiler"){{
            hasLiquids = hasPower =  outputsLiquid = true;

            size = 2;
            health = 600;
            craftTime = 10f;

            consumePower(1f);
            consumeItem(rustyIron, 2);
            consumeItem(scrap, 1).boost();
            outputLiquid = new LiquidStack(Liquids.slag, 12f / 60f);
            drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion());
            requirements(Category.liquid, with(graphite, 25, iron, 20, lead, 40, silicon, 10));
        }};

        lubricantMixer = new GenericCrafter("lubricant-mixer"){{
            hasLiquids = hasPower =  outputsLiquid = true;

            size = 3;

            consumeItem(silicon);
            lightLiquid = lubricant;
            consumePower(1f);
            consumeLiquid(Liquids.oil, 12f / 60f);
            outputLiquid = new LiquidStack(lubricant, 10/60f);
            requirements(Category.liquid, with(quartz, 25, iron, 50, silicon, 40, quartz, 10));
        }};

        //endregion
        //region Production
        rustElectrolyzer = new GenericCrafter("rust-electrolyzer"){{
            hasPower = hasItems = hasLiquids = solid = true;
            rotate = false;

            size = 2;
            craftTime = 120;
            liquidCapacity = 24f;
            envEnabled = Env.any;
            buildCostMultiplier = 0.4f;

            lightLiquid = Liquids.cryofluid;
            consumePower(1f);
            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(iron, 2);
            consumeLiquid(Liquids.water, 24f / 60f);
            consumeItems(with(lead, 2, rustyIron, 2));
            researchCost = with(rustyIron, 50, lead, 50);
            requirements(Category.crafting, with(rustyIron, 15, lead, 30));
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawDefault());
        }};

        rustEngraver = new BoostableGenericCrafter("rust-engraver"){{
            hasPower = hasItems = hasLiquids = solid  = true;
            rotate = false;

            size = 4;
            craftTime = 250;
            envEnabled = Env.any;
            buildCostMultiplier = 0.4f;

            lightLiquid = Liquids.cryofluid;
            consumePower(200f / 60f);
            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(iron, 2);
            consumeLiquid(Liquids.water, 38f / 60f).boost();
            consumeItems(with(quartz, 1, rustyIron, 6));
            researchCost = with(iron, 2000, lead, 2000, rustyIron, 2000, quartz, 1000, cobalt, 500);
            requirements(Category.crafting, with(iron, 25, lead, 30, rustyIron, 30, quartz, 25, cobalt, 25));
        }};

        hydrochloricGraphitePress  = new GenericCrafter("hydro-graphite-press"){{
            hasItems = hasLiquids = hasPower = true;

            size = 3;
            craftTime = 50f;
            itemCapacity = 20;
            buildCostMultiplier = 0.5f;
            craftEffect = Fx.pulverizeMedium;

            consumePower(30f/60f);
            researchCost = with(lead, 650,  iron, 250, rustyIron, 650);
            outputItem = new ItemStack(Items.graphite, 1);
            requirements(Category.crafting, with(iron, 10, lead, 50, rustyIron, 40));
            consumeLiquids(LiquidStack.with(Liquids.oil, 10f / 60f, NyfalisItemsLiquid.steam, 10f/60f));
        }};

        siliconKiln = new GenericCrafter("silicon-kiln"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25, rustyIron, 15));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(Items.silicon, 1);
            craftTime = 40f;
            size = 2;
            hasPower = hasLiquids = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumeItem(quartz, 4);
            consumeLiquid(oil, 15f/60f);
            consumePower(0.50f);
        }};

        siliconArcSmelter = new GenericCrafter("silicon-arc-smelters") {{
            hasPower= hasItems = true;
            size = 4;
            craftTime = 30f;
            itemCapacity = 30;

            consumePower(70f/60f);
            outputItem = new ItemStack(silicon, 3);
            consumeItems(with(quartz, 2, graphite, 1));
            requirements(Category.crafting, with(lead, 65, iron, 65, graphite, 15, silicon, 15, cobalt, 15));
        }};

        inductionSmelter = new SeparatorWithLiquidOutput("induction-smelter"){{
            size = 3;
            craftTime = 80f;
            liquidCapacity = 30;
            results = with(
                aluminum, 3,
                cobalt, 1
            );
            consumeItem(alcoAlloy);
            consumePower(80f /60f);
            liquidOutputDirections = new int[]{4};
            hasLiquids = outputsLiquid = rotate = quickRotate = true;
            liquidOutputs = LiquidStack.with(Liquids.slag, 5f / 60f);
            requirements(Category.crafting, with(iron, 25, lead, 25));
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
            consumePower(35f /60f);
            consumeItem(condensedBiomatter, 1);
            researchCost = with(iron, 250, lead, 500);
            outputLiquid = new LiquidStack(oil, 20f / 60f);
            requirements(Category.crafting, with(iron, 25, lead, 25));
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
            researchCost = with(lead, 700, rustyIron, 700);
            requirements(Category.crafting, with(rustyIron, 20, lead, 50));
        }};

        discardDriver = new DiscardDriver("discard-driver"){{
            range = itemCapacity = 110;
            size = 1;
            bullet = new BasicBulletType(2.5f, 9){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
            }};
            requirements(Category.crafting, with(iron, 25, copper, 25));
        }};

        //discardDriver -> mass driver that discards item in a random direction

        //endregion
        //region Units
        construct = new PowerUnitTurret("construct"){{
            size = 4;
            shootY = 0f;
            reload = 1200f;
            itemCapacity = 20;
            alternateCapacity = 40;
            failedMakeSoundPitch = 0.7f;
            powerBulletType = new SpawnHelperBulletType(){{
                shootEffect = Fx.unitLand;
                ammoMultiplier = 1f;
                reloadMultiplier = 1.2f;
                spawnUnit = spirit;
            }};
            ammo(
                quartz, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 0.75f;
                    spawnUnit = banshee;
                }},
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 0.65f;
                    spawnUnit = phantom;
                }},
                silicon, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 0.65f;
                    spawnUnit = revenant;
                }}
            );
            alwaysShooting = true;
            consumePower(80f / 60f);
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            requiredItems = with(lead, 10, copper, 10);
            researchCost = with(lead, 1000, iron, 600, rustyIron, 1000);
            requirements(Category.units, with(iron, 50, lead, 50, rustyIron, 50));
        }};

        // arialConstruct -> offensive air units
        arialConstruct = new ItemUnitTurret("arial-construct"){{
            size = 4;
            shootY = 0f;
            reload = 1200f;
            itemCapacity = 20;
            alternateCapacity = 40;
            failedMakeSoundPitch = 0.7f;

            ammo(
                lead, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 2f;
                    spawnUnit = aero;
                    alternateType = new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 2f;
                        reloadMultiplier = 0.45f;
                        spawnUnit = striker;
                    }};
                }},
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 2f;
                    spawnUnit = pteropus;
                    alternateType = new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 2f;
                        reloadMultiplier = 0.45f;
                        spawnUnit = acerodon;
                    }};
                }}
            );
            alwaysShooting = true;
            requiredItems = with(copper, 10);
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            researchCost = with(lead, 100, silicon, 600,  iron, 600);
            requirements(Category.units, with(iron, 100, lead, 100, copper, 100));
        }};

        // groundConstruct -> offensive ground units
        groundConstruct = new ItemUnitTurret("ground-construct"){{
            size = 4;
            shootY = 2.5f * Vars.tilesize;
            reload = 1200f;
            itemCapacity = 20;
            alternateCapacity = 40;
            failedMakeSoundPitch = 0.7f;

            ammo(
                lead, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = supella;
                    alternateType = new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 2f;
                        reloadMultiplier = 0.45f;
                        spawnUnit = germanica;
                    }};
                }},
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = venom;
                        alternateType = new SpawnHelperBulletType(){{
                            shootEffect = Fx.shootBig;
                            ammoMultiplier = 2f;
                            reloadMultiplier = 0.45f;
                            spawnUnit = serpent;
                        }};
                }}
            );
            requiredItems = with(copper, 10);
            alwaysShooting = hoverShowsSpawn = arrowShootPos = true;
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            researchCost = with(rustyIron, 5000, silicon, 300,  iron, 300);
            requirements(Category.units, with(iron, 100, rustyIron, 100, copper, 100));
        }};

        //navalConstruct -> offensive naval units
        navalConstruct = new ItemUnitTurret("naval-construct"){{
            size = 4;
            reload = 1200f;
            itemCapacity = 20;
            alternateCapacity = 40;
            failedMakeSoundPitch = 0.7f;
            shootY = 2.5f * Vars.tilesize;

            ammo(
                graphite, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = bay;
                    alternateType = new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 2f;
                        reloadMultiplier = 0.45f;
                        spawnUnit = blitz;
                    }};
                }},
                iron, new SpawnHelperBulletType(){{
                    shootEffect = Fx.smeltsmoke;
                    ammoMultiplier = 2f;
                    spawnUnit = porter;
                    alternateType = new SpawnHelperBulletType(){{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 2f;
                        reloadMultiplier = 0.45f;
                        spawnUnit = essex;
                    }};
                }}
            );
            consumePower(80f / 60f);
            requiredItems = with(copper, 10);
            failedMakeSound = NyfalisSounds.as2ArmorBreak;
            alwaysShooting = hoverShowsSpawn = hasPower = floating = arrowShootPos = true;
            researchCost = with(lead, 500, silicon, 300,  iron, 300);
            requirements(Category.units, with(iron, 100, lead, 100, silicon, 50));
        }};

        alternateArticulator = new Articulator("alternate-articulator"){{
            size = 3;

            ((ItemUnitTurret) arialConstruct).statArticulator = this;
            ((ItemUnitTurret) navalConstruct).statArticulator = this;
            ((ItemUnitTurret) groundConstruct).statArticulator = this;


            hasPower = consumesPower = conductivePower = true;
            requirements(Category.units, with(aluminum, 100, rustyIron, 100, copper, 100));
        }};

        //Unit Tree: t1 = construct
        // T2 = construct + Articulator
        // t3 = t1 + reconstructor
        // t4 = t2 + t3 reconstructor + Articulator
        // t5 = t1-t4 at assembler

        repairPin = new UnitRailingRepairTurret("repair-pin"){{
            //Intrusive bottom thoughts won -Rushie
            size = 3;
            shootY = 10;
            repairSpeed = 2.5f;
            repairRadius = 110;
            powerUse =100f / 60f;

            length = 100f;
            lineFx = new Effect(20f, e -> {
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

            fireFx = new Effect(10, e -> {
                color(e.color);
                float w = 1.2f + 7 * e.fout();

                Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                color(e.color);

                for(int i : Mathf.signs){
                    Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                }

                Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
            });
            requirements(Category.units, with(iron, 15, Items.lead, 20));
        }};

        fortifiePayloadConveyor = new PayloadConveyor("fortified-payload-conveyor"){{
            requirements(Category.units, with(Items.graphite, 10 , iron, 5));
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
            armor = 1f;
            health = 55;
            baseExplosiveness = 0.5f;
            //TODO: if possible 1 capacity & power usage, this only does usage
            consume(new ConsumePower(1/60f, 1f, false));
            researchCost = with(rustyIron, 20, lead, 20);
            requirements(Category.power, with(rustyIron, 1, lead, 2));
        }};

        superConductors = new Wire("super-conductor"){{
            floating = true;
            solid = false;
            armor = 5;
            health = 150;
            baseExplosiveness = 0.7f;
            researchCost = with(iron, 250, cobalt, 100);
            requirements(Category.power, with(cobalt, 10, iron, 5));
        }};

        wireBridge = new BeamNode("wire-bridge"){{
            consumesPower = outputsPower = floating = true;
            range = 5;
            armor = 3;
            health = 100;
            pulseMag = 0f;
            laserWidth = 0.4f;
            baseExplosiveness = 0.6f;
            buildCostMultiplier = 0.6f;
            consumePower(10f/ 60f);
            laserColor2 = Color.valueOf("65717E");
            laserColor1 = Color.valueOf("ACB5BA");
            researchCost = with(lead, 100, iron, 50);
            requirements(Category.power, with(iron, 10, Items.lead, 5));
        }};

        windMills = new WindMill("wind-mill"){{
            size = 3;
            boosterMultiplier = 3.8f;/* rationed for press + 2 gardens*/
            powerProduction = 20f/60f;
            displayEfficiencyScale = 1.1f;
            attribute = Attribute.steam;
            consumeLiquid(oil, 10f / 60f).boost();
            researchCost = with(rustyIron, 20);
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
            researchCost = with(iron, 750, silicon, 500, lead, 1500, cobalt, 500);
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
            researchCost = with(quartz, 500, lead, 500, silicon, 500);
            requirements(Category.power, with(quartz, 50, lead, 50, silicon, 50));
        }};

        steamTurbine = new ConsumeGenerator("steam-turbine"){{
            size = 6;
            powerProduction = 145f/60f;

            consumeLiquid(steam, 24f/60f);
            consumeLiquid(oil, 20f / 60f).boost();
            requirements(Category.power, with(iron, 50, silicon, 50, lead, 100, cobalt, 50));
        }};
        //TODO: Solar receiver & Mirror -> Super structure `Mirror(s)->Redirector->Solar tower+water=steam->steam turbine(s)`
        // Mirror -> SolarTower -> Heat + water-> SteamTurbine -> power

        //endregion
        //region Wall
        rustyWall = new Wall("rusty-wall"){{
            floating = true;
            size = 1;
            health =  350;
            buildCostMultiplier = 0.8f;
            researchCost = with(rustyIron, 50);
            requirements(Category.defense,with(rustyIron, 6));
        }};

        rustyWallLarge = new Wall("rusty-wall-large"){{
            floating = true;
            size = 2;
            health =  1400;
            buildCostMultiplier = 0.7f;
            researchCost = with(rustyIron, 100);
            requirements(Category.defense,with(rustyIron, 24));
        }};

        rustyWallHuge = new Wall("rusty-wall-huge"){{
            floating = true;
            size = 3;
            health = 2690;
            buildCostMultiplier = 0.7f;
            researchCost = with(rustyIron, 200);
            requirements(Category.defense,with(rustyIron, 54));
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
            requirements(Category.defense,with(iron, 6));
        }};

        ironWallLarge = new Wall("iron-wall-large"){{
            size = 2;
            health = 2800;
            buildCostMultiplier = 0.7f;
            researchCost = with(iron, 1000);
            requirements(Category.defense,with(iron, 24));
        }};

        quartzWall = new Wall("quartz-wall"){{
            absorbLasers = true;
            size = 1;
            health = 1100;
            buildCostMultiplier = 0.7f;
            researchCost = with(quartz, 500, lead, 200);
            requirements(Category.defense,with(quartz, 6, lead, 2));
        }};

        quartzWallLarge = new Wall("quartz-wall-large"){{
            absorbLasers = true;
            size = 2;
            health = 4600;
            buildCostMultiplier = 0.7f;
            researchCost = with(quartz, 1000, lead, 200);
            requirements(Category.defense,with(quartz, 24, lead, 8));
        }};


        cobaltWall = new PoweredLightingWall("cobalt-wall"){{
            conductivePower = consumesPower = update = true;
            size = 1;
            health = 1500;
            buildCostMultiplier = 0.7f;
            lightningChancePowered = 0.06f;
            consumePower(5f / 60f);
            researchCost = with(cobalt, 500, copper, 200);
            requirements(Category.defense,with(cobalt, 6, silicon, 2));
            lightningColor = new Color().set(cobalt.color).lerp(surgeAlloy.color, 0.5f);
        }};

        cobaltWallLarge = new PoweredLightingWall("cobalt-wall-large"){{
            conductivePower = consumesPower = update = true;
            size = 2;
            health = 5700;
            buildCostMultiplier = 0.7f;
            lightningChancePowered = 0.06f;
            consumePower(20f / 60f);
            researchCost = with(cobalt, 1000, copper, 200);
            requirements(Category.defense,with(cobalt, 24, silicon, 8));
            lightningColor = new Color().set(cobalt.color).lerp(surgeAlloy.color, 0.5f);
        }};

        //TODO: late game wall is also a router


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
        //region Effect / Storage
        mendFieldProjector = new DirectionalMendProjector("mend-field-projector");

        taurus = new PowerTurret("taurus"){{
            size = 3;
            recoils = 2;
            reload = 10f;
            shootCone =12f;
            rotateSpeed = 3;
            inaccuracy = 15f;
            coolantMultiplier = 1.6f;
            shootY = (size * tilesize / 2f) -2f;

            hasPower = targetHealing = true;
            targetAir = targetGround  = false;
            shootType = new HealOnlyBulletType(5.2f, -5, "olupis-diamond-bullet"){{
                collidesTeam = despawnHit = splashDamagePierce = alwaysSplashDamage = despawnHitEffect = true;
                collidesAir = absorbable = false;
                width = 10f;
                height = 16f;
                lifetime = 30f;
                healPercent = 3f;
                splashDamage = 0f;
                /*added slight homing, so it can hit 1x1 blocks better or at all*/
                homingRange = 5f;
                homingPower = 0.2f;
                splashDamageRadius = Vars.tilesize * 2;
                backColor = Pal.heal;
                hitEffect = despawnEffect = NyfalisFxs.taurusHeal;
                frontColor = Color.white;
                shootSound = Sounds.sap;
            }};
            limitRange(3f);
            shootEffect = Fx.shootHeal;
            group = BlockGroup.projectors;
            outlineColor = nyfalisBlockOutlineColour;
            shoot = new ShootAlternate(9f);
            consumePower(100f / 60f);
            researchCost = with(iron, 100, lead, 200);
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
            flags = EnumSet.of(BlockFlag.repair, BlockFlag.turret);
            coolant = consume(new ConsumeLubricant(45f / 60f));
            requirements(Category.effect, with(iron, 15, Items.lead, 20));
        }};

        ladar = new Radar("ladar"){{
            size = 2;
            fogRadius = 64;
            rotateSpeed = 20f;
            glowMag = glowScl = 0f;
            discoveryTime = 60f * 80f;
            consumePower(240f/60f);
            glowColor = Color.valueOf("00000000");
            requirements(Category.effect, BuildVisibility.fogOnly, with(Items.lead, 60, Items.graphite, 50, iron, 10));
        }};

        //Healing turret that has ammo and water to heal better

        //TODO: Mister -> phase fluid = to give units a temp shied
        //  -> Nanite Fluid = repair

        fortifiedContainer = new StorageBlock("fortified-container"){{
            coreMerge = false;
            size = 2;
            health =  790;
            buildCostMultiplier = 0.7f;
            scaledHealth = itemCapacity = 150;
            researchCost = with(iron, 250, rustyIron, 550);
            requirements(Category.effect, with(rustyIron, 55, iron, 25));
        }};

        fortifiedVault = new StorageBlock("fortified-vault"){{
            coreMerge = false;
            size = 3;
            health =  1500;
            scaledHealth = 120;
            itemCapacity = 950;
            researchCost = with(iron, 550, rustyIron, 800, silicon, 550);
            requirements(Category.effect, with(rustyIron, 75, iron, 50, silicon, 50));
        }};

//        deliveryCannon == payload cannon
//         deliveryTerminal == redirects all cannons in a planet to this sector (since only 1 sector can be played)
//        Vars.state.getPlanet().sectors.forEach(a -> {a.info.destination = Vars.state.getSector();});

        int coreBaseHp = 600;

        coreRemnant = new PropellerCoreBlock("core-remnant"){{
            alwaysUnlocked = isFirstTier = true;
            size = 2;
            itemCapacity = 1500;
            health = coreBaseHp * 3;
            buildCostMultiplier = 0.5f;

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1300, lead, 1300));
        }};

        coreVestige = new PropellerCoreBlock("core-vestige"){{
            size = 3;
            itemCapacity = 3000;
            buildCostMultiplier = 0.5f;
            researchCostMultiplier = 0.5f;
            health = Math.round(coreBaseHp * 3.5f);

            unitType = gnat;
            requirements(Category.effect, with(rustyIron, 1300, lead, 1300, iron, 1000));
        }};

        coreRelic = new PropellerCoreTurret("core-relic"){{
            size = 4;
            reload = 80f;
            itemCapacity = 4500;
            shootX = shootY = 0f;
            buildCostMultiplier = 0.5f;
            range = 20f * Vars.tilesize;
            researchCostMultiplier = 0.5f;
            health = Math.round(coreBaseHp * 5f);

            unitType = phorid;
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            requirements(Category.effect, with(rustyIron, 3000, lead, 3000, iron, 1500, graphite, 500));
            shootType = new ArtilleryBulletType(3f, 10){{
                lifetime = 80f;
                knockback = 0.8f;
                homingRange = 50f;
                width = height = 9f;
                splashDamage = 10f;
                homingPower = 0.08f;
                reloadMultiplier = 1.2f;
                buildCostMultiplier = 0.5f;
                splashDamageRadius = 30f;

                collidesTiles = false;
                frontColor = iron.color;
                backColor = rustyIron.color;
                collidesAir = collidesGround;
            }};
        }};

        coreShrine = new PropellerCoreTurret("core-shrine"){{
            size = 5;
            reload = 55f;
            itemCapacity = 6000;
            shootX = shootY = 0f;
            buildCostMultiplier = 0.5f;
            range = 25f * Vars.tilesize;
            researchCostMultiplier = 0.5f;
            Math.round(coreBaseHp * 6.5f);


            unitType = phorid;
            limitRange(0);
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            requirements(Category.effect, with(rustyIron, 3400, lead, 4000, iron, 3500, silicon, 2500, graphite, 2500, quartz, 2500));
            shootType = new SapBulletType(){{
                damage = 25f;
                width = 0.8f;
                lifetime = 20f;
                sapStrength = 0f;
                length = 25f * Vars.tilesize;
                status = StatusEffects.none;
                despawnEffect = Fx.none;
                color = hitColor = rustyIron.color;
                collidesTiles = false;
                collidesAir = collidesGround = collidesTeam = true;
            }};
        }};

        coreTemple = new PropellerCoreTurret("core-temple"){{
            size = 6;
            reload = 35f;
            itemCapacity = 7500;
            shootX = shootY = 0f;
            buildCostMultiplier = 0.5f;
            range = 35 * Vars.tilesize;
            Math.round(coreBaseHp * 8f);
            researchCostMultiplier = 0.5f;

            unitType = phorid;
            shootEffect = Fx.none;
            shootSound = Sounds.bigshot;
            targetGround = targetHealing = targetAir = true;
            requirements(Category.effect, with(rustyIron, 6000, lead, 6000, iron, 4500, silicon, 4500, graphite, 4500, quartz, 2500, cobalt, 2500));
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
            requirements(Category.logic, with(lead, 150, iron, 50, graphite, 30, silicon, 30));
            researchCost = with(lead, 500, iron, 350, graphite, 100, silicon, 100);

            buildCostMultiplier = 0.45f;
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
            requirements(Category.logic, with(Items.graphite, 10, iron, 10, silicon, 5));
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

        cinderBloomy.mapColor = new Color().set(cinderBloomGrass.mapColor).lerp(basalt.mapColor, 0.75f);
        cinderBloomier.mapColor = new Color().set(cinderBloomGrass.mapColor).lerp(basalt.mapColor, 0.5f);
        cinderBloomiest.mapColor = new Color().set(cinderBloomGrass.mapColor).lerp(basalt.mapColor, 0.25f);
        mossyStone.mapColor = new Color().set(mossStone.mapColor).lerp(stone.mapColor, 0.75f);
        mossierStone.mapColor = new Color().set(mossStone.mapColor).lerp(stone.mapColor, 0.5f);
        mossiestStone.mapColor = new Color().set(mossStone.mapColor).lerp(stone.mapColor, 0.25f);
        mossyDirt.mapColor = new Color().set(mossyStone.mapColor).lerp(dirt.mapColor, 0.5f);
        frozenDirt.mapColor = new Color().set(ice.mapColor).lerp(dirt.mapColor, 0.5f);

    }
}
