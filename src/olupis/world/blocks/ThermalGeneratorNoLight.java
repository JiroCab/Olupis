package olupis.world.blocks;

import mindustry.world.blocks.power.ThermalGenerator;

public class ThermalGeneratorNoLight extends ThermalGenerator {
    public ThermalGeneratorNoLight(String name){
        super(name);
    }

    public class ThermalGeneratorBuild extends GeneratorBuild {

        @Override
        public void drawLight(){
            /*Don't*/
        }
    }
}
