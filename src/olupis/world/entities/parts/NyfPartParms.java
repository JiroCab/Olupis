package olupis.world.entities.parts;

import mindustry.entities.part.DrawPart;

public class NyfPartParms {
    public static final NyfPartParms.NyfPartParams nyfparams = new NyfPartParms.NyfPartParams();

    public static class NyfPartParams{
        public int team;
        public float health,elevation;

        public NyfPartParams set(float health, int team, float elevation){
            this.health = health;
            this.team = team;
            this.elevation = elevation;
            return this;
        }
    }

    public interface NyfPartProgress {
        NyfPartProgress
            health = p -> nyfparams.team,
            team = p -> nyfparams.team,
            elevation = p -> nyfparams.elevation;
        ;

        DrawPart.PartProgress elevationP = p-> nyfparams.elevation;

        float get(NyfPartParams p);
    }

}
