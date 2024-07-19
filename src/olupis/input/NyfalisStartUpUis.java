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
import mindustry.core.Logic;
import mindustry.editor.WaveInfoDialog;
import mindustry.game.Rules;
import mindustry.gen.Icon;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.CustomRulesDialog;
import olupis.NyfalisMain;
import olupis.content.NyfalisPlanets;
import olupis.content.NyfalisSectors;
import olupis.world.EnvUpdater;

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
            body.sizeBy(10f);
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
        //This is here bc in the beta versions, placeholder was int (`1`) and would crash with the new check
        float lVer = Float.parseFloat(Core.settings.get("nyf-lastver", 0).toString());
        boolean hasSave = false;
        for (Planet p : content.planets()) {
            if (!p.name.contains("olupis-")) continue;
            if(hasSave) break;
            for (Sector s : p.sectors) {
                if(s.hasSave()) hasSave = true;
                break;
            }
        }
        if (hasSave && !(lVer < NyfalisSectors.sectorVersion)) return;
        showSaveDisclaimerDialog();
    }

    public static void showSaveDisclaimerDialog(){
        float lVer = Float.parseFloat(Core.settings.get("nyf-lastver", 0).toString());
        BaseDialog dialog = new BaseDialog("@nyfalis-disclaimer.name");
        dialog.centerWindow();

        dialog.cont.setOrigin(Align.center);
        dialog.cont.table(t -> {
            t.defaults().growY().growX().center();

            Label body = new Label(Core.bundle.format("nyfalis-disclaimer.save", lVer, NyfalisSectors.sectorVersion));
            body.setWrap(true);
            body.setAlignment(Align.center);
            t.add(body).row();


        }).growX().growY().center().top().row();

        dialog.cont.button("@back", Icon.left, () -> {
            dialog.hide();
            Core.settings.put("nyf-lastver", NyfalisSectors.sectorVersion);
        }).padTop(-1f).size(220f, 55f).bottom();
        dialog.closeOnBack( () -> {
            Core.settings.put("nyf-lastver", NyfalisSectors.sectorVersion);
        });
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

        debugTable.table( z -> {
            z.button("E", Icon.export, Styles.squareTogglet, () -> {
                state.rules.blockWhitelist = true;
                NyfalisPlanets.nyfalis.applyRules(state.rules);
                NyfalisMain.sandBoxCheck();
                ui.paused.show();
            }).width(77.5f).height(40f).checked(false).tooltip("Apply Nyfalis Settings/Env to current game");
            z.button("C", Icon.down, Styles.squareTogglet, () -> {
                if(state.isCampaign()) Logic.sectorCapture();
                state.wave += 100;
                for (Item i : content.items())
                    if (i.unlocked()) player.team().core().items.set(i, player.team().core().storageCapacity);
            }).width(77.5f).height(40f).checked(false).tooltip("Capture Sector & fill core with Items");
            z.row();
            z.button("Ua", Icon.modeAttack, Styles.squareTogglet, EnvUpdater::debugUpdateActive).width(77.5f).height(40f).checked(false).tooltip("Update Spreading Moss");
        }).width(155f).growY().margin(12f).checked(false).row();
        debugTable.button("@editor.rules", Icon.list, Styles.squareTogglet, ()->{
            ruleInfo.show(Vars.state.rules, () -> Vars.state.rules = new Rules());
        }).width(155f).height(40f).margin(12f).checked(false).row();
        debugTable.button("@editor.waves", Icon.waves, Styles.squareTogglet, waveInfo::show).width(155f).height(40f).margin(12f).checked(false);
        if(mobile || testMobile){
            debugTable.row();
            debugTable.add(new Element()).width(155f).height(50f).margin(12f).touchable( Touchable.disabled);
        }
        debugTable.marginBottom(200f);
    }


}
