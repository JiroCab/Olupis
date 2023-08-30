package olupis.world.entities.units;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.ai.types.LogicAI;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.meta.Env;
import olupis.world.ai.NyfalisMiningAi;

import static mindustry.Vars.*;

/*Unit that dies when it runs out of ammo, ammo Depletes over time*/
public class AmmoLifeTimeUnitType extends  NyfalisUnitType {
    /*Custom logic to remove ammo over time*/
    public  boolean ammoDepletesOverTime = true;
    /*Custom logic to kill unit on no ammo*/
    public  boolean killOnAmmoDepletes = true;
    /*Amount to deplete per tick*/
    public float ammoDepleteAmount = 0.2f;
    public float ammoDepleteAmountPassive = ammoDepleteAmount;
    /*Ammo amount that will trigger death*/
    public float minimumAmmoBeforeKill = 0.1f;
    /*mining depletes ammo*/
    public boolean miningDepletesAmmo = false;
    /*Time before depleting ammo*/
    public float depleteAmmoOffset = 10f;
    /*Manually controlling and moving items depletes ammo*/
    public boolean carryingMaxDepletes = true, maxCarryUsesPassive = false;
    float startTime;
    /*Time out params */
    public Sound timedOutSound = Sounds.explosion;
    public Effect timedOutFx = Fx.steam;
    public float timedOutSoundPitch = 1f, timedOutSoundVolume = 0.4f;

    //TODO: Range limit them, deplete ammo when N tiles away from X & Y

    public AmmoLifeTimeUnitType(String name){
        /*let's just hope that ammo is never removed at least not removed internally */
        super(name);
        envDisabled = Env.none;
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

            bars.add(new Bar("stat.health", Pal.health, unit::healthf).blink(Color.white));
            bars.row();

            if(state.rules.unitAmmo || killOnAmmoDepletes ){
                bars.add(new Bar(ammoType.icon() + " " + Core.bundle.get("stat.ammo"), ammoType.barColor(), () -> (unit.ammo - minimumAmmoBeforeKill ) / (ammoCapacity - minimumAmmoBeforeKill) ));
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

        if(unit.controller() instanceof NyfalisMiningAi ai && ai.targetItem != null){
            table.row();
            table.table(i -> {
                TextureRegion ore = ai.mineType == 1 ? ai.ore.floor().fullIcon : ai.mineType == 2 ? ai.ore.block().fullIcon: ai.mineType == 3 ? ai.ore.overlay().fullIcon: Icon.ok.getRegion();
                i.image(ore);
                table.add(ai.targetItem.localizedName).wrap();
            });

            //if (ai.ore != null && (Core.settings.getBool("mouseposition") || Core.settings.getBool("position"))) {
            if (ai.ore != null && (Core.settings.getBool("blockstatus"))) {
                table.row();
                table.add("[lightgray](" + Math.round(ai.ore.x) + ", " + Math.round(ai.ore.y) + ")").growX().wrap();
            }
        }
        table.row();
    }


    @Override
    public void update(Unit unit){
        if (unit.ammo <= minimumAmmoBeforeKill && killOnAmmoDepletes){
            timedOut(unit);
        }

        boolean shouldDeplete = (startTime+ depleteAmmoOffset) >= startTime;
        if(ammoDepletesOverTime && shouldDeplete){
            unit.ammo = unit.ammo - (maxCarryUsesPassive ? ammoDepleteAmountPassive : ammoDepleteAmount);
        }

        if(miningDepletesAmmo && unit.mining()){
            unit.ammo = unit.ammo - ammoDepleteAmount;
        }

        if(unit.stack.amount == unit.itemCapacity() && carryingMaxDepletes && unit.ammo >= minimumAmmoBeforeKill +0.05f ){
            unit.ammo = unit.ammo - ammoDepleteAmount;
        }
    }

    public void timedOut(Unit unit){
        timedOutFx.at(unit);
        timedOutSound.at(unit.x, unit.y, timedOutSoundPitch, timedOutSoundVolume);
        unit.remove();
    }
}
