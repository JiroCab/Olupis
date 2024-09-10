package olupis.world.blocks.turret;

import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

public class NyfalisLiquidTurret  extends LiquidTurret {
    public NyfalisLiquidTurret(String name){
        super(name);
    }

    public class NyfalisLiquidTurretBuild extends LiquidTurretBuild{

        @Override
        public void drawLight() {
            boolean check = (hasPower && power.status >= 0.5f);
            if(emitLight && check)Drawf.light(x, y, lightRadius, lightColor, lightColor.a);
            super.drawLight();
        }

    }

}
