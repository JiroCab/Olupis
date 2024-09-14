package olupis.world.blocks.environment;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;

public class RotatingProp extends Prop {

    public  RotatingProp(String name){
        super(name);
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy(), Mathf.randomSeed(tile.pos(), 0, 4) * 90);
    }
}
