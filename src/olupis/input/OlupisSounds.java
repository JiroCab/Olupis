package olupis.input;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import olupis.content.OlupisPlanets;

import static mindustry.Vars.control;

public class OlupisSounds {

    public static Music
            rick = new Music(),
            reclaiming_the_wasteland = new Music();
    public Seq<Music>
            olupisAmbient = new Seq<>(),
            olupisBoss = new Seq<>(),
            olupisDark = new Seq<>();

    boolean olupisMusicSet = false;
    public static Seq<Music> previousAmbientMusic, previousBossMusic, previousDarkMusic;

    public static void LoadMusic(){
        //https://www.youtube.com/watch?v=Jd-Yckgrf08 @60% speed
        Core.assets.load("music/rick.ogg", arc.audio.Music.class).loaded = a -> rick = a;
        Core.assets.load("music/reclaiming_the_wasteland.ogg", arc.audio.Music.class).loaded = a -> reclaiming_the_wasteland = a;
    }

    //*least invasive approach, hopefully a mod that changes music still has the Seqs public*//
    public void replaceSoundHandler(){
        //funny long if statement
        if(Core.settings.getBool("olupis-music-only") || Core.settings.getBool("olupis-music") && (Vars.state.getSector() != null && (Vars.state.getSector().planet == OlupisPlanets.olupis || Vars.state.getSector().planet == OlupisPlanets.arthin || Vars.state.getSector().planet == OlupisPlanets.spelta))){
            if(olupisMusicSet) return;

            previousAmbientMusic = control.sound.ambientMusic.copy();
            previousBossMusic = control.sound.bossMusic.copy();
            previousDarkMusic = control.sound.darkMusic.copy();

            olupisAmbient.add(reclaiming_the_wasteland, rick);
            olupisDark.add(reclaiming_the_wasteland, rick);
            olupisBoss.add(rick);

            control.sound.ambientMusic.clear();
            control.sound.bossMusic.clear();
            control.sound.darkMusic.clear();

            control.sound.darkMusic.set(olupisAmbient);
            control.sound.ambientMusic.set(olupisDark);
            control.sound.bossMusic.set(olupisBoss);

            olupisMusicSet = true;
            Log.info("Olupis replaced SoundControl's music Seq(s)!");

        }else if (olupisMusicSet){
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.bossMusic.clear().addAll(previousBossMusic);
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            Log.info("Olupis Restored the previous SoundControl's music Seq(s)!");
        }
    }


}
