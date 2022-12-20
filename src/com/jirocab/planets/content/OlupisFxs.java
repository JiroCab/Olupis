package com.jirocab.planets.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class OlupisFxs{

    public static final Effect
        shootSteamSmall = new Effect(33f, 80f, e -> {
            color(Pal.lancerLaser, Color.lightGray, Color.gray, e.fin());

            randLenVectors(e.id, 10, e.finpow() * 70f, e.rotation, 10f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 0.85f + e.fout() * 1.6f);
            });
        }),
        //TODO: broken
        shootSteamLarge = new Effect(33f, 80f, e -> {
            color(Pal.lancerLaser, Color.lightGray, Color.gray, e.fin());

            randLenVectors(e.id, 10, e.finpow() * 70f, e.rotation, 10f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 1f + e.fout() * 1.6f);
            });
        });
}

