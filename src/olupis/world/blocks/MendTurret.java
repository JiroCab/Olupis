package olupis.world.blocks;

import mindustry.entities.Units;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.meta.BlockGroup;

public class MendTurret extends PowerTurret {

    public MendTurret(String name){
        super(name);
        hasPower = true;
        group = BlockGroup.projectors;
    }

    public class MendTurretBuild extends PowerTurretBuild{
        @Override
        protected void findTarget(){
            float range = range();

            /*Heal Tiles only*/
            if (target == null || target instanceof Conduit.ConduitBuild || !target.within(this, range)){
                target = Units.findDamagedTile(this.team, this.x, this.y);
            }
        }

    }

}
