package olupis.world;

import mindustry.io.SaveFileReader.*;

import java.io.*;
import java.util.Arrays;

import static mindustry.Vars.*;

public class EnvSaveIO implements CustomChunk{
    @Override
    public void write(DataOutput stream) throws IOException {
        for(int x = 0; x < world.width(); x++){
            for(int y = 0; y < world.height(); y++){
                for(int i = 0; i < 4; i++){
                    stream.writeShort(EnvUpdater.data[x][y][i]);
                    stream.writeShort(EnvUpdater.replaced[x][y][i]);
                }
            }
        }
    }

    @Override
    public void read(DataInput stream) throws IOException {
        if(EnvUpdater.data == null || EnvUpdater.replaced == null){
            EnvUpdater.data = EnvUpdater.replaced = new short[world.width()][world.height()][4];
            for(int x = 1; x < world.width(); x++)
                for(int y = 1; y < world.height(); y++)
                    Arrays.fill(EnvUpdater.replaced[x][y], (short) -1);
        }

        for(int x = 0; x < world.width(); x++){
            for(int y = 0; y < world.height(); y++){
                for(int i = 0; i < 4; i++){
                    EnvUpdater.data[x][y][i] = stream.readShort();
                    EnvUpdater.replaced[x][y][i] = stream.readShort();
                }
            }
        }
    }

    @Override
    public boolean writeNet(){
        return false;
    }
}