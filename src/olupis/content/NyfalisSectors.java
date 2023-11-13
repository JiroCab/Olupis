package olupis.content;

import arc.func.Cons;
import mindustry.content.Items;
import mindustry.game.Rules;
import mindustry.type.ItemStack;
import mindustry.type.SectorPreset;

import static olupis.content.NyfalisPlanets.*;

public class NyfalisSectors {

    public  static SectorPreset
        /*Arthin*/
        sanctuary, vakinyaDesert, muddyRivers, mossyRavine, gardeniorTemple,
        /*Nyfalis*/
        placeholder2,
        /*Spelta*/
         dormantCell, forestOfHope
    ;

    public static void LoadSectors(){
        //region Seredris
        sanctuary = new SectorPreset("sanctuary", arthin, 2){{
            alwaysUnlocked = overrideLaunchDefaults = true;
            addStartingItems = false;

            captureWave = 15;
            difficulty = 1;
            rules = commonRules(captureWave);
        }};

        mossyRavine = new SectorPreset("mossy-ravine", arthin, 31){{
            addStartingItems = allowLaunchLoadout = false;
            allowLaunchSchematics = true;

            difficulty = 2;
            captureWave = 33;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 700, Items.lead, 700, NyfalisItemsLiquid.iron, 250));
        }};

        muddyRivers = new SectorPreset("muddy-rivers", arthin, 21){{
            addStartingItems = allowLaunchSchematics = true;
            allowLaunchLoadout = false;

            captureWave = 38;
            difficulty = 2;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 500, Items.lead, 500));
        }};

        vakinyaDesert = new SectorPreset("vakinya-desert", arthin, 10){{
            addStartingItems = allowLaunchLoadout  = false;
            allowLaunchSchematics = true;

            captureWave = 16;
            difficulty = 2;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 500, Items.lead, 500, NyfalisItemsLiquid.iron, 100));
        }};

        gardeniorTemple = new SectorPreset("gardenior-temple", arthin, 7){{
            addStartingItems = true;
            allowLaunchLoadout = allowLaunchSchematics = false;

            difficulty = 3;
            captureWave = 26;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 700, Items.lead, 700, NyfalisItemsLiquid.iron, 250));
        }};

        //endregion
        //region Nyfalis

        placeholder2 = new SectorPreset("placeholder2", nyfalis, 69){{
            captureWave = 20;
            difficulty = 3;
            rules = commonRules(captureWave);
        }};

        //endregion
        //region Vorgin
        dormantCell = new SectorPreset("dorment-cell", spelta, 1){{
            /*Yes this map's lore may or may not be a reference to command and conquer*/
            difficulty = 4;
            rules = commonRules(captureWave);
        }};

        forestOfHope = new SectorPreset("forest-of-hope", spelta,  4){{
            difficulty = 2;
            rules = commonRules(captureWave);
        }};
    }

    //moved it here, so players switching planets rule isn't affected & per map dropZonesRadius are possible
    public static void defaultRules(Rules r, float dzRadius, ItemStack[] startItems){
        if(dzRadius <= 0)r.dropZoneRadius = 400f;
        else r.dropZoneRadius = dzRadius;
        r.enemyCoreBuildRadius = 650f;

        NyfalisBlocks.nyfalisBuildBlockSet.each(b -> r.bannedBlocks.add(b));
        NyfalisBlocks.sandBoxBlocks.each(b -> r.bannedBlocks.add(b));
        if(startItems.length >= 1)r.loadout.set(startItems);
    }

    public static void defaultRules(Rules r){
        defaultRules(r, -1, ItemStack.with(NyfalisItemsLiquid.rustyIron, 100, Items.lead, 100));
    }
    public static void defaultRules(Rules r, float dzRadius){
        defaultRules(r, dzRadius);
    }

    public static Cons<Rules> commonRules(int captureWave, float dzRadius, ItemStack[] startItems){ return r ->{
        r.winWave = captureWave;
        defaultRules(r, dzRadius, startItems);
    };}

    public static Cons<Rules> commonRules(int captureWave, ItemStack[] startItems){
        return commonRules(captureWave, -1f, startItems);
    }

    public static Cons<Rules> commonRules(int captureWave){
        return commonRules(captureWave, -1f);
    }
    public static Cons<Rules> commonRules(int captureWave, float dzRadius){
        return commonRules(captureWave,  dzRadius, ItemStack.with(NyfalisItemsLiquid.rustyIron, 100, Items.lead, 100));
    }

}
