package olupis.world.consumer;

import arc.struct.Seq;
import mindustry.content.Liquids;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeCoolant;
import olupis.content.NyfalisItemsLiquid;

public class ConsumeLubricant extends ConsumeCoolant {
    Seq<Liquid> allowedCoolants = Seq.with(Liquids.oil, NyfalisItemsLiquid.lubricant);

    public ConsumeLubricant(float amount){
        super(amount);
        this.filter = liquid -> allowedCoolants.contains(liquid);
    }
}
