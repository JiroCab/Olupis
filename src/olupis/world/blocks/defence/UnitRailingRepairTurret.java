package olupis.world.blocks.defence;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class UnitRailingRepairTurret extends RepairTurret {
    /*Yes, we *rail* units in order to repair them ;3 */
    static final Rect rect = new Rect();

    protected float reloadTimer;
    public float reload = 100f, statusDuration = 60f * 6f, shootX = 0f, shootY = 0f;
    public Effect fireFx = Fx.none, lineFx = Fx.none;
    public StatusEffect healStatus = StatusEffects.none;


    public UnitRailingRepairTurret(String name){
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.repairSpeed);
        stats.add(Stat.repairSpeed,60f / (repairSpeed * (60f / (reload))), StatUnit.perShot);
    }

    @Override
    public void load(){
        super.load();
        baseRegion = Core.atlas.find(minfo.mod.name + "-iron-block-" + size);
    }


    public class UnitRailingTurretBuild extends RepairTurret.RepairPointBuild {

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);

            Draw.z(Layer.turret);
            Drawf.shadow(region, x - (size / 2f), y - (size / 2f), rotation - 90);
            Draw.rect(region, x, y, rotation - 90);
        }


        @Override
        public void updateTile(){
            float multiplier = 1f;
            if(acceptCoolant){
                multiplier = 1f + liquids.current().heatCapacity * coolantMultiplier * optionalEfficiency;
            }

            if(target != null && (target.dead() || target.dst(this) - target.hitSize/2f > repairRadius || target.health() >= target.maxHealth())){
                target = null;
            }

            if(target == null){
                offset.setZero();
            }

            if(target != null && efficiency > 0){
                float angle = Angles.angle(x, y, target.x + offset.x, target.y + offset.y);
                if(Angles.angleDist(angle, rotation) < (target.hitSize() * 0.9f) && (reloadTimer += Time.delta) >= reload){
                   shoot(multiplier);
                }
                rotation = Mathf.slerpDelta(rotation, angle, 0.5f * efficiency * timeScale);
            }

            if(timer(timerTarget, 20)){
                rect.setSize(repairRadius * 2).setCenter(x, y);
                target = Units.closest(team, x, y, repairRadius, Unit::damaged);
            }
        }

        public void shoot( float  multiplier){
            target.heal(repairSpeed * edelta() * multiplier);
            reloadTimer = 0f;
            float xf = x + Angles.trnsx(rotation - 90, shootX, shootY),
                    yf = y + Angles.trnsy(rotation - 90, shootX, shootY);
            boolean onTop = !target.within(x, y, size);

            if(onTop)fireFx.at(xf, yf, rotation, Pal.heal);
            else fireFx.at(target.x, target.y, rotation, Pal.heal);

            if(lineFx != Fx.none && onTop){
                lineFx.at(xf, yf, rotation, Pal.heal, new Vec2().set(target));
            }
            if(healStatus != StatusEffects.none) target.apply(healStatus, statusDuration);
        }
    }
}
