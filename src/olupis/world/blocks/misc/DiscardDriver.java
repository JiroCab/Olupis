package olupis.world.blocks.misc;

import arc.audio.Sound;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.MassDriverBolt;
import mindustry.gen.*;
import mindustry.world.Block;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class DiscardDriver extends Block {
    public float range;
    public float rotateSpeed = 5f;
    public float translation = 7f;
    public int minDistribute = 10;
    public float knockback = 4f;
    public float reload = 100f;
    public MassDriverBolt bullet = new MassDriverBolt();
    public float bulletSpeed = 5.5f;
    public float bulletLifetime = 200f;
    public Effect shootEffect = Fx.shootBig2;
    public Effect smokeEffect = Fx.shootBigSmoke2;
    public Effect receiveEffect = Fx.mineBig;
    public Sound shootSound = Sounds.shootBig;
    public float shake = 3f;


    public DiscardDriver(String name){
        super(name);

        update = true;
        solid = true;
        configurable = true;
        hasItems = true;
        hasPower = true;
        outlineIcon = true;
        sync = true;
        envEnabled |= Env.space;

    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
        stats.add(Stat.reload, 60f / reload, StatUnit.perSecond);
    }


    public class MassDriverBuild extends Building {
        public int link = -1;
        public float rotation = 90;
        public float reloadCounter = 0f;
        public Posc discardPoint;

        @Override
        public void updateTile(){
            //reload regardless of state
            if(reloadCounter > 0f){
                reloadCounter = Mathf.clamp(reloadCounter - edelta() / reload);
            }


            //skip when there's no power
            if(efficiency <= 0f){
                return;
            }

            if(reloadCounter <= 0.0001f){
                rotation = Angles.moveToward(rotateSpeed, angleTo(discardPoint), rotateSpeed * efficiency);
            }



        }
    }
}
