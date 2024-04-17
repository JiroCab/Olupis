package olupis.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Music;
import arc.audio.Sound;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.world.Block;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisSounds {
    //TODO: Make a jar build w/o music for players that don't want/internet is slow
    // Or alternatively make it a independent mod

    public static Music
            space = new Music(),
            reclaiming_the_wasteland = new Music(),
            dusk = new Music(),
            blossom = new Music(),
            feu = new Music(),
            main_title = new Music(),
            sparkles_of_hope = new Music()
    ;
    /*Not final, just wanted to see how custom sounds would work*/
    public static Sound
            as2ArmorBreak = new Sound(),
            as2PlasmaShot = new Sound()
    ;

    public Seq<Music>
            nyfalisAmbient = new Seq<>(),
            nyfalisBoss = new Seq<>(),
            nyfalisDark = new Seq<>();

    public boolean nyfalisMusicSet = false;
    public static Seq<Music> previousAmbientMusic, previousBossMusic, previousDarkMusic;

     public static void LoadMusic(){
        if(headless) return;
        reclaiming_the_wasteland = tree.loadMusic("reclaiming_the_wasteland");
        dusk = tree.loadMusic("dusk");
        blossom = tree.loadMusic("blossom");
        feu = tree.loadMusic("feu");
        main_title = tree.loadMusic("main_title");
        sparkles_of_hope = tree.loadMusic("sparkles_of_hope");

        space = tree.loadMusic("space");
    }

    public static void  LoadSounds(){
        //Note: Vars.tree.loadSound only works with .mp3 and .ogg
        as2PlasmaShot = tree.loadSound("as2-plasma-shot");
        as2ArmorBreak = tree.loadSound("as2-broke-armor");
    }

    /*least invasive approach, hopefully a mod that changes music still has the Seqs public*/
    public void replaceSoundHandler(){
        if(shouldReplaceMusic()){
            if(nyfalisMusicSet) return;

            previousAmbientMusic = control.sound.ambientMusic.copy();
            previousDarkMusic = control.sound.darkMusic.copy();
            previousBossMusic = control.sound.bossMusic.copy();

            nyfalisAmbient.add(reclaiming_the_wasteland, blossom, feu, main_title);
            nyfalisDark.add(reclaiming_the_wasteland, dusk, sparkles_of_hope);
            nyfalisBoss.add(dusk, sparkles_of_hope);

            if (Core.settings.getBool("nyfalis-music-add")){
                control.sound.ambientMusic.clear();
                control.sound.darkMusic.clear();
                control.sound.bossMusic.clear();
            }

            control.sound.darkMusic.add(nyfalisAmbient);
            control.sound.ambientMusic.add(nyfalisDark);
            control.sound.bossMusic.add(nyfalisBoss);

            nyfalisMusicSet = true;
            if (Core.settings.getBool("nyfalis-music-add")) Log.info("Nyfalis replaced SoundControl's music Seq(s)!");
            else Log.info("Nyfalis added custom music to SoundControl");
        }else if (nyfalisMusicSet){
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.ambientMusic.clear().addAll(previousAmbientMusic );
            control.sound.bossMusic.clear().addAll(previousBossMusic);
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
}
