package olupis.input;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.editor.WaveInfoDialog;
import mindustry.game.Rules;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.CustomRulesDialog;
import olupis.content.NyfalisPlanets;

import java.util.Objects;

import static mindustry.Vars.*;

public class NyfalisStartUpUis {
    public static Table debugTable = new Table();

    public static void  disclaimerDialog(){
        BaseDialog dialog = new BaseDialog("@nyfalis-disclaimer.name");
        dialog.centerWindow();

        dialog.cont.setOrigin(Align.center);
        dialog.cont.table(t -> {
            t.defaults().growY().growX().center();

            Label header = new Label("@nyfalis-disclaimer.header");
            Label body = new Label("@nyfalis-disclaimer.body");
            header.setAlignment(Align.center);
            header.setWrap(true);
            body.setWrap(true);
            body.setAlignment(Align.center);

            t.add(header).row();
            /*Very convoluted way to load the mod icon, because I'm not bright to think of any other way*/
            TextureRegion icon = new TextureRegion(mods.list().find(a -> Objects.equals(a.name, "olupis")).iconTexture);
            t.table(a -> a.image(icon).scaling(Scaling.bounded).row()).tooltip("Art By RushieWashie").maxSize(700).margin(14).pad(3).center().row();

            t.add(body).row();


        }).growX().growY().center().top().row();

        dialog.cont.button("@back", Icon.left, dialog::hide).padTop(-1f).size(220f, 55f).bottom();
        dialog.closeOnBack();
        dialog.show();
    }


    public static void  saveDisclaimerDialog(){
        BaseDialog dialog = new BaseDialog("@nyfalis-disclaimer.name");
        dialog.centerWindow();

        dialog.cont.setOrigin(Align.center);
        dialog.cont.table(t -> {
            t.defaults().growY().growX().center();

            Label body = new Label("@nyfalis-disclaimer.save");
            body.setWrap(true);
            body.setAlignment(Align.center);
            t.add(body).row();


        }).growX().growY().center().top().row();

        dialog.cont.button("@back", Icon.left, () -> {
            dialog.hide();
            Core.settings.put("nyf-lastver", 1); //TODO IMPLEMENT
        }).padTop(-1f).size(220f, 55f).bottom();
        dialog.closeOnBack();
        dialog.show();
    }


    public static void buildDebugUI(Group group){

        group.fill(t -> {
            t.name = "nyfalis-debug-cont";
            t.visible(() -> Vars.ui.hudfrag.shown);
            t.bottom().left();
            rebuildDebugTable();
            t.table(tab -> tab.add(debugTable)).row();
        });
    }

    public static void rebuildDebugTable(){
        debugTable.reset();
        debugTable.clear();

        CustomRulesDialog ruleInfo = new CustomRulesDialog();
        WaveInfoDialog waveInfo = new WaveInfoDialog();

        debugTable.button("Export w/ Nyf", Icon.file, Styles.squareTogglet, () -> {
            state.rules.blockWhitelist = true;
            NyfalisPlanets.nyfalis.applyRules(state.rules);
            ui.paused.show();
        }).width(155f).height(40f).margin(12f).checked(false).row();
        debugTable.button("@editor.rules", Icon.list, Styles.squareTogglet, ()->{
            ruleInfo.show(Vars.state.rules, () -> Vars.state.rules = new Rules());
        }).width(155f).height(40f).margin(12f).checked(false).row();
        debugTable.button("@editor.waves", Icon.waves, Styles.squareTogglet, waveInfo::show).width(155f).height(40f).margin(12f).checked(false);
        if(mobile || testMobile){
            debugTable.row();
            debugTable.add(new Element()).width(155f).height(50f).margin(12f).touchable( Touchable.disabled);
        }
    }


}
