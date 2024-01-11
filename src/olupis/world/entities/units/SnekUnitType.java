package olupis.world.entities.units;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Crawlc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

/*ehehe snek*/
public class SnekUnitType extends NyfalisUnitType{
    /*Affects how the Status cell is renders, replaces the two `for` statements*/
    public int cellSegmentParent = segments -1, cellSegmentParentRot = segments + 4;
    public Seq <Integer> segmentsWithCells = new Seq<>();
    public boolean customShadow = true;

    public SnekUnitType(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        for(int i = 0; i < segments; i++) if (Core.atlas.find(name + "-cell-" + i).found()) segmentsWithCells.add(i);
        if(customShadow) softShadowRegion = Core.atlas.find("olupis-shadow-long");
    }

    @Override
    public void drawCell(Unit unit){
        if (unit instanceof Crawlc crawl) {
            /*Tbh I don't know what half this does since I just copied and pasted it, just manually tune the numbers*/
            if(drawCell){
                float trns = Mathf.sin(crawl.crawlTime() + cellSegmentParent * segmentPhase, segmentScl, segmentMag),
                        rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, (float) cellSegmentParentRot  /( segments - 1f)),
                        tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);

                applyColor(unit);
                Draw.color(cellColor(unit));
                Draw.rect(cellRegion, unit.x + tx, unit.y + ty, rot - 90);
                Draw.reset();
            }

            if(segmentsWithCells.size >= 1f) for (Integer i : segmentsWithCells) {
                float trns = Mathf.sin(crawl.crawlTime() + i * segmentPhase, segmentScl, segmentMag),
                        rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, (float) i / (segments - 1f)),
                        tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);
                applyColor(unit);
                Draw.color(cellColor(unit));
                Draw.rect(Core.atlas.find(name + "-cell-" + i), unit.x + tx, unit.y + ty, rot - 90);
                Draw.reset();
            }

        }else {
            super.drawCell(unit);
            Log.err(unit.type.localizedName + " is not a Crawlc! >:( not snek");
        }

    }
    /*Very janky but gets the work done*/
    public class SnekWeapon extends NyfalisWeapon {
        /*Sets which segment the weapon is "mounted" to*/
        public float weaponSegmentParent = segments -1;

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
        public void draw(Unit unit, WeaponMount mount){
            if (unit instanceof Crawlc crawl) {
                //apply layer offset, roll it back at the end
                float z = Draw.z();
                Draw.z(z + layerOffset);

                float
                        trns = Mathf.sin(crawl.crawlTime() + weaponSegmentParent * segmentPhase, segmentScl, segmentMag),
                        rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, weaponSegmentParent / (segments - 1f)),
                        tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns), rotation = rot - 90,
                        realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
                        weaponRotation = rotation + (rotate ? mount.rotation : baseRotation),
                        wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil) + tx,
                        wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil) + ty;

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
}

