package olupis.world;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Fires;
import mindustry.entities.Puddles;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.type.Liquid;
import mindustry.world.Tile;

import static mindustry.Vars.*;

public class NoBoilLiquidBulletType extends BulletType {
    public boolean evaporatePuddles = true;
    public Liquid liquid;
    public final float puddleSize = 6f;
    public final float orbSize = 3f;

    public  NoBoilLiquidBulletType(@Nullable Liquid liquid){
        super(3.5f, 0);

        if(liquid != null){
            this.liquid = liquid;
            this.status = liquid.effect;
            hitColor = liquid.color;
            lightColor = liquid.lightColor;
            lightOpacity = liquid.lightColor.a;
        }

        ammoMultiplier = 1f;
        statusDuration = 60f * 2f;
        despawnEffect = Fx.none;
        hitEffect = Fx.hitLiquid;
        smokeEffect = Fx.none;
        shootEffect = Fx.none;
        drag = 0.001f;
        knockback = 0.55f;
        displayAmmoMultiplier = false;
    }

    public NoBoilLiquidBulletType(){
        this(null);
    }
    @Override
    public void update(Bullet b){
        super.update(b);

        //No, don't boil, just don't

        if(liquid.canExtinguish()){
            Tile tile = world.tileWorld(b.x, b.y);
            if(tile != null && Fires.has(tile.x, tile.y)){
                Fires.extinguish(tile, 100f);
                b.remove();
                hit(b);
            }
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);
        if(evaporatePuddles){
            Draw.color(liquid.color, Tmp.c3.set(liquid.gasColor).a(0.8f), b.time / Mathf.randomSeed(b.id, lifetime));
            Fill.circle(b.x, b.y, orbSize * (b.fin() * 0.9f + 1f));
        }else{
            Draw.color(liquid.color, Color.white, b.fout() / 100f);
            Fill.circle(b.x, b.y, orbSize);
        }
        Draw.reset();
    }
    @Override
    public void despawned(Bullet b){
        super.despawned(b);

        hitEffect.at(b.x, b.y, b.rotation(), liquid.color);
    }

    @Override
    public void hit(Bullet b, float hitx, float hity){
        hitEffect.at(hitx, hity, liquid.color);
        if (!evaporatePuddles) {
            Puddles.deposit(world.tileWorld(hitx, hity), liquid, puddleSize);
        } else {
            Fx.vaporSmall.at(b.x, b.y, liquid.gasColor);
        }


        if(liquid.temperature <= 0.5f && liquid.flammability < 0.3f){
            float intensity = 400f * puddleSize/6f;
            Fires.extinguish(world.tileWorld(hitx, hity), intensity);
            for(Point2 p : Geometry.d4){
                Fires.extinguish(world.tileWorld(hitx + p.x * tilesize, hity + p.y * tilesize), intensity);
            }
        }
    }
}
