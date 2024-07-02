package olupis.world.blocks.defence;

import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import arc.util.Scaling;
import arc.util.io.Reads;
import mindustry.content.Items;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.gen.Iconc;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.consumers.ConsumeItemDynamic;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import olupis.content.NyfalisItemsLiquid;
import olupis.world.entities.bullets.SpawnHelperBulletType;

import java.util.Objects;

import static mindustry.Vars.ui;

/*Im sorry to for whatever curse this class has and may you spared from it*/
public class PowerUnitTurret extends ItemUnitTurret {
    /*Weapon to use when there's no modifier item*/
    public Item internalItem = NyfalisItemsLiquid.powerAmmoItem;

    public PowerUnitTurret(String name){
        super(name);
        hasPower = consumesPower = true;
        setDynamicConsumer = false;

        consume(new ConsumeItemDynamic((PowerUnitTurretBuild e) -> e.regularShoot() ? e.useAlternate ? requiredAlternate : requiredItems : ItemStack.with(Items.copper, 0, Items.lead, 0)));
    }

    public void setBars(){
        super.setBars();
        removeBar("units");

        addBar("units", (PowerUnitTurretBuild e) ->{
            UnitType unit = e.peekAmmo().spawnUnit;
            if(unit == null) return null;
            return new Bar(() -> Core.bundle.format("bar.unitcap",
                !Objects.equals(Fonts.getUnicodeStr(unit.localizedName), "") ? Fonts.getUnicodeStr(unit.localizedName) : Iconc.units,
                e.team.data().countType(unit),
                Units.getStringCap(e.team)
            ),() -> Pal.accent,
            () -> (float) e.team.data().countType(unit) / Units.getCap(e.team));
        });

        removeBar("ammo");
        addBar("ammo", (ItemTurretBuild entity) ->
                new Bar(
                        "stat.ammo",
                        Pal.ammo,
                        () -> ((float)entity.totalAmmo - 1f)/ (maxAmmo -1f) //Hide the internal ammo
                )
        );
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.output);
        stats.add(Stat.output, table -> {
            table.row();
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
                            title.add(item != internalItem ? item.localizedName : Core.bundle.get("stat.olupis-unitpowercost")).left().top();
                        }).left().row();
                        info.add(displayUnit.localizedName).left().row();
                        info.add("[lightgray]"+Math.round(bul.reloadMultiplier * reload / 60) + " " + StatUnit.seconds.localized()).left().row();
                        if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                    });
                    b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
                }).growX().pad(5).row();
            });
        });
    }

    public class PowerUnitTurretBuild extends ItemUnitTurretBuild {
        //note: Setting ammo multiplier bellow 1f causes ammo not to be properly added
        @Override
        public void read(Reads read, byte revision){
            if(revision > 1){ //Back when this used to extend powerTurret, prevents reading of item entry
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
            if(!hasReqItems() && regularShoot()) return false;

            //skip first entry if it has less than the required amount of ammo
            if(ammo.size >= 2 && ammo.peek().amount < ammoPerShot && ammo.get(ammo.size - 2).amount >= ammoPerShot){
                ammo.swap(ammo.size - 1, ammo.size - 2);
            }
            return true;
        }

        @Override
        public boolean hasReqItems() {
            if(!regularShoot()) return true;
            return super.hasReqItems();
        }

        @Override
        protected void shoot(BulletType type){
            boolean creatable = shootCreatable(type);
            if(direction != -1){
                shootPayload(type, true);
            } else{
                if(payload != null) payload = null;
                shootRegular(type, creatable, true);
            }

            if(consumeAmmoOnce && regularShoot()) useAmmo();
        }

        public boolean regularShoot(){
            if(peekAmmo() == null || ammo.isEmpty() || ammo.peek() == null) return false;
            if((ammo.peek() instanceof  ItemEntry i && i.item ==internalItem )) return false;
            return ammo.peek().amount >= ammoPerShot;
        }

        @Override
        public @Nullable BulletType peekAmmo(){
            if (ammo.isEmpty() || ammo.peek() == null || ammo.peek().amount < ammoPerShot) return ammoTypes.get(internalItem);
            return super.peekAmmo();
        }

        @Override
        public UnitType getUnit(){
            if(ammo.size > 0 && peekAmmo().spawnUnit != null){
                if(useAlternate && peekAmmo() instanceof SpawnHelperBulletType bt ){
                    if(bt.alternateType != null) return bt.alternateType.spawnUnit;
                }
                return peekAmmo().spawnUnit;
            }
            return ammoTypes.get(internalItem).spawnUnit;
        }

        @Override
        public void updateTile() {
            //we "handle in" an item for when we're empty, so ItemTurret's consumer is happy and lets us consume power
            if(ammo.isEmpty() || ammo.peek() == null || ammo.peek().amount < ammoPerShot){
                handleItem(this, internalItem);
            }
            super.updateTile();
        }
    }
}
