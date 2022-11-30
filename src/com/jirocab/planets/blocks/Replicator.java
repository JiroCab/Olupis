package com.jirocab.planets.blocks;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.*;

public class Replicator extends PayloadBlock {
    public float delay = 60f;

    public Replicator(String name){
        super(name);

        //size = 4;
        update = true;
        outputsPayload = true;
        hasPower = false;
        rotate = true;
        selectionRows = selectionColumns = 8;
        //make sure to display large units.
        clipSize = 120;
        noUpdateDisabled = true;
        clearOnDoubleTap = true;
        regionRotated1 = 1;
        commandable = true;
        configurable = true;
        group = BlockGroup.units;
        solid = true;
        privileged = true;

        config(UnitType.class, (ReplicatorBuild build, UnitType unit) -> {
            if(canProduce(unit) && build.unit != unit){
                build.unit = unit;
                build.block = null;
                build.payload = null;
                build.scl = 0f;
            }
        });

        configClear((ReplicatorBuild build) -> {
            build.block = null;
            build.unit = null;
            build.payload = null;
            build.scl = 0f;
        });
    }
    public boolean accessible(){
            return !privileged || state.rules.editor || state.playtestingMap != null;
    }

    @Override
    public boolean canBreak(Tile tile){
        return accessible();
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, outRegion, topRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        if (!accessible())return;
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(outRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(topRegion, plan.drawx(), plan.drawy());
    }

    public boolean canProduce(UnitType t){
        return !t.isHidden() && !t.isBanned() && t.supportsEnv(state.rules.env);
    }

    public class ReplicatorBuild extends PayloadBlockBuild<Payload>{
        public UnitType unit;
        public Block block;
        public @Nullable Vec2 commandPos;
        public float scl;

        @Override
        public Vec2 getCommandPosition(){
            return commandPos;
        }

        @Override
        public void onCommand(Vec2 target){
            commandPos = target;
        }

        @Override
        public void buildConfiguration(Table table){
            if(!accessible()){
                //go away
                deselect();
                return;
            };
            ItemSelection.buildTable(Replicator.this, table,
                    content.units().select(Replicator.this::canProduce).as(),
                    () -> (UnlockableContent)config(), this::configure, selectionRows, selectionColumns);
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            return false;
        }

        @Override
        public void updateTile(){
            super.updateTile();
            Time.runTask(delay, ()->{
                if(payload == null){
                    scl = 0f;
                    if(unit != null){
                        payload = new UnitPayload(unit.create(team));

                        Unit p = ((UnitPayload)payload).unit;
                        if(commandPos != null && p.isCommandable()){
                            p.command().commandPosition(commandPos);
                        }
                    }else if(block != null){
                        payload = new BuildPayload(block, team);
                    }
                    payVector.setZero();
                    payRotation = rotdeg();
                }
            });
            scl = Mathf.lerpDelta(scl, 1f, 0.1f);

            moveOutPayload();
        }

        @Override
        public Object config(){
            return  unit;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(outRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y);

            Draw.scl(scl);
            drawPayload();
            Draw.reset();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(unit == null ? -1 : unit.id);
            write.s(block == null ? -1 : block.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            unit = Vars.content.unit(read.s());
            block = Vars.content.block(read.s());
        }
        @Override
        public boolean canPickup(){
            return false;
        }

        @Override
        public void damage(float damage){
            if(!privileged){
                super.damage(damage);
            }
        }

        //editor-only processors cannot be damaged or destroyed
        @Override
        public boolean collide(Bullet other){
            return !privileged;
        }
    }
}
