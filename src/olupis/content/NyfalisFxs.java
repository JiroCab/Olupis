package olupis.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.stroke;
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
        })
    ;

}

