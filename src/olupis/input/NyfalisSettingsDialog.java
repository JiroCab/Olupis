package olupis.input;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;
import olupis.NyfalisMain;

import static mindustry.Vars.*;

public class NyfalisSettingsDialog {

    public NyfalisSettingsDialog() {
        if(!headless) BuildDialog();
    }

    public void BuildDialog(){
        ui.settings.addCategory("@category.nyfalis.name", Icon.effect, table -> {
            table.checkPref("nyfalis-green-icon", true);
            table.checkPref("nyfalis-green-name", true);
            table.checkPref("nyfalis-auto-ban", true);
            table.checkPref("nyfalis-debug", false);

            table.row();

            BuildNyfalisSoundSettings(table, false);
            table.button("@nyfalis-disclaimer.name", NyfalisMain::disclaimerDialog).margin(14).width(260f).pad(6);
        });
    }

    public static void BuildNyfalisSoundSettings(Table table, Boolean hide){
        if(!Core.settings.getBool("nyfalis-hide-sound") && hide)return;

        boolean[] shown = {false};
        table.row();
        table.button("@setting.nyfalis-sound-category", Icon.effect, Styles.togglet, () -> shown[0] = !shown[0]).margin(14f).growX().height(60f).checked(a -> shown[0]).pad(5f).center().row();

        table.collapser(t -> {
            SettingsMenuDialog.SettingsTable subTable = new SettingsMenuDialog.SettingsTable();
            if (!hide){
                subTable.checkPref("nyfalis-hide-sound",true);
            } else  t.label(() -> "@settings.nyfalis-music-hint").top().center().padBottom(5f).margin(3).row();

            subTable.checkPref("nyfalis-space-sfx",true);
            subTable.checkPref("nyfalis-music",true);
            subTable.checkPref("nyfalis-music-only",false);
            subTable.checkPref("nyfalis-music-add",true, c -> NyfalisMain.soundHandler.nyfalisMusicSet = false);
            subTable.checkPref("nyfalis-music-custom-game",true);
            t.add(subTable);
        }, () ->shown[0]).growX().center().row();
    }

    public static void AddNyfalisSoundSettings(){
        BuildNyfalisSoundSettings(Vars.ui.settings.sound, true);
    }
}
