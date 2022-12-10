package com.jirocab.planets.content;

import arc.graphics.Color;
import com.jirocab.planets.planets.OlupisPlanetGenerator;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.type.Planet;
import mindustry.world.meta.Env;

public class OlupisPlanets {
    public  static Planet olupis, olupisMoon;

    public  static void LoadPlanets(){
        olupis = new Planet("Olupis", Planets.sun, 1f, 3){{
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
                r.env = OlupisPlanets.olupis.defaultEnv;
                r.hiddenBuildItems.addAll(olupis.hiddenItems);
            };
            atmosphereColor = Color.valueOf("87CEEB");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.22f;
            startSector = 1;
            alwaysUnlocked = true;
            landCloudColor = Pal.engine.cpy().a(0.5f);
            defaultEnv = Env.terrestrial | Env.oxygen ;
            hiddenItems.addAll(OlupisItemsLiquid.olupisOnlyItems).removeAll(OlupisItemsLiquid.nonOlupisItems);
        }};

        olupisMoon = new Planet("olupis-moon", OlupisPlanets.olupis, 0.5f, 1){{
            generator = new ErekirPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 3);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("eba768").a(0.75f), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            alwaysUnlocked = true;
            defaultEnv = Env.space;
            clearSectorOnLose = true;
            defaultCore = Blocks.coreBastion;
            iconColor = Color.valueOf("61615B");
            icon = "boulder";
            hiddenItems.addAll(OlupisItemsLiquid.olupisOnlyItems).removeAll(OlupisItemsLiquid.nonOlupisItems);
            enemyBuildSpeedMultiplier = 0.4f;

            //TODO SHOULD there be lighting?
            updateLighting = true;

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

            unlockedOnLand.add(Blocks.coreBastion);
        }};
    }


}
