package olupis.world.entities.bullets;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;

import java.util.concurrent.atomic.AtomicReference;

public class RollBulletType extends BasicBulletType {
    public boolean ricochetHoming = true;

    public RollBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
        collidesAir = false;
        this.collides = this.collidesGround = collidesTiles = true;
        layer = Layer.legUnit +1f;
    }
    public RollBulletType(float speed, float damage){
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
        if(homingPower > 0.0001f && target != null && target.team() != b.team) {
            if(!ricochetHoming && b.time >= homingDelay){
                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
            }else if (b.within(target, b.hitSize())) {
                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
            }
        }
    }

    public void updateCollision(Bullet b, Teamc target, AtomicReference<Float> tarSize){
        /*If someone finds a better way to do this, please let us know -RushieWsahie*/
        boolean within = target != null && b.within(target.x(), target.y(), Math.max(tarSize.get(),  b.hitSize)),
                onOwner = b.owner instanceof Building d && !b.within(d.x(), d.y, d.hitSize()) || b.owner instanceof Hitboxc c && !b.within(c.x(), c.y(), c.hitSize());
                /*Feature/bug: ignore one tile blocks beside the owner except  when shot in corner angle*/
        if(b.tileOn() != null && !within && onOwner) {
            if (b.tileOn().solid()) b.remove();
            else if (b.tileOn().build != null && !b.tileOn().build.block.solid && b.tileOn().team() != b.team && b.tileOn().team() != Team.derelict && b.hasCollided(b.tileOn().build.id)){
                /*hits the block twice, can't be bothered to fix it, so it's a nerf for BvB*/
                hitTile(b, b.tileOn().build, b.x(), b.y,  b.tileOn().build.health, true);
                b.collided.add(b.tileOn().build.id);
            }
        }
    }

    public Teamc findTarget(Bullet b, AtomicReference<Float> tarSize){
        float realAimX = b.aimX < 0 ? b.x : b.aimX,
                realAimY = b.aimY < 0 ? b.y : b.aimY;
        //Ignore allied non solids
        return Units.closestTarget(null, realAimX, realAimY, homingRange,
            e ->{ if(e == null)return false;
            tarSize.set(e.hitSize);
            return  e.checkTarget(collidesAir, collidesGround) && e.team != b.team && !b.hasCollided(e.id);
            },
            t ->{
                if(tarSize.get() > t.hitSize())tarSize.set(t.hitSize());
                return (t.team != b.team || !t.block.solid ) && !b.hasCollided(t.id);
            }
        );
    }
}
