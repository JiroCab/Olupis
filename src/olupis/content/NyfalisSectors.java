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
             dormantCell, placeholderSpelta1
        ;

    public static void LoadSectors(){
        //region Arthin
        sanctuary = new SectorPreset("sanctuary", arthin, 2){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 0;
        }};

        //endregion
        //region Nyfalis
        placeholder1 = new SectorPreset("placeholder1",  nyfalis, 1){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};

        placeholder2 = new SectorPreset("placeholder2", nyfalis, 69){{
            captureWave = 20;
            difficulty = 3;
        }};
        //endregion
        //region Spelta
        placeholderSpelta1 = new SectorPreset("placeholderSpelta1",  spelta, 0){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};



    }
}
