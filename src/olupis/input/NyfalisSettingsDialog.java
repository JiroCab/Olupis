package olupis.input;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;

import static mindustry.Vars.*;

public class NyfalisSettingsDialog {

    public NyfalisSettingsDialog() {
        if(!headless) BuildDialog();
    }

    public void BuildDialog(){
        ui.settings.addCategory("@category.nyfalis.name", Icon.effect, table -> {
            table.checkPref("olupis-green-icon", true);
            table.checkPref("olupis-green-name", true);
            table.checkPref("olupis-debug", false);

            table.row();

            BuildNyfalisSoundSettings(table, false);
        });
    }

    public static void BuildNyfalisSoundSettings(Table table, Boolean hide){
        if(!Core.settings.getBool("olupis-hide-sound") && hide)return;

        boolean[] shown = {false};
        table.row();
        table.button("@setting.olupis-sound-category", Icon.effect, Styles.togglet, () -> shown[0] = !shown[0]).marginLeft(14f).growX().height(60f).checked(a -> shown[0]).padTop(5f).center().row();

        table.collapser(t -> {
            SettingsMenuDialog.SettingsTable subTable = new SettingsMenuDialog.SettingsTable();
            if (!hide){
                subTable.checkPref("olupis-hide-sound",true);
            } else  t.label(() -> "@settings.olupis-music-hint").top().center().padBottom(5f).row();

            subTable.checkPref("olupis-space-sfx",true);
            subTable.checkPref("olupis-music",true);
            subTable.checkPref("olupis-music-only",false);
            subTable.checkPref("olupis-music-add",true);
            subTable.checkPref("olupis-music-custom-game",true);
            t.add(subTable);
        }, () ->shown[0]).growX().center().row();
    }

    public static void AddNyfalisSoundSettings(){
        BuildNyfalisSoundSettings(Vars.ui.settings.sound, true);
    }
}
