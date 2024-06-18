package olupis.world;

import arc.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;
import olupis.world.environment.*;

import static mindustry.Vars.*;

public class FloorUpdater{
    public static final ObjectMap<Tile, Block> replacedTiles = new ObjectMap<>();
    private static final ObjectIntMap<Tile> tiles = new ObjectIntMap<>(), ores = new ObjectIntMap<>();
    private static final Seq<Tile> dormantTiles = new Seq<>(), dormantOres = new Seq<>();
    private static Timer.Task validator, dormantValidator;

    public static void load(){
        Log.info("FloorUpdater loaded");
        Events.on(EventType.WorldLoadEvent.class, e -> {
            tiles.clear();
            ores.clear();
            dormantTiles.clear();
            dormantOres.clear();
            replacedTiles.clear();

            if(net.client()) return;

            world.tiles.eachTile(t -> {
                if(t.floor() instanceof SpreadingFloor f && f.spreadChance > 0
                || t.overlay() instanceof SpreadingFloor s && s.spreadChance > 0)
                    tiles.put(t, 0);
                else if(t.overlay() instanceof SpreadingOre o && o.spreadChance > 0)
                    ores.put(t, 0);
            });

            Log.info("FloorUpdater setup complete, " + (tiles.size + ores.size) + " tiles to update");

            if(!validator.isScheduled())
                validator = Timer.schedule(FloorUpdater::updateSpread, 0, 1);
            if(!dormantValidator.isScheduled())
                dormantValidator = Timer.schedule(FloorUpdater::updateDormant, 0, 10);
        });
    }

    private static void updateSpread(){
        if(state.isGame() && !state.isEditor()){
            if(state.isPaused()) return;

            for(ObjectIntMap.Entry<Tile> entry : tiles.entries()){
                if(entry.key == null) continue;

                if(entry.key.floor() instanceof SpreadingFloor || entry.key.overlay() instanceof SpreadingFloor){
                    var t = entry.key.floor() instanceof SpreadingFloor s ? s : (SpreadingFloor) entry.key.overlay();

                    if(t.next != null){
                        if(entry.value > t.spreadTries){
                            tiles.remove(entry.key);
                            if(t.next instanceof SpreadingFloor f){
                                if(entry.key.floor() instanceof SpreadingFloor s && s.overlay){
                                    Seq<Floor> tmp = new Seq<>();
                                    for(int i = 0; i <= 3; i++){
                                        Tile nearby = entry.key.nearby(i);
                                        if(nearby != null && nearby.floor() != null && !(nearby.floor() instanceof SpreadingFloor sf && sf.overlay))
                                            tmp.add(nearby.floor());
                                    }
                                    entry.key.setFloorNet(tmp.isEmpty() ? Blocks.stone : tmp.random(), entry.key.overlay());
                                }
                                entry.key.setFloorNet(f.overlay ? entry.key.floor() : f.set, f.overlay ? f.set : (t.overlay ? Blocks.air : entry.key.overlay()));
                                tiles.put(entry.key, 0);
                            }else entry.key.setFloorNet(t.next.isOverlay() ? entry.key.floor() : t.next, entry.key.overlay() instanceof SpreadingFloor ? (t.next.isOverlay() ? t.next : Blocks.air) : entry.key.overlay());

                            if(t.growSpread){
                                for(Tile tile : getNearby(entry.key, 0, t.blacklist)){
                                    replacedTiles.put(tile, t.overlay ? tile.overlay() : tile.floor());
                                    setFloor(tile, t);
                                    tiles.put(tile, 0);
                                }
                            }
                        }else if(Mathf.chance(t.spreadChance)) tiles.increment(entry.key);

                        continue;
                    }

                    if(entry.value >= t.spreadTries){
                        tiles.remove(entry.key);
                        Seq<Tile> nearby = getNearby(entry.key, t.spreadOffset, t.blacklist);
                        if(nearby.isEmpty()){
                            dormantTiles.addUnique(entry.key);
                            continue;
                        }

                        tiles.put(entry.key, 0);
                        if(t.fullSpread) for(Tile tile : nearby){
                            replacedTiles.put(tile, t.overlay ? tile.overlay() : tile.floor());
                            setFloor(tile, t);
                            tiles.put(tile, 0);
                        }
                        else{
                            Tile next = nearby.random();
                            replacedTiles.put(next, t.overlay ? next.overlay() : next.floor());
                            setFloor(next, t);
                            tiles.put(next, 0);
                        }
                    }

                    if(Mathf.chance(t.spreadChance))
                        tiles.increment(entry.key);
                }
            }

            for(ObjectIntMap.Entry<Tile> entry : ores.entries()){
                if(entry.key == null) continue;
                var o = (SpreadingOre) entry.key.overlay();

                if(entry.value >= o.spreadTries){
                    ores.remove(entry.key);
                    Seq<Tile> nearby = getNearby(entry.key, o.spreadOffset, o.blacklist);
                    ores.put(entry.key, 0);
                    if(nearby.isEmpty()){
                        for(int i = 0; i <= 3; i++){
                            Tile tile = entry.key.nearby(i);
                            if(tile != null && tile.floor() == o.baseFloor){
                                replacedTiles.put(entry.key, entry.key.floor());
                                entry.key.setFloorNet(o.baseFloor, entry.key.overlay());
                                if(o.baseFloor instanceof SpreadingFloor) tiles.put(entry.key, 0);
                                dormantOres.addUnique(entry.key);
                            }
                        }

                        continue;
                    }

                    if(o.fullSpread) for(Tile tile : nearby){
                        replacedTiles.put(tile, tile.overlay());
                        (o.parent.replacements.containsKey(tile.overlay()) ? ores : tiles).put(tile, 0);
                        tile.setFloorNet(tile.floor(), o.parent.replacements.containsKey(tile.overlay()) ? o.parent.replacements.get(tile.overlay()) : o.parent);
                    }
                    else{
                        Tile next = nearby.random();
                        replacedTiles.put(next, next.overlay());
                        (o.parent.replacements.containsKey(next.overlay()) ? ores : tiles).put(next, 0); // yeah it is a bit painful
                        next.setFloorNet(next.floor(), o.parent.replacements.containsKey(next.overlay()) ? o.parent.replacements.get(next.overlay()) : o.parent);
                    }
                }else if(Mathf.chance(o.spreadChance)) ores.increment(entry.key);
            }
        }else{
            tiles.clear();
            ores.clear();
            dormantTiles.clear();
            dormantOres.clear();
            replacedTiles.clear();

            if(validator != null)
                validator.cancel();
            if(dormantValidator != null)
                dormantValidator.cancel();
            Log.info("FloorUpdater disposed, tile cache empty");
        }
    }

    private static void updateDormant(){
        if(state.isGame() && !state.isEditor()){
            if(state.isPaused()) return;

            if(tiles.size > 0)
                Log.info(Strings.format("Total objects: @, Active - (@ tiles, @ ores), Dormant - (@ tiles, @ ores)",
                tiles.size + ores.size + dormantTiles.size + dormantOres.size, tiles.size, ores.size, dormantTiles.size, dormantOres.size));

            dormantTiles.each(t -> {
                var tmp = (SpreadingFloor) t.floor();
                Seq<Tile> check = getNearby(t, tmp.spreadOffset, tmp.blacklist);
                if(check.isEmpty()) return;

                dormantTiles.remove(t);
                tiles.put(t, 0);
            });

            dormantOres.each(o -> {
                var tmp = (SpreadingOre) o.overlay();
                Seq<Tile> check = getNearby(o, tmp.spreadOffset, tmp.blacklist);

                boolean replaced = true;
                for(Tile tile : check){
                    if(tile.floor() != tmp.baseFloor){
                        replaced = false;
                        break;
                    }
                }

                if(replaced) return;

                dormantOres.remove(o);
                ores.put(o, 0);
            });
        }
    }

    private static Seq<Tile> getNearby(Tile tile, int radius, ObjectSet<Block> blacklist){
        Seq<Tile> tiles = new Seq<>();
        if(tile.block().isStatic())
            return tiles;
        Tile t = null;

        if(radius <= 0)
            for(int i = 0; i <= 3; i++){ // linear
                t = tile.nearby(i);
                if(t != null && t.floor() != null && !(blacklist.contains(t.floor()) || blacklist.contains(t.overlay())))
                    tiles.add(t);
            }
        else
            tile.circle(radius, tmp -> { // random
                if(tmp != null && tmp.floor() != null && !(blacklist.contains(tmp.floor()) || blacklist.contains(tmp.overlay())))
                    tiles.add(tmp);
            });

        return tiles;
    }

    private static void setFloor(Tile tile, SpreadingFloor t){
        if(t.replacements.containsKey(tile.overlay()) && tile.drop() != null)
            ores.put(tile, 0);

        tile.setFloorNet(
            t.overlay ? tile.floor() : (t.replacements.containsKey(tile.floor()) ? t.replacements.get(tile.floor()) : t.set),
            t.overlay ? (t.replacements.containsKey(tile.overlay()) ? t.replacements.get(tile.overlay()) : t.set) : tile.overlay()
        );
    }
}
