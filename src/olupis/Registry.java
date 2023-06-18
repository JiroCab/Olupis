package olupis;

import arc.Core;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.world.meta.Attribute;
import olupis.content.*;
import olupis.input.NyfalisSettingsDialog;
import olupis.input.NyfalisSounds;
import olupis.world.planets.NyfalisTechTree;

/*Handles all new content*/
public class Registry {

    /*Used by the biomatter compressor */
    public static final Attribute Bio = Attribute.add("bio");
    /*Used by hydroMill yield*/
    public static final Attribute hydro = Attribute.add("hydro");



    public static void register() {
        NyfalisItemsLiquid.LoadItems();
        NyfalisItemsLiquid.LoadLiquids();
        NyfalisUnits.LoadUnits();
        NyfalisBlocks.LoadWorldTiles();
        NyfalisBlocks.LoadBlocks();
        NyfalisSchematic.LoadSchematics();
        NyfalisPlanets.LoadPlanets();
        NyfalisSectors.LoadSectors();
        NyfalisSounds.LoadMusic();


        //region Misc
        NyfalisPlanets.PostLoadPlanet();
        NyfalisTechTree.load();
        NyfalisBlocks.AddAttributes();
        //endregion

    }

    public static void postRegister(){
        NyfalisBlocks.NyfalisBlocksPlacementFix();
        NyfalisSettingsDialog.AddNyfalisSoundSettings();

        Vars.ui.planet.shown(() -> {
            if(Core.settings.getBool("olupis-space-sfx")) {Core.audio.play(NyfalisSounds.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);}
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