package olupis.world.entities.parts;

import mindustry.entities.part.DrawPart;

public class NyfPartParms {
    public static final NyfPartParms.NyfPartParams nyfparams = new NyfPartParms.NyfPartParams();

    public static class NyfPartParams{
        public int team;
        public float health,elevation, ammo;

        public NyfPartParams set(float health, int team, float elevation, float ammo){
            this.health = health;
            this.team = team;
            this.elevation = elevation;
            this.ammo = ammo;

            return this;
        }
    }

    public interface NyfPartProgress {
        NyfPartProgress
            health = p -> nyfparams.team,
            team = p -> nyfparams.team,
            elevation = p -> nyfparams.elevation,
            ammo = p -> nyfparams.ammo
                ;
        ;

        DrawPart.PartProgress elevationP = p-> nyfparams.elevation;

        float get(NyfPartParams p);
    }

}
