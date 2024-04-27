package olupis.world.entities.bullets;

import arc.audio.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

import static mindustry.Vars.world;

public class MineBulletType extends BulletType{
    public Block mine;
    public Sound creationSound = Sounds.build,creationFailureSound = Sounds.breaks;

    public boolean createChance;
    public int createChancePercent;
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

    public MineBulletType(Block mine, Effect placeEffect,Integer createChancePercent){
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
            int createChanceRan = (int)Mathf.range(1,99);
            int set;
            if(createChancePercent > 99){
                set = 99;
            } else if (createChancePercent < 1){
                set = 1;
            } else {
                set = createChancePercent;
            }
            if(createChanceRan <= set){
                if(tile.block() == Blocks.air && !tile.floor().isLiquid){
                    if(!occupied){
                        tile.setNet(mine, b.team, (int) b.rotation()/90);
                        placeEffect.at(b.x, b.y, mine.size);
                        creationSound.at(b.x, b.y, Mathf.random(1.2f,3f), soundsVolume);
                    }
                }
            } else {
                creationFailureSound.at(b.x, b.y, Mathf.random(1.2f,3f), soundsVolume);
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
    }
}
