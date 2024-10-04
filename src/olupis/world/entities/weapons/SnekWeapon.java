package olupis.world.entities.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Crawlc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;

/*Very janky but gets the work done*/
public class SnekWeapon extends NyfalisWeapon {
    /*Sets which segment the weapon is "mounted" to*/
    public float weaponSegmentParent = 3;

    public SnekWeapon(String name){
        super(name);
    }
    public SnekWeapon(String name, boolean boostShoot, boolean groundShoot ){
        super(name, boostShoot, groundShoot);
    }
    public SnekWeapon(){
        super("");
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {

        if ( altWeaponPos &&unit instanceof Crawlc crawl) {
            UnitType type = unit.type;
            float trns = Mathf.sin(crawl.crawlTime() + weaponSegmentParent * type.segmentPhase, type.segmentScl, type.segmentMag),
                    rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, weaponSegmentParent / (type.segments - 1f)),
                    tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns), rotation = rot - 90,
                    mountX = unit.x + Angles.trnsx(rotation, x, y),
                    mountY = unit.y + Angles.trnsy(rotation, x, y);
            shootXf  = mountX + Angles.trnsx(rotation, this.shootX, this.shootY) + tx;
            shootYf  = mountY + Angles.trnsy(rotation, this.shootX, this.shootY) + ty;
        }
        super.update(unit, mount);
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        if (unit instanceof Crawlc crawl) {
            //apply layer offset, roll it back at the end
            float z = Draw.z();
            Draw.z(z + layerOffset);
            UnitType type = unit.type;

            float
                    trns = Mathf.sin(crawl.crawlTime() + weaponSegmentParent * type.segmentPhase, type.segmentScl, type.segmentMag),
                    rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, weaponSegmentParent / (type.segments - 1f)),
                    tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns), rotation = rot - 90,
                    realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
                    weaponRotation = rotation + (rotate ? mount.rotation : baseRotation),
                    wx = (unit.x + Angles.trnsx(rotation, x, y)+ Angles.trnsx(weaponRotation, 0, -realRecoil)) + tx,
                    wy = (unit.y + Angles.trnsy(rotation, x, y)+ Angles.trnsy(weaponRotation, 0, -realRecoil)) + ty;

            if (shadow > 0) {
                Drawf.shadow(wx, wy, shadow);
            }

            if (top) {
                drawOutline(unit, mount);
            }

            if (parts.size > 0) {
                DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
                DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

                for (int i = 0; i < parts.size; i++) {
                    var part = parts.get(i);
                    DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                    if (part.under) {
                        part.draw(DrawPart.params);
                    }
                }
            }

            Draw.xscl = -Mathf.sign(flipSprite);

            //fix color
            unit.type.applyColor(unit);

            if (region.found()) Draw.rect(region, wx, wy, weaponRotation);

            if (cellRegion.found()) {
                Draw.color(unit.type.cellColor(unit));
                Draw.rect(cellRegion, wx, wy, weaponRotation);
                Draw.color();
            }

            if (heatRegion.found() && mount.heat > 0) {
                Draw.color(heatColor, mount.heat);
                Draw.blend(Blending.additive);
                Draw.rect(heatRegion, wx, wy, weaponRotation);
                Draw.blend();
                Draw.color();
            }

            Draw.xscl = 1f;

            if (parts.size > 0) {
                //TODO does it need an outline?
                for (int i = 0; i < parts.size; i++) {
                    var part = parts.get(i);
                    DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                    if (!part.under) {
                        part.draw(DrawPart.params);
                    }
                }
            }

            Draw.xscl = 1f;

            Draw.z(z);
        }else super.draw(unit, mount);

    }
}
