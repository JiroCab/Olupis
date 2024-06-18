package olupis.world.environment;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

public class SpreadingOre extends OreBlock{
    public int spreadTries;
    public double spreadChance;
    public int spreadOffset;
    public boolean fullSpread;
    public ObjectSet<Block> blacklist = new ObjectSet<>();
    public SpreadingFloor parent;
    /** A floor this ore needs to have around in order to start growing, as well as the one it places under itself when it's done growing */
    public Block baseFloor = Blocks.air;
    /** How many variants of overlay this ore has, not required if the ore already has a dedicated sprite */
    public int overlayVariants = 0;
    public TextureRegion[] overlayRegion = new TextureRegion[overlayVariants];
    /** Name of the overlay texture */
    public String overlayName = "";

    public SpreadingOre(String name, Block block){
        super(name);
        baseFloor = block; // <- here
    }
    // TODO: Make this autogenerate ores in hjson if they do not exist already, use replaced renedering for drawing the additional overlays, copy stats from the floor that can replace the ore
    // This will be used to provide compatibility with other mods without the need for those mods to add the ore themselves

    @Override
    public void drawBase(Tile tile) {
        Draw.rect(variantRegions[Mathf.randomSeed((long)tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        if(overlayRegion.length >= 1) Draw.rect(overlayRegion[Mathf.randomSeed((long)tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
    }

    @Override
    public void load(){
        super.load();
        if(overlayVariants <= 0) return;

        if(overlayRegion.length >= 1){
            for(int i = 0; i < overlayVariants; i++){
                overlayRegion[i] = Core.atlas.find(overlayName + (i + 1));
            }
        }else overlayRegion[0] = Core.atlas.find(overlayName);
    }

    @Override
    public void init(){
        Vars.content.blocks().each(b -> {
            if(b instanceof SpreadingFloor
            || b.isFloor() && b.asFloor().isLiquid)
                blacklist.add(b);
        });

        if(baseFloor != null)
            blacklist.add(baseFloor);

        handleBlacklist(blacklist);
    }

    public void handleBlacklist(ObjectSet<Block> list){
        if(baseFloor instanceof SpreadingFloor f){
            if(f.growSpread || f.next == null)
                f.blacklist.addAll(list);
            else f.handleBlacklist(list);
        }
    }
}
