package olupis.world.blocks.misc;

import arc.util.io.Reads;
import mindustry.gen.Building;
import mindustry.world.blocks.legacy.LegacyBlock;

public class DirectionalMendProjector extends LegacyBlock {
    //scrapped

    public  DirectionalMendProjector(String name){
        super(name);
        rotate = true;
    }

    public class DirectionalMendBuild extends Building {

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            read.f(); //heat
            read.f(); //phaseHeat
        }
    }
}
