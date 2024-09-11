package olupis.world.blocks.misc;

import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.ControlBlock;
import olupis.content.NyfalisUnits;

import static mindustry.Vars.net;

public class MechPad extends Block {
    UnitType type = NyfalisUnits.scarab;
    public float unPowerThershold = 0.3f;
    public float lowPowerThershold = 0.7f;
    public StatusEffect lowPowerStatus = StatusEffects.slow;
    public StatusEffect unPowerStatus = StatusEffects.unmoving;

    public  MechPad(String name){
        super(name);
        update = true;
    }

    public class MechPadBuild extends Building implements ControlBlock{
        public int readUnitId = -1;
        public @Nullable Unit slave;

        @Override
        public void updateTile(){
            //unit was lost/destroyed
            if(slave != null && (slave.dead || !slave.isAdded())) slave = null;

            if(readUnitId != -1){
                slave = Groups.unit.getByID(readUnitId);
                if(slave != null || !net.client()) readUnitId = -1;
            }

            if(slave == null && Units.canCreate(team, type) && efficiency > unPowerThershold){
                if(!net.client()){
                    slave = type.create(team);
                    slave.set(x, y);
                    slave.rotation = 90f;
                    slave.add();
                    readUnitId = slave.id;
                }
            }

            if (slave != null){
                if(efficiency < unPowerThershold) slave.apply(unPowerStatus, 5 * Time.toSeconds);
                else if(efficiency < lowPowerThershold) slave.apply(lowPowerStatus, 5 * Time.toSeconds);
            }
        }

        @Override
        public boolean canControl(){
            return slave != null;
        }

        @Override
        public Unit unit(){//make sure stats are correct
            return slave;
        }

        public byte version() {
            return 1;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision >= 1)readUnitId = read.i();
        }


        @Override
        public void write(Writes write) {
            super.write(write);

            write.i(slave == null ? -1 : slave.id);
        }

    }
}
