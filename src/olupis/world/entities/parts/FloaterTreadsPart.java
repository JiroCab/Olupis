package olupis.world.entities.parts;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.entities.part.RegionPart;

public class FloaterTreadsPart extends RegionPart {
	//todo: actully make this go from one to zero not instantly
    public PartProgress alphaProgress =  NyfPartParms.NyfPartProgress.floatingP;
    float minAlpha = 0.1f, maxAlpha = 1f;

    public  FloaterTreadsPart(String region){super(region);}
    public FloaterTreadsPart(String region, Blending blending, Color color){
        super(region, blending, color);
    }
    public FloaterTreadsPart(){}


    @Override
    public void draw(PartParams params){
        Draw.alpha(Mathf.lerp(minAlpha, maxAlpha, alphaProgress.getClamp(params)));
        super.draw(params);//todo maybe split and do the fancy tack animation
    }

}
