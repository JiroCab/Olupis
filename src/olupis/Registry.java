package olupis;

import arc.Core;
import arc.audio.Music;
import mindustry.world.meta.Attribute;
import olupis.content.*;
import olupis.input.OlupisSettingsDialog;
import olupis.world.planets.OlupisTechTree;

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
        OlupisSounds.LoadMusic();


        //region Misc
        OlupisTechTree.load();
        OlupisBlocks.AddAttributes();
        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;
        //endregion

    }

    public static void postRegister(){
        OlupisBlocks.OlupisBlocksPlacementFix();
        OlupisBlocks.NoIconFix();
        OlupisSettingsDialog.AddOlupisSoundSettings();
    }

}