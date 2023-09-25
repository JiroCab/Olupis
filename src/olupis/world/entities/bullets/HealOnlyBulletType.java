package olupis.world.entities.bullets;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;

import java.util.concurrent.atomic.AtomicReference;

public class HealOnlyBulletType extends BasicBulletType {
    public HealOnlyBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
        this.collidesAir = this.hittable = false;
        this.collidesTeam = true;
    }

    public HealOnlyBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    @Override
    public void update(Bullet b){
        AtomicReference<Float> tarSize = new AtomicReference<>(b.hitSize);
        Teamc tar = findTarget(b, tarSize);

        updateCollision(b, tar, tarSize);
        updateTrail(b);
        updateHoming(b, tar);
        updateWeaving(b);
        updateTrailEffects(b);
        updateBulletInterval(b);
    }

    public void updateHoming(Bullet b, Teamc target){
        if(homingPower > 0.0001f && b.time >= homingDelay && target != null) {
            b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
        }
    }

    /*Overcomplicated way to say: Hit only Blocks but not units*/
    public void updateCollision(Bullet b, Teamc target, AtomicReference<Float> tarSize){
        /*If someone finds a better way to do this, please let us know -RushieWsahie*/
        if (!heals())return;
        /*heals only the target, passing over any damaged blocks is ignored, debating if this a feature or bug*/
        if(target == null) this.collides = this.collidesGround = false;
        else this.collides  = this.collidesGround = target.within(b.x(), b.y(), Math.max(tarSize.get() -3.5f, 1f));
    }

    public Teamc findTarget(Bullet b, AtomicReference<Float> tarSize){
        if(!heals()) return null;
        float realAimX = b.aimX < 0 ? b.x : b.aimX,
                realAimY = b.aimY < 0 ? b.y : b.aimY;
        //Only Home on allies
        return Units.closestTarget(null, realAimX, realAimY, homingRange,
            e -> false /*don't*/,
            t ->{tarSize.set(t.hitSize());
                return (t.team == b.team && t.damaged()) && !b.hasCollided(t.id);
            }
        );
    }
}
