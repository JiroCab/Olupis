package olupis.world.ai;

import mindustry.ai.Pathfinder;
import mindustry.gen.PathTile;

public class NyfalisPathfind {
    static final int impassable = -1;

    public static final Pathfinder.PathCost

        costLeggedNaval =(team, tile) ->
            PathTile.legSolid(tile) ? impassable : 1 +
            (PathTile.nearLegSolid(tile) ? 3 : 0);
}
