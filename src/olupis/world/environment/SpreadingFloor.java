package olupis.world.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Log;
import mindustry.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class SpreadingFloor extends Floor{
    /** The amount of times the chance must be rolled */
    public int spreadTries = 3;
    /** Base chance for the tile to try to spread, updated every second */
    public double spreadChance = 0.013;
    /** Max tile offset, leave at 0 for linear spread */
    public int spreadOffset = 0;
    /** Efficiency of drills on ores converted by this, set to -1 in order to manually adjust for each ore, otherwise this value will be set for each */
    public float drillEfficiency = 1f;
    /** Whether this block spreads to all surrounding tiles at once, linear spreading only! */
    public boolean fullSpread = false;
    /** Spreading blacklist */
    public ObjectSet<Block> blacklist = new ObjectSet<>();
    /** Block this can "upgrade" into, upgrading takes just as long as spreading */
    public Block next = null;
    /** Block this can spread around, don't set custom unless necessary */
    public Block set = this;
    /** Whether this floor spreads while growing, spreading is always full-linear here */
    public boolean growSpread = false;
    /** Whether this floor is used as an overlay, DO NOT USE unless you know what you're doing, it WILL replace ores */
    public boolean overlay = false;
    /** A list of replacements for floors, stock block first, then replacement */
    public ObjectMap<Block, Block> replacements = new ObjectMap<>();

    public SpreadingFloor(String name, int variants){
        super(name);
        this.variants = variants;
    }

    @Override
    public void init(){
        super.init();

        if(growSpread)
            fullSpread = true;

        if(fullSpread)
            spreadOffset = 0;

        Vars.content.blocks().each(b -> {
            if(b instanceof SpreadingFloor
            || b instanceof SpreadingOre
            || b.isFloor() && b.asFloor().isLiquid)
                blacklist.add(b);
        });

        if(next != null)
            blacklist.add(next);
        if(set != null)
            blacklist.add(set);

        handleBlacklist(blacklist);

        replacements.each((b, r) -> {
            if(r instanceof SpreadingOre o){ // automation ftw
                o.itemDrop = b.itemDrop;
                o.mapColor = b.mapColor;
                o.spreadChance = spreadChance;
                o.spreadTries = spreadTries;
                o.spreadOffset = spreadOffset;
                if(drillEfficiency > 0)
                    o.drillEfficiency = drillEfficiency;
                o.fullSpread = fullSpread;
                o.blacklist = blacklist;
                o.parent = this;
            }
        });
    }

    public void handleBlacklist(ObjectSet<Block> list){
        if(set != this && set instanceof SpreadingFloor t){
            if(t.growSpread || t.next == null)
                t.blacklist.addAll(list);
            else t.handleBlacklist(list);
        }

        if(next instanceof SpreadingFloor f){
            if(f.growSpread || f.next == null)
                f.blacklist.addAll(list);
            else f.handleBlacklist(list);
        }
    }

    @Override
    public void drawBase(Tile tile){
        if(overlay)
            Draw.rect(this.variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, this.variantRegions.length - 1))], tile.worldx(), tile.worldy());
        else
            super.drawBase(tile);
    }
}
