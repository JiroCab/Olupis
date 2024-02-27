package olupis.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;

import static mindustry.Vars.content;

public class NyfalisItemsLiquid {

    public static Item condensedBiomatter, rustyIron, iron, cobalt, quartz, alcoAlloy, aluminum, alucryoRods, steel, silicatePowder;
    public static final Seq<Item> nyfalisOnlyItems = new Seq<>(), nyfalisItems = new Seq<>();
    public static Liquid heavyOil, lightOil, steam, lubricant;

    public static  void LoadItems(){
        //region Items
        /*Texture is from Tech reborn: https://github.com/TechReborn/TechReborn/blob/1.19/src/main/resources/assets/techreborn/textures/item/part/compressed_plantball.png*/
        condensedBiomatter = new Item("condensed-biomatter", Color.valueOf("5a9e70")) {{
            buildable = false;

            flammability = 1.2f;
        }};
        rustyIron = new Item("rusty-iron", Color.valueOf("8E320A")) {{
            hardness = 1;
        }};
        iron = new Item("iron", Color.valueOf("989AA4")) {{
            hardness = 2;
            healthScaling = 0.25f;
        }};
        cobalt = new Item("cobalt", Color.valueOf("5D5D74")) {{
            hardness = 2;
            charge = 0.5f;
            healthScaling = 0.25f;
        }};
        quartz = new Item("quartz", Color.valueOf("494963")){{
            hardness = 2;
        }};

        aluminum = new Item("aluminum", Color.valueOf("6D6D6D")){{
            hardness = 2;
        }};

        alcoAlloy = new Item("aloco-alloy", Color.valueOf("546295")){{
            hardness = 2;
        }};


        nyfalisOnlyItems.addAll(rustyIron,iron,condensedBiomatter,cobalt, quartz);
        nyfalisItems.add(nyfalisOnlyItems);
        nyfalisItems.add(Items.serpuloItems);

        /*.forEach() Crashes mobile*/
        for (Planet p : content.planets()) {
            if (!p.name.contains("olupis-")) p.hiddenItems.addAll(NyfalisItemsLiquid.nyfalisOnlyItems);
        }
    }

    public static void LoadLiquids(){
        heavyOil = lightOil  = Liquids.oil;

        steam = new Liquid("steam", Color.valueOf("E0DAE9")){{
            /*hacky way so it acts like a liquid in NoBoilLiquidBulletType  but a gas outside of thar*/
            gas = coolant = false;

            heatCapacity = 0.35f;
            boilPoint = 0f;
            effect = StatusEffects.corroded;
        }};

        lubricant = new Liquid("lubricant", Color.valueOf("787878")){{
            viscosity = 0.7f;
            flammability = 1.35f;
            explosiveness = 1.25f;
            heatCapacity = 0.86f;
            barColor = Color.valueOf("6b675f");
            effect = NyfalisStatusEffects.lubed;
            boilPoint = 0.65f;
            gasColor = Color.grays(0.4f);
            canStayOn.addAll(Liquids.water, Liquids.oil);
        }};

        Liquids.water.canStayOn.add(lubricant);
        Liquids.oil.canStayOn.add(lubricant);
    }
}
