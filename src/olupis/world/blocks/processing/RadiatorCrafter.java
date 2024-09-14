package olupis.world.blocks.processing;

import arc.Core;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.maps.Map;
import mindustry.ui.Styles;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

public class RadiatorCrafter extends GenericCrafter {
    public float passiveOutput = 1f;
    public float attributeMul = 0.5f;
    public Attribute attribute = Attribute.heat;

    public RadiatorCrafter(String name){
        super(name);
    }

    public void setStats(){
        super.setStats();
        stats.add(Stat.affinities, table -> {
            table.table(Styles.grayPanel, c -> {
                Runnable[] rebuild = {null};
                Map[] lastMap = {null};

                rebuild[0] = () -> {
                    c.clearChildren();
                    c.left();
                    if(state.isGame()){
                        
                        var blocks = Vars.content.blocks()
                                .select(block -> {
                                    if ((!(block instanceof Floor)) || !indexer.isBlockPresent(block) || block.attributes.get(attribute) == 0)
                                        return false;
                                    Floor f = (Floor) block;
                                    return !(f.isDeep() && !floating);
                                })
                                .with(s -> s.sort(f -> f.attributes.get(attribute)));

                        if(blocks.any()){
                            int i = 0;
                            for(var block : blocks){

                                c.stack(
                                        new Image(block.uiIcon).setScaling(Scaling.fit),
                                        new Table(t -> t.top().right().add(Core.bundle.formatFloat("bar.nyfalis-radiator.stat", (passiveOutput * 60f), 1)).style(Styles.outlineLabel))
                                ).maxSize(64f);
                                if(++i % 5 == 0){
                                    c.row();
                                }
                            }
                        }else{
                            c.add("@none.inmap");
                        }
                    }else{
                        c.add("@stat.showinmap");
                    }
                };

                rebuild[0].run();

                //rebuild when map changes.
                c.update(() -> {
                    Map current = state.isGame() ? state.map : null;

                    if(current != lastMap[0]){
                        rebuild[0].run();
                        lastMap[0] = current;
                    }
                });
            });
        });
    }


    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(sumAttribute(attribute, x, y) != 0 && outputLiquids != null){
            drawPlaceText(Core.bundle.formatFloat("bar.nyfalis-radiator",  passiveOutput * sumAttribute(attribute, x, y) * 60f , 1), x, y, valid);
        }
    }

    public class  RadiatorCrafterBuild extends GenericCrafterBuild{
        public float sum;

        @Override
        public void updateTile(){
            super.updateTile();


            if(outputLiquids != null && sum > 0){
                for(var output : outputLiquids){
                    handleLiquid(this, output.liquid, Math.min((passiveOutput * sum), liquidCapacity - liquids.get(output.liquid)));
                }
            }

        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();

            sum = sumAttribute(attribute, tile.x, tile.y);
        }
    }

}
