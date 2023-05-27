package olupis.input;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;

import static mindustry.Vars.*;

public class OlupisSettingsDialog {

    public OlupisSettingsDialog() {
        BuildDialog();
    }

    public void BuildDialog(){
        ui.settings.addCategory("@category.olupis.name", Icon.effect, table -> {
            table.checkPref("olupis-green-icon", true);
            table.checkPref("olupis-green-name", true);
            table.checkPref("olupis-debug", false);

            BuildOlupisSoundSettings(table, false);
        });
    }

    public static void BuildOlupisSoundSettings(Table table, Boolean hide){
        if(Core.settings.getBool("olupis-hide-sound") && hide)return;

        boolean[] shown = {false};
        table.row();
        table.button("@setting.olupis-sound-category", Icon.effect, Styles.togglet, () -> shown[0] = !shown[0]).marginLeft(14f).growX().height(60f).checked(a -> shown[0]).padTop(5f).row();

        table.collapser(t -> {
            SettingsMenuDialog.SettingsTable subTable = new SettingsMenuDialog.SettingsTable();
            subTable.checkPref("olupis-space-sfx",true);
            subTable.checkPref("olupis-music-only",false);
            subTable.checkPref("olupis-music",true);
            if (!hide){
                subTable.checkPref("olupis-hide-sound",true);
            }
            t.add(subTable);
        }, () ->shown[0]).growX().row();
    }

    public static void AddOlupisSoundSettings(){
        BuildOlupisSoundSettings(Vars.ui.settings.sound, true);
    }
}
