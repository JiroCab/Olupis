package olupis.world.entities.bullets;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;

public class EffectivenessMissleType extends MissileBulletType {
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public float groundDamageMultiplier = 1f;
    public boolean flatDamage = false;

    public EffectivenessMissleType(float speed, float damge, String bulletSprite){
        super(speed, damge, bulletSprite);
    }

    public EffectivenessMissleType(float speed, float damge){
        super(speed, damge);
    }

    public EffectivenessMissleType(){
        super();
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h){
            float dmg = entity instanceof  Unit u && u.isGrounded() ? flatDamage ? groundDamageMultiplier :   groundDamageMultiplier * damage : damage;

            if(pierceArmor){
                h.damagePierce(dmg);
            }else{
                h.damage(dmg);
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);

            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }

        handlePierce(b, health, entity.x(), entity.y());
    }

}
