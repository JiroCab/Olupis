package olupis.world.blocks.defence;

import mindustry.world.blocks.defense.Radar;

public class Ladar extends Radar {

   public Ladar(String name){
       super(name);
   }

    public class LadarBuild extends RadarBuild{
        @Override
        public void updateTile(){
           super.updateTile(); //cant be bothered
        }
    }

}
