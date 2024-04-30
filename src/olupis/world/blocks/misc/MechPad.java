package olupis.world.blocks.misc;

import arc.util.Nullable;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.ControlBlock;
import olupis.content.NyfalisUnits;

public class MechPad extends Block {
    UnitType type = NyfalisUnits.scarab;
    public  MechPad(String name){
        super(name);
        update = true;
    }

    public class MechPadBuild extends Building implements ControlBlock{
        public @Nullable Unit salve;

        @Override
        public Unit unit(){
            if(salve == null){
                salve = type.spawn(this.team, this.x, this.y);
            }
            //make sure stats are correct
            return salve;
        }


    }
}
