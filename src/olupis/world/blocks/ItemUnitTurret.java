package olupis.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.*;
import mindustry.world.meta.Stat;
import olupis.content.NyfalisUnits;

import static mindustry.Vars.ui;

public class ItemUnitTurret extends ItemTurret {
    /*Turret with fancy units as stats display*/
    public Seq<UnitType> displayUnits = Seq.with(NyfalisUnits.gnat);
    public ItemUnitTurret(String name){
        super(name);

        if(Core.settings.getBool("olupis-debug")){
            drawer = new DrawMulti(new DrawTurret("iron-"));
        }else drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRegion("")
        );

    }

    @Override
    public void setStats(){
        super.setStats();
        if(displayUnits == null) return;
        stats.remove(Stat.ammo);

        stats.add(Stat.unitType, table -> displayUnits.each(displayUnit -> {
            table.row();
            table.table(Styles.grayPanel, b -> {
                if(!displayUnit.isBanned()) b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                else b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                b.table(info -> {
                    info.add(displayUnit.localizedName).left();
                    if (Core.settings.getBool("console")) {
                        info.row();
                        info.add(displayUnit.name).left().color(Color.lightGray);
                    }
                });
                b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
            }).growX().pad(5).row();
        }));
    }
    public class ItemUnitTurretBuild extends ItemTurretBuild{

    }
}
