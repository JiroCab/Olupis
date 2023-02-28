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

    public static Music rick = new Music();

    boolean olupisMusicSet = false;
    public static Seq<Music> PreviousAmbientMusic;

    public static void LoadMusic(){
        Core.assets.load("music/rick.ogg", Music.class).loaded = (a) -> {
            //https://www.youtube.com/watch?v=Jd-Yckgrf08 @60% speed
            rick = a;
        };
    }


    public void replaceSoundHandler(){
        // Events.on(EventType.WorldLoadEvent.class, l ->{
        Time.run(120f, () -> {
            if(Core.settings.getBool("olupis-music-only") ||Core.settings.getBool("olupis-music") && (Vars.state.getSector() != null && Vars.state.getSector().planet == OlupisPlanets.olupis)){
                if(olupisMusicSet) return;

                PreviousAmbientMusic = control.sound.ambientMusic.copy();
                control.sound.ambientMusic.clear().addAll(rick);
                olupisMusicSet = true;
                Log.debug("Olupis replaced SoundControl's music Seq(s)!");
            }else if (olupisMusicSet){
                control.sound.ambientMusic.clear().addAll(PreviousAmbientMusic );
                Log.debug("Olupis Restored the previous SoundControl's music Seq(s)!");
            }
        });
    }

    public OlupisSounds(){
        ambientMusic.clear().add(rick);
        setupFilters();
    }

}
