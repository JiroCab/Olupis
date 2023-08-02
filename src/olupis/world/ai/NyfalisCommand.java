package olupis.world.ai;

import mindustry.ai.UnitCommand;

public class NyfalisCommand {

    public static final UnitCommand circleCommand = new UnitCommand("nyfalis-circle", "commandRally", u-> {
        var ai = new AgressiveFlyingAi();
        ai.shouldCircle = true;
        return ai;
    }){{
        switchToMove = resetTarget = false;
        drawTarget = true;
    }};
}
