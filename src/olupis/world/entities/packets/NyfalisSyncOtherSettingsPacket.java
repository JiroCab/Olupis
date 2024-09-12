package olupis.world.entities.packets;

import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.io.TypeIO;
import mindustry.net.NetConnection;
import mindustry.net.Packet;
import olupis.content.NyfalisTurrets;

public class NyfalisSyncOtherSettingsPacket extends Packet {
    private byte[] DATA;
    public boolean cascadeBread = false;

    public NyfalisSyncOtherSettingsPacket() {
        this.DATA = NODATA;
    }

    public void write(Writes WRITE) {
        TypeIO.writeObject(WRITE, this.cascadeBread);
    }

    public void read(Reads READ, int LENGTH) {
        this.DATA = READ.b(LENGTH);
    }

    public void handled() {
        BAIS.setBytes(this.DATA);
        this.cascadeBread = (boolean) TypeIO.readObject(READ);
    }

    public void handleClient() {
        NyfalisTurrets.cascadeAlt = cascadeBread;
        NyfalisTurrets.dynamicTurretContent();
        Log.debug("host's settings: bread=" + cascadeBread);
    }

    public void handleServer(NetConnection con) {
        NyfalisTurrets.cascadeAlt = cascadeBread;
        NyfalisTurrets.dynamicTurretContent();
        Log.debug("server settings: bread:" + cascadeBread);
    }
}
