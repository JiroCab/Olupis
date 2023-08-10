package olupis.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class NyfalisFxs{
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
        }).layer(Layer.bullet)
    ;

}

