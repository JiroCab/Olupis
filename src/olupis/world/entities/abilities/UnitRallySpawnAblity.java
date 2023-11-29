package olupis.world.entities.abilities;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.entities.Units;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;
import olupis.input.NyfalisUnitCommands;

import static mindustry.Vars.state;

public class UnitRallySpawnAblity extends UnitSpawnAbility {

    public UnitRallySpawnAblity(UnitType unit, float spawnTime, float spawnX, float spawnY){
        super(unit, spawnTime, spawnX, spawnY);
    }
    public UnitRallySpawnAblity(){
        super();
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta * state.rules.unitBuildSpeed(unit.team);

        if(timer >= spawnTime && Units.canCreate(unit.team, this.unit)){
            float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
            spawnEffect.at(x, y, 0f, parentizeEffects ? unit : null);
            Unit u = this.unit.create(unit.team);
            u.set(x, y);
            u.rotation = unit.rotation;
            if (unit.command() != null && unit.isCommandable() && u.isCommandable()){
                u.command().commandPosition(unit.command().targetPos);
                if(unit.isCommandable() && unit.command().command == NyfalisUnitCommands.nyfalisDeployCommand) u.command().command(UnitCommand.moveCommand);
            }
            Events.fire(new EventType.UnitCreateEvent(u, null, unit));
            if(!Vars.net.client()){
                u.add();
            }

            timer = 0f;
        }
    }

    @Override
    public void draw(Unit unit){
        Draw.draw(Draw.z(), () -> {
            float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
            if(Units.canCreate(unit.team, this.unit))Drawf.construct(x, y, this.unit.fullIcon, unit.rotation - 90, timer / spawnTime, 1f, timer);
            else Draw.rect(this.unit.fullIcon, x, y, unit.rotation - 90);
        });

    }

}
