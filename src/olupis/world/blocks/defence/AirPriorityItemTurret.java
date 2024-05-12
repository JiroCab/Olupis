package olupis.world.blocks.defence;

import mindustry.entities.Units;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class AirPriorityItemTurret extends ItemTurret {

    public AirPriorityItemTurret(String name){
        super(name);
    }

    public class AirPriorityTurretItemBuild extends ItemTurretBuild{
        @Override
        protected void findTarget(){
            float range = range();

            if(targetAir && !targetGround){
                target = Units.bestEnemy(team, x, y, range, e -> !e.dead() && !e.isGrounded() && unitFilter.get(e), unitSort);
            }else{
                target = Units.bestEnemy(team, x, y, range, e -> !e.dead() && !e.isGrounded() && unitFilter.get(e), unitSort);
                //hit air 1st before doing ground
                if(target == null) target = Units.bestTarget(team, x, y, range, e -> !e.dead() && unitFilter.get(e) && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> targetGround && buildingFilter.get(b), unitSort);
            }

            if(target == null && canHeal()){
                target = Units.findAllyTile(team, x, y, range, b -> b.damaged() && b != this);
            }
        }
    }
}