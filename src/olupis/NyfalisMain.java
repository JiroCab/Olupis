package olupis;

import arc.Core;
import arc.Events;
import arc.scene.Group;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.type.Planet;
import mindustry.ui.Styles;
import mindustry.world.Block;
import olupis.content.*;
import olupis.input.NyfalisSettingsDialog;
import olupis.input.NyfalisSounds;
import olupis.world.planets.NyfalisTechTree;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisMain extends Mod{
    public NyfalisSounds soundHandler = new NyfalisSounds();
    public NyfalisSettingsDialog nyfalisSettings;

    @Override
    public void loadContent(){
        NyfalisItemsLiquid.LoadItems();
        NyfalisItemsLiquid.LoadLiquids();
        NyfalisUnits.LoadUnits();
        NyfalisBlocks.LoadWorldTiles();
        NyfalisBlocks.LoadBlocks();
        NyfalisSchematic.LoadSchematics();
        NyfalisPlanets.LoadPlanets();
        NyfalisSectors.LoadSectors();
        NyfalisSounds.LoadMusic();

        NyfalisPlanets.PostLoadPlanet();
        NyfalisTechTree.load();
        NyfalisBlocks.AddAttributes();
        NyfalisUnits.PostLoadUnits();

        Log.info("OwO, Nyfalis (Olupis) content Loaded! Hope you enjoy nya~");
    }

    public NyfalisMain(){
        Events.on(EventType.WorldLoadEvent.class, l ->{
            /*Delayed so custom games are affected*/
            Time.run(1 * Time.toSeconds, () ->{
                if(shouldAutoBan()) {
                    Log.debug("Nyfalis has banned its blocks!");
                    if (!state.rules.blockWhitelist) {
                        state.rules.bannedBlocks.addAll(NyfalisBlocks.nyfalisBuildBlockSet);
                    } else state.rules.bannedBlocks.removeAll(NyfalisBlocks.nyfalisBuildBlockSet.toSeq());
                }
            });
            if(headless)return;

            //debug and if someone needs to convert a map and said map does not have the Nyfalis Block set / testing
            if( Core.settings.getBool("olupis-debug")) buildDebugUI(Vars.ui.hudGroup);
            soundHandler.replaceSoundHandler();
        });

        if(headless)return;
        Events.on(ClientLoadEvent.class, e -> {
            NyfalisBlocks.NyfalisBlocksPlacementFix();
            NyfalisSettingsDialog.AddNyfalisSoundSettings();

            Vars.ui.planet.shown(() -> {
                if(Core.settings.getBool("olupis-space-sfx")) Core.audio.play(NyfalisSounds.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);
            });

            /*For those people who don't like the name/icon or overwrites in general*/
            if(Core.settings.getBool("olupis-green-icon")) Team.green.emoji = "\uf7a6";
            if(Core.settings.getBool("olupis-green-name")) Team.green.name = "olupis-green";
        });
    }

    public boolean shouldAutoBan(){
        if(net.client())return false;
        if(!Core.settings.getBool("olupis-auto-ban")) return false;
        if(state.isCampaign()){ Planet sector = state.getSector().planet;
            if(sector == arthin) return false;
            if(sector == spelta) return false;
            return sector != nyfalis;
        }
        if(state.rules.env == defaultEnv && state.getPlanet() == Planets.sun) return false;
        AtomicBoolean hasCore = new AtomicBoolean(false);
        for (Block c : NyfalisBlocks.nyfalisCores) {
            if (indexer.isBlockPresent(c)) {
                hasCore.set(true);
                break;
            }
        }
        return !hasCore.get();
    }

    public static void buildDebugUI(Group group){
        group.fill(t -> {
            t.visible(() -> Vars.ui.hudfrag.shown);
            t.bottom().left();
            t.button("Export w/ Nyfalis", Icon.file, Styles.squareTogglet, () -> {
                NyfalisPlanets.nyfalis.applyRules(state.rules);
                ui.paused.show();
            }).width(155f).height(50f).margin(12f).checked(false);
        });
    }


    @Override
    public void init() {
        nyfalisSettings = new NyfalisSettingsDialog();
    }


}
