package olupis.input;

import mindustry.net.Net;
import olupis.world.entities.packets.NyfalisDebugPackets;
import olupis.world.entities.packets.NyfalisUnitTimedOutPacket;

public class NyfalisPackets {
    public static NyfalisUnitTimedOutPacket unitTimedOut = new NyfalisUnitTimedOutPacket();
    public static NyfalisDebugPackets debugPackets = new NyfalisDebugPackets();

    public static void LoadPackets(){

        Net.registerPacket(NyfalisUnitTimedOutPacket::new);

        Net.registerPacket(NyfalisDebugPackets::new);


    }


}
