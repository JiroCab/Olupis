package olupis;

import arc.Core;
import arc.Events;
import arc.scene.Group;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.gen.Iconc;
import mindustry.mod.Mod;
import mindustry.ui.Styles;
import olupis.content.OlupisBlocks;
import olupis.content.OlupisPlanets;
import olupis.input.OlupisSettingsDialog;

import static mindustry.Vars.*;

public class Main extends Mod{
    public OlupisSounds soundHandler = new OlupisSounds();
    public OlupisSettingsDialog olupisSettings;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Vars.ui.planet.shown(() -> {
                if(Core.settings.getBool("olupis-space-sfx")) {Core.audio.play(Registry.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);}
            });
            Registry.postRegister();
            if(Core.settings.getBool("olupis-green-icon")) Team.green.emoji = Character.toString(Iconc.statusCorroded) ;
        });

        Events.on(EventType.WorldLoadEvent.class, l ->{
            //debug and if someone needs to convert a map and said map does not have the Olupis Block set
            if( Core.settings.getBool("olupis-debug")){
                buildDebugUI(Vars.ui.hudGroup);
            }

            /*Don't add Olupis block to Serpulo !*/
            if ( !state.rules.infiniteResources && (state.getPlanet() == Planets.serpulo || state.rules.env == Planets.serpulo.defaultEnv || state.rules.hiddenBuildItems.toSeq() != OlupisPlanets.olupis.hiddenItems)){
                OlupisBlocks.olupisBuildBlockSet.each(b -> state.rules.bannedBlocks.add(b));
                state.rules.hideBannedBlocks = true;
                Log.info("non-olupis sector detected");
            }
            if (!(state.rules.env == Vars.defaultEnv && state.rules.hiddenBuildItems.isEmpty() ) || (state.isCampaign() && (state.getPlanet() == OlupisPlanets.olupis || state.getPlanet() == OlupisPlanets.spelta || state.getPlanet() == OlupisPlanets.arthin))){ //TODO: Funny long If statement
                /* TODO: Can't build on Sectors! (banned list is blank without this)*/
                OlupisBlocks.olupisBuildBlockSet.each(b -> state.rules.bannedBlocks.add(b));
                state.rules.hideBannedBlocks = true;
                state.rules.blockWhitelist = true;
                Log.info("olupis sector detected");
            }

            soundHandler.replaceSoundHandler();
        });

    }

    public static void buildDebugUI(Group group){
        group.fill(t -> {
            t.visible(() -> Vars.ui.hudfrag.shown);
            t.bottom().left();
            t.button("Export w/ Olupis", Icon.file, Styles.squareTogglet, () -> {
                OlupisPlanets.olupis.applyRules(state.rules);
                ui.paused.show();
            }).width(155f).height(50f).margin(12f).checked(false);
        });
    }


    @Override
    public void loadContent(){
        Registry.register();
        Log.info("OwO, Olupis content Loaded!");
    }

    @Override
    public void init() {
        olupisSettings = new OlupisSettingsDialog();
    }


}
