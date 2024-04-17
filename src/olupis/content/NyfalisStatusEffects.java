package olupis.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;

public class NyfalisStatusEffects {
    public static StatusEffect lubed, mossed, deployed;

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



    }
}