package olupis.world.blocks.turret;

import arc.*;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.*;
import arc.struct.ObjectMap;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Puddles;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import olupis.content.NyfalisSounds;
import olupis.world.entities.parts.DrawUnstableTurret;

import static mindustry.Vars.world;

public class UnstablePowerTurret extends PowerTurret {

    public Effect explodeEffect = Fx.titanExplosion;
    public Sound explodeSound = Sounds.boom;
    public Sound warningSound = NyfalisSounds.cascadeDangerWarning;

    public float explosionDamage = 200, explosionRadius = 80;
    public Liquid explosionPuddleLiquid = null;
    public int explosionPuddles = 4;
    public float explosionShake = 3, explosionShakeDuration = 30;
    public float explosionPuddleRange = 80, explosionPuddleAmount = 10;
    public float smokeThreshold = 0.3f, flashThreshold = 0.6f;
    public float coolantPower = 0.1f;
    public Color coolColor = new Color(1, 1, 1, 0f);
    public Color hotColor = Color.red;
    public Color flashColor1 = Color.red, flashColor2 = Color.yellow;

    public float illuminateTime = 30f;

    public UnstablePowerTurret(String name){
        super(name);
        liquidCapacity = 30;
        drawer = new DrawUnstableTurret();
        rebuildable = false;
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("heat", (UnstablePowerTurretBuild entity) -> new Bar("bar.heat", Pal.lightOrange, () -> entity.heatT));
    }

    public void setStats() {
        super.setStats();
        this.stats.remove(Stat.ammo);
        this.stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(new Object[]{this, this.shootType})));
        this.stats.remove(Stat.booster);
        this.stats.add(Stat.input, StatValues.boosters(this.reload, this.coolant.amount, this.coolantMultiplier, false, this::consumesLiquid));
    }

    public class UnstablePowerTurretBuild extends PowerTurretBuild{
        public float progressLight;
        public float heatT;
        public float flash;
        @Override
        public void updateTile(){
            unit.ammo(power.status * (float)unit.type().ammoCapacity);
            super.updateTile();

            if(isShooting() && power.status > 0){
                heatT = Mathf.clamp(heatT + 0.1f);
            }

            if(heatT > 0){
                float maxUsed = Math.min(liquids.currentAmount(), heatT / coolantPower);
                heatT -= maxUsed * coolantPower;
                liquids.remove(liquids.current(), maxUsed);
                if(!isShooting() && liquids.currentAmount() <= 0){
                    heatT = Mathf.clamp(heatT - 0.0005f);
                }
            }
            if(heatT > flashThreshold){
                float sound = 1.0f + (heatT - flashThreshold) / (1f - flashThreshold); //ranges from 1.0 to 2.0
                if(Mathf.chance(sound / 20.0 * delta())){
                    warningSound.at(this);
                }
            }
            if(heatT > smokeThreshold){
                float smoke = 1.0f + (heatT - smokeThreshold) / (1f - smokeThreshold); //ranges from 1.0 to 2.0
                if(Mathf.chance(smoke / 20.0 * delta())){
                    Fx.reactorsmoke.at(x + Mathf.range(size * 8 / 2f),
                            y + Mathf.range(size * 8 / 2f));
                }
            }

            heatT = Mathf.clamp(heatT);

            if(heatT >= 0.999f){
                Events.fire(Trigger.thoriumReactorOverheat);
                createExplosion();
                kill();
            }
        }

        public void createExplosion(){
            if(explosionDamage > 0){
                Damage.damage(x, y, explosionRadius * 8, explosionDamage);
            }

            explodeEffect.at(this);
            explodeSound.at(this);

            if(explosionPuddleLiquid != null){
                for(int i = 0; i < explosionPuddles; i++){
                    Tmp.v1.trns(Mathf.random(360f), Mathf.random(explosionPuddleRange));
                    Tile tile = world.tileWorld(x + Tmp.v1.x, y + Tmp.v1.y);
                    Puddles.deposit(tile, explosionPuddleLiquid, explosionPuddleAmount);
                }
            }

            if(explosionShake > 0){
                Effect.shake(explosionShake, explosionShakeDuration, this);
            }
        }

        @Override
        public void drawLight() {
            boolean check = (!hasPower || power.status >= 0.5f) && (hasAmmo());
            if(emitLight){
                progressLight = Mathf.lerpDelta(progressLight, check ? lightRadius : 0, this.delta() / illuminateTime);
                if(progressLight >= 0)Drawf.light(x, y, progressLight, lightColor, lightColor.a);
            }
            super.drawLight();
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.heat) return heatT;
            return super.sense(sensor);
        }
    }
}

