package olupis.content;

import arc.func.Cons;
import mindustry.content.Items;
import mindustry.game.Rules;
import mindustry.type.ItemStack;
import mindustry.type.SectorPreset;

import static olupis.content.NyfalisPlanets.*;

public class NyfalisSectors {
    public static final float sectorVersion = 1.1f;

    public static SectorPreset
        /*Arthin / Seredris*/
        sanctuary, vakinyaDesert, mossyCaverns, muddyLakes, citadelOfOasis, inundataDesert, abandonedTrainStations, conciditRuins, frostbiteBasin,
        /*Nyfalis*/
        conservatorium, forestOfSerenity,
        /*Spelta / Vorgin*/
         dormantCell, forestOfHope
    ;

    public static void LoadSectors(){
        //region Seredris
        sanctuary = new SectorPreset("sanctuary", arthin, 2){{
            alwaysUnlocked = overrideLaunchDefaults =  true;
            addStartingItems = allowLaunchSchematics = false;

            captureWave = 15;
            difficulty = 1;
            rules = commonRules(captureWave);
        }};

        mossyCaverns = new SectorPreset("mossy-caverns", arthin, 31){{
            overrideLaunchDefaults =  true;
            addStartingItems = allowLaunchLoadout = allowLaunchSchematics = false;

            difficulty = 2;
            captureWave = 23;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 200, Items.lead, 150));
        }};

        muddyLakes = new SectorPreset("muddy-lakes", arthin, 21){{
            addStartingItems  = overrideLaunchDefaults =  true;
            allowLaunchLoadout = allowLaunchSchematics = false;

            captureWave = 38;
            difficulty = 2;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 500, Items.lead, 500));
        }};

        vakinyaDesert = new SectorPreset("vakinya-desert", arthin, 10){{
            overrideLaunchDefaults =  true;
            addStartingItems = allowLaunchLoadout = allowLaunchSchematics  = false;

            captureWave = 16;
            difficulty = 2;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 1000, Items.lead, 1000, NyfalisItemsLiquid.iron, 200));
        }};

        citadelOfOasis = new SectorPreset("citadel-of-oasis", arthin, 11){{
            addStartingItems = overrideLaunchDefaults =  true;
            allowLaunchLoadout = allowLaunchSchematics =  false;

            difficulty = 3.5f;
            captureWave = 26;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 2000, Items.lead, 2000, NyfalisItemsLiquid.iron, 750, Items.copper, 500,  Items.graphite, 350, NyfalisItemsLiquid.quartz, 475));
        }};

        inundataDesert = new SectorPreset("inundata-desert", arthin, 7){{
            addStartingItems = overrideLaunchDefaults =  true;
            allowLaunchLoadout = allowLaunchSchematics =  false;

            difficulty = 4;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 1200, Items.lead, 1200, NyfalisItemsLiquid.iron, 200, Items.copper, 300,  Items.graphite, 200));
        }};

        abandonedTrainStations = new SectorPreset("abandoned-train-station", arthin, 0){{
            addStartingItems = overrideLaunchDefaults =  true;
            allowLaunchLoadout = allowLaunchSchematics =  false;

            difficulty = 5;
            captureWave = 26;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 1500, Items.lead, 1500, NyfalisItemsLiquid.iron, 500, Items.graphite, 500, Items.copper, 500));
        }};

        conciditRuins = new SectorPreset("concidit-ruins", arthin, 6){{
            addStartingItems = overrideLaunchDefaults = true;
            allowLaunchLoadout = allowLaunchSchematics =  false;

            difficulty = 3;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 200, Items.lead, 200, NyfalisItemsLiquid.iron, 50));
        }};

        frostbiteBasin = new SectorPreset("frostbite-basin", arthin, 29){{
            addStartingItems = overrideLaunchDefaults =  true;
            allowLaunchLoadout = allowLaunchSchematics =  false;

            difficulty = 5;
            captureWave = 46;
            rules = commonRules(captureWave, ItemStack.with(NyfalisItemsLiquid.rustyIron, 1000, Items.lead, 1000,Items.copper, 500, Items.graphite, 250, NyfalisItemsLiquid.iron, 550));
        }};

        //endregion
        //region Nyfalis

        conservatorium = new SectorPreset("conservatorium", nyfalis, 0){{
            captureWave = 20;
            difficulty = 3;
            rules = commonRules(captureWave);
        }};

        forestOfSerenity  = new SectorPreset("forest-of-serenity", nyfalis, 43){{
            difficulty = 4;
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
        //endregion
    }

    //moved it here, so players switching planets rule isn't affected & per map dropZonesRadius are possible
    public static void defaultRules(Rules r, float dzRadius, ItemStack[] startItems){
        if(dzRadius <= 0)r.dropZoneRadius = 400f;
        else r.dropZoneRadius = dzRadius;
        r.enemyCoreBuildRadius = 650f;
        r.env = nyfalis.defaultEnv;

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
