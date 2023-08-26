package olupis.world.entities.bullets;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;

import java.util.concurrent.atomic.AtomicReference;

public class HealOnlyBulletType extends BasicBulletType {
    public HealOnlyBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
        this.collidesAir = false;
    }

    public HealOnlyBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    @Override
    public void update(Bullet b){
        updateCollision(b);
        super.update(b);
    }

    @Override
    public void updateHoming(Bullet b){
        if(homingPower > 0.0001f && b.time >= homingDelay){
            float realAimX = b.aimX < 0 ? b.x : b.aimX;
            float realAimY = b.aimY < 0 ? b.y : b.aimY;

            Teamc target = null;
            //Only Home on allies
            if(heals()){
                target = Units.closestTarget(null, realAimX, realAimY, homingRange,
                        e -> false /*don't*/,
                        t -> (t.team != b.team || t.damaged()) && !b.hasCollided(t.id)
                );

            }

            if(target != null){
                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));

            }
        }
    }

    public void updateCollision(Bullet b){
        /*If someone finds a better way to do this, please let us know -RushieWsahie*/
        if (!heals())return;
        AtomicReference<Float> size = new AtomicReference<>(b.hitSize);
        Teamc target = Units.closestTarget(null, b.x, b.y, homingRange,
                e -> false /*don't*/,
                t -> {
                    size.set(t.hitSize());
                    return (t.team != b.team || t.damaged()) && !b.hasCollided(t.id);
                }
        );
        if(target == null) this.collides  = this.collidesGround = false;
        else this.collides  = this.collidesGround = target.within(b.x(), b.y(), Math.max(size.get() -3.5f, 1f));

    }
}
