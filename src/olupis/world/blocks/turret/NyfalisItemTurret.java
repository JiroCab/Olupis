package olupis.world.blocks.turret;

import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class NyfalisItemTurret extends ItemTurret {
    public  NyfalisItemTurret(String name){
        super(name);
    }

    public class NyfalisItemTurretBuild extends ItemTurretBuild{

        @Override
        public void drawLight() {
            boolean check = (!hasPower || power.status >= 0.5f) && (hasAmmo());
            if(emitLight && check)Drawf.light(x, y, lightRadius, lightColor, lightColor.a);
            super.drawLight();
        }


    }

}
