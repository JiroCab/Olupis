package olupis.content;

import arc.func.Cons;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.type.*;
import mindustry.world.meta.Env;
import olupis.world.planets.*;

import static mindustry.Vars.content;

public class NyfalisPlanets {
    public static Planet nyfalis, arthin, spelta, system;
    private static final Seq<Sector> systemSector = new Seq<>();

    public static Cons<Rules> commonRules = r ->{
        r.dropZoneRadius = 400f;
        r.enemyCoreBuildRadius = 650f;
        r.unitCrashDamageMultiplier = 0.25f;
        r.loadout.clear().add(new ItemStack(NyfalisItemsLiquid.rustyIron, 100), new ItemStack(Items.lead, 100));

        r.bannedBlocks.clear();
        r.waveTeam = Team.green;

        r.placeRangeCheck = r.disableOutsideArea = r.staticFog = false;
        r.waves = r.showSpawns = r.unitPayloadUpdate = r.coreDestroyClear =  r.hideBannedBlocks = r.coreIncinerates = r.fog = r.blockWhitelist = true;

        NyfalisBlocks.nyfalisBuildBlockSet.each(b -> r.bannedBlocks.add(b));
        NyfalisBlocks.sandBoxBlocks.each(b -> r.bannedBlocks.add(b));
    };

    public  static void LoadPlanets(){
        /*I Exist so Tech Tree's Item pool is shared among the 3 planets*/
        system = new Planet("system", Planets.sun, 0.4f){{
            sectors.set(systemSector);
            generator = new AsteroidGenerator();
            meshLoader = () -> new HexMesh(this, 3);
            accessible = visible = unlocked = hasAtmosphere = updateLighting = drawOrbit = false;
            hideDetails = alwaysUnlocked = true;
            camRadius = 0.68f * 3;
            minZoom = 0.7f;
            clipRadius = 2f;
            defaultEnv = Env.space;
            icon = "rename";
        }};

        nyfalis = new Planet("olupis", Planets.sun, 1, 3){{
            allowSectorInvasion = allowLaunchLoadout = false;
            allowWaves = enemyCoreSpawnReplace = allowLaunchSchematics = prebuildBase = allowWaveSimulation = alwaysUnlocked = hasAtmosphere = true;

            startSector = 1;
            sectorSeed = 2;
            totalRadius = 2.7f;
            launchCapacityMultiplier = 0.4f;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.05f;

            systemSector.add(sectors);
            ruleSetter = commonRules;
            system.position = this.position;
            defaultCore = NyfalisBlocks.coreRemnant;
            generator = new NyfalisPlanetGenerator();
            defaultEnv = Env.terrestrial | Env.oxygen;
            iconColor = NyfalisBlocks.redSand.mapColor;
            atmosphereColor = Color.valueOf("87CEEB");
            landCloudColor = new Color().set(Color.valueOf("C7E7F1").cpy().lerp(Color.valueOf("D7F5DC"), 0.55f));
            unlockedOnLand.addAll(NyfalisBlocks.coreRemnant, NyfalisItemsLiquid.rustyIron);
            hiddenItems.addAll(content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
            meshLoader = () -> new HexMesh(this, 7);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.7f, 0.13f, 5, new Color().set(Color.valueOf("C7E7F1")).mul(0.9f).a(0.55f), 2, 0.45f, 0.9f, 0.38f),
                new HexSkyMesh(this, 1, 0.2f, 0.16f, 5, Pal.regen.cpy().lerp(Color.valueOf("D7F5DC"), 0.55f).a(0.55f), 2, 0.45f, 1f, 0.41f)
            );
        }};

        //1st moon
        arthin = new Planet("arthin", NyfalisPlanets.nyfalis, 0.9f, 2){{
            accessible = alwaysUnlocked = clearSectorOnLose = allowSectorInvasion= allowLaunchLoadout = updateLighting = true;

            startSector = 2;
            enemyBuildSpeedMultiplier = 0.4f;
            icon = "effect";
            ruleSetter = commonRules;
            systemSector.add(sectors);
            defaultCore = NyfalisBlocks.coreRemnant;
            generator = new ArthinPlanetGenerator();
            defaultEnv = Env.terrestrial | Env.oxygen;
            iconColor = NyfalisItemsLiquid.condensedBiomatter.color;
            meshLoader = () -> new HexMesh(this, 5);
            hiddenItems.addAll(content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
        }};

        spelta = new Planet("spelta", NyfalisPlanets.nyfalis, 0.9f, 2){{
            //TODO: planet gimmick: mostly attack sectors + you can place a core in any spot
            alwaysUnlocked = clearSectorOnLose = allowSectorInvasion = updateLighting = accessible= true;

            startSector = 1;
            enemyBuildSpeedMultiplier = 0.4f;
            icon = "effect";
            ruleSetter = commonRules;
            systemSector.add(sectors);
            generator = new SpeltaPlanetGenerator();
            defaultCore = NyfalisBlocks.coreRemnant;
            iconColor = NyfalisBlocks.pinkTree.mapColor;
            defaultEnv = Env.terrestrial | Env.oxygen;
            meshLoader = () -> new HexMesh(this, 5);
            hiddenItems.addAll(content.items()).removeAll(NyfalisItemsLiquid.nyfalisItems);
        }};

        //TODO: rework the planets generators
    }

    public  static void PostLoadPlanet(){
        system.sectors.set(systemSector);
    }
}
