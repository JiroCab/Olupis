package olupis.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.gen.*;
import mindustry.world.meta.Stat;

import java.util.HashMap;

public class NyfalisStatusEffects {
    public static StatusEffect lubed, mossed, deployed, corupt, malfuct, glitch, sloppy, unloaded, drained;

    public static void loadStatusEffects(){

        lubed = new StatusEffect("lubed"){{
            color = Color.valueOf("6b675f");
            speedMultiplier = 1.15f;
            effect = Fx.oily;

            init(() -> this.affinities = StatusEffects.tarred.affinities);
        }};

        deployed = new StatusEffect("deployed"){{
            color = Color.valueOf("DE9458");
            speedMultiplier = 0f;
            effectChance = 0.25f;
            healthMultiplier = 1.15f;
        }};

        corupt = new StatusEffect("corupt"){
            @Override
            public void update(Unit unit, float time){
                if(unit.type.canBoost && !unit.type.flying) unit.elevation = Math.max(unit.elevation - 0.1f * Time.delta, 0f);
                if(unit.hasEffect(malfuct)){
                    unit.unapply(this);
                    unit.unapply(malfuct);
                    if(Core.settings.getBool("nyfalis-rainbow-music")){
                        unit.apply(glitch,12 * 60);
                        NyfalisSounds.rainbow.at(unit.x, unit.y);
                    } else {
                        unit.apply(glitch,180);
                    }
                }
            }
            @Override
            public void draw(Unit unit){
                Draw.z(Layer.flyingUnit + 0.05f);
                Draw.blend(Blending.additive);
                float f = Mathf.sin(Time.time / 5f) * 3f;
                float a = Mathf.random();
                int c = (int)(Mathf.randomSeed(unit.id) * 3f);
                Draw.color(c == 0 ? Color.valueOf("4f4b62") : c == 1 ? Color.valueOf("737383") : Color.valueOf("5d5d74"), a);
                Draw.rect(unit.icon(), unit.x + f, unit.y, unit.rotation - 90f);
                Draw.color(c == 0 ? Color.valueOf("5d5d74") : c == 1 ? Color.valueOf("4f4b62") : Color.valueOf("737383"), a);
                Draw.rect(unit.icon(), unit.x - f, unit.y, unit.rotation - 90f);
                Draw.blend();
                Draw.color();
            }
            @Override
            public void setStats(){
                super.setStats();
                stats.remove(Stat.speedMultiplier);
                stats.add(new Stat("olupis-scram-m"),"");
                stats.add(new Stat("olupis-scram-b"),"");
                stats.add(new Stat("olupis-scram-s"), "");
            }

            {
                color = NyfalisItemsLiquid.cobalt.color;
                show = true;
                effect = Fx.none;
                speedMultiplier = -0.5f;
            }
        };
        malfuct = new StatusEffect("malfuct"){
            float dmg = 4;
            @Override
            public void update(Unit unit, float time){
                if(unit.isShooting()) unit.damagePierce(dmg);
                if(unit.type.canBoost && !unit.type.flying) unit.elevation = Math.max(unit.elevation - 0.1f * Time.delta, 0f);
                if(unit.hasEffect(corupt)){
                    unit.unapply(this);
                    unit.unapply(corupt);
                    if(Core.settings.getBool("nyfalis-rainbow-music")){
                        unit.apply(glitch,12 * 60);
                        NyfalisSounds.rainbow.at(unit.x, unit.y);
                    } else {
                        unit.apply(glitch,180);
                    }
                }
            }
            @Override
            public void draw(Unit unit){
                Draw.z(Layer.flyingUnit + 0.05f);
                Draw.blend(Blending.additive);
                float f = Mathf.sin(Time.time / 5f) * 3f;
                float r = Mathf.sin(Time.time / 3f) * 60f;
                float a = Mathf.random();
                int c = (int)(Mathf.randomSeed(unit.id) * 3f);

                //TODO nanite colors put em here

                Draw.color(c == 0 ? Color.valueOf("4f4b62"/*TODO nanite colors*/) : c == 1 ? Color.valueOf("737383"/*TODO nanite colors*/) : Color.valueOf("5d5d74"), a);
                Draw.rect(Core.atlas.find("malfuct-effect"/*TODO effect sprite*/), unit.x - f, unit.y, unit.rotation - r);
                Draw.color(c == 0 ? Color.valueOf("5d5d74"/*TODO nanite colors*/) : c == 1 ? Color.valueOf("4f4b62"/*TODO nanite colors*/) : Color.valueOf("737383"), a);
                Draw.rect(Core.atlas.find("malfuct-effect"/*TODO effect sprite*/), unit.x - f, unit.y, unit.rotation - r);
                Draw.blend();
                Draw.color();
            }
            {
                //TODO color = NyfalisItemsLiquid.nanite.color;
                show = true;
            }
        };
        glitch = new StatusEffect("glitch"){
            {
             buildSpeedMultiplier = 0;
            }
            public HashMap<Unit, Integer> U = new HashMap<>();
            public HashMap<Unit, Float> unitTime = new HashMap<>();
            public HashMap<Unit, Team> unitTeam = new HashMap<>();

            public void start(Unit unit,float time)
            {
                if (!U.containsKey(unit))
                {{
                    U.put(unit, 0);
                    unitTime.put(unit,time);
                    unitTeam.put(unit,unit.team);
                }}
            }

            public void end(Unit unit)
            {
                U.remove(unit);
                unitTime.remove(unit);
                unit.team(unitTeam.get(unit));
                unitTeam.remove(unit);
            }

            @Override
            public void update(Unit unit, float time){
                if (!U.containsKey(unit))
                    start(unit,time);
                if (unitTime.get(unit) < time)
                    start(unit,time);
                unitTime.replace(unit,time);


                unit.team(Team.get(Mathf.random(0,250)));

                if (U.containsKey(unit) && (time <= Time.delta * 2f || !unit.isValid()))
                {{
                    end(unit);
                }}
            }
        };

        sloppy = new StatusEffect("sloppy"){{
            color = Color.valueOf("6b675f");
            speedMultiplier = 1.15f;
            effect = Fx.oily;

            init(() -> this.affinities = StatusEffects.tarred.affinities);
            init(() -> this.opposites = StatusEffects.tarred.opposites);
        }};

        mossed = new StatusEffect("mossed"){{
            color = Color.valueOf("5a9e70");
            speedMultiplier = reloadMultiplier = 0.9f;
            transitionDamage = 80;
            init(() -> {
                affinity(StatusEffects.burning, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                });
            });
        }};

        unloaded = new StatusEffect("unloaded"){{
            color = Color.valueOf("6b675f");
            effect = Fx.oily;
            show = false;
        }};

        drained = new StatusEffect("drained"){{
            color = Color.valueOf("A258A3");
            speedMultiplier = healthMultiplier = 0.9f;
        }};
    }
}
