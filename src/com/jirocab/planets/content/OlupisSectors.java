package com.jirocab.planets.content;

import mindustry.type.SectorPreset;

public class OlupisSectors {

    public  static SectorPreset placeholder1, placeholder2;

    public static void LoadSectors(){
        //region sectors

        placeholder1 = new SectorPreset("placeholder1",  OlupisPlanets.olupis, 1){{
            captureWave = 15;
            difficulty = 2;
            addStartingItems = true;
        }};

        placeholder2 = new SectorPreset("placeholder2", OlupisPlanets.olupis, 69){{
            captureWave = 20;
            difficulty = 3;
        }};

        //endregion
    }

}
