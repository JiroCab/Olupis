package olupis.input;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import olupis.content.NyfalisPlanets;

import static mindustry.Vars.*;

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
        //funny long if statement
        if(Core.settings.getBool("olupis-music-only") || Core.settings.getBool("olupis-music") && (Vars.state.getSector() != null && (Vars.state.getSector().planet == NyfalisPlanets.nyfalis || Vars.state.getSector().planet == NyfalisPlanets.arthin || Vars.state.getSector().planet == NyfalisPlanets.spelta))){
            if(nyfalisMusicSet) return;

            previousAmbientMusic = control.sound.ambientMusic.copy();
            previousBossMusic = control.sound.bossMusic.copy();
            previousDarkMusic = control.sound.darkMusic.copy();

            nyfalisAmbient.add(reclaiming_the_wasteland, rick);
            nyfalisDark.add(reclaiming_the_wasteland, rick);
            nyfalisBoss.add(rick);

            control.sound.ambientMusic.clear();
            control.sound.bossMusic.clear();
            control.sound.darkMusic.clear();

            control.sound.darkMusic.set(nyfalisAmbient);
            control.sound.ambientMusic.set(nyfalisDark);
            control.sound.bossMusic.set(nyfalisBoss);

            nyfalisMusicSet = true;
            Log.info("Nyfalis replaced SoundControl's music Seq(s)!");

        }else if (nyfalisMusicSet){
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.bossMusic.clear().addAll(previousBossMusic);
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            Log.info("Nyfalis Restored the previous SoundControl's music Seq(s)!");
        }
    }


}
