package olupis.input;

import arc.Core;
import mindustry.Vars;
import mindustry.gen.Icon;

import static mindustry.Vars.*;

public class OlupisSettingsDialog {

    public OlupisSettingsDialog() {
        BuildDialog();
    }

    public void BuildDialog(){
        ui.settings.addCategory("@category.olupis.name", Icon.effect, table -> {

            table.add("@olupis-restart").row();
            table.checkPref("olupis-green-icon", true);

            table.checkPref("olupis-space-sfx",true);
            table.checkPref("olupis-hide-sound",true);
            table.checkPref("olupis-debug",false);
            table.checkPref("olupis-music",true);
            table.checkPref("olupis-music-only",false);
        });
    }

    public static void AddOlupisSoundSettings(){
        if(Core.settings.getBool("olupis-hide-sound"))return;

        Vars.ui.settings.sound.row();
        Vars.ui.settings.sound.checkPref("olupis-space-sfx",true);
        Vars.ui.settings.sound.row();
        Vars.ui.settings.sound.checkPref("olupis-music-only",false);
        Vars.ui.settings.sound.row();
        Vars.ui.settings.sound.checkPref("olupis-music",true);
    }
}
