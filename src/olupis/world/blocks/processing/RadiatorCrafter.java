package olupis.world.blocks.processing;

import arc.Core;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Attribute;

public class RadiatorCrafter extends GenericCrafter {
    public float passiveOutput = 1f;
    public float attributeMul = 0.5f;
    public Attribute attribute = Attribute.heat;

    public RadiatorCrafter(String name){
        super(name);
    }


    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(sumAttribute(attribute, x, y) != 0 && outputLiquids != null){
            drawPlaceText(Core.bundle.formatFloat("bar.nyfalis-radiator",  passiveOutput * sumAttribute(attribute, x, y) * 60f , 0), x, y, valid);
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
