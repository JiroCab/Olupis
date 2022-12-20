package com.jirocab.planets;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import com.jirocab.planets.content.*;
import com.jirocab.planets.planets.OlupisTechTree;
import mindustry.content.Blocks;
import mindustry.gen.Unit;
import mindustry.type.Item;
import mindustry.world.meta.Attribute;

/*Handles all new content*/
public class Registry {
    public static final Seq<Item> nonOlupisItems = new Seq<>(), olupisOnlyItems = new Seq<>();
    public static Music space = new Music();

    /*Used by the biomatter compressor */
    public static final Attribute Bio = Attribute.add("bio");
    /*Used by hydroMill yield*/
    public static final Attribute hydro = Attribute.add("hydro");
    public static Unit coreshipPH;



    public static void register() {
        OlupisItemsLiquid.LoadItems();
        OlupisItemsLiquid.LoadLiquids();
        OlupisUnits.LoadUnits();
        OlupisBlocks.LoadWorldTiles();
        OlupisBlocks.LoadBlocks();
        OlupisSchematic.LoadSchematics();
        OlupisPlanets.LoadPlanets();
        OlupisSectors.LoadSectors();

        //region Misc
        OlupisTechTree.load();

        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;

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