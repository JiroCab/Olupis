package olupis.world.ai;

import mindustry.ai.types.CommandAI;
import mindustry.ai.types.FlyingAI;
import olupis.input.NyfalisUnitCommands;
import olupis.world.entities.units.NyfalisUnitType;

public class DeployedAi extends FlyingAI {

    @Override
    public void updateUnit(){
        if(unit.controller() instanceof CommandAI ai){
            if(unit.isCommandable() && unit.command().command != NyfalisUnitCommands.nyfalisDeployCommand) ai.defaultBehavior();
            target = ai.attackTarget;
        }
        super.updateUnit();
    }

    @Override
    public void updateMovement(){
        if(unit.isFlying()){
            if (!unit.type.flying && unit.controller() instanceof CommandAI ai) {
                ai.defaultBehavior();
            }
            if(unit.type.canBoost && unit.type instanceof NyfalisUnitType nyf && nyf.deployLands){
                unit.updateBoosting(false);
            }
        }
    }

}
