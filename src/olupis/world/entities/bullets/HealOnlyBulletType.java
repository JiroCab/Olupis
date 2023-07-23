package olupis.world.entities.bullets;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;

public class HealOnlyBulletType extends BasicBulletType {
    public HealOnlyBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public HealOnlyBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    @Override
    public void update(Bullet b){
        updateTrail(b);
        updateNewHoming(b);
        updateWeaving(b);
        updateTrailEffects(b);
        updateBulletInterval(b);
    }

    public void updateNewHoming(Bullet b){
        if(homingPower > 0.0001f && b.time >= homingDelay){
            float realAimX = b.aimX < 0 ? b.x : b.aimX;
            float realAimY = b.aimY < 0 ? b.y : b.aimY;

            Teamc target = null;
            //only in on allies if possible
            if(heals()){
                target = Units.closestTarget(null, realAimX, realAimY, homingRange,
                        e -> false /*don't*/,
                        t -> collidesGround && (t.team != b.team || t.damaged()) && !b.hasCollided(t.id)
                );
            }

            if(target != null){
                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
            }
        }
    }

}
