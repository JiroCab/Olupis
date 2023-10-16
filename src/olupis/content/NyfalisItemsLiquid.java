package olupis.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;

import static mindustry.Vars.content;

public class NyfalisItemsLiquid {

    public static Item condensedBiomatter, rustyIron, iron, cobalt, quartz, cryoRods;
    public static final Seq<Item> nyfalisOnlyItems = new Seq<>(), nyfalisItems = new Seq<>();
    public static Liquid heavyOil, lightOil, steam;

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
        cobalt = new Item("cobalt", Color.valueOf("0b6e87")) {{
            hardness = 2;
            charge = 0.5f;
            healthScaling = 0.25f;
        }};
        quartz = new Item("quartz", Color.valueOf("E2D6D5")){{
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
    }
}
