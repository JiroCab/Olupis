package olupis.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import arc.util.Scaling;
import arc.util.io.Reads;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.meta.Stat;

import static mindustry.Vars.ui;

/*Todo: Parity/extend with ItemUnitTurret*/
public class PowerUnitTurret extends ItemUnitTurret {
    /*Weapon to use when there's no modifier item*/
    public BulletType shootType;

    //TODO SAVES/MAPS BROKEN
    public PowerUnitTurret(String name){
        super(name);
        hasPower = true;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.output);
        stats.add(Stat.output, table ->{
            if(shootType != null){
                table.row();
                table.table(Styles.grayPanel, b -> {
                    UnitType displayUnit = shootType.spawnUnit;
                    if(!displayUnit.isBanned()) b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                    else b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                    b.table(info -> {
                        info.add(displayUnit.localizedName).left().row();
                        if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                    });
                    b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
            }).growX().pad(5).row();}
            this.ammoTypes.each((item, bul) -> {
                UnitType displayUnit = bul.spawnUnit;
                if(displayUnit == null) return;
                table.row();
                table.table(Styles.grayPanel, b -> {
                    if(!displayUnit.isBanned()) b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                    else b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                    b.table(info -> {
                        if(item != null) info.table(title -> {
                            title.image(item.fullIcon).size(3 * 8).left().scaling(Scaling.fit).top();
                            title.add(item.localizedName).left().top();
                        }).left().row();
                        info.add(displayUnit.localizedName).left().row();
                        if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                    });
                    b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
            }).growX().pad(5).row(); });
        });
    }

    public class PowerUnitTurretBuild extends ItemUnitTurretBuild {
        @Override
        public void read(Reads read, byte revision){

            if(revision != 1){ /*Back when this used to extend powerTurret, prevents reading of item entry*/
                super.read(read, revision);
            }

            reloadCounter = read.f();
            rotation = read.f();
        }

        @Override
        public boolean hasAmmo(){
            return true;
        }

        @Override
        public @Nullable BulletType peekAmmo(){
            return ammo.size == 0 ? shootType : ammo.peek().type();
        }

        @Override
        public BulletType useAmmo(){
            if(cheating()) return peekAmmo();
            if(peekAmmo() == shootType)return peekAmmo();

            AmmoEntry entry = ammo.peek();
            entry.amount -= ammoPerShot;
            if(entry.amount <= 0) ammo.pop();
            totalAmmo -= ammoPerShot;
            totalAmmo = Math.max(totalAmmo, 0);
            return entry.type();
        }

        //Hack to make it reload when only powered
         @Override
        protected float baseReloadSpeed(){
            return power.status >= 1f ? 1f : efficiency;
        }
    }
}
