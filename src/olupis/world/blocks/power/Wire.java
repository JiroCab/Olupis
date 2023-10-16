package olupis.world.blocks.power;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BlockStatus;
import olupis.content.NyfalisBlocks;
import olupis.input.NyfalisPlacement;

public class Wire extends Battery {
    public @Nullable Block  bridgeReplacement;

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
        checkNewDrawDefault();

        super.load();
        drawer.load(this);
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans){
        if(bridgeReplacement == null ) return;

        NyfalisPlacement.calculateBridges(plans, (BeamNode) bridgeReplacement);
    }


    public  class  WireBuild extends BatteryBuild{
        @Override
        public void draw(){
            Draw.rect(this.block.region, this.x, this.y, this.drawrot());
            drawTeamTop();
        }

        @Override
        public BlockStatus status(){
            if(Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if(Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }

    }
}
