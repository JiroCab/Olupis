package olupis;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.net.*;
import mindustry.type.*;
import mindustry.world.*;
import olupis.content.*;
import olupis.input.*;
import olupis.world.FloorUpdater;
import olupis.world.entities.packets.*;
import olupis.world.planets.*;

import static mindustry.Vars.*;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisMain extends Mod{

    public static NyfalisSounds soundHandler = new NyfalisSounds();
    public static NyfalisUnitTimedOutPacket unitTimedOut = new NyfalisUnitTimedOutPacket();
    public static LimitedLauncherSelect sectorSelect;
    public static NyfalisLogicDialog logicDialog;
    public NyfalisSettingsDialog nyfalisSettings;

    @Override
    public void loadContent(){
        NyfalisShaders.LoadShaders();
        NyfalisShaders.LoadCacheLayer(); //idk when to load this so it 1st -Rushie
        NyfalisItemsLiquid.LoadItems();
        NyfalisStatusEffects.loadStatusEffects();
        NyfalisItemsLiquid.LoadLiquids();
        NyfalisUnits.LoadUnits();
        NyfalisBlocks.LoadWorldTiles();
        NyfalisBlocks.LoadBlocks();
        NyfalisSchematic.LoadSchematics();
        NyfalisAttributeWeather.loadWeather();
        NyfalisPlanets.LoadPlanets();
        NyfalisSectors.LoadSectors();
        Net.registerPacket(NyfalisUnitTimedOutPacket::new); //If new packets are needed, turn this into a dedicated class

        NyfalisPlanets.PostLoadPlanet();
        NyfalisTechTree.load();
        NyfalisAttributeWeather.AddAttributes();
        NyfalisUnits.PostLoadUnits();

        Log.info("OwO, Nyfalis (Olupis) content Loaded! Hope you enjoy nya~");
    }

    public NyfalisMain(){
        FloorUpdater.load();

        //Load sounds once they're added to the file tree
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(NyfalisSounds::LoadSounds));

        Events.on(EventType.WorldLoadBeginEvent.class, I -> {
            NyfalisTurrets.dynamicTurretContent(); //Vars.content.sectors().forEach(s => {if(s.sector == null || s.sector.preset == null) Log.err(s.name);});
        });

        Events.on(EventType.WorldLoadEvent.class, l ->{
            /*Delayed since custom games, for some reason needs it*/
            Time.run(0.5f * Time.toSeconds, NyfalisMain::sandBoxCheck);

            unlockPlanets();
            NyfalisStartUpUis.rebuildDebugTable();
            //Clean up of the old system of banning stuff

            if(state.isCampaign() && NyfalisPlanets.isNyfalianPlanet(state.getPlanet())){
                if(state.rules.blockWhitelist) state.rules.blockWhitelist = false;
            }
            Events.on(EventType.SectorLaunchEvent.class, e -> {
                //When launching, prevents exporting to items to where you launched from if it's out of range
               if(NyfalisPlanets.isNyfalianPlanet(e.sector.planet) && !e.sector.near().contains(e.sector.info.destination)) e.sector.info.destination = e.sector;
            });
            if(headless)return;

            Events.on(EventType.TurnEvent.class, e -> {
                for (Sector sec : system.sectors) { //Guaranteed lost, if a base is left alone in 3 turns (6 minutes)
                    if(sec.hasBase() && !sec.isBeingPlayed() && !sec.isCaptured()){
                        sec.info.damage = Math.min(sec.info.damage + 0.33f, 1f);

                        if(sec.info.damage >= 0.999){
                            Log.info("(Nyfalis) " + sec + " was lost from being left alone for too long!");
                            if(sec.info.wave < sec.info.winWave && sec.info.hasCore){
                                Events.fire(new EventType.SectorLoseEvent(sec));

                                sec.info.items.clear();
                                sec.info.damage = 1f;
                                sec.info.hasCore = false;
                                sec.info.production.clear();
                            }
                        }
                    }
                }
            });
            //debug and if someone needs to convert a map and said map does not have the Nyfalis Block set / testing
            if( Core.settings.getBool("nyfalis-debug")) NyfalisStartUpUis.buildDebugUI(Vars.ui.hudGroup);
            soundHandler.replaceSoundHandler();
        });

        if(headless)return;
        Events.on(EventType.UnlockEvent.class, event -> unlockPlanets());
        Events.on(EventType.SectorCaptureEvent.class, event -> unlockPlanets());

        Events.on(ClientLoadEvent.class, e -> {
            NyfalisBlocks.NyfalisBlocksPlacementFix();
            NyfalisSettingsDialog.AddNyfalisSoundSettings();
            NyfalisStartUpUis.saveDisclaimerDialog();
            if(Core.settings.getBool("nyfalis-disclaimer"))NyfalisStartUpUis.disclaimerDialog();

            Vars.ui.planet.shown(() -> {
                if(Core.settings.getBool("nyfalis-space-sfx")) Core.audio.play(NyfalisSounds.spaces.random(), Core.settings.getInt("ambientvol", 100) / 100f, 1, 0, false);
            });

            arthin.uiIcon = bush.fullIcon;
            nyfalis.uiIcon = redSandBoulder.fullIcon;
            spelta.uiIcon = pinkTree.fullIcon;
            system.uiIcon = Icon.planet.getRegion();
            if(Core.settings.getBool("nyfalis-debug")){
                Vars.renderer.maxZoom  = 100; //just going to leave this here so aligning, screenshot are easier
                //if(control.saves.getSaveSlots().first() != null) ui.load.runLoadSave(control.saves.getSaveSlots().first());
            }

            /*For those people who don't like the name/icon or overwrites in general*/
            if(Core.settings.getBool("nyfalis-green-icon")) Team.green.emoji = "\uf7a6";
            if(Core.settings.getBool("nyfalis-green-name")) Team.green.name = "nyfalis-green";
            /* uncomment when name/icon is final
            if(Core.settings.getBool("nyfalis-blue-icon")) Team.green.name = "";
            if(Core.settings.getBool("nyfalis-blue-name")) Team.green.name = "nyfalis-blue";*/
        });
    }

    public static void sandBoxCheck(){ //for any sandbox maps
        if(net.client())return;
        if(!Core.settings.getBool("nyfalis-auto-ban")) return;
        boolean changed = false, anyPlanet = false;
        int prevEnv = state.rules.env;
        if(state.isCampaign()){ Planet sector = state.getSector().planet;
            if(NyfalisPlanets.isNyfalianPlanet(sector)){
                changed = true;
            }
        }
        if(state.rules.env == defaultEnv && state.getPlanet() == Planets.sun){
            anyPlanet = changed = true;
        }
        if(state.rules.hiddenBuildItems.isEmpty()){
            anyPlanet = changed = true;
        }

        if(!changed){
            for (Block c : NyfalisBlocks.nyfalisCores) {
                if (indexer.isBlockPresent(c)) {
                    changed = true;
                    break;
                }
            }
        }
        if(changed){
            state.rules.env = prevEnv | NyfalisAttributeWeather.nyfalian;
        }

        if(anyPlanet) return;
        /*this is here so A)Hotkeys aren't broken even if blocks are hidden due to env B)Prevent Serpulo cores to be built here*/
        if(state.rules.hasEnv(NyfalisAttributeWeather.nyfalian) && !state.rules.isBanned(Blocks.coreShard)){
            for (Block b : hiddenNyfalisBlocks) {
                if (state.rules.bannedBlocks.contains(b) && b != Blocks.coreShard) { //shard core shouldn't be built anyway and will be our check
                    state.rules.bannedBlocks.remove(b);
                    continue;
                }
                if(!sandBoxBlocks.contains(b))state.rules.bannedBlocks.add(b);
            }
        }
    }

    @Override
    public void init() {
        nyfalisSettings = new NyfalisSettingsDialog();
        logicDialog = new NyfalisLogicDialog();
        sectorSelect = new LimitedLauncherSelect();
        unlockPlanets();
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
