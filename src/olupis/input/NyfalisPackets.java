package olupis.input;

import mindustry.Vars;
import mindustry.net.Net;
import olupis.content.NyfalisTurrets;
import olupis.world.entities.packets.*;

import static mindustry.Vars.netServer;

public class NyfalisPackets {
    public static NyfalisUnitTimedOutPacket unitTimedOut = new NyfalisUnitTimedOutPacket();
    public static NyfalisDebugPackets debugPackets = new NyfalisDebugPackets();
    public static NyfalisSyncOtherSettingsPacket settingPacket = new NyfalisSyncOtherSettingsPacket();

    public static void LoadPackets(){

        Net.registerPacket(NyfalisUnitTimedOutPacket::new);

        Net.registerPacket(NyfalisSyncOtherSettingsPacket::new);
        Net.registerPacket(NyfalisDebugPackets::new);

        /*Too lazy to make a new class lmao*/
        netServer.addPacketHandler("olupis-getsettings", (p, s) ->{
            NyfalisSyncOtherSettingsPacket packet = new NyfalisSyncOtherSettingsPacket();
            packet.cascadeBread = NyfalisTurrets.cascadeAlt;
            Vars.net.send(packet, true);
        });


    }


}
