package olupis.world.blocks.distribution;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.bullet.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class DiscardDriver extends Block {
    public float range;
    public float reload = 100f;
    public float rotateSpeed = 5f;
    public float translation = 2f;
    public BulletType bullet = new BasicBulletType();
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


    public void limitRange(float margin){
        float realRange = bullet.rangeChange + range;
        bullet.lifetime = (realRange + margin) / bullet.speed;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
        stats.add(Stat.reload, 60f / reload, StatUnit.perSecond);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
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

            rotation = Angles.moveToward(rotation, angleTo(targetX, targetY), rotateSpeed * efficiency);
            if(reloadCounter <= 0.0001f && Angles.near(rotation, angleTo(targetX, targetY), 2f) && items.total() >= 1) {
                fire();
            }
        }

        protected  void fire(){
            items.clear();

            float  bulletX = x + Angles.trnsx(rotation, targetX, targetY),
                    bulletY = y + Angles.trnsy(rotation, targetX, targetY),
                    angle = tile.angleTo(bulletX, bulletY),
                    lifeScl = bullet.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, targetX, targetY) / bullet.range) : 1f;

            bullet.create(this, team,
                    x + Angles.trnsx(angle, translation), y + Angles.trnsy(angle, translation),
                    angle, -1f, bullet.speed, lifeScl, null, null, targetX, targetY);
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

        @Override
        public void drawSelect(){
            super.drawSelect();

            if(Core.settings.getBool("nyfalis-debug") && targetX != 0 && targetY != 0){
                Drawf.target(targetX, targetY, 1f, Color.scarlet);
            }

            Drawf.dashCircle(x, y, range, Pal.accent);
        }

    }
}
