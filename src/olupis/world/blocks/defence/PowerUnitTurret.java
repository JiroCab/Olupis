package olupis.world.blocks.defence;

import arc.Core;
import arc.util.Nullable;
import arc.util.io.Reads;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Iconc;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;

import java.util.Objects;

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

        /*TODO: Takes a bit to update*/
        addBar("units", (PowerUnitTurretBuild e) ->{
            UnitType unit = e.peekAmmo() == shootType ? shootType.spawnUnit : e.peekAmmo().spawnUnit;
            return new Bar(() -> Core.bundle.format("bar.unitcap",
                !Objects.equals(Fonts.getUnicodeStr(unit.localizedName), "") ? Fonts.getUnicodeStr(unit.localizedName) : Iconc.units,
                e.team.data().countType(unit),
                Units.getStringCap(e.team)
            ),() -> Pal.accent,
            () -> e.peekAmmo() == null ? 0f : e.peekAmmo().spawnUnit == null ? 0f : (float) e.team.data().countType(e.peekAmmo().spawnUnit) / Units.getCap(e.team));
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
            if(ammo.size >= 2 && ammo.peek().amount < ammoPerShot && ammo.get(ammo.size - 2).amount >= ammoPerShot){
                ammo.swap(ammo.size - 1, ammo.size - 2);
            }
            return true;
        }

        @Override
        public @Nullable BulletType peekAmmo(){
            //Crash on place
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
