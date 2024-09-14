package olupis.world.blocks.power;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.Autotiler;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BlockStatus;
import olupis.content.NyfalisBlocks;
import olupis.input.NyfalisPlacement;

import static mindustry.Vars.headless;

public class Wire extends Battery implements Autotiler {
    public @Nullable Block  bridgeReplacement;
    public TextureRegion edgeRegion;

    public Wire(String name){
        super(name);
        consumesPower = outputsPower = drawDisabled = allowDiagonal = solid = false;
        underBullets = replaceable = conveyorPlacement = true;
        priority = TargetPriority.transport;
        group = BlockGroup.power;

        ambientSound = Sounds.spark;
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("power", PowerNode.makePowerBalance());
        addBar("batteries", PowerNode.makeBatteryBalance());
    }


    @Override
    public void init(){
        super.init();

        if(bridgeReplacement == null || !(bridgeReplacement instanceof BeamNode)) bridgeReplacement = NyfalisBlocks.wireBridge;
        checkNewDrawDefault();
    }

    void checkNewDrawDefault(){
        if(drawer == null){
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-preview"));
        }
    }

    @Override
    public void load(){
        edgeRegion = Core.atlas.find(name + "-edge");
        checkNewDrawDefault();

        super.load();
        drawer.load(this);
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans){
        if(bridgeReplacement == null ) return;

        NyfalisPlacement.calculateBridges(plans, (BeamNode) bridgeReplacement);
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return tile.build instanceof WireBuild;
    }


    public  class  WireBuild extends BatteryBuild{
        public int blendprox;

        @Override
        public void draw(){
            Draw.rect(this.block.region, this.x, this.y, this.drawrot());

            for(int i = 0; i < 4; i++){
                if((blendprox & (1 << i)) == 0){
                    Draw.rect(edgeRegion, x, y, (rotation - i) * 90);
                }
            }

            drawTeamTop();
        }


        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            if(!headless){
                blendprox = 0;

                for(int i = 0; i < 4; i++){
                    if(nearby(Mathf.mod(rotation - i, 4)) instanceof WireBuild || nearby(Mathf.mod(rotation - i, 4)) instanceof BeamNode.BeamNodeBuild){
                        blendprox |= (1 << i);
                    }
                }
            }
        }


        @Override
        public BlockStatus status(){
            if(Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if(Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }

    }
}
