package olupis.input.ui;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.core.Logic;
import mindustry.core.Version;
import mindustry.editor.WaveInfoDialog;
import mindustry.game.Rules;
import mindustry.gen.Icon;
import mindustry.input.Binding;
import mindustry.input.DesktopInput;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.CustomRulesDialog;
import mindustry.ui.fragments.HintsFragment;
import olupis.NyfalisMain;
import olupis.content.*;
import olupis.world.EnvUpdater;
import olupis.world.entities.packets.NyfalisDebugPackets;

import java.util.Objects;

import static mindustry.Vars.*;

public class NyfalisStartUpUis {
    public static Table debugTable = new Table();
    public boolean forceShowFunny = false;

    public static void  disclaimerDialog(){
        BaseDialog dialog = new BaseDialog("@nyfalis-disclaimer.name");
        dialog.centerWindow();

        dialog.cont.setOrigin(Align.center);
        dialog.cont.table(t -> {
            t.defaults().growY().growX().center();

            Label header = new Label("@nyfalis-disclaimer.header");
            Label body = new Label("@nyfalis-disclaimer.body");
            Label funny = new Label("@nyfalis-disclaimer.funny");
            header.setAlignment(Align.center);
            header.setWrap(true);
            body.setWrap(true);
            body.sizeBy(10f);
            body.setAlignment(Align.center);

            t.add(header).row();


            boolean foos = Structs.contains(Version.class.getDeclaredFields(), var -> var.getName().equals("foos"));
            //Crash on foo's with how the icon is loaded so this a temp fix that will be here till the end of time

            if( foos || Mathf.random(1, 200) == 1 || (Core.input.keyDown(KeyCode.altLeft) && Core.input.keyDown(KeyCode.shiftLeft))){
                TextureRegion icon = NyfalisUnits.gnat.uiIcon;
                t.table(a ->{
                    a.image(icon).scaling(Scaling.bounded).row();
                    a.add(funny).center().growX().row();
                }).maxSize(700).margin(14).pad(3).center().row();
            } else {
                /*Very convoluted way to load the mod icon, because I'm not bright to think of any other way*/
                @Nullable TextureRegion icon = new TextureRegion(mods.list().find(a -> Objects.equals(a.name, "olupis")).iconTexture);
                t.table(a -> a.image(icon).scaling(Scaling.bounded).row()).tooltip("Art By RushieWashie").maxSize(700).margin(14).pad(3).center().row();
            }


            t.add(body).row();


        }).growX().growY().center().top().row();

        dialog.cont.button("@back", Icon.left, dialog::hide).padTop(-1f).size(220f, 55f).bottom();
        dialog.closeOnBack();
        dialog.show();
    }

    public static void  saveDisclaimerDialog(){
        //This is here bc in the beta versions, placeholder was int (`1`) and would crash with the new check
        @Nullable float lVer = Float.parseFloat(Core.settings.get("nyf-lastver", 0).toString());
        if((lVer >= NyfalisSectors.sectorVersion)){
            Log.info("Nyf data: @ >= @ ", lVer, NyfalisSectors.sectorVersion);
            return;
        }
        if(lVer == 0){
            Log.info("no nyf data detected! (0)");
            return;
        }

        boolean hasSave = false;
        for (Planet p : content.planets()) {
            if (!p.name.contains("olupis-")) continue;
            if(hasSave) break;
            for (Sector s : p.sectors) {
                if(s.hasSave()) hasSave = true;
                break;
            }
        }
        if(!hasSave)return;
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
                if(player.admin && net.active() && net.client()){
                    NyfalisDebugPackets packet = new NyfalisDebugPackets();
                    packet.type = 1;
                    Vars.net.send(packet, true);
                } else {
                    state.rules.blockWhitelist = true;
                    NyfalisPlanets.nyfalis.applyRules(state.rules);
                    NyfalisMain.sandBoxCheck();
                    ui.paused.show();
                }
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

    public static void loadHints(){
        ui.hints.hints.addAll(new HintsFragment.Hint() {
            final Seq<UnitType> coreUnits = Seq.with(NyfalisUnits.gnat, NyfalisUnits.pedicia, NyfalisUnits.phorid) ;
            @Override
            public String name() {return "hint.nyflais-command";}

            @Override
            public String text() {return Core.bundle.get("hint.nyflais-command.text");}

            @Override
            public boolean complete() {return Core.keybinds.get(Binding.command_mode).key != Core.keybinds.get(Binding.boost).key;}

            @Override
            public boolean show() {return Core.keybinds.get(Binding.command_mode).key == Core.keybinds.get(Binding.boost).key;}

            @Override
            public int order() {return 5;}

            @Override
            public boolean valid() {return !Vars.mobile ||  control.input instanceof DesktopInput;}
        },new HintsFragment.Hint() {
            @Override
            public String name() {return "hint.nyflais-end-of-content";}

            @Override
            public String text() {return Core.bundle.get("hint.nyflais-end-of-content.text");}

            @Override
            public boolean complete() {return state.isCampaign() && state.getSector().preset == NyfalisSectors.conservatorium && state.getSector().isCaptured();}

            @Override
            public boolean show() {return state.isCampaign() && state.getSector().preset == NyfalisSectors.conservatorium;}

            @Override
            public int order() {return 6;}

            @Override
            public boolean valid() {return true;}
        }, new HintsFragment.Hint() {
            @Override
            public String name() {return "hint.nyflais-optional-sectors";}

            @Override
            public String text() {return Core.bundle.get("hint.nyflais-optional-sectors.text");}

            @Override
            public boolean complete() {return state.isCampaign() && NyfalisPlanets.isNyfalianPlanet(state.getPlanet()) && state.getSector().preset == null && state.getSector().isCaptured() ;}

            @Override
            public boolean show() {return state.isCampaign() &&  NyfalisPlanets.isNyfalianPlanet(state.getPlanet()) && state.getSector().preset == null;}

            @Override
            public int order() {return 7;}

            @Override
            public boolean valid() {return true;}
        });
    }
}
