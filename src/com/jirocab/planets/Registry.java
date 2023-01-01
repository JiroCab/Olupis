package com.jirocab.planets;

import arc.Core;
import arc.audio.Music;
import com.jirocab.planets.content.*;
import com.jirocab.planets.world.planets.OlupisTechTree;
import mindustry.world.meta.Attribute;

/*Handles all new content*/
public class Registry {
    public static Music space = new Music();

    /*Used by the biomatter compressor */
    public static final Attribute Bio = Attribute.add("bio");
    /*Used by hydroMill yield*/
    public static final Attribute hydro = Attribute.add("hydro");



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
        OlupisBlocks.AddAttributes();
        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;


    }

}