package olupis.world.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.ai.UnitCommand;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import olupis.content.NyfalisItemsLiquid;
import olupis.world.ai.NyfalisCommand;

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
    public void setStats(){
        super.setStats();

        if(displayFactory.size >= 0){
            stats.add(Stat.input, table -> displayFactory.each(fac -> {
                table.row();
                table.table(Styles.grayPanel, t -> {
                    boolean show = (fac instanceof Block b && b.isVisible()) || (fac instanceof  UnitType u && !u.isBanned());

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
                }).growX().pad(5).row();
            }));
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
