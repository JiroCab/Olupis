package olupis.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Unit;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static olupis.content.NyfalisItemsLiquid.rustyIron;

public class NyfalisFxs extends Fx {
    public static final Effect
        hollowPointHit =  new Effect(30f, e -> {
            color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
            alpha(e.fout(0.5f));
            e.scaled(7f, s -> {
                stroke(0.5f + s.fout());
                Lines.circle(e.x, e.y, s.fin() * 3.5f);
            });
            randLenVectors(e.id, 5, e.finpow() * 17f, (x, y) -> Fill.rect(
                e.x + x + Mathf.randomSeedRange((long) (e.id + e.rotation + 7), 3f * e.fin()),
                e.y + y + Mathf.randomSeedRange((long) (e.id + e.rotation + 8), 3f * e.fin()),
                1f, 2f, e.rotation + e.fin() * 50f * e.rotation
            ));
        }).layer(Layer.bullet),

        hollowPointHitSmall =  new Effect(30f, e -> {
            color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
            alpha(e.fout(0.5f));
            e.scaled(7f, s -> {
                stroke(0.5f + s.fout());
                Lines.circle(e.x, e.y, s.fin() * 3.5f);
            });
            randLenVectors(e.id, 1, e.finpow() * 17f, (x, y) -> Fill.rect(
                e.x + x + Mathf.randomSeedRange((long) (e.id + e.rotation + 7), 3f * e.fin()),
                e.y + y + Mathf.randomSeedRange((long) (e.id + e.rotation + 8), 3f * e.fin()),
                1f, 2f, e.rotation + e.fin() * 50f * e.rotation
            ));
        }).layer(Layer.bullet),

        scatterDebris =  new Effect(15f, e -> {
            color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
            randLenVectors(e.id, 1, e.finpow() * 12f, (x, y) -> Fill.rect(
                e.x + x + Mathf.randomSeedRange((long) (e.id + e.rotation + 7), 3f * e.fin()),
                e.y + y + Mathf.randomSeedRange((long) (e.id + e.rotation + 8), 3f * e.fin()),
                1f, 2f, e.rotation + e.fin() * 50f * e.rotation
            ));
        }).layer(Layer.bullet),

        failedMake =  new Effect(30f, e -> {
            color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
            alpha(e.fout(0.5f));
            e.scaled(7f, s -> {
                stroke(0.5f + s.fout());
                Lines.circle(e.x, e.y, s.fin() * 7f);
            });
            randLenVectors(e.id, 5, e.finpow() * 17f, (x, y) -> Fill.rect(
               e.x + x + Mathf.randomSeedRange((long) (e.id + e.rotation + 7), 3f * e.fin()),
               e.y + y + Mathf.randomSeedRange((long) (e.id + e.rotation + 8), 3f * e.fin()),
               1f, 2f, e.rotation + e.fin() * 50f * e.rotation
           ));
            Drawf.light(e.x, e.y, 20f, Pal.lightOrange, 0.6f * e.fout());
        }).layer(Layer.bullet),

        unitBreakdown = new Effect(100f, e -> {
            if(!(e.data instanceof Unit select) || select.type == null) return;

            float scl = e.fout(Interp.pow2Out);
            float p = Draw.scl;
            Draw.scl *= scl;

            mixcol(Pal.darkMetal, 1f);
            rect(select.type.fullIcon, select.x, select.y, select.rotation - 90f);
            Lines.stroke(e.fslope());
            Lines.square(select.x, select.y, (e.fout() * 0.9f) * select.hitSize * 1.5f, 45);
            reset();

            Draw.scl = p;
        }),

        unitDischarge = new Effect(18, e -> {
            color(NyfalisItemsLiquid.rustyIron.color, 0.7f);
            stroke(e.fout() * 2f);
            float s = 16f;
            Lines.circle(e.x, e.y, 30f + e.finpow() * s);
            Lines.square(e.x, e.y, 30f + e.finpow() * -s, 45);
            randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
                color( new Color().set(rustyIron.color).lerp(Pal.stoneGray, 0.7f));
                Fill.square(e.x + x, e.y + y, e.fout() + 1.5f , 45);
            });
        }),

        taurusHeal = new Effect(11, e -> {
            color(Pal.heal);
            stroke(e.fout() * 2f);
            Lines.circle(e.x, e.y, 2f + e.finpow() * (Vars.tilesize * 2));
            Lines.square(e.x, e.y, 2f + e.finpow() * (Vars.tilesize * -2), 45);
        }),
        smolPorpolKaboom = new Effect(25, e -> {
            color(Pal.sapBullet);
            e.scaled(6, i -> {
                stroke(i.fout());
                Lines.circle(e.x, e.y, 3f + i.fin() * 26.66f);
            });

            color(Color.gray);

            randLenVectors(e.id, 9, 2f + 23.33f * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 1.33f + 0.5f));

            color(Pal.sapBulletBack);
            stroke(e.fout());

            randLenVectors(e.id + 1, 8, 1f + 20f * e.finpow(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout()));
            Drawf.light(e.x, e.y, 30f, Pal.sapBulletBack, 0.8f * e.fout());
        }),

        colouredShockwave =  new Effect(8f, 120f, e -> {
            z(Layer.blockProp);
            color(Color.darkGray, e.color, e.fin());
            stroke(e.fout() * 2f + 0.2f);
            Lines.circle(e.x, e.y, e.fin() * 28f);
        }),

        fastSquareSmokeCloud = new Effect(30, e -> {
            z(Layer.blockProp);
            rand.setSeed(e.id);
            color(new Color().set(Color.black).lerp(Color.darkGray, 0.7f), Color.gray, e.fin());
            alpha(Math.max(e.fout() * 2f, 0.45f));
            randLenVectors(e.id, e.fin() , 8, 45f, (x, y, fin, fout) -> {
                //TLDR: have a minimum distance for the smoke to travel, or I think so, feel free to pr any better ideas -Rushie
                Fill.poly(e.x + (Math.max(Math.abs(x), fin + 0.7f) * Math.signum(x)), e.y + (Math.max(Math.abs(y), fin + 0.7f) * Math.signum(y)), 6, 2.4f + fout * 6f , rand.random(360f));
            });
        }),

        shootTaurus = new Effect(14, e -> {
            color(Pal.heal);
            float w = 1f + 5 * e.fout();
            Drawf.tri(e.x, e.y, w, 8f * e.fout(), e.rotation + 45f);
            Drawf.tri(e.x, e.y, w, 8f * e.fout(), e.rotation - 45f);
            Drawf.tri(e.x, e.y, w, 17f * e.fout(), e.rotation);
            Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
        }),

        obliteratorShockwave = new MultiEffect(colouredShockwave, fastSquareSmokeCloud)

    ;

}

