package olupis.world.entities.packets;

import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.*;
import mindustry.io.TypeIO;
import mindustry.net.NetConnection;
import mindustry.net.Packet;
import olupis.NyfalisMain;
import olupis.content.NyfalisPlanets;

import static mindustry.Vars.state;

public class NyfalisDebugPackets extends Packet {
    private byte[] DATA;
    public int type = 0;

    public NyfalisDebugPackets() {
        this.DATA = NODATA;
    }

    public void write(Writes WRITE) {
        TypeIO.writeObject(WRITE, type);
    }

    public void read(Reads READ, int LENGTH) {this.DATA = READ.b(LENGTH);}

    public void handled() {
        BAIS.setBytes(this.DATA);
        this.type = (int)TypeIO.readObject(READ);
    }

    public void handleServer(NetConnection con) {
        if(!con.player.admin)return;

        if(type == 1){
            NyfalisMain.sandBoxCheck(false);
            for (Building b : Groups.build) {
                if (!b.enabled && b.lastDisabler == null && b.block.supportsEnv(state.rules.env)) {
                    b.enabled = true;
                }
            }
        } else if (type ==2) {
            state.rules.blockWhitelist = true;
            NyfalisPlanets.nyfalis.applyRules(state.rules);
            NyfalisMain.sandBoxCheck();
        }else NyfalisMain.sandBoxCheck(false);
        Log.info(con.player.name() + " called " + type + " type debug packet!");
    }

}
