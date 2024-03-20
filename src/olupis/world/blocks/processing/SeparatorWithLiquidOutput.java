package olupis.world.blocks.processing;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Nullable;
import mindustry.type.*;
import mindustry.world.blocks.production.Separator;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.tilesize;

public class SeparatorWithLiquidOutput extends Separator {
    public @Nullable LiquidStack[] liquidOutputs;
    /** if true, crafters with multiple liquid outputs will dump excess when there's still space for at least one liquid type */
    public boolean dumpExtraLiquid = true;
    public boolean ignoreLiquidFullness = false;

    /** Liquid output directions, specified in the same order as liquidOutputs. Use -1 to dump in every direction. Rotations are relative to block. */
    public int[] liquidOutputDirections = {-1};

    public SeparatorWithLiquidOutput(String name){
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        if(liquidOutputs != null){
            stats.add(Stat.output, StatValues.liquids(1f, liquidOutputs));
        }
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(liquidOutputs != null){
            for(int i = 0; i < liquidOutputs.length; i++){
                int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                if(dir != -1){
                    Draw.rect(
                            liquidOutputs[i].liquid.fullIcon,
                            x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                            y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                            8f, 8f
                    );
                }
            }
        }
    }

    @Override
    public void setBars() {
        super.setBars();

        //set up liquid bars for liquid outputs
        if(liquidOutputs != null && liquidOutputs.length > 0){
            //no need for dynamic liquid bar
            removeBar("liquid");

            //then display output buffer
            for(var stack : liquidOutputs){
                addLiquidBar(stack.liquid);
            }
        }
    }

    public class SeparatorWithLiquidOutputBuild extends SeparatorBuild{
        @Override
        public boolean shouldConsume(){
            if(liquidOutputs != null && !ignoreLiquidFullness){
                boolean allFull = true;
                for(var output : liquidOutputs){
                    if(liquids.get(output.liquid) >= liquidCapacity - 0.001f){
                        if(!dumpExtraLiquid){
                            return false;
                        }
                    }else{
                        //if there's still space left, it's not full for all liquids
                        allFull = false;
                    }
                }

                //if there is no space left for any liquid, it can't reproduce
                if(allFull){
                    return false;
                }
            }

            int total = items.total();
            //very inefficient way of allowing separators to ignore input buffer storage
            if(consItems != null){
                for(ItemStack stack : consItems.items){
                    total -= items.get(stack.item);
                }
            }

            return total < itemCapacity && enabled;
        }

        @Override
        public void updateTile() {
            totalProgress += warmup * delta();

            if (efficiency > 0) {
                progress += getProgressIncrease(craftTime);
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);

                //continuously output based on efficiency
                if (liquidOutputs != null) {
                    float inc = getProgressIncrease(1f);
                    for (var output : liquidOutputs) {
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

            } else {
                warmup = Mathf.lerpDelta(warmup, 0f, 0.02f);
            }

            if (progress >= 1f) {
                progress %= 1f;
                int sum = 0;
                for (ItemStack stack : results) sum += stack.amount;

                int i = Mathf.randomSeed(seed++, 0, sum - 1);
                int count = 0;
                Item item = null;

                //guaranteed desync since items are random - won't be fixed and probably isn't too important
                for (ItemStack stack : results) {
                    if (i >= count && i < count + stack.amount) {
                        item = stack.item;
                        break;
                    }
                    count += stack.amount;
                }

                consume();

                if (item != null && items.get(item) < itemCapacity) {
                    offload(item);
                }
            }

            if (timer(timerDump, dumpTime)) {
                dump();
                dumpLiquids();
            }
        }

        public void dumpLiquids(){

            if(liquidOutputs != null){
                for(int i = 0; i < liquidOutputs.length; i++){
                    int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                    dumpLiquid(liquidOutputs[i].liquid, 2f, dir);
                }
            }
        }
    }
}
