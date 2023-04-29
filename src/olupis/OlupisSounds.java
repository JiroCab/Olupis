package olupis;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import olupis.content.OlupisPlanets;
import mindustry.Vars;
import mindustry.audio.SoundControl;

import static mindustry.Vars.control;

public class OlupisSounds extends SoundControl {

    public static Music
            rick = new Music(),
            reclaiming_the_wasteland = new Music();
    ;
    public static Seq<Music>
            olupisAmbient = new Seq<>(),
            olupisBoss = new Seq<>();

    boolean olupisMusicSet = false;
    public static Seq<Music> PreviousAmbientMusic, PreviousBossMusic;

    public static void LoadMusic(){
        Core.assets.load("music/rick.ogg", Music.class).loaded = (a) -> {
            //https://www.youtube.com/watch?v=Jd-Yckgrf08 @60% speed
            rick = a;
        };
        Core.assets.load("music/reclaiming_the_wasteland.ogg", Music.class).loaded = (a) ->reclaiming_the_wasteland = a;

        olupisBoss.add(rick);
        olupisAmbient.add(reclaiming_the_wasteland, rick);
    }


    public void replaceSoundHandler(){
        // Events.on(EventType.WorldLoadEvent.class, l ->{
        Time.run(120f, () -> {
            if(Core.settings.getBool("olupis-music-only") ||Core.settings.getBool("olupis-music") && (Vars.state.getSector() != null && Vars.state.getSector().planet == OlupisPlanets.olupis)){
                if(olupisMusicSet) return;

                PreviousAmbientMusic = control.sound.ambientMusic.copy();
                PreviousBossMusic = control.sound.bossMusic.copy();
                control.sound.ambientMusic.clear().addAll(olupisAmbient);
                control.sound.ambientMusic.clear().addAll(olupisBoss);

                olupisMusicSet = true;
                Log.debug("Olupis replaced SoundControl's music Seq(s)!");
            }else if (olupisMusicSet){
                control.sound.ambientMusic.clear().addAll(PreviousAmbientMusic );
                Log.debug("Olupis Restored the previous SoundControl's music Seq(s)!");
            }
        });
    }


}
