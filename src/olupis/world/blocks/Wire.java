package olupis.world.blocks;

import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Nullable;
import olupis.input.OlupisPlacement;
import olupis.content.OlupisBlocks;
import mindustry.entities.units.BuildPlan;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;

public class Wire extends BeamNode {
    public @Nullable Block  bridgeReplacement;

    public Wire(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();

        if(bridgeReplacement == null || !(bridgeReplacement instanceof BeamNode)) bridgeReplacement = OlupisBlocks.wireBridge;
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans){
        if(bridgeReplacement == null || bridgeReplacement != OlupisBlocks.wire || bridgeReplacement != OlupisBlocks.superConductors) return;

        OlupisPlacement.calculateBridges(plans, (BeamNode) bridgeReplacement);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("power", PowerNode.makePowerBalance());
        addBar("batteries", PowerNode.makeBatteryBalance());
    }

    public  class  WireBuild extends BeamNodeBuild{
        @Override
        public void draw(){
            Draw.rect(this.block.region, this.x, this.y, this.drawrot());
            drawTeamTop();
        }
    }
}
