package olupis;

import arc.Core;
import arc.audio.Music;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.world.meta.Attribute;
import olupis.content.*;
import olupis.input.OlupisSettingsDialog;
import olupis.input.OlupisSounds;
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
        OlupisPlanets.PostLoadPlanet();
        OlupisTechTree.load();
        OlupisBlocks.AddAttributes();
        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;
        //endregion

    }

    public static void postRegister(){
        OlupisBlocks.OlupisBlocksPlacementFix();
        OlupisSettingsDialog.AddOlupisSoundSettings();

        Vars.ui.planet.shown(() -> {
            if(Core.settings.getBool("olupis-space-sfx")) {Core.audio.play(Registry.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);}
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