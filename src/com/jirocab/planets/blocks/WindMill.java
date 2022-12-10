package com.jirocab.planets.blocks;

import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.*;

import static mindustry.Vars.state;

public class WindMill extends PowerGenerator {

    public WindMill(String name){
        super(name);
        flags = EnumSet.of();
        envEnabled ^= Env.space;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
    }

    public  class windMillBuild extends GeneratorBuild{
        @Override
        public void updateTile(){
            productionEfficiency = 1f;
        }
    }

}
