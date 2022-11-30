package com.jirocab.planets.content;

import arc.struct.EnumSet;
import com.jirocab.planets.Registry;
import com.jirocab.planets.blocks.Replicator;
import mindustry.content.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class OlupisBlocks {
    //region Blocks Variables
    public static Block
    //environment
    olupisTree, bush, mossyBoulder, mossTree ,

    oreIron, oreIronWall, oreCobalt, OreCobaltWall,

    redSand, redDune, redSandWater, greenShrubsIrregular,  mossyStoneWall, mossierStoneWall, mossiestStoneWall, mossStone,
    frozenGrass, yellowGrass, yellowTree, yellowBush, yellowShrubs, yellowShrubsIrregular,  mossyStone, mossierStone, mossiestStone, mossStoneWall,

    //Buildings
    garden, bioMatterPress, unitReplicator, unitReplicatorSmall, rustElectrolyzer,
    rustyIronConveyor, ironConveyor,
    architonnerre, architronito
    ;  //endregion

    public static void LoadWorldTiles(){
        //region World Tiles
        oreIron = new OreBlock("ore-iron", Registry.rustyIron);
        oreIronWall = new OreBlock("ore-iron-wall", Registry.rustyIron){{
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
            mindustry.content.Blocks.grass.asFloor().decoration = this;
        }};

        mossyBoulder = new Prop("mossy-boulder"){{
            variants = 2;
            frozenGrass.asFloor().decoration = this;
            mindustry.content.Blocks.grass.asFloor().decoration = this;
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
        }};

        mossierStone = new Floor("mossier-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
        }};

        mossiestStone = new Floor("mossiest-stone"){{
            attributes.set(Attribute.water, 0.1f);
            attributes.set(Registry.Bio, 0.1f);
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

        //endregion

    }
    public static void LoadBlocks(){
//region Buildable Blocks
        ironConveyor = new Conveyor("iron-conveyor"){{
            requirements(Category.distribution, with(Registry.iron,1 ));
            health = 70;
            speed = 0.015f;
            displayedSpeed = 2.1f;
            buildCostMultiplier = 2f;
            researchCost = with(Registry.iron, 100);
        }};

        rustyIronConveyor = new Conveyor("rusty-iron-conveyor"){{
            requirements(Category.distribution, with(OlupisBlocks.rustyIronConveyor, 1));
            health = 45;
            speed = 0.03f;
            displayedSpeed = 4.2f;
            buildCostMultiplier = 2f;
            researchCost = with(Registry.rustyIron, 100);
        }};

        garden = new AttributeCrafter("garden"){{
            requirements(Category.production, ItemStack.with(Items.copper, 25, Items.silicon, 10, Items.metaglass, 10));
            outputItem = new ItemStack(Registry.condensedBiomatter, 1);
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
            requirements(Category.crafting, with(Items.lead, 35, Items.silicon, 30));
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

            consumeItem(Registry.condensedBiomatter, 1);
            consumePower(0.7f);
        }};

        rustElectrolyzer = new GenericCrafter("rust-electrolyzer"){{
            requirements(Category.crafting, with(Items.lead, 65, Items.silicon, 40, Items.titanium, 60));
            outputItem = new ItemStack(Registry.iron, 1);
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
            consumeItems(with(Items.lead, 1, Registry.rustyIron,1));
            consumeLiquid(Liquids.water, 12f / 60f);
        }};

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

        architonnerre = new LiquidTurret("architonnerre"){{
            requirements(Category.turret, with(Registry.rustyIron, 20));
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
                        despawnEffect = Fx.none;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }}
            );
            recoil = 0.2f;
            reload = 10f;
            range = 60f;
            shootCone = 50f;
            ammoUseEffect = Fx.steam;
            shootSound = Sounds.steam;
            health = 400;
            size = 2;
            targetAir = false;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        architronito = new LiquidTurret("architronito"){{
            requirements(Category.turret, with(Registry.rustyIron, 20));
            ammo(
                    Liquids.water, new BulletType(3.33f, 20f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        shootEffect = OlupisFxs.shootSteamLarge;
                        hitEffect = Fx.steam;
                        despawnEffect = Fx.none;
                        status = StatusEffects.corroded;
                        keepVelocity = false;
                        hittable = false;
                    }}
            );
            recoil = 0.2f;
            reload = 10f;
            range = 90f;
            shootCone = 50f;
            ammoUseEffect = Fx.steam;
            shootSound = Sounds.steam;
            health = 700;
            size = 3;
            targetAir = false;
            consumePower(3.3f);
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};
        //endregion

    }
}
