package olupis.world.blocks.processing;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.*;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.content;

public class BoostableGenericCrafter extends GenericCrafter {
    float boostProductionMultiplier = 0.35f;
    float boostLiquidOutputMultiplier = 1.2f;

    public BoostableGenericCrafter(String name){
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.booster);

        stats.add(Stat.booster, table -> {
            table.row();
            ConsumeItems Icon = findConsumer(cons -> cons instanceof ConsumeItems && cons.booster);
            if(Icon != null) table.table(c -> {
                for (ItemStack stack : Icon.items) {
                    Item item = stack.item;
                    c.table(Styles.grayPanel, b -> {
                        b.image(item.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.add(item.localizedName).left().row();
                            info.add(stack.amount + StatUnit.perSecond.localized()).left().color(Color.lightGray);
                        });

                        b.table(bt -> {
                            bt.right().defaults().padRight(3).left();
                            bt.add("[accent]" + Strings.autoFixed(boostLiquidOutputMultiplier * 100, 2) + "%[lightgray] " + Core.bundle.get("stat.output")).pad(5);
                        }).right().grow().pad(10f).padRight(15f);
                    }).growX().pad(5).row();
                }
            }).growX().colspan(table.getColumns());

            table.table(c -> {
                for(Liquid liquid : content.liquids()){
                    if(!liquidFilter[liquid.id]) continue;
                    ConsumeLiquid lcon = findConsumer(cons -> cons instanceof ConsumeLiquid e && cons.booster && e.liquid == liquid);

                    c.table(Styles.grayPanel, b -> {
                        b.image(liquid.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.add(liquid.localizedName).left().row();
                            info.add(Strings.autoFixed(lcon.amount * 60f, 2) + StatUnit.perSecond.localized()).left().color(Color.lightGray);
                        });

                        b.table(bt -> {
                            bt.right().defaults().padRight(3).left();
                            b.table(bst -> {
                                bt.add("[accent]" + Strings.autoFixed((craftTime * boostProductionMultiplier) * 60f, 2) + " " + StatUnit.seconds.localized()).center().row();
                                bt.add("[lightgray]" + Core.bundle.get("stat.productiontime"));
                            }).pad(5);
                        }).right().grow().pad(10f).padRight(15f);
                    }).growX().pad(5).row();
                }
            }).growX().colspan(table.getColumns());
            table.row();
        });
    }


    public class  BoostableGenericCrafterBuild extends GenericCrafterBuild{

        @Override
        public void updateTile(){
            if(efficiency > 0){
                float mul = Mathf.lerp(1f, boostProductionMultiplier, optionalEfficiency);
                progress += getProgressIncrease(craftTime * mul);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

                //continuously output based on efficiency
                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f), out = Mathf.lerp(1f, boostLiquidOutputMultiplier, optionalEfficiency);
                    for(var output : outputLiquids){
                        handleLiquid(this, output.liquid, Math.min((output.amount * out) * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            //TODO may look bad, revert to edelta() if so
            totalProgress += warmup * Time.delta;

            if(progress >= 1f){
                craft();
            }

            dumpOutputs();
        }

    }



}
