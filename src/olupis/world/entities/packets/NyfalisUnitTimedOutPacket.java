package olupis.world.entities.packets;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.io.TypeIO;
import mindustry.net.Packet;
import olupis.world.entities.units.AmmoLifeTimeUnitType;

public class NyfalisUnitTimedOutPacket extends Packet {
    private byte[] DATA;
    public Unit unit;

    public NyfalisUnitTimedOutPacket() {
        this.DATA = NODATA;
    }

    public void write(Writes WRITE) {
        TypeIO.writeUnit(WRITE, this.unit);
    }

    public void read(Reads READ, int LENGTH) {
        this.DATA = READ.b(LENGTH);
    }

    public void handled() {
        BAIS.setBytes(this.DATA);
        this.unit = TypeIO.readUnit(READ);
    }

    public void handleClient() {
        if(this.unit.type instanceof AmmoLifeTimeUnitType u) u.timedOut(this.unit);
        else Units.unitDespawn(this.unit);
    }
}
