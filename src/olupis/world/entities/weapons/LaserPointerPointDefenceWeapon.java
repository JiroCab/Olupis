package olupis.world.entities.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.type.weapons.PointDefenseWeapon;

import static mindustry.Vars.state;

public class LaserPointerPointDefenceWeapon extends PointDefenseWeapon {
    public float aoe = 50,
                     laserSize = 1.5f,
                     laserAlphaMin = 0.1f, laserAlphaMax = 0.5f,
                     trackingRange = 2f,
                     soundVol = 1f
    ;
    boolean drawPointer = true;
    public Effect hitAoeEffect = Fx.none,
                        aoeBeamEffect = Fx.pointBeam;
    /*visual tracking that lags to remove stutter since bullets tracking is snappy*/
    @Nullable float lastx = Float.NEGATIVE_INFINITY, lasty = Float.NEGATIVE_INFINITY, lastdiv = 5;

    public LaserPointerPointDefenceWeapon(String name){
        super(name);
    }


    public void draw(Unit unit, WeaponMount mount){
        //apply layer offset, roll it back at the end
        float z = Draw.z();
        Draw.z(z + layerOffset);

        float
                rotation = unit.rotation - 90,
                realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
                weaponRotation  = rotation + (rotate ? mount.rotation : baseRotation),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

        if(shadow > 0){
            Drawf.shadow(wx, wy, shadow);
        }

        if(top){
            drawOutline(unit, mount);
        }

        if(parts.size > 0){
            DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
            DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(part.under){
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = -Mathf.sign(flipSprite);

        //fix color
        unit.type.applyColor(unit);


        if(mount.target != null && drawPointer){
            Lines.stroke(laserSize, unit.type.cellColor(unit));
            Draw.color(unit.type.cellColor(unit), Mathf.lerp(laserAlphaMin, laserAlphaMax, mount.warmup));
            Lines.dashLine(wx, wy, lastx, lasty,Math.round(lastdiv));
            Draw.color();
        }

        if(region.found()) Draw.rect(region, wx, wy, weaponRotation);

        if(cellRegion.found()){
            Draw.color(unit.type.cellColor(unit));
            Draw.rect(cellRegion, wx, wy, weaponRotation);
            Draw.color();
        }

        if(heatRegion.found() && mount.heat > 0){
            Draw.color(heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(heatRegion, wx, wy, weaponRotation);
            Draw.blend();
            Draw.color();
        }

        Draw.xscl = 1f;

        if(parts.size > 0){
            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(!part.under){
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = 1f;

        Draw.z(z);
    }


    @Override
    protected boolean checkTarget(Unit unit, Teamc target, float x, float y, float range){
        boolean check = !(target.within(unit, range) && target.team() != unit.team && target instanceof Bullet bullet && bullet.type != null && bullet.type.hittable);

        if(!check){
            if(!target.within(lastx, lasty, trackingRange) || lastx == Float.NEGATIVE_INFINITY || lasty == Float.NEGATIVE_INFINITY){
                lastx = target.x() ;
                lasty = target.y();
            }
            lastx = Mathf.lerp(lastx, target.x() , 0.5f);
            lasty = Mathf.lerp(lasty, target.y(), 0.5f);
            lastdiv = Math.max(Mathf.lerp( lastdiv, (target.dst(unit) / 5), 0.5f), 5);
        } else {
            lastx = Float.NEGATIVE_INFINITY;
            lasty = Float.NEGATIVE_INFINITY;
        }
        return check;
    }


    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        if(!(mount.target instanceof Bullet target)) return;

        // not sure whether it should multiply by the damageMultiplier of the unit
        float bulletDamage = bullet.damage * unit.damageMultiplier() * state.rules.unitDamage(unit.team);
        if(target.damage() > bulletDamage){
            target.damage(target.damage() - bulletDamage);
            bulletDamage -= target.damage();
        }else{
            target.remove();
        }

        beamEffect.at(shootX, shootY, rotation, color, new Vec2().set(target));
        bullet.shootEffect.at(shootX, shootY, rotation, color);
        bullet.hitEffect.at(target.x, target.y, color);
        shootSound.at(shootX, shootY, Mathf.random(soundPitchMin, soundPitchMax), soundVol);
        mount.recoil = 1f;
        mount.heat = 1f;

        if(aoe >= 0 && bulletDamage  > 0){
            
            final float[] aoeData = {bulletDamage, aoe/2};
            Groups.bullet.intersect(target.x - aoeData[1], target.y - aoeData[1], aoe, aoe, s -> {
                if(s == target ) return;
                if(s.team != unit.team && s.type().hittable){
                    if(target.damage() > aoeData[0]){
                        s.damage(s.damage() - aoeData[0]);
                        aoeData[0] -= s.damage;
                    }
                    else s.remove();
                    aoeBeamEffect.at(target.x, target.y, rotation, color, new Vec2().set(s));
                    hitAoeEffect.at(s.x, s.y, color);
                }
            });

        }
    }

}
