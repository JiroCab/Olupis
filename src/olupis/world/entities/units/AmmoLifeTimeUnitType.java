package olupis.world.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.ai.types.LogicAI;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

/*Unit that dies when it runs out of ammo, ammo Depletes over time*/
public class AmmoLifeTimeUnitType extends  NyfalisUnitType {
    /*Custom logic to remove ammo over time*/
    public  boolean ammoDepletesOverTime = true;
    /*Custom logic to kill unit on no ammo*/
    public  boolean killOnAmmoDepletes = true;
    /*Amount to deplete per tick*/
    public float ammoDepleteAmount = 0.2f;
    /*Ammo amount that will trigger death*/
    public float minimumAmmoBeforeKill = 0.1f;
    /*mining depletes ammo*/
    public boolean miningDepletesAmmo = false;
    /*Time before depleting ammo*/
    public float depleteAmmoOffset = 10f;
    float startTime;
    /*Block its created from displayed*/
    public Seq<Block> displayedBlocks = new Seq<>();
    //TODO: Range limit them

    public AmmoLifeTimeUnitType(String name){
        /*let's just hope that ammo is never removed at least not removed internally */
        super(name);
        envDisabled = Env.none;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.tiles, table -> displayedBlocks.each(d -> {
            if(d == null)return;
            table.row();
            table.table(Styles.grayPanel, b -> {
                if(d.canBeBuilt()) b.image(d.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                else b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                b.table(info -> {
                    info.add(d.localizedName).left();
                    if (Core.settings.getBool("console")) {
                        info.row();
                        info.add(d.name).left().color(Color.lightGray);
                    }
                });
                b.button("?", Styles.flatBordert, () -> ui.content.show(d)).size(40f).pad(10).right().grow().visible(d::unlockedNow);
            }).growX().pad(5).row();
        }));
    }

    @Override
    public void display(Unit unit, Table table){
        table.table(t -> {
            t.left();
            t.add(new Image(uiIcon)).size(iconMed).scaling(Scaling.fit);
            t.labelWrap(localizedName).left().width(190f).padLeft(5);
        }).growX().left();
        table.row();

        table.table(bars -> {
            bars.defaults().growX().height(20f).pad(4);

            //TODO overlay shields
            bars.add(new Bar("stat.health", Pal.health, unit::healthf).blink(Color.white));
            bars.row();

            if(state.rules.unitAmmo || killOnAmmoDepletes ){
                bars.add(new Bar(ammoType.icon() + " " + Core.bundle.get("stat.ammo"), ammoType.barColor(), () -> (unit.ammo  / ammoCapacity) - minimumAmmoBeforeKill));
                bars.row();
            }

            for(Ability ability : unit.abilities){
                ability.displayBars(unit, bars);
            }

            if(payloadCapacity > 0 && unit instanceof Payloadc payload){
                bars.add(new Bar("stat.payloadcapacity", Pal.items, () -> payload.payloadUsed() / unit.type().payloadCapacity));
                bars.row();

                var count = new float[]{-1};
                bars.table().update(t -> {
                    if(count[0] != payload.payloadUsed()){
                        payload.contentInfo(t, 8 * 2, 270);
                        count[0] = payload.payloadUsed();
                    }
                }).growX().left().height(0f).pad(0f);
            }
        }).growX();

        if(unit.controller() instanceof LogicAI ai){
            table.row();
            table.add(Blocks.microProcessor.emoji() + " " + Core.bundle.get("units.processorcontrol")).growX().wrap().left();
            if(ai.controller != null && (Core.settings.getBool("mouseposition") || Core.settings.getBool("position"))){
                table.row();
                table.add("[lightgray](" + ai.controller.tileX() + ", " + ai.controller.tileY() + ")").growX().wrap().left();
            }
            table.row();
            table.label(() -> Iconc.settings + " " + (long)unit.flag).color(Color.lightGray).growX().wrap().left();
            if(net.active() && ai.controller != null && ai.controller.lastAccessed != null){
                table.row();
                table.add(Core.bundle.format("lastaccessed", ai.controller.lastAccessed)).growX().wrap().left();
            }
        }else if(net.active() && unit.lastCommanded != null){
            table.row();
            table.add(Core.bundle.format("lastcommanded", unit.lastCommanded)).growX().wrap().left();
        }

        table.row();
    }


    @Override
    public void update(Unit unit){
        if (unit.ammo <= minimumAmmoBeforeKill && killOnAmmoDepletes){
            deathSFX(unit);
            unit.remove();
        }

        boolean shouldDeplete = (startTime+ depleteAmmoOffset) >= startTime;
        if(ammoDepletesOverTime && shouldDeplete){
            unit.ammo = unit.ammo - ammoDepleteAmount;
        }

        if(miningDepletesAmmo && unit.mining()){
            unit.ammo = unit.ammo - ammoDepleteAmount;
        }
    }

    public void deathSFX(Unit unit){
        Fx.drillSteam.at(unit);
        Fx.steam.at(unit);
        Sounds.explosion.at(unit);
    }
}
