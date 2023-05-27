package olupis;

import arc.Core;
import arc.Events;
import arc.scene.Group;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.ui.Styles;
import olupis.content.OlupisPlanets;
import olupis.input.OlupisSettingsDialog;
import olupis.input.OlupisSounds;

import static mindustry.Vars.*;

public class Main extends Mod{
    public OlupisSounds soundHandler = new OlupisSounds();
    public OlupisSettingsDialog olupisSettings;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> Registry.postRegister());

        Events.on(EventType.WorldLoadEvent.class, l ->{
            //debug and if someone needs to convert a map and said map does not have the Olupis Block set
            if( Core.settings.getBool("olupis-debug")) buildDebugUI(Vars.ui.hudGroup);

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
