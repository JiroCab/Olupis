package olupis.world.entities.pattern;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootAlternate;

public class ShootAlternateAlt extends ShootAlternate {

    public ShootAlternateAlt(float spread){
       super(spread);
    }

    public ShootAlternateAlt(){

    }


    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int i = 0; i < shots; i++){
            float index = ((totalShots + i + barrelOffset) % barrels) - (barrels-1)/2f;
            handler.shoot(index * spread, 0, 0f, firstShotDelay + shotDelay * i);
        }
        if(barrelIncrementer != null) barrelIncrementer.run();
    }

}
