package olupis.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Music;
import arc.audio.Sound;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.world.Block;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisSounds {
    /*Music has been moved to https://github.com/JiroCab/Nyfalis-Music */
    /*Not final, just wanted to see how custom sounds would work*/
    public static Music
            space = new Music()
    ;

    public static Sound
            as2ArmorBreak = new Sound(),
            as2PlasmaShot = new Sound()
    ;

    public static void LoadMusic() {
        if (headless) return;
        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;
    }
    public static void  LoadSounds(){
        as2PlasmaShot = loadSound("sounds/as2_plasma_shot.wav");
        as2ArmorBreak = loadSound("sounds/as2_broke_armor.wav");
    }


    /*least invasive approach, hopefully a mod that changes music still has the Seqs public
    Music mod checks for this boolean, bc im lazy to move this to the music mod*/
    public void replaceSoundHandler(){
        Core.settings.put("nyfalis-replacemusic", shouldReplaceMusic());
    }


    public boolean shouldReplaceMusic(){
        if (Core.settings.getBool("nyfalis-music-only")) return true;
        if (Core.settings.getBool("nyfalis-music") && state.isCampaign()){
            Planet sector = state.getSector().planet;
            if(sector == arthin) return true;
            if(sector == spelta) return true;
            return sector == nyfalis;
        }
        if(state.rules.env == defaultEnv && state.getPlanet() == Planets.sun) return false;

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

    public static Sound loadSound(String path){
        /*Stolen from: https://github.com/sk7725/BetaMindy/blob/erekir/src/betamindy/content/MindySounds.java*/
        Sound sound = new Sound();
        if(headless) return sound;
        AssetDescriptor<?> a = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
        a.errored = Throwable::printStackTrace;
        return sound;
    }

}
