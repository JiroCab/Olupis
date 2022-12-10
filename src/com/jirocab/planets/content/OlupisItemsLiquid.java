package com.jirocab.planets.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;

public class OlupisItemsLiquid {

    public static Item condensedBiomatter, rustyIron, iron, cobalt;
    public static Planet[] hideList = new Planet[]{Planets.erekir, Planets.serpulo, Planets.tantros};
    public static final Seq<Item> nonOlupisItems = new Seq<>(), olupisOnlyItems = new Seq<>();
    public static Liquid heavyOil, lightOil, steam;

    public static  void LoadItems(){
        //region Items
        /*Texture is from Tech reborn: https://github.com/TechReborn/TechReborn/blob/1.19/src/main/resources/assets/techreborn/textures/item/part/compressed_plantball.png*/
        condensedBiomatter = new Item("condensed-biomatter", Color.valueOf("5a9e70")) {{
            flammability = 1.2f;
            buildable = false;
            hiddenOnPlanets = hideList;
        }};
        rustyIron = new Item("rusty-iron", Color.valueOf("ccac8b")) {{
            buildable = false;
            hardness = 1;
            hiddenOnPlanets = hideList;
        }};
        iron = new Item("iron", Color.valueOf("f0ece4")) {{
            hardness = 2;
            healthScaling = 0.25f;
            buildable = false;
            hiddenOnPlanets = hideList;
        }};
        cobalt = new Item("cobalt", Color.valueOf("0b6e87")) {{
            hardness = 2;
            charge = 0.5f;
            healthScaling = 0.25f;
            buildable = false;
        }};

        nonOlupisItems.add(Items.erekirItems);
        nonOlupisItems.add(Items.serpuloItems);
        olupisOnlyItems.addAll(rustyIron, iron, condensedBiomatter, cobalt, Items.sand, Items.lead, Items.graphite, Items.silicon);

    }

    public static void LoadLiquids(){
        heavyOil = new Liquid("heavy-oil", Color.valueOf("1A1919")){{
            viscosity = 0.75f;
            flammability = 1.2f;
            explosiveness = 1.2f;
            heatCapacity = 0.7f;
            barColor = Color.valueOf("6b675f");
            effect = StatusEffects.tarred;
            boilPoint = 0.65f;
            gasColor = Color.grays(0.4f);
            canStayOn.add(Liquids.water);
        }};
        lightOil = new Liquid("light-oil", Color.valueOf("4B4A49")){{
            viscosity = 0.75f;
            flammability = 1.2f;
            explosiveness = 1.2f;
            heatCapacity = 0.7f;
            barColor = Color.valueOf("6b675f");
            effect = StatusEffects.tarred;
            boilPoint = 0.65f;
            gasColor = Color.grays(0.4f);
            canStayOn.add(Liquids.water);
        }};

        steam = new Liquid("steam", Color.valueOf("efe3ff")){{
            gas = true;
            effect = StatusEffects.corroded;
        }};
    }
}
