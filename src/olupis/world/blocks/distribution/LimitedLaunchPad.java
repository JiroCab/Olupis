package olupis.world.blocks.distribution;

import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.blocks.campaign.LaunchPad;
import olupis.NyfalisMain;

import static mindustry.Vars.*;

public class LimitedLaunchPad extends LaunchPad {
    public  LimitedLaunchPad(String name){
        super(name);
    }


    public class LimitedLaunchPadBuild extends  LaunchPadBuild{


        @Override
        public void buildConfiguration(Table table){
            if(!state.isCampaign() || net.client()){
                deselect();
                return;
            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
                NyfalisMain.sectorSelect.showSelect(state.rules.sector, other -> {
                    if(state.isCampaign() && other.planet == state.rules.sector.planet){
                        state.rules.sector.info.destination = other;
                    }
                });
                deselect();
            }).size(40f);
        }
    }

}
