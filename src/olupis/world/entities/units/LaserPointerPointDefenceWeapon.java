package olupis.world.entities.units;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.weapons.PointDefenseWeapon;
import olupis.content.NyfalisFxs;

import static mindustry.Vars.*;

public class LaserPointerPointDefenceWeapon extends PointDefenseWeapon {
    public float aoe = 50,
                     laserSize = 2f,
                     laserAlpha = 0.5f
    ;

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


        if(mount.target != null){
            Lines.stroke(laserSize);
            Draw.color(unit.type.cellColor(unit), laserAlpha);
            Lines.line(wx, wy, mount.target.x(), mount.target.y());
            Lines.circle(mount.target.x(), mount.target.y(), aoe);
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
            //TODO does it need an outline?
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
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        if(!(mount.target instanceof Bullet target)) return;

        // not sure whether it should multiply by the damageMultiplier of the unit
        float bulletDamage = bullet.damage * unit.damageMultiplier() * state.rules.unitDamage(unit.team);
        if(target.damage() > bulletDamage){
            target.damage(target.damage() - bulletDamage);
            target.damage(target.damage() + bulletDamage);
        }else{
            target.remove();
        }

        if(aoe >= 0){
            Groups.bullet.intersect(target.x, target.y, aoe * tilesize, aoe * tilesize, s -> {
                if(s == target) return;
                if(s.team != unit.team && s.type().hittable){
                    if(target.damage() > bulletDamage){
                        s.damage(s.damage() - bulletDamage);
                    }else{
                        s.remove();
                    }

                    NyfalisFxs.hitEmpSpark.at(target.x, target.y, Pal.redSpark); //TODO dedicate a fx here
                }
            });


        }


        //beamEffect.at(shootX, shootY, rotation, color, new Vec2().set(target));
        bullet.shootEffect.at(shootX, shootY, rotation, color);
        bullet.hitEffect.at(target.x, target.y, color);
        shootSound.at(shootX, shootY, Mathf.random(0.9f, 1.1f));
        mount.recoil = 1f;
        mount.heat = 1f;
    }

}
