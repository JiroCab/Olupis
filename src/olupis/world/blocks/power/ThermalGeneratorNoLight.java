package olupis.world.blocks.power;

import mindustry.world.blocks.power.ThermalGenerator;

public class ThermalGeneratorNoLight extends ThermalGenerator {
    public ThermalGeneratorNoLight(String name){
        super(name);
        noUpdateDisabled = true;
    }

    public class ThermalGeneratorNoLightBuild extends ThermalGeneratorBuild {

        @Override
        public void drawLight(){
            /*Don't*/
        }
    }
}
