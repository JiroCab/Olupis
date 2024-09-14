package olupis.world.blocks.turret;

import arc.math.Mathf;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class NyfalisItemTurret extends ItemTurret {
    public float illuminateTime = 30f;

    public  NyfalisItemTurret(String name){
        super(name);
    }

    public class NyfalisItemTurretBuild extends ItemTurretBuild{
        public float progressLight;

        @Override
        public void drawLight() {
            boolean check = (!hasPower || power.status >= 0.5f) && (hasAmmo());
            if(emitLight){
                progressLight = Mathf.lerpDelta(progressLight, check ? lightRadius : 0, this.delta() / illuminateTime);
                if(progressLight >= 0)Drawf.light(x, y, progressLight, lightColor, lightColor.a);
            }
            super.drawLight();
        }


    }

}
