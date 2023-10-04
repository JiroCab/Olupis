package olupis.input;

import mindustry.ai.UnitCommand;
import mindustry.ai.types.DefenderAI;
import olupis.world.ai.*;

public class NyfalisUnitCommands {

    public static final UnitCommand
        circleCommand = new UnitCommand("nyfalis-circle", "commandRally", u-> {
            if(!u.type().flying){
                var ai = new NyfalisGroundAi();
                ai.shouldCircle = true;
                return ai;
            }else {
                var ai = new AgressiveFlyingAi();
                ai.shouldCircle = true;
                return ai;
            }
        }) {{
            switchToMove = resetTarget = false;
            drawTarget = true;
        }}, healCommand = new UnitCommand("nyfalis-heal", "units", u -> new UnitHealerAi()),
        nyfalisMineCommand = new UnitCommand("mine", "production", u -> new NyfalisMiningAi()),
        nyfalisGuardCommand = new UnitCommand ("nyfalis-guard", "units", u -> new DefenderAI()),
        nyfalisMendCommand = new UnitCommand ("nyfalis-mend", "add", u -> {
            //No other better word for this
            var ai = new UnitHealerAi();
            ai.includeBlocks = true;
            return ai;
        }),
        nyfalisMoveCommand = new UnitCommand("move", "right", u ->new NyfalisGroundAi()){{
            switchToMove = resetTarget = false;
            drawTarget = true;
        }}
    ;

}
