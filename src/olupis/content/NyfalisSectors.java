package olupis.content;

import mindustry.type.SectorPreset;

import static olupis.content.NyfalisPlanets.*;

public class NyfalisSectors {

    public  static SectorPreset
            /*Arthin*/
            sanctuary,
            /*Nyfalis*/
            placeholder1, placeholder2,
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
        }};

        //endregion
        //region Nyfalis
        placeholder1 = new SectorPreset("placeholder1",  nyfalis, 1){{
            addStartingItems = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};

        placeholder2 = new SectorPreset("placeholder2", nyfalis, 69){{
            captureWave = 20;
            difficulty = 3;
        }};
        //endregion
        //region Vorgin
        dormantCell = new SectorPreset("dorment-cell", spelta, 1){{
            /*Yes this map's lore may or may not be a reference to command and conquer*/
            difficulty = 4;
        }};

        forestOfHope = new SectorPreset("forest-of-hope", spelta,  4){{
            difficulty = 2;
        }};

    }
}
