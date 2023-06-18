package olupis.content;

import mindustry.type.SectorPreset;

public class NyfalisSectors {

    public  static SectorPreset placeholder1, placeholder2, placeholderArthin1, placeholderSpelta1;

    public static void LoadSectors(){
        //region sectors

        placeholder1 = new SectorPreset("placeholder1",  NyfalisPlanets.nyfalis, 1){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};

        placeholder2 = new SectorPreset("placeholder2", NyfalisPlanets.nyfalis, 69){{
            captureWave = 20;
            difficulty = 3;
        }};


        placeholderArthin1 = new SectorPreset("placeholderArthin1",  NyfalisPlanets.arthin, 0){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};

        placeholderSpelta1 = new SectorPreset("placeholderSpelta1",  NyfalisPlanets.spelta, 0){{
            addStartingItems = alwaysUnlocked = overrideLaunchDefaults = true;

            captureWave = 15;
            difficulty = 2;
        }};


        //endregion
    }

}
