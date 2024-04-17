package olupis.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.gen.*;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class NyfalisStatusEffects {
    public static StatusEffect lubed, mossed, deployed, glitch;

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

        glitch = new StatusEffect("glitched"){
            float dmg = 4;
            @Override
            public void update(Unit unit, float time){
                if(unit.isShooting()) unit.damagePierce(dmg);
                if(unit.type.canBoost && !unit.type.flying) unit.elevation = Math.max(unit.elevation - 0.1f * Time.delta, 0f);
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
                stats.add(new Stat("olupis-scram-s"),dmg * 60, StatUnit.perSecond);
            }

            {
                color = NyfalisItemsLiquid.cobalt.color;
                show = true;
                effect = Fx.none;
                speedMultiplier = -0.5f;
            }
        };
    }
}
