package olupis.world.blocks.power;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.Autotiler;
import mindustry.world.blocks.power.BeamNode;

import static mindustry.Vars.headless;

public class WireBridge extends BeamNode implements Autotiler {
    public TextureRegion edgeRegion;

    public WireBridge(String name) {
        super(name);
    }

    @Override
    public void load(){
        edgeRegion = Core.atlas.find(name + "-edge");

        super.load();
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return tile.build instanceof Wire.WireBuild;
    }

    public class  WireBridgeBuild extends BeamNodeBuild{
        public int blendprox;

        @Override
        public void draw(){
            super.draw();

            for(int i = 0; i < 4; i++){
                if((blendprox & (1 << i)) == 0){
                    Draw.rect(edgeRegion, x, y, (rotation - i) * 90);
                }
            }
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            if(!headless){
                blendprox = 0;

                for(int i = 0; i < 4; i++){
                    if(nearby(Mathf.mod(rotation - i, 4)) instanceof Wire.WireBuild || nearby(Mathf.mod(rotation - i, 4)) instanceof BeamNode.BeamNodeBuild){
                        blendprox |= (1 << i);
                    }
                }
            }
        }



    }

}
