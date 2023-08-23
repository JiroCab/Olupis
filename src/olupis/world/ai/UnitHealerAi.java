package olupis.world.ai;

import arc.math.Mathf;
import mindustry.ai.types.DefenderAI;
import mindustry.entities.Units;
import mindustry.gen.Teamc;

public class UnitHealerAi extends DefenderAI {

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){

        //Sort by lowest health and closer target.
        var result = Units.closest(unit.team, x, y, Math.max(range, 400f), u -> !u.dead() && u.type != unit.type && u.targetable(unit.team) && u.type.playerControllable,
                (u, tx, ty) -> -(u.maxHealth / unit.health) + Mathf.dst2(u.x, u.y, tx, ty) / 6400f);
        if(result != null) return result;
        return super.findTarget(x, y, range, air, ground);
    }

}
