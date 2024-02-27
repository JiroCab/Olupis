package olupis.world.blocks.misc;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.MassDriverBolt;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class DiscardDriver extends Block {
    public float range;
    public float reload = 100f;
    public float rotateSpeed = 5f;
    public BulletType bullet = new MassDriverBolt();
    public TextureRegion bottomRegion;


    public DiscardDriver(String name){
        super(name);

        update = true;
        solid = true;
        configurable = false;
        hasItems = true;
        hasPower = false;
        outlineIcon = true;
        sync = true;
        envEnabled |= Env.space;

    }

    @Override
    public void load(){
        bottomRegion = Core.atlas.find(name + "-bottom", "olupis-construct-bottom");
        super.load();
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
        stats.add(Stat.reload, 60f / reload, StatUnit.perSecond);
    }


    public class DiscardDriverBuild extends Building {
        public float rotation = 90;
        public float reloadCounter = 1f;
        public float targetX = x, targetY = y;

        @Override
        public boolean acceptItem(Building source, Item item){
            return items.total() < itemCapacity ;
        }

        @Override
        public void updateTile(){
            //reload regardless of state
            if(reloadCounter > 0f){
                reloadCounter = Mathf.clamp(reloadCounter - edelta() / reload);
            }


//            //skip when there's no power
//            if(efficiency <= 0f){
//                return;
//            }

            if(targetX == 0 && targetY == 0){
                targetX = x + Mathf.range(range);
                targetY = y + Mathf.range(range);
            }

            Log.err("x:" + targetX + " y:" + targetY + " r:" + reloadCounter +  " t" +( (rotation - 90) - angleTo(targetX, targetY)) );
            rotation = Angles.moveToward(rotateSpeed, angleTo(targetX, targetY), rotateSpeed * efficiency);
            if(reloadCounter <= 0.0001f && Angles.near(rotation, angleTo(targetX, targetY), 2f) && items.total() >= 1) {
                fire();
            }
        }

        protected  void fire(){
            items.clear();
            Log.err("iwi");

            float  bulletX = x + Angles.trnsx(rotation - 90, targetX, targetY),
                    bulletY = y + Angles.trnsy(rotation - 90, targetX, targetY),
                    angle = tile.angleTo(bulletX, bulletY);

            bullet.create(this, this.team, bulletX, bulletY, angle);
            reloadCounter = 1f;
            targetX = x + Mathf.range(range);
            targetY = y + Mathf.range(range);
        }


        @Override
        public void draw() {
            Draw.rect(bottomRegion, x, y);

            Draw.z(Layer.turret);

            Draw.rect(region,
                    x + Angles.trnsx(rotation + 180f, reloadCounter),
                    y + Angles.trnsy(rotation + 180f, reloadCounter), rotation - 90);
        }
    }
}
