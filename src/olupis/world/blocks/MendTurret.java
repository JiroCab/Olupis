package olupis.world.blocks;

import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import static mindustry.Vars.*;
public class MendTurret extends PowerTurret {

    public MendTurret(String name){
        super(name);
        hasPower = true;

    }

    public class MendTurretBuild extends PowerTurretBuild{
        @Override
        protected void findTarget(){
            float range = range();

            /*Heal Tiles first*/
            if (target == null){
                target = indexer.findTile(this.team, this.x, this.y, range, Building::damaged);
            }else if (targetAir && !targetGround){
                target = Units.bestEnemy(team, x, y, range, e -> !e.dead() && !e.isGrounded() && unitFilter.get(e), unitSort);
            }else{
                target = Units.bestTarget(team, x, y, range, e -> !e.dead() && unitFilter.get(e) && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> targetGround && buildingFilter.get(b), unitSort);

                if(target == null && canHeal()){
                    target = Units.findAllyTile(team, x, y, range, b -> b.damaged() && b != this);
                }
            }
        }

    }

}
