package olupis.world.blocks.misc;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.world.blocks.distribution.DirectionalUnloader;

public class DirectionalUnloaderRotatable extends DirectionalUnloader {
    public DirectionalUnloaderRotatable(String name){
        super(name);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, name + "-center"); //doesn't rotate but oh well
    }

    public class  DirectionalUnloaderRotatableBuild extends DirectionalUnloaderBuild{
        @Override
        public void draw(){
            Draw.rect(region, x, y);

            Draw.rect(topRegion, x, y, rotdeg());

            if(unloadItem != null){
                Draw.color(unloadItem.color);
                Draw.rect(centerRegion, x, y, rotdeg());
                Draw.color();
            }else{
                Draw.rect(arrowRegion, x, y, rotdeg());
            }

        }
    }
}
