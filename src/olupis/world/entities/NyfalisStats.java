package olupis.world.entities;

import arc.Core;
import arc.func.Boolf;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ShockMine;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.*;
import olupis.world.entities.bullets.EffectivenessMissleType;
import olupis.world.entities.bullets.MineBulletType;

import java.util.Iterator;

import static mindustry.Vars.*;

public class NyfalisStats extends StatValues {

    public static <T extends UnlockableContent> StatValue ammoWithInfo(ObjectMap<T, BulletType> map, Block parent){
        return ammoWithInfo(map, 0, false, parent != null ? parent.name : null);
    }

    public static <T extends UnlockableContent> StatValue ammoBlocksOnly(ObjectMap<T, BulletType> map, Block parent){
        ObjectMap<T, BulletType> children = new OrderedMap<>();
        for (ObjectMap.Entry<T, BulletType> b : map) {
            children.put(b.key, checkChildren(b.value));
        }

        return ammoWithInfo(children, parent);
    }

    public static BulletType checkChildren(BulletType type){
        if(type instanceof MineBulletType){
            return type;
        }
        if(type.fragBullet != null) return checkChildren(type.fragBullet);
        return type;
    }


    public static <T extends UnlockableContent> StatValue ammoWithInfo(ObjectMap<T, BulletType> map, int indent, boolean showUnit, String parent){
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t : orderedKeys) {
                boolean compact = t instanceof UnitType && !showUnit || indent > 0;

                BulletType type = map.get(t);

                if (!(type instanceof MineBulletType)) {
                    if (type.spawnUnit != null && type.spawnUnit.weapons.size > 0) {
                        ammoWithInfo(ObjectMap.of(t, type.spawnUnit.weapons.first().bullet), indent, false, parent).display(table);
                        continue;
                    }

                    table.table(Styles.grayPanel, bt -> {
                        bt.left().top().defaults().padRight(3).left();
                        //no point in displaying unit icon twice

                        if (!compact && !(t instanceof Turret)) {
                            bt.table(title -> {
                                title.image(icon(t)).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top();
                                title.labelWrap(t.localizedName).padRight(10).left().top();
                            });
                            bt.row();
                        }

                        if (parent != null && Core.bundle.has(parent + "." + t.name)) {
                            bt.table(info -> {
                                info.add(Core.bundle.get(parent + "." + t.name)).padRight(10).left().top();
                            }).row();
                        }

                        if (type.damage > 0 && (type.collides || type.splashDamage <= 0)) {
                            if (type.continuousDamage() > 0) {
                                bt.add(Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized());
                            } else {
                                bt.add(Core.bundle.format("bullet.damage", type.damage));
                            }
                        }

                        if (type.buildingDamageMultiplier != 1) {
                            int val = (int) (type.buildingDamageMultiplier * 100 - 100);
                            sep(bt, Core.bundle.format("bullet.buildingdamage", ammoStat(val)));
                        }

                        if (type.rangeChange != 0 && !compact) {
                            sep(bt, Core.bundle.format("bullet.range", ammoStat(type.rangeChange / tilesize)));
                        }

                        if (type.splashDamage > 0) {
                            sep(bt, Core.bundle.format("bullet.splashdamage", (int) type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                        }

                        if (!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && (!(t instanceof Turret turret) || turret.displayAmmoMultiplier)) {
                            sep(bt, Core.bundle.format("bullet.multiplier", (int) type.ammoMultiplier));
                        }

                        if (!compact && !Mathf.equal(type.reloadMultiplier, 1f)) {
                            int val = (int) (type.reloadMultiplier * 100 - 100);
                            sep(bt, Core.bundle.format("bullet.reload", ammoStat(val)));
                        }

                        if (type.knockback > 0) {
                            sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                        }

                        if (type.healPercent > 0f) {
                            sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                        }

                        if (type.healAmount > 0f) {
                            sep(bt, Core.bundle.format("bullet.healamount", Strings.autoFixed(type.healAmount, 2)));
                        }

                        if (type.pierce || type.pierceCap != -1) {
                            sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                        }

                        if (type.incendAmount > 0) {
                            sep(bt, "@bullet.incendiary");
                        }

                        if (type.homingPower > 0.01f) {
                            sep(bt, "@bullet.homing");
                        }

                        if (type.lightning > 0) {
                            sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                        }

                        if (type.pierceArmor) {
                            sep(bt, "@bullet.armorpierce");
                        }

                        if (type.suppressionRange > 0) {
                            sep(bt, Core.bundle.format("bullet.suppression", Strings.autoFixed(type.suppressionDuration / 60f, 2), Strings.fixed(type.suppressionRange / tilesize, 1)));
                        }

                        if (type.status != StatusEffects.none) {
                            sep(bt, (type.status.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" + (Strings.autoFixed(type.statusDuration / 60f, 1)) + "[lightgray] " + Core.bundle.get("unit.seconds")));
                        }

                        if (type instanceof EffectivenessMissleType m && m.groundDamageMultiplier != 1f) {
                            float val = (m.flatDamage ? (m.groundDamageMultiplier / m.damage) * 100 -100 : m.groundDamageMultiplier * 100 - 100);
                            sep(bt, Core.bundle.format("stat.olupis-groundpenalty", ammoStat(val), m.flatDamage ? m.groundDamageMultiplier : m.damage * m.groundDamageMultiplier, 2));
                        }


                        if (type.intervalBullet != null) {
                            bt.row();

                            Table ic = new Table();
                            ammoWithInfo(ObjectMap.of(t, type.intervalBullet), indent + 1, false, null).display(ic);
                            Collapser coll = new Collapser(ic, true);
                            coll.setDuration(0.1f);

                            bt.table(it -> {
                                it.left().defaults().left();

                                it.add(Core.bundle.format("bullet.interval", Strings.autoFixed(type.intervalBullets / type.bulletInterval * 60, 2)));
                                it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }

                        if (type.fragBullet != null) {
                            bt.row();

                            Table fc = new Table();
                            ammoWithInfo(ObjectMap.of(t, type.fragBullet), indent + 1, false, null).display(fc);
                            Collapser coll = new Collapser(fc, true);
                            coll.setDuration(0.1f);

                            bt.table(ft -> {
                                ft.left().defaults().left();

                                ft.add(Core.bundle.format("bullet.frags", type.fragBullets));
                                ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }
                    }).padLeft(indent * 5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);
                    table.row();
                } else {
                    table.table(Styles.grayPanel, in -> {
                        in.left().top().defaults().padRight(3).left();

                        if (type instanceof MineBulletType mb && mb.mine != null) {
                            if(mb.mine.canBeBuilt() && mb.mine.fullIcon != null) in.image(mb.mine.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                            else in.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                            in.table(bt -> {
                                bt.left().top().defaults().padRight(3).left();
                                title(bt, (t.localizedName), t.fullIcon);
                                sepLeft(bt, (mb.mine.localizedName));
                                sepLeft(bt, (mb.mine.description));
                                if (mb.createChance) {
                                    float set;
                                    if (mb.createChancePercent > 0.99) {
                                        set = 99;
                                    } else if (mb.createChancePercent < 0.01) {
                                        set = 1;
                                    } else {
                                        set = (mb.createChancePercent * 100);
                                    }
                                    sep(bt, Core.bundle.format("stat.olupis-chancepercent", Strings.autoFixed(set, 2)));
                                }
                                if (mb.mine instanceof ShockMine sm) {
                                    float mdmg = (sm.damage * sm.tendrils) + sm.tileDamage;
                                    if (mdmg != 0) {
                                        sep(bt, Core.bundle.format("bullet.damage", (sm.damage * sm.tendrils) + sm.tileDamage));
                                    }
                                    if (sm.bullet != null) {
                                        bt.row();

                                        Table ic = new Table();
                                        ammoWithInfo(ObjectMap.of(t, sm.bullet), indent + 1, false, null).display(ic);
                                        Collapser coll = new Collapser(ic, true);
                                        coll.setDuration(0.1f);

                                        bt.table(it -> {
                                            it.left().defaults().left();

                                            it.add(Core.bundle.format("stat.olupis-bullet", Strings.autoFixed(sm.shots, 2)));
                                            it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                                        });
                                        bt.row();
                                        bt.add(coll);
                                    }
                                }
                            }).left().growX().pad(5);
                            in.button("?", Styles.flatBordert, () -> ui.content.show(mb.mine)).size(40f).pad(10).right().grow().visible(mb.mine::unlockedNow).row();
                        }
                    }).padLeft(indent * 5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);
                    table.row();
                }
            }
        };
    }

    public static StatValue weaponsB(UnitType unit, Seq<Weapon> weapons) {
        return (table) -> {
            table.row();

            for(int i = 0; i < weapons.size; ++i) {
                Weapon weapon = (Weapon)weapons.get(i);
                if (!weapon.flipSprite && weapon.hasStats(unit)) {
                    TextureRegion region = !weapon.name.isEmpty() ? Core.atlas.find(weapon.name + "-preview", weapon.region) : null;
                    table.table(Styles.grayPanel, (w) -> {
                        w.left().top().defaults().padRight(3.0F).left();
                        if (region != null && region.found() && weapon.showStatSprite) {
                            w.image(region).size(60.0F).scaling(Scaling.bounded).left().top();
                        }

                        w.row();
                        weapon.addStats(unit, w);
                    }).growX().pad(5.0F).margin(10.0F);
                    table.row();
                }
            }

        };
    }

    //for AmmoListValue
    private static void sep(Table table, String text){
        table.row();
        table.add(text);
    }

    private static void sepLeft(Table table, String text){
        table.table(te -> {
            te.add(text).growX().left();
        }).row();
    }

    private static void title(Table table, String text, TextureRegion icon){
        table.table(te -> {
            te.image(icon).size(3 * 8).left().scaling(Scaling.fit).top();
            te.add(text).left().top();
        }).left().row();
    }

    //for AmmoListValue
    private static String ammoStat(float val){
        return (val > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }

    public static StatValue sawBoosters(float reload, float maxUsed, float multiplier, boolean baseReload, Boolf<Liquid> filter) {
        return (table) -> {
            table.row();
            table.table((c) -> {
                Iterator var6 = Vars.content.liquids().iterator();

                while(var6.hasNext()) {
                    Liquid liquid = (Liquid)var6.next();
                    if (filter.get(liquid)) {
                        c.table(Styles.grayPanel, (b) -> {
                            b.image(liquid.uiIcon).size(40.0F).pad(10.0F).left().scaling(Scaling.fit);
                            b.table((info) -> {
                                info.add(liquid.localizedName).left().row();
                                info.add(Strings.autoFixed(maxUsed * 60.0F, 2) + StatUnit.perSecond.localized()).left().color(Color.lightGray);
                            });
                            b.table((bt) -> {
                                bt.right().defaults().padRight(3.0F).left();
                                float reloadRate = (baseReload ? 1.0F : 0.0F) + maxUsed * multiplier * liquid.heatCapacity;
                                float standardReload = baseReload ? reload : reload / (maxUsed * multiplier * 0.4F);
                                float result = standardReload / (reload / reloadRate);
                                bt.add(Core.bundle.format("stat.olupis-spin-speed-bonus", new Object[]{Strings.autoFixed(result * 100.0F, 2)})).pad(5.0F);
                            }).right().grow().pad(10.0F).padRight(15.0F);
                        }).growX().pad(5.0F).row();
                    }
                }

            }).growX().colspan(table.getColumns());
            table.row();
        };
    }

    private static TextureRegion icon(UnlockableContent t){
        return t.uiIcon;
    }


}
