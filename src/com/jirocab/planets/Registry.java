package com.jirocab.planets;

import arc.Core;
import arc.audio.Music;
import arc.graphics.Color;
import arc.struct.Seq;
import com.jirocab.planets.content.OlupisBlocks;
import com.jirocab.planets.content.OlupisFxs;
import com.jirocab.planets.planets.OlupisPlanetGenerator;
import com.jirocab.planets.planets.OlupisTechTree;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;

/*Handles all new content*/
public class Registry {
    public static final Seq<Item> nonOlupisItems = new Seq<>(), olupisOnlyItems = new Seq<>();
    public static Music space = new Music();
    public static Planet[] hideList = new Planet[]{Planets.erekir, Planets.serpulo, Planets.tantros};
    public  static  Planet Olupis;
    public  static SectorPreset placeholder1, placeholder2;
    public static Item condensedBiomatter, rustyIron, iron, cobalt;
    /*Used buy the biomatter compressor */
    public static final Attribute Bio = Attribute.add("bio") ;
    public static Unit coreshipPH;



    public static void register() {
        //region Items
        /*Texture is from Tech reborn: https://github.com/TechReborn/TechReborn/blob/1.19/src/main/resources/assets/techreborn/textures/item/part/compressed_plantball.png*/
        condensedBiomatter = new Item("condensed-biomatter", Color.valueOf("5a9e70")){{
            flammability = 1.2f;
            buildable = false;
            hiddenOnPlanets = hideList;
        }};
        rustyIron = new Item("rusty-iron", Color.valueOf("ccac8b")){{
            buildable = false;
            hardness = 1;
            hiddenOnPlanets = hideList;
        }};
        iron = new Item("iron", Color.valueOf("f0ece4")){{
            hardness = 2;
            healthScaling = 0.25f;
            buildable = false;
            hiddenOnPlanets = hideList;
        }};
        cobalt = new Item("cobalt", Color.valueOf("0b6e87")){{
            hardness = 2;
            charge = 0.5f;
            healthScaling = 0.25f;
            buildable = false;
        }};

        nonOlupisItems.add(Items.erekirItems);
        nonOlupisItems.add(Items.serpuloItems);
        olupisOnlyItems.addAll(rustyIron, iron, condensedBiomatter, cobalt, Items.sand, Items.lead);

        //endregion
        OlupisBlocks.LoadWorldTiles();
        OlupisBlocks.LoadBlocks();
        //region planets
        Olupis = new Planet("Olupis", Planets.sun, 1f, 3){{
            generator = new OlupisPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Color.valueOf("cee9f2")).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("cee9f2"), 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );

            launchCapacityMultiplier = 0.4f;
            sectorSeed = 2;
            allowWaves = true;
            allowWaveSimulation = false;
            allowSectorInvasion = true;
            allowLaunchSchematics = false;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            //doesn't play well with configs
            prebuildBase = true;
            ruleSetter = r -> {
                r.waveTeam = Team.green;
                r.placeRangeCheck = false;
                r.attributes.clear();
                r.showSpawns = true;
                r.unitPayloadUpdate = true;
                r.enemyCoreBuildRadius = 650f;
                r.coreDestroyClear = true;
                r.dropZoneRadius = 400f;
                r.disableOutsideArea = false;
                r.infiniteResources = true; //testing only
            };
            atmosphereColor = Color.valueOf("87CEEB");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.22f;
            startSector = 1;
            alwaysUnlocked = true;
            landCloudColor = Pal.engine.cpy().a(0.5f);
            defaultEnv = Env.terrestrial | Env.oxygen ;
            hiddenItems.addAll(olupisOnlyItems).removeAll(nonOlupisItems);
        }};

        //endregion
        //region sectors
        placeholder1 = new SectorPreset("placeholder1",  Olupis, 1){{
            captureWave = 15;
            difficulty = 2;
            addStartingItems = true;
        }};

        placeholder2 = new SectorPreset("placeholder2", Olupis, 69){{
            captureWave = 20;
            difficulty = 3;
        }};

        //endregion
        //region Misc
        OlupisTechTree.load();

        Core.assets.load("sounds/space.ogg", Music.class).loaded = (a) -> space = a;

        Blocks.grass.attributes.set(Registry.Bio, 0.1f);
        Blocks.stone.attributes.set(Registry.Bio, 0.03f);
        Blocks.charr.attributes.set(Registry.Bio, 0.03f);
        Blocks.mud.attributes.set(Registry.Bio, 0.03f);
        Blocks.dirt.attributes.set(Registry.Bio, 0.03f);
        Blocks.snow.attributes.set(Registry.Bio, 0.01f);
        Blocks.ice.attributes.set(Registry.Bio, 0.01f);
        Blocks.craters.attributes.set(Registry.Bio, 0.5f);
    }

}