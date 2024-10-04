package olupis.world.entities.bullets;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.Damage;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;

public class TracterBeamBullet extends ContinuousLaserBulletType {
    public TextureRegion laserEndSprite;
    public TextureRegion laserStartSprite;
    public TextureRegion laserSprite;
    public String laserEnd = "parallax-laser-end";
    public String laserStart = laserEnd;
    public String laser = "parallax-laser";

    public TracterBeamBullet(float damage){
        this.damage = damage;
    }

    public TracterBeamBullet(){

    }


    @Override
    public void draw(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);

        if(b.data instanceof  Position data){
            Tmp.v2.set(data);
            for(int i = 0; i < colors.length; i++){
                Draw.color(Tmp.c1.set(colors[i]));

                Draw.mixcol(colors[i], Mathf.absin(4f, 0.6f));
                float stroke = (width) * fout ;
                Lines.stroke(stroke);

                Drawf.laser(laserSprite, laserStartSprite, laserEndSprite,
                        b.x, b.y, Tmp.v2.x,  Tmp.v2.y,
                        stroke);
                Draw.rect(laserEndSprite, Tmp.v2.x,  Tmp.v2.y, laserEndSprite.width * stroke * laserEndSprite.scl(), laserEndSprite.height * stroke * laserEndSprite.scl(), b.rotation() + 180);
            }
            Draw.reset();
            Drawf.light(b.x, b.y, Tmp.v2.x,  Tmp.v2.y, lightStroke, lightColor, 0.7f);

        }
        Draw.reset();
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), length);
        boolean check = target instanceof  Hitboxc || target instanceof  Building;
        b.data = check ? target : new Vec2().trns(b.rotation(), length).add(b.x, b.y);
    }


    @Override
    public void load() {
        super.load();
        laserEndSprite = Core.atlas.find(laserEnd);
        laserStartSprite = Core.atlas.find(laserStart);
        laserSprite = Core.atlas.find(laser);
    }

    @Override
    public void applyDamage(Bullet b){

        if(b.data instanceof Hitboxc hit){
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        }else if(b.data instanceof Building tile){
            if(tile.collide(b)){
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        }
    }
}
