package olupis.world.entities.units;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.Crawlc;
import mindustry.gen.Unit;

/*ehehe snek*/
public class SnekUnitType extends NyfalisUnitType{
    /*Affects how the Status cell is renders, replaces the two `for` statements*/
    public int cellSegmentParent = segments -1, cellSegmentParentRot = segments + 4;
    public Seq <Integer> segmentsWithCells = new Seq<>();
    public boolean customShadow = true;

    public SnekUnitType(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        for(int i = 0; i < segments; i++) if (Core.atlas.find(name + "-cell-" + i).found()) segmentsWithCells.add(i);
        if(customShadow) softShadowRegion = Core.atlas.find("olupis-shadow-long");
    }

    @Override
    public void drawCell(Unit unit){
        if (unit instanceof Crawlc crawl) {
            /*Tbh I don't know what half this does since I just copied and pasted it, just manually tune the numbers*/
            if(drawCell){
                float trns = Mathf.sin(crawl.crawlTime() + cellSegmentParent * segmentPhase, segmentScl, segmentMag),
                        rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, (float) cellSegmentParentRot  /( segments - 1f)),
                        tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);

                applyColor(unit);
                Draw.color(cellColor(unit));
                Draw.rect(cellRegion, unit.x + tx, unit.y + ty, rot - 90);
                Draw.reset();
            }

            if(segmentsWithCells.size >= 1f) for (Integer i : segmentsWithCells) {
                float trns = Mathf.sin(crawl.crawlTime() + i * segmentPhase, segmentScl, segmentMag),
                        rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, (float) i / (segments - 1f)),
                        tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);
                applyColor(unit);
                Draw.color(cellColor(unit));
                Draw.rect(Core.atlas.find(name + "-cell-" + i), unit.x + tx, unit.y + ty, rot - 90);
                Draw.reset();
            }

        }else {
            super.drawCell(unit);
            Log.err(unit.type.localizedName + " is not a Crawlc! >:( not snek");
        }

    }
}

