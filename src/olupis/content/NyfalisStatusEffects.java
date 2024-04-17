package olupis.content;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
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
                transitionDamage = 80;
                effect = NyfalisFxs.glitch;
                speedMultiplier = -0.5f;
                reactive = true;
                init(() -> {
                    affinity(StatusEffects.overclock, (unit, result, time) -> {
                        unit.damagePierce(transitionDamage);
                        Fx.coreBuildShockwave.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    });
                });
            }
        };
    }
}
