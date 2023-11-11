package olupis.world.blocks.defence;

import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import arc.util.Scaling;
import arc.util.io.Reads;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.gen.Iconc;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.*;
import mindustry.world.meta.Stat;

import java.util.Objects;

import static mindustry.Vars.*;

public class PowerUnitTurret extends ItemUnitTurret {
    /*Weapon to use when there's no modifier item*/
    public BulletType shootType;

    public PowerUnitTurret(String name){
        super(name);
        hasPower = true;
    }

    public void setBars(){
        super.setBars();
        removeBar("units");

        addBar("units", (PowerUnitTurretBuild e) ->{
            UnitType unit = e.peekAmmo() == null ? shootType.spawnUnit : e.peekAmmo().spawnUnit;
            return new Bar(() -> Core.bundle.format("bar.unitcap",
                !Objects.equals(Fonts.getUnicodeStr(unit.localizedName), "") ? Fonts.getUnicodeStr(unit.localizedName) : Iconc.units,
                e.team.data().countType(unit),
                Units.getStringCap(e.team)
            ),() -> Pal.accent,
            () -> e.peekAmmo() == null ? 0f : e.peekAmmo().spawnUnit == null ? 0f : (float) e.team.data().countType(e.peekAmmo().spawnUnit) / Units.getCap(e.team));
        });
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.output);
        stats.add(Stat.output, table -> {
            table.row();
            table.table(Styles.grayPanel, b -> {
                UnitType displayUnit = shootType.spawnUnit;
                if (!displayUnit.isBanned())
                    b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                else
                    b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                b.table(info -> {
                    info.add(displayUnit.localizedName).left().row();
                    if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                });
                b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
            }).growX().pad(5).row();

            this.ammoTypes.each((item, bul) -> {
                UnitType displayUnit = bul.spawnUnit;
                if (displayUnit == null) return;
                table.row();
                table.table(Styles.grayPanel, b -> {
                    if (!displayUnit.isBanned())
                        b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                    else
                        b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                    b.table(info -> {
                        if (item != null) info.table(title -> {
                            title.image(item.fullIcon).size(3 * 8).left().scaling(Scaling.fit).top();
                            title.add(item.localizedName).left().top();
                        }).left().row();
                        info.add(displayUnit.localizedName).left().row();
                        if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                    });
                    b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
                }).growX().pad(5).row();
            });
        });
    }

    public class PowerUnitTurretBuild extends ItemUnitTurretBuild {
        @Override
        public void read(Reads read, byte revision){

            if(revision > 1){ /*Back when this used to extend powerTurret, prevents reading of item entry*/
                super.read(read, revision);
            }

            if(revision == 1){
                reloadCounter = read.f();
                rotation = read.f();
            }
        }

        @Override
        public boolean hasAmmo(){
            if(payload != null) return false;

            //skip first entry if it has less than the required amount of ammo
            if(ammo.size >= 2 && ammo.peek().amount < ammoPerShot && ammo.get(ammo.size - 2).amount >= ammoPerShot){
                ammo.swap(ammo.size - 1, ammo.size - 2);
            }

            return power.status >= 1f && (state.rules.waveTeam == this.team || (shootType.spawnUnit.useUnitCap && this.team.data().countType(shootType.spawnUnit) < this.team.data().unitCap) || !shootType.spawnUnit.useUnitCap);
        }

        @Override
        protected void shoot(BulletType type){
            boolean creatable = shootCreatable(type);
            if(direction != -1){
                shootPayload(type, type != shootType);
            } else{
                if(payload != null) payload = null;
                shootRegular(type, creatable, type != shootType);
            }

            if(consumeAmmoOnce) useAmmo();
        }

        @Override
        public @Nullable BulletType peekAmmo(){
            if (ammo.size > 0){
                return ammo.peek().amount > ammoPerShot ? shootType : ammo.peek().type();
            } else  return shootType;
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
