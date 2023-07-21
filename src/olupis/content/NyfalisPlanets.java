package olupis.content;

import arc.func.Cons;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.world.meta.Env;
import olupis.world.planets.*;

import static mindustry.Vars.content;

public class NyfalisPlanets {
    public static Planet nyfalis, arthin, spelta, system;
    private static final Seq<Sector> systemSector = new Seq<>();

    public static Cons<Rules> commonRules = r ->{
        r.waveTeam = Team.green;
        r.placeRangeCheck = false;
        r.showSpawns = true;
        r.unitPayloadUpdate = true;
        r.enemyCoreBuildRadius = 650f;
        r.coreDestroyClear = true;
        r.dropZoneRadius = 400f;
        r.disableOutsideArea = false;
        r.blockWhitelist = true;
        r.bannedBlocks.clear();
        r.hideBannedBlocks = true;
        NyfalisBlocks.nyfalisBuildBlockSet.each(b -> r.bannedBlocks.add(b));
        NyfalisBlocks.sandBoxBlocks.each(b -> r.bannedBlocks.add(b));
    };

    public  static void LoadPlanets(){
        content.planets().forEach( p -> {
            if(p.name.contains("olupis-"))return;
            p.hiddenItems.addAll(NyfalisItemsLiquid.nyfalisOnlyItems);
        });

        system = new Planet("system", Planets.sun, 0.4f){{
            sectors.set(systemSector);
            generator = new AsteroidGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            accessible = visible = unlocked = hasAtmosphere = updateLighting = drawOrbit = false;
            hideDetails = true;
            camRadius = 0.68f * 3;
            minZoom = 0.6f;
            clipRadius = 2f;
            defaultEnv = Env.space;
        }};

        nyfalis = new Planet("olupis", Planets.sun, 1, 3){{
            generator = new NyfalisPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 7);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Color.valueOf("cee9f2")).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("cee9f2"), 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );
            defaultCore = NyfalisBlocks.coreRemnant;
            unlockedOnLand.addAll(NyfalisBlocks.coreRemnant, NyfalisItemsLiquid.rustyIron);
            defaultEnv = Env.terrestrial | Env.oxygen;
            launchCapacityMultiplier = 0.4f;
            sectorSeed = 2;
            allowWaves = enemyCoreSpawnReplace = allowLaunchLoadout = prebuildBase = alwaysUnlocked = allowWaveSimulation = true;
            allowSectorInvasion = allowLaunchSchematics = false;
            ruleSetter = commonRules;
            atmosphereColor = Color.valueOf("87CEEB");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.22f;
            startSector = 1;
            landCloudColor = Pal.engine.cpy().a(0.5f);
            hiddenItems.addAll(Vars.content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
            totalRadius = 2.7f;
            systemSector.add(sectors);
            system.position = this.position;
        }};

        //1st moon
        arthin = new Planet("arthin", NyfalisPlanets.nyfalis, 0.9f, 2){{
            generator = new ArthinPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            defaultCore = NyfalisBlocks.coreRemnant;
            alwaysUnlocked = clearSectorOnLose = allowSectorInvasion = updateLighting = true;
            accessible = false;
            defaultEnv = nyfalis.defaultEnv;
            iconColor = Color.valueOf("61615B");
            icon = "effect";
            hiddenItems.addAll(Vars.content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
            enemyBuildSpeedMultiplier = 0.4f;

            ruleSetter = commonRules;
            systemSector.add(sectors);
        }};

        spelta = new Planet("spelta", NyfalisPlanets.nyfalis, 0.9f, 2){{
            generator = new SpeltaPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            defaultCore = NyfalisBlocks.coreRemnant;
            alwaysUnlocked = clearSectorOnLose = allowSectorInvasion = updateLighting = true;
            accessible = false;
            defaultEnv = nyfalis.defaultEnv;
            iconColor = Color.valueOf("61615B");
            icon = "effect";
            hiddenItems.addAll(Vars.content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
            enemyBuildSpeedMultiplier = 0.4f;

            ruleSetter = commonRules;
            systemSector.add(sectors);
        }};
    }

    public  static void PostLoadPlanet(){
        system.sectors.set(systemSector);
    }
}
