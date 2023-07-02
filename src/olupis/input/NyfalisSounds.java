package olupis.input;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.type.Planet;
import olupis.content.NyfalisBlocks;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisSounds {
    //TODO: Make a jar build w/o music for players that don't want/internet is slow

    public static Music
            space = new Music(),
            rick = new Music(),
            reclaiming_the_wasteland = new Music();
    public Seq<Music>
            nyfalisAmbient = new Seq<>(),
            nyfalisBoss = new Seq<>(),
            nyfalisDark = new Seq<>();

    boolean nyfalisMusicSet = false;
    public static Seq<Music> previousAmbientMusic, previousBossMusic, previousDarkMusic;

    public static void LoadMusic(){
        if(headless) return;
        //https://www.youtube.com/watch?v=Jd-Yckgrf08 @60% speed
        Core.assets.load("music/rick.ogg", arc.audio.Music.class).loaded = a -> rick = a;
        Core.assets.load("music/reclaiming_the_wasteland.ogg", arc.audio.Music.class).loaded = a -> reclaiming_the_wasteland = a;

        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;
    }

    //*least invasive approach, hopefully a mod that changes music still has the Seqs public*//
    public void replaceSoundHandler(){
        if(shouldReplaceMusic()){
            if(nyfalisMusicSet) return;

            previousAmbientMusic = control.sound.ambientMusic.copy();
            previousBossMusic = control.sound.bossMusic.copy();
            previousDarkMusic = control.sound.darkMusic.copy();

            nyfalisAmbient.add(reclaiming_the_wasteland, rick);
            nyfalisDark.add(reclaiming_the_wasteland, rick);
            nyfalisBoss.add(rick);

            if (Core.settings.getBool("olupis-music-add")){
                control.sound.ambientMusic.clear();
                control.sound.bossMusic.clear();
                control.sound.darkMusic.clear();
            }

            control.sound.darkMusic.add(nyfalisAmbient);
            control.sound.ambientMusic.add(nyfalisDark);
            control.sound.bossMusic.add(nyfalisBoss);

            nyfalisMusicSet = true;
            Log.info("Nyfalis replaced SoundControl's music Seq(s)!");

        }else if (nyfalisMusicSet){
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.bossMusic.clear().addAll(previousBossMusic);
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            Log.info("Nyfalis Restored the previous SoundControl's music Seq(s)!");
        }
    }

    public boolean shouldReplaceMusic(){
        if (Core.settings.getBool("olupis-music-only")) return true;
        if (Core.settings.getBool("olupis-music") && state.isCampaign()){
            Planet sector = state.getSector().planet;
            if(sector == arthin) return true;
            if(sector == spelta) return true;
            return sector == nyfalis;
        }
        if (Core.settings.getBool("olupis-music-custom-game") && !state.isCampaign()){
            int env = state.rules.env;
            /*Somewhat prevents rare cases with other (modded) planets with same env as nyfalis*/
            AtomicBoolean hasCore = new AtomicBoolean(false);
            NyfalisBlocks.nyfalisCores.each(c ->{if (state.stats.placedBlockCount.get(c, 0) >= 1) hasCore.set(true);});

            return (env == nyfalis.defaultEnv && hasCore.get());
        }
        return false;
    }


}
