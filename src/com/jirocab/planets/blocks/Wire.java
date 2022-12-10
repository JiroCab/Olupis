package com.jirocab.planets.blocks;

import arc.graphics.g2d.Draw;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.PowerNode;

public class Wire extends Battery {
    public Wire(String name){
        super(name);

    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("power", PowerNode.makePowerBalance());
        addBar("batteries", PowerNode.makeBatteryBalance());
    }

    public  class  WireBuild extends BatteryBuild{
        @Override
        public void draw(){
            Draw.rect(this.block.region, this.x, this.y, this.drawrot());
            drawTeamTop();
        }
    }


}
