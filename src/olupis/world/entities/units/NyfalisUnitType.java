package olupis.world.entities.units;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import olupis.content.NyfalisItemsLiquid;
import olupis.world.ai.NyfalisCommand;

public class NyfalisUnitType extends UnitType {
    public boolean canCircleTarget = false;
    /*Effects that a unit spawns with, gnat cheese fix*/
    public StatusEffect spawnStatus = StatusEffects.none;
    public float spawnStatusDuration = 60f * 5f;

    public NyfalisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("371404");
        ammoType = new ItemAmmoType(NyfalisItemsLiquid.rustyIron);
        researchCostMultiplier = 6f;
    }

    @Override
    public void init(){
        super.init();
        if(canCircleTarget){
            Seq<UnitCommand> cmds = Seq.with(commands);
            cmds.add(NyfalisCommand.circleCommand);
            commands = cmds.toArray();
        }
    }

    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        unit.ammo = ammoCapacity; //fill up on ammo upon creation
        unit.elevation = flying ? 1f : 0;
        unit.heal();
        if(unit instanceof TimedKillc u){
            u.lifetime(lifetime);
        }
        unit.apply(spawnStatus, 200f);
        return unit;
    }
}
