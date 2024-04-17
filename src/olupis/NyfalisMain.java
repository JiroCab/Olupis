package olupis;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Group;
import arc.scene.ui.Label;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import olupis.content.*;
import olupis.input.NyfalisLogicDialog;
import olupis.input.NyfalisSettingsDialog;
import olupis.world.planets.NyfalisTechTree;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisMain extends Mod{
    public static NyfalisSounds soundHandler = new NyfalisSounds();
    public static NyfalisLogicDialog logicDialog;
    public NyfalisSettingsDialog nyfalisSettings;

    @Override
    public void loadContent(){
        NyfalisItemsLiquid.LoadItems();
        NyfalisItemsLiquid.LoadLiquids();
        NyfalisUnits.LoadUnits();
        NyfalisBlocks.LoadWorldTiles();
        NyfalisBlocks.LoadBlocks();
        NyfalisSchematic.LoadSchematics();
        NyfalisAttributeWeather.loadWeather();
        NyfalisPlanets.LoadPlanets();
        NyfalisSectors.LoadSectors();

        NyfalisPlanets.PostLoadPlanet();
        NyfalisTechTree.load();
        NyfalisAttributeWeather.AddAttributes();
        NyfalisUnits.PostLoadUnits();

        Log.info("OwO, Nyfalis (Olupis) content Loaded! Hope you enjoy nya~");
    }

    public NyfalisMain(){
        //Load sounds once they're added to the file tree
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(() -> {
            NyfalisSounds.LoadSounds();
            NyfalisSounds.LoadMusic();
        }));

        Events.on(EventType.WorldLoadEvent.class, l ->{
            if(shouldAutoBan()) Time.run(0.5f * Time.toSeconds, () ->{ /*Delayed since custom games, for some reason needs it*/
                /*Hiding blocks w/o banning them mainly for custom games */
                state.rules.hiddenBuildItems.addAll(NyfalisItemsLiquid.nyfalisOnlyItems);
            });
            unlockPlanets();
            if(headless)return;

            //debug and if someone needs to convert a map and said map does not have the Nyfalis Block set / testing
            if( Core.settings.getBool("nyfalis-debug")) buildDebugUI(Vars.ui.hudGroup);
            soundHandler.replaceSoundHandler();
        });

        if(headless)return;
        Events.on(EventType.UnlockEvent.class, event -> unlockPlanets());
        Events.on(EventType.SectorCaptureEvent.class, event -> unlockPlanets());

        Events.on(ClientLoadEvent.class, e -> {
            NyfalisBlocks.NyfalisBlocksPlacementFix();
            NyfalisSettingsDialog.AddNyfalisSoundSettings();
            if(Core.settings.getBool("nyfalis-disclaimer"))disclaimerDialog();

            Vars.ui.planet.shown(() -> {
                if(Core.settings.getBool("nyfalis-space-sfx")) Core.audio.play(NyfalisSounds.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);
            });

            arthin.uiIcon = bush.fullIcon;
            nyfalis.uiIcon = redSandBoulder.fullIcon;
            spelta.uiIcon = pinkTree.fullIcon;
            system.uiIcon = Icon.planet.getRegion();

            /*For those people who don't like the name/icon or overwrites in general*/
            if(Core.settings.getBool("nyfalis-green-icon")) Team.green.emoji = "\uf7a6";
            if(Core.settings.getBool("nyfalis-green-name")) Team.green.name = "nyfalis-green";
            /* uncomment when name/icon is final
            if(Core.settings.getBool("nyfalis-blue-icon")) Team.green.name = "";
            if(Core.settings.getBool("nyfalis-blue-name")) Team.green.name = "nyfalis-blue";*/
        });
    }

    public boolean shouldAutoBan(){
        if(net.client())return false;
        if(!Core.settings.getBool("nyfalis-auto-ban")) return false;
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
                state.rules.blockWhitelist = true;
                NyfalisPlanets.nyfalis.applyRules(state.rules);
                ui.paused.show();
            }).width(155f).height(50f).margin(12f).checked(false);
        });
    }


    @Override
    public void init() {
        nyfalisSettings = new NyfalisSettingsDialog();
        logicDialog = new NyfalisLogicDialog();
        unlockPlanets();
    }

    public static void disclaimerDialog(){
        BaseDialog dialog = new BaseDialog("@nyfalis-disclaimer.name");
        dialog.centerWindow();

        dialog.cont.setOrigin(Align.center);
        dialog.cont.table(t -> {
            t.defaults().growY().growX().center();

            Label header = new Label("@nyfalis-disclaimer.header");
            Label body = new Label("@nyfalis-disclaimer.body");
            header.setAlignment(Align.center);
            header.setWrap(true);
            body.setWrap(true);
            body.setAlignment(Align.center);

            t.add(header).row();
            /*Very convoluted way to load the mod icon, because I'm not bright to think of any other way*/
            TextureRegion icon = new TextureRegion(mods.list().find(a -> Objects.equals(a.name, "olupis")).iconTexture);
            t.table(a -> a.image(icon).scaling(Scaling.bounded).row()).tooltip("Art By RushieWashie").maxSize(700).margin(14).pad(3).center().row();

            t.add(body).row();


        }).growX().growY().center().top().row();

        dialog.cont.button("@back", Icon.left, dialog::hide).padTop(-1f).size(220f, 55f).bottom();
        dialog.closeOnBack();
        dialog.show();
    }

    public void unlockPlanets(){
        if(nyfalis.unlocked() && spelta.unlocked()) return;
        if(nyfalis.unlocked()){
            for (Sector s : nyfalis.sectors) {
                if (s.unlocked() || (s.preset != null && s.preset.unlocked())) {
                    nyfalis.quietUnlock();
                    nyfalis.alwaysUnlocked = true;
                    break;
                }
            }
        }
        if(spelta.unlocked()){
            for (Sector s : spelta.sectors) {
                if (s.unlocked() || (s.preset != null && s.preset.unlocked())) {
                    nyfalis.quietUnlock();
                    nyfalis.alwaysUnlocked = true;
                    break;
                }
            }
        }
    }

}
