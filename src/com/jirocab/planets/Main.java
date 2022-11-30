package com.jirocab.planets;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;

public class Main extends Mod{

    public Main(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
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
            Vars.ui.planet.shown(() ->Core.audio.play(Registry.space, Core.settings.getInt("ambientvol", 100)/100f, 0, 0, false));
        });

    }


    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        Registry.register();
    }

}
