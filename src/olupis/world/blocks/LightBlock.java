package olupis.world.blocks;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.*;

public class LightBlock extends Block {
    public float brightness = 0.9f;
    public float radius = 200f;

    public LightBlock(String name){
        super(name);
        configurable = true;
        saveConfig = true;
        update = true;
        swapDiagonalPlacement = true;
        config(Integer.class, (lightBuild tile, Integer value) -> tile.color = value);
    }

    @Override
    public void init(){
        lightRadius = radius*3f;
        emitLight = true;

        super.init();
    }
    @Override
    public boolean canBreak(Tile tile){
        return accessible();
    }

    public boolean accessible(){
        return !privileged || state.rules.infiniteResources || state.playtestingMap != null;
    }



    public class lightBuild extends Building{
        public int color = Pal.accent.rgba();
        public float smoothTime = 1f;

        @Override
        public void updateTile(){
            smoothTime = Mathf.lerpDelta(smoothTime, timeScale, 0.1f);
        }


        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4){
            if(!accessible())return;
            if(type == LAccess.color){
                color = Tmp.c1.fromDouble(p1).rgba8888();
            }

            super.control(type, p1, p2, p3, p4);
        }

        @Override
        public void buildConfiguration(Table table){
            if(!accessible())return;
            table.button(Icon.pencil, Styles.cleari, () -> {
                ui.picker.show(Tmp.c1.set(color).a(0.5f), false, res -> configure(res.rgba()));
                deselect();
            }).size(40f);

        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(!accessible())return false;
            if(this == other){
                deselect();
                return false;
            }

            return true;
        }

        @Override
        public Integer config(){
            return color;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(color);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            color = read.i();
        }
    }



}
