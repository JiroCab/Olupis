package com.jirocab.planets;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Group;
import arc.util.Log;
import arc.util.Time;
import com.jirocab.planets.content.OlupisItemsLiquid;
import com.jirocab.planets.content.OlupisPlanets;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.io.MapIO;
import mindustry.mod.Mod;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;

public class Main extends Mod{

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("olupis-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f).row();
                dialog.cont.add("idk why i still have this from the example mod, but why not", Color.darkGray).row();
                dialog.show();
            });
            Vars.ui.planet.shown(() -> {
                if(Core.settings.getBool("olupis-space-sfx")) {Core.audio.play(Registry.space, Core.settings.getInt("ambientvol", 100) / 100f, 0, 0, false);}
            });

            Vars.ui.settings.sound.row();
            Vars.ui.settings.sound.checkPref("olupis-space-sfx",true);
            Vars.ui.settings.sound.row();
            Vars.ui.settings.sound.checkPref("olupis-debug",false);

        });

        Events.on(EventType.WorldLoadEvent.class, l ->{
            //debug and if someone needs to convert a map and said map does not have the Olupis Block set
            if( Core.settings.getBool("olupis-debug")){
                buildDebugUI(Vars.ui.hudGroup);
            }
        });

    }

    public static void buildDebugUI(Group group){
        group.fill(t -> {
            t.visible(() -> Vars.ui.hudfrag.shown);
            t.bottom().left();
            t.button("Export w/ Olupis", Icon.file, Styles.squareTogglet, () -> {
                Vars.state.rules.env = OlupisPlanets.olupis.defaultEnv;
                Vars.state.rules.hiddenBuildItems.clear();
                state.rules.hiddenBuildItems.addAll(OlupisPlanets.olupis.hiddenItems);
                ui.paused.show();
            }).width(155f).height(50f).margin(12f).checked(false);
        });
    }


    @Override
    public void loadContent(){
        Registry.register();
        Log.info("Olupis content Loaded!");
    }

}
