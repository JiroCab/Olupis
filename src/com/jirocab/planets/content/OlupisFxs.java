package com.jirocab.planets.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class OlupisFxs {

    public static final Effect
            shootSteamSmall = new Effect(33f, 80f, e -> {
                color(Color.valueOf("c9f0f2"), Color.lightGray, Color.gray, e.fin());

                randLenVectors(e.id, 10, e.finpow() * 70f, e.rotation, 10f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f);
                });
            }),
            shootSteamLarge = new Effect(66f, 120f, e -> {
                color(Color.valueOf("c9f0f2"), Color.lightGray, Color.gray, e.fin());

                randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 15f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f);
                });
            });


}

