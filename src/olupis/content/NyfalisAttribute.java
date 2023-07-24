package olupis.content;

import mindustry.world.meta.Attribute;

import static mindustry.content.Blocks.*;
import static olupis.content.NyfalisBlocks.*;

public class NyfalisAttribute {
    /*Used by the biomatter compressor */
    public static final Attribute bio = Attribute.add("bio");
    /*Used by hydroMill yield*/
    public static final Attribute hydro = Attribute.add("hydro");

    public static void AddAttributes(){
        ice.attributes.set(bio, 0.01f);
        grass.attributes.set(bio, 0.1f);
        dirt.attributes.set(bio, 0.03f);
        mud.attributes.set(bio, 0.03f);
        stone.attributes.set(bio, 0.03f);
        charr.attributes.set(bio, 0.03f);
        snow.attributes.set(bio, 0.01f);
        craters.attributes.set(bio, 0.5f);

        water.attributes.set(hydro, 0.3f);
        deepwater.attributes.set(hydro, 0.5f);
        sandWater.attributes.set(hydro, 0.3f);
        taintedWater.attributes.set(hydro, 0.3f);
        darksandWater.attributes.set(hydro, 0.3f);
        deepTaintedWater.attributes.set(hydro, 0.3f);
        darksandTaintedWater.attributes.set(hydro, 0.3f);

        mossyWater.attributes.set(hydro, 0.3f);
        redSandWater.attributes.set(hydro, 0.3f);
        pinkGrassWater.attributes.set(hydro, 0.3f);
        lumaGrassWater.attributes.set(hydro, 0.3f);
        yellowMossyWater.attributes.set(hydro, 0.3f);

        mossyStone.asFloor().decoration = bush;
        mossyStone.asFloor().decoration = boulder;
        cinderBloomy.asFloor().decoration = basaltBoulder;
    }
}
