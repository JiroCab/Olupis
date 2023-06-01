package olupis;

import arc.Core;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.world.meta.Attribute;
import olupis.content.*;
import olupis.input.OlupisSettingsDialog;
import olupis.input.OlupisSounds;
import olupis.world.planets.OlupisTechTree;

/*Handles all new content*/
public class Registry {

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
        OlupisPlanets.PostLoadPlanet();
        OlupisTechTree.load();
        OlupisBlocks.AddAttributes();
        //endregion

    }

    public static void postRegister(){
        OlupisBlocks.OlupisBlocksPlacementFix();
        OlupisSettingsDialog.AddOlupisSoundSettings();

        Vars.ui.planet.shown(() -> {
            if(Core.settings.getBool("olupis-space-sfx")) {Core.audio.play(OlupisSounds.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);}
        });

        /*For those people who don't like the name/icon or overwrites in general*/
        if(Core.settings.getBool("olupis-green-icon")){
            Team.green.emoji = "\uf7a6";
        }
        if(Core.settings.getBool("olupis-green-name")){
            Team.green.name = "olupis-green";
        }
    }

}