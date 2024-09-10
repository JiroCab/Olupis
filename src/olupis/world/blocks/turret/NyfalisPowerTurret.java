package olupis.world.blocks.turret;

import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.PowerTurret;

public class NyfalisPowerTurret extends PowerTurret {
    public NyfalisPowerTurret(String name){
        super(name);
    }

    public class NyfalisPowerTurretBuild extends PowerTurretBuild{

        @Override
        public void drawLight() {
            boolean check = (hasPower && power.status >= 0.5f);
            if(emitLight && check)Drawf.light(x, y, lightRadius, lightColor, lightColor.a);
            super.drawLight();
        }

    }

}
