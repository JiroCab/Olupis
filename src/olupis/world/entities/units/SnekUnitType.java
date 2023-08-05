package olupis.world.entities.units;

import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.gen.Crawlc;
import mindustry.gen.Unit;

/*ehehe snek*/
public class SnekUnitType extends NyfalisUnitType{
    /*Affects how the Status cell is renders, replaces the two `for` statements*/
    public int cellSegmentParent = segments -1, cellSegmentParentRot = segments + 4;

    public SnekUnitType(String name){
        super(name);
    }

    @Override
    public void drawCell(Unit unit){
        if (unit instanceof Crawlc crawl) {
            /*Tbh I don't know what half this does since i just copied and pasted it, just manually tune the numbers*/
            float trns = Mathf.sin(crawl.crawlTime() + cellSegmentParent * segmentPhase, segmentScl, segmentMag);
            float rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, (float) cellSegmentParentRot  /( segments - 1f));
            float tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);

            applyColor(unit);
            Draw.color(cellColor(unit));
            Draw.rect(cellRegion, unit.x + tx, unit.y + ty, rot - 90);
            Draw.reset();
        }else {
            super.drawCell(unit);
            Log.err(unit.type.localizedName + " is not a Crawlc! >:( not snek");
        }

    }
}

