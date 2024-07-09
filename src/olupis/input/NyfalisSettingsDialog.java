package olupis.input;

import arc.Core;
import arc.graphics.Color;
import arc.input.KeyCode;
import arc.scene.ui.Dialog;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Saves;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;
import olupis.NyfalisMain;
import olupis.content.NyfalisPlanets;

import static mindustry.Vars.*;

public class NyfalisSettingsDialog {
    public static String nyfalisDiscordInvite = "https://discord.gg/K5YX2ECjv7";
    public NyfalisSettingsDialog() {
        if(!headless) BuildDialog();
    }
    public boolean musicModPresent = false;

    public void BuildDialog(){
        musicModPresent =  mods.locateMod("nyfalis-music") != null;

        ui.settings.addCategory("@category.nyfalis.name", Icon.effect, table -> {
            table.checkPref("nyfalis-bread-gun", false);
            table.checkPref("nyfalis-rainbow-music", false);
            table.checkPref("nyfalis-green-icon", true);
            table.checkPref("nyfalis-green-name", true);
            /* uncomment when name/icon is final
               table.checkPref("nyfalis-blue-icon", true);
               table.checkPref("nyfalis-blue-name", true); */
            table.checkPref("nyfalis-cloud-shadows", true);
            table.checkPref("nyfalis-auto-ban", true);
            table.checkPref("nyfalis-disclaimer", true);
            table.checkPref("nyfalis-debug", false);

            table.row();

            if(musicModPresent)BuildNyfalisSoundSettings(table, false);
            else table.checkPref("nyfalis-space-sfx", false);
            table.row();

            table.pref(new CollapserSetting("data-buttons", 1));
            table.pref(new CollapserSetting("disclaimer-button", 2));
            table.pref(new CollapserSetting("discord-button"));

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

            subTable.checkPref("nyfalis-space-sfx",false);
            subTable.checkPref("nyfalis-music",true);
            subTable.checkPref("nyfalis-music-only",false);
            subTable.checkPref("nyfalis-music-add",true, c -> Core.settings.put("nyfalis-replacemusic", false));
            subTable.checkPref("nyfalis-music-custom-game",true);
            t.add(subTable);
        }, () ->shown[0]).growX().center().row();
    }

    public static void AddNyfalisSoundSettings(){
        BuildNyfalisSoundSettings(Vars.ui.settings.sound, true);
    }

    public static void nyfalisDiscordDialog(){
        Dialog dialog = new Dialog("");

        float h = 70f;
        dialog.cont.margin(12f);
        Color color = Color.valueOf("3ED09A");

        dialog.cont.table(t -> {
            t.background(Tex.button).margin(0);

            t.table(img -> {
                img.image().height(h - 5).width(40f).color(color);
                img.row();
                img.image().height(5).width(40f).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
            }).expandY();

            t.table(i -> {
                i.image(Icon.discord);
            }).size(h).left();

            t.add("@nyfalis-discord-long").color(Pal.accent).growX().padLeft(10f);

            Table c = new Table();
            c.add(nyfalisDiscordInvite).growX();

        }).size(520f, h).pad(10f);

        dialog.buttons.defaults().size(170f, 50);

        dialog.buttons.button("@back", Icon.left, dialog::hide);
        dialog.buttons.button("@copylink", Icon.copy, () -> {
            Core.app.setClipboardText(nyfalisDiscordInvite);
            ui.showInfoFade("@copied");
        });
        dialog.buttons.button("@openlink", Icon.discord, () -> {
            if(!Core.app.openURI(nyfalisDiscordInvite)){
                ui.showErrorMessage("@linkfail");
                Core.app.setClipboardText(nyfalisDiscordInvite);
            }
        });
        dialog.closeOnBack();
        dialog.show();
    }


    //Foo's workaround
    public static class CollapserSetting extends SettingsMenuDialog.SettingsTable.Setting{
        int type = 0;
        public CollapserSetting (String name){
            super(name);
        }
        public CollapserSetting (String name, int type){
            super(name);
            this.type = type;
        }

        public void add(SettingsMenuDialog.SettingsTable table) {
            if(type == 1) addDiscordButton(table);
            else if(type == 2) addDisclaimerButton(table);
            else addDataButtons(table);

        }

        public void addDiscordButton(SettingsMenuDialog.SettingsTable table) {
            table.button("@nyfalis-discord", Icon.discord, NyfalisSettingsDialog::nyfalisDiscordDialog).margin(14).width(260f).pad(6).row();
        }

        public void addDataButtons(SettingsMenuDialog.SettingsTable table) {
            boolean[] showData = {false, false};
            table.button("@setting.nyfalis-data-category", Icon.trash, Styles.togglet, () ->{
                showData[0] = !showData[0];
                if(Core.input.keyDown(Binding.boost) && Core.input.keyDown(KeyCode.altLeft)) showData[1] = !showData[1];
            }).margin(14f).padLeft(5f).padRight(5f).growX().height(60f).checked(a -> showData[0]).pad(5f).center().row();
            table.collapser(t -> {
                Table subTable = new Table();
                subTable.button("@setting.nyfalis-resetsector.name", Icon.trash, () -> {
                    ui.showConfirm("@confirm", "@setting.nyfalis-resetsector.confirm", () -> {
                        for (Planet p : content.planets()) {
                            if (!p.name.contains("olupis-")) continue;
                            for (Sector s : p.sectors) {
                                s.clearInfo();
                                if (s.save == null) continue;
                                s.save.delete();
                                s.save = null;
                            }
                        }

                        for (Saves.SaveSlot s : control.saves.getSaveSlots()) {
                            if (s.isSector() && NyfalisPlanets.isNyfalianPlanet(s.getSector().planet)) s.delete();
                        }
                    });
                }).margin(14).width(260f).pad(6);
                subTable.button("@setting.nyfalis-resetresearch.name", Icon.trash, () -> {
                    ui.showConfirm("@confirm", "@setting.nyfalis-resetresearch.confirm", () -> {
                        content.each(c -> {
                            if(c instanceof UnlockableContent u && u.name.contains("olupis-")){
                                u.clearUnlock();
                            }
                        });
                        for(Planet planet : NyfalisPlanets.planetList ){
                            planet.techTree.reset();
                            planet.techTree.each(TechTree.TechNode::reset);
                        }
                    });
                }).margin(14).width(260f).pad(6);
                t.add(subTable);
            }, true, () -> showData[0]).growX().center().row();

            table.collapser(t -> {
                Table subTable = new Table();
                subTable.button("sandbox check", () -> {
                    NyfalisMain.sandBoxCheck(false);
                }).margin(14).width(260f).pad(6);
                subTable.button("sector turn", NyfalisMain::sectorPostTurn).margin(14).width(260f).pad(6);
                subTable.row();
                subTable.button("test save disclaimer", () -> {
                    Core.settings.put("nyf-lastver", 0.1f);
                    Sounds.respawn.play();
                    float lVer = Float.parseFloat(Core.settings.get("nyf-lastver", 0).toString());
                    Log.info("" + lVer);
                }).margin(14).width(260f).pad(6);
                subTable.button("Save Disclaimer", NyfalisStartUpUis::showSaveDisclaimerDialog).margin(14).width(260f).pad(6);
                subTable.row();
                subTable.button("Repair save", () -> {
                    if(state.isGame()){
                        Log.err("fix?");
                        NyfalisMain.sandBoxCheck(false);
                        for (Building b : Groups.build) {
                            if (!b.enabled && b.lastDisabler == null && b.block.supportsEnv(state.rules.env)) {
                                b.enabled = true;
                            }
                        }
                    }
                }).tooltip("may not always work").margin(14).width(260f).pad(6);
                subTable.row();

                t.add(subTable);
            }, true, () -> showData[1]).growX().center().row();
            if(Core.settings.getBool("nyfalis-debug")){
                table.row();
                table.button("extra", () -> showData[1] = !showData[1]).margin(14).width(260f).pad(6);
                table.row();
            }
        }

        public void addDisclaimerButton(SettingsMenuDialog.SettingsTable table){
            table.button("@nyfalis-disclaimer.name", Icon.chat, NyfalisStartUpUis::disclaimerDialog).margin(14).width(260f).pad(6).row();
        }
    }


}
