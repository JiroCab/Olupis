package olupis.world.entities.bullets;

import arc.audio.*;
import arc.math.*;
import arc.util.Log;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

import static mindustry.Vars.world;

public class MineBulletType extends BulletType{
    public Block mine;
    public Sound creationSound = Sounds.mineDeploy,creationFailureSound = Sounds.boom;

    public boolean createChance;
    public float createChancePercent;
    public float soundsVolume = 0.75f;

    public Effect placeEffect;


    public MineBulletType(Block mine, Effect placeEffect){
        super();
        this.mine = mine;
        this.placeEffect = placeEffect;

        absorbable = hittable = createChance = false;
        speed = createChancePercent = 0;
        collides = false;
        hitEffect = despawnEffect = shootEffect = smokeEffect = Fx.none;
        layer = Layer.debris;
        displayAmmoMultiplier = false;
    }

    public MineBulletType(Block mine, Effect placeEffect,Float createChancePercent){
        super();
        this.mine = mine;
        this.placeEffect = placeEffect;
        createChance = true;
        this.createChancePercent = createChancePercent;

        absorbable = hittable = false;
        damage = 0;
        speed = 0;
        collides = false;
        hitEffect = despawnEffect = shootEffect = smokeEffect = Fx.none;
        layer = Layer.debris;
        displayAmmoMultiplier = false; 
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        Tile tile = world.tile((int)b.x/8, (int)b.y/8);
        //just in case
        if(tile == null) return;
        boolean occupied = Groups.unit.intersect(b.x, b.y, 1, 1).contains(Flyingc::isGrounded);
        if (createChance){
            float createChanceRan = Mathf.random(0.01f,0.99f);
            float set;
            if(createChancePercent > 0.99f){
                set = 0.99f;
            } else if (createChancePercent < 0.01f){
                set = 0.01f;
            } else {
                set = createChancePercent;
            }
            if(createChanceRan <= set){
                if(tile.block() == Blocks.air && !tile.floor().isLiquid){
                    if(!occupied){
                        tile.setNet(mine, b.team, (int) b.rotation()/90);
                        placeEffect.at(b.x, b.y, mine.size);
                        creationSound.at(b.x, b.y, 1.2f, soundsVolume);
                    }
                }
            } else {
                creationFailureSound.at(b.x, b.y, 1.2f, soundsVolume);
            }
        } else {
            if(tile.block() == Blocks.air && !tile.floor().isLiquid){
                if(!occupied){
                    tile.setNet(mine, b.team, (int) b.rotation()/90);
                    placeEffect.at(b.x, b.y, mine.size);
                    creationSound.at(b.x, b.y, Mathf.random(0.8f, 1.2f), soundsVolume);
                }
            }
        }
        b.remove();
    }
}
