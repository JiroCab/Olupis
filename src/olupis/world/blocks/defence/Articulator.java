package olupis.world.blocks.defence;

import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.world;

public class Articulator extends Block {

    public Articulator(String name){
        super(name);
        rotate = rotateDraw = destructible = update = true;
        quickRotate = solid = false;
        group = BlockGroup.units;
    }

    public class ArticulatorBuild extends Building {
        public ItemUnitTurret.ItemUnitTurretBuild link;
        public int lastChange = -2;

        /*There's probably a better way to do this, but I can't be asked to look for it */
        public void findLink(){
            if(link != null){
                link.removeModule(this);
            }
            link = this.front() instanceof ItemUnitTurret.ItemUnitTurretBuild bld ? bld : null;
            if(link != null){
                link.updateModules(this);
            }
        }

        @Override
        public void onRemoved(){
            super.onRemoved();

            link = this.front() instanceof ItemUnitTurret.ItemUnitTurretBuild bld ? bld : null;
            if(link != null){
                link.removeModule(this);
            }
        }

        @Override
        public void placed() {
            super.placed();

            link = this.front() instanceof ItemUnitTurret.ItemUnitTurretBuild bld ? bld : null;
            if(link != null){
                link.updateModules(this);
            }
        }

        @Override
        public void updateTile() {
            if (lastChange != world.tileChanges) {
                lastChange = world.tileChanges;
                findLink();
            }
        }


    }
}
