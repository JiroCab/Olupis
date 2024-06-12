package olupis.world;

import arc.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import olupis.world.environment.*;

import static mindustry.Vars.*;

public class FloorUpdater{
    private static final ObjectIntMap<Tile> tiles = new ObjectIntMap<>();
    public static final ObjectMap<Tile, Floor> replacedTiles = new ObjectMap<>();
    private static final Seq<Tile> dormantTiles = new Seq<>();
    private static Timer.Task validator, dormantValidator;

    public static void load(){
        Log.info("FloorUpdater loaded");
        Events.on(EventType.WorldLoadEvent.class, e -> {
            tiles.clear();
            dormantTiles.clear();
            replacedTiles.clear();

            if(net.client()) return;

            world.tiles.eachTile(t -> {
                if(t.floor() instanceof SpreadingFloor s && s.spreadChance > 0)
                    tiles.put(t, 0);
            });

            if(Core.settings.getBool("nyfalis-debug"))Log.info("FloorUpdater setup complete, " + tiles.size + " tiles to update");
            else Log.debug("FloorUpdater setup complete, " + tiles.size + " tiles to update");

            validator = Timer.schedule(FloorUpdater::updateSpread, 0, 1);
            dormantValidator = Timer.schedule(FloorUpdater::updateDormant, 0, 10);
        });
    }

    private static void updateSpread(){
        if(state.isGame()){
            if(state.isPaused()) return;

            for(ObjectIntMap.Entry<Tile> entry : tiles.entries()){
                var t = (SpreadingFloor) entry.key.floor();

                if(entry.value >= t.spreadTries){
                    tiles.remove(entry.key);
                    Seq<Tile> nearby = getNearby(entry.key, t.spreadOffset, t.blacklist);
                    if(nearby.isEmpty()){
                        Log.info("Tile at " + entry.key.x + ", " + entry.key.y + " is now dormant");
                        tiles.remove(entry.key);
                        dormantTiles.addUnique(entry.key);
                        return;
                    }

                    Tile next = nearby.random();
                    replacedTiles.put(next, next.floor());
                    next.setFloorNet(entry.key.floor(), next.overlay());
                    Log.info("Spreading tile at " + entry.key.x + ", " + entry.key.y + " to " + next.x + ", " + next.y);

                    tiles.put(next, 0);
                    tiles.put(entry.key, 0);
                }

                if(Mathf.chance(t.spreadChance))
                    tiles.increment(entry.key);
            }
        }else{
            tiles.clear();
            dormantTiles.clear();
            replacedTiles.clear();

            if(validator != null)
                validator.cancel();
            if(dormantValidator != null)
                dormantValidator.cancel();
            Log.info("FloorUpdater disposed, tile cache empty");
        }
    }

    private static void updateDormant(){
        if(state.isGame()){
            if(state.isPaused()) return;
            //TODO, make the check slower if there ar 0 tiles total in the map, but not stop since map makers can add them after the fact w/ world logic
            Log.info("Total tiles: " + (tiles.size + dormantTiles.size) + ", Of which: " + tiles.size + " active, " + dormantTiles.size + " dormant");

            dormantTiles.each(t -> {
                var tmp = (SpreadingFloor) t.floor();
                Seq<Tile> check = getNearby(t, tmp.spreadOffset, tmp.blacklist);
                if(check.isEmpty()) return;

                dormantTiles.remove(t);
                tiles.put(t, 0);
            });
        }
    }

    private static Seq<Tile> getNearby(Tile tile, int radius, ObjectSet<Block> blacklist){
        Seq<Tile> tiles = new Seq<>();
        Tile t = null;

        if(radius <= 0)
            for(int i = 0; i <= 3; i++) // linear
                t = tile.nearby(i);
                if(t != null && t.floor() != null
                && !blacklist.contains(t.floor()))
                    tiles.add(t);
        else
            tile.circle(radius, tmp -> { // random
                if(tmp != null && tmp.floor() != null
                && !blacklist.contains(tmp.floor()))
                    tiles.add(tmp);
            });

        return tiles;
    }
}
