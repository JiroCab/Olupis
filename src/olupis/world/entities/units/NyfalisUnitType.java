package olupis.world.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import olupis.content.NyfalisItemsLiquid;
import olupis.world.ai.NyfalisCommand;
import olupis.world.ai.NyfalisMiningAi;
import olupis.world.entities.bullets.SpawnHelperBulletType;

import static mindustry.Vars.ui;

public class NyfalisUnitType extends UnitType {
    public boolean canCircleTarget = false;
    /*Effects that a unit spawns with, gnat cheese fix*/
    public StatusEffect spawnStatus = StatusEffects.none;
    public float spawnStatusDuration = 60f * 5f;
    public Seq<UnlockableContent> displayFactory = new Seq();

    public NyfalisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("371404");
        ammoType = new ItemAmmoType(NyfalisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
    }

    @Override
    public void init(){
        super.init();
        if(canCircleTarget){
            Seq<UnitCommand> cmds = Seq.with(commands);
            cmds.add(NyfalisCommand.circleCommand);
            commands = cmds.toArray();
        }
    }

    @Override
    public void display(Unit unit, Table table){
        super.display(unit, table);
        if(unit.controller() instanceof NyfalisMiningAi ai && ai.targetItem != null) table.table(t -> {
            table.row();
            t.table(i ->  {
                i.image(ai.ore != null ? ai.ore.overlay().fullIcon : ai.targetItem.fullIcon).scaling(Scaling.bounded).left().pad(5);
                i.add(ai.targetItem.localizedName).pad(5).wrap().center();
            }).left();
            if (ai.ore != null && (Core.settings.getBool("mouseposition") || Core.settings.getBool("position"))) {
                t.row();
                t.add("[lightgray](" + Math.round(ai.ore.x) + ", " + Math.round(ai.ore.y) + ")").growX().wrap().center();
            }
        }).grow();
    }

    @Override
    public void setStats(){
        super.setStats();

        /*We have a weird tech tree, this just makes it easier for the player's end*/
        if(displayFactory.size >= 1){
            stats.add(Stat.input, table -> displayFactory.each(fac -> {
                table.row();
                table.table(Styles.grayPanel, t -> {
                    boolean show = (fac instanceof Block b && b.isVisible()) || (fac instanceof  UnitType u && !u.isBanned());
                    if(!fac.unlocked() && (Vars.state.isCampaign() || !Vars.state.isPlaying())) t.image(Icon.lock.getRegion()).tooltip(fac.localizedName).size(25).pad(10f).left().scaling(Scaling.fit);
                    else {
                        if(show) t.image(fac.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        else t.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);
                        t.table(info -> {
                            info.add(fac.localizedName).left();
                            if (Core.settings.getBool("console")) {
                                info.row();
                                info.add(fac.name).left().color(Color.lightGray);
                            }
                        });
                        t.button("?", Styles.flatBordert, () -> ui.content.show(fac)).size(40f).pad(10).right().grow().visible(fac::unlockedNow);
                    }
                }).growX().pad(5).row();
            }));
        }


        if(weapons.any()){
            stats.remove(Stat.weapons);
            stats.add(Stat.weapons, table -> {
                for(Weapon w : weapons) {
                    if(!w.hasStats(this) || !w.flipSprite) continue;

                    if (w.bullet instanceof SpawnHelperBulletType) {
                        UnitType spawn = w.bullet.spawnUnit;
                        table.row();
                        table.table(Styles.grayPanel, t -> {
                            boolean show = !spawn.isBanned();
                            if (!spawn.unlocked() && (Vars.state.isCampaign() || !Vars.state.isPlaying()))
                                t.image(Icon.lock.getRegion()).tooltip(spawn.localizedName).size(25).pad(10f).left().scaling(Scaling.fit);
                            else {
                                if (show) t.image(spawn.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                                else
                                    t.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);
                                t.table(info -> {
                                    info.add(spawn.localizedName).left();
                                    if (Core.settings.getBool("console")) {
                                        info.row();
                                        info.add(spawn.name).left().color(Color.lightGray);
                                    }
                                });
                                t.button("?", Styles.flatBordert, () -> ui.content.show(spawn)).size(40f).pad(10).right().grow().visible(spawn::unlockedNow);
                            }
                        }).growX().pad(5).row();
                    } else {
                        table.row();
                        TextureRegion region = !w.name.isEmpty() ? Core.atlas.find(w.name + "-preview", w.region) : null;
                        table.table(Styles.grayPanel, wt -> {
                            wt.left().top().defaults().padRight(3).left();
                            if (region != null && region.found() && w.showStatSprite)
                                wt.image(region).size(60).scaling(Scaling.bounded).left().top();
                            wt.row();
                            w.addStats(this, wt);
                        }).growX().pad(5).margin(10);
                        table.row();
                    }
                }
            });
        }

    }

    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        unit.ammo = ammoCapacity; //fill up on ammo upon creation
        unit.elevation = flying ? 1f : 0;
        unit.heal();
        if(unit instanceof TimedKillc u){
            u.lifetime(lifetime);
        }
        unit.apply(spawnStatus, 200f);
        return unit;
    }
}
