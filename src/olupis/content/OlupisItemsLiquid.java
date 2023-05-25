package olupis.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;

public class OlupisItemsLiquid {

    public static Item condensedBiomatter, rustyIron, iron, cobalt;
    public static Planet[] hideList = new Planet[]{Planets.erekir, Planets.serpulo, Planets.tantros};
    public static final Seq<Item> olupisOnlyItems = new Seq<>(), olupisItems = new Seq<>();
    public static Liquid heavyOil, lightOil, steam;

    public static  void LoadItems(){
        //region Items
        /*Texture is from Tech reborn: https://github.com/TechReborn/TechReborn/blob/1.19/src/main/resources/assets/techreborn/textures/item/part/compressed_plantball.png*/
        condensedBiomatter = new Item("condensed-biomatter", Color.valueOf("5a9e70")) {{
            buildable = false;

            flammability = 1.2f;

            hiddenOnPlanets = hideList;
        }};
        rustyIron = new Item("rusty-iron", Color.valueOf("ccac8b")) {{
            buildable = false;

            hardness = 1;

            hiddenOnPlanets = hideList;
        }};
        iron = new Item("iron", Color.valueOf("f0ece4")) {{
            buildable = false;

            hardness = 2;
            healthScaling = 0.25f;

            hiddenOnPlanets = hideList;
        }};
        cobalt = new Item("cobalt", Color.valueOf("0b6e87")) {{
            buildable = false;

            hardness = 2;
            charge = 0.5f;
            healthScaling = 0.25f;
        }};

        olupisOnlyItems.addAll(rustyIron,iron,condensedBiomatter,cobalt);
        olupisItems.add(olupisOnlyItems);
        olupisItems.add(Items.serpuloItems);

    }

    public static void LoadLiquids(){
        heavyOil = new Liquid("heavy-oil", Color.valueOf("1A1919")){{
            viscosity = 0.75f;
            flammability = 1.2f;
            explosiveness = 1.2f;
            heatCapacity = 0.7f;
            boilPoint = 0.65f;
            effect = StatusEffects.tarred;
            canStayOn.add(Liquids.water);

            barColor = Color.valueOf("6b675f");
            gasColor = Color.grays(0.4f);
        }};
        lightOil = new Liquid("light-oil", Color.valueOf("4B4A49")){{
            viscosity = 0.75f;
            flammability = 1.2f;
            explosiveness = 1.2f;
            heatCapacity = 0.7f;
            boilPoint = 0.65f;
            effect = StatusEffects.tarred;
            canStayOn.add(Liquids.water);

            barColor = Color.valueOf("6b675f");
            gasColor = Color.grays(0.4f);
        }};

        steam = new Liquid("steam", Color.valueOf("E0DAE9")){{
            /*hacky way so it acts like a liquid in NoBoilLiquidBulletType  but a gas outside of thar*/
            gas = coolant = false;

            heatCapacity = 0.35f;
            boilPoint = 0f;
            effect = StatusEffects.corroded;
        }};
    }
}
