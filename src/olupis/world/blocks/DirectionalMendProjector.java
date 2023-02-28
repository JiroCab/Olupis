package olupis.world.blocks;

import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.graphics.Drawf;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.MendProjector;

import static mindustry.Vars.*;

public class DirectionalMendProjector extends MendProjector {

    public  DirectionalMendProjector(String name){
        super(name);
        rotate = true;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        int offsetX, offsetY;
        if(rotation == 1) {offsetX = 0; offsetY = -20;}
        else if(rotation == 2) {offsetX = 0; offsetY = 20;}
        else if(rotation == 3) {offsetX = -20; offsetY = 0;}
        else {offsetX = 20; offsetY= 0;}

        indexer.eachBlock(player.team(), x * tilesize + offset + offsetX, y * tilesize + offset + offsetY, range, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));
    }

    public class DirectionalMendBuild extends MendBuild implements Ranged{
        public int offsetX, offsetY;

        @Override
        public void updateTile(){
            boolean canHeal = !checkSuppression();

            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency, 0.08f);
            heat = Mathf.lerpDelta(heat, efficiency > 0 && canHeal ? 1f : 0f, 0.08f);
            charge += heat * delta();

            phaseHeat = Mathf.lerpDelta(phaseHeat, optionalEfficiency, 0.1f);

            if(optionalEfficiency > 0 && timer(timerUse, useTime) && canHeal){
                consume();
            }

            if(charge >= reload && canHeal){
                float realRange = range + phaseHeat * phaseRangeBoost;
                charge = 0f;
                if(rotation == 1) {offsetX = 0; offsetY = -20;}
                else if(rotation == 2) {offsetX = 0; offsetY = 20;}
                else if(rotation == 3) {offsetX = -20; offsetY = 0;}
                else {offsetX = 20; offsetY= 0;}

                indexer.eachBlock(this.team, this.x + offsetX, this.y + offsetY , realRange, b -> b.damaged() && !b.isHealSuppressed(), other -> {
                    other.heal(other.maxHealth() * (healPercent + phaseHeat * phaseBoost) / 100f * efficiency);
                    other.recentlyHealed();
                    Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor, other.block);
                });
            }
        }

        @Override
        public void drawSelect(){
            float realRange = range + phaseHeat * phaseRangeBoost;
            if(rotation == 1) {offsetX = 0; offsetY = -20;}
            else if(rotation == 2) {offsetX = 0; offsetY = 20;}
            else if(rotation == 3) {offsetX = -20; offsetY = 0;}
            else {offsetX = 20; offsetY= 0;}

            indexer.eachBlock(this.team, this.x + offsetX, this.y + offsetY, realRange, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));

            Drawf.dashCircle(x, y, realRange, baseColor);
        }

    }
}
