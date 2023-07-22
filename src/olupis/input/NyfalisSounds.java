package olupis.input;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.type.Planet;
import mindustry.world.Block;
import olupis.content.NyfalisBlocks;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisSounds {
    //TODO: Make a jar build w/o music for players that don't want/internet is slow

    public static Music
            space = new Music(),
            reclaiming_the_wasteland = new Music();
    public Seq<Music>
            nyfalisAmbient = new Seq<>(),
            nyfalisBoss = new Seq<>(),
            nyfalisDark = new Seq<>();

    boolean nyfalisMusicSet = false;
    public static Seq<Music> previousAmbientMusic, previousBossMusic, previousDarkMusic;

    public static void LoadMusic(){
        if(headless) return;
        Core.assets.load("music/reclaiming_the_wasteland.ogg", arc.audio.Music.class).loaded = a -> reclaiming_the_wasteland = a;

        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;
    }

    //*least invasive approach, hopefully a mod that changes music still has the Seqs public*//
    public void replaceSoundHandler(){
        if(shouldReplaceMusic()){
            if(nyfalisMusicSet) return;

            previousAmbientMusic = control.sound.ambientMusic.copy();
            previousDarkMusic = control.sound.darkMusic.copy();
           // previousBossMusic = control.sound.bossMusic.copy();

            nyfalisAmbient.add(reclaiming_the_wasteland);
            nyfalisDark.add(reclaiming_the_wasteland);
            //nyfalisBoss.add();

            if (!Core.settings.getBool("nyfalis-music-add")){
                control.sound.ambientMusic.clear();
                control.sound.darkMusic.clear();
               // control.sound.bossMusic.clear();
            }

            control.sound.darkMusic.add(nyfalisAmbient);
            control.sound.ambientMusic.add(nyfalisDark);
            //control.sound.bossMusic.add(nyfalisBoss);

            nyfalisMusicSet = true;
            Log.info("Nyfalis replaced SoundControl's music Seq(s)!");

        }else if (nyfalisMusicSet){
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            //control.sound.bossMusic.clear().addAll(previousBossMusic);
            Log.info("Nyfalis Restored the previous SoundControl's music Seq(s)!");
        }
    }

    public boolean shouldReplaceMusic(){
        if (Core.settings.getBool("nyfalis-music-only")) return true;
        if (Core.settings.getBool("nyfalis-music") && state.isCampaign()){
            Planet sector = state.getSector().planet;
            if(sector == arthin) return true;
            if(sector == spelta) return true;
            return sector == nyfalis;
        }

        if (Core.settings.getBool("nyfalis-music-custom-game") && !state.isCampaign()){
            int env = state.rules.env;
            /*Somewhat prevents rare cases with other (modded) planets with same env as nyfalis*/
            AtomicBoolean hasCore = new AtomicBoolean(false);
            for (Block c : NyfalisBlocks.nyfalisCores) {
                if (indexer.isBlockPresent(c)) {
                    hasCore.set(true);
                    break;
                }
            }

            return (env == nyfalis.defaultEnv && hasCore.get());
        }
        return false;
    }


}
