package com.jirocab.planets.blocks;

import arc.math.Mathf;
import mindustry.world.blocks.distribution.Conveyor;

public class PowerConveyor extends Conveyor {
    public float baseEfficiency = 0f;
    public float poweredSpeed = 1f;
    public float unpoweredSpeed = 0.5f;
    private static final float itemSpace = 0.4f;

    public PowerConveyor(String name){
        super(name);

    }
    public class PowerConveyorBuild extends ConveyorBuild {
        @Override
        public void updateTile(){
            float eff = enabled ? (efficiency + baseEfficiency) : 0f;
            float speed = power.status <=1 ? unpoweredSpeed : poweredSpeed;

            minitem = 1f;
            mid = 0;

            //skip updates if possible
            if(len == 0){
                clogHeat = 0f;
                sleep();
                return;
            }

            float nextMax = aligned ? 1f - Math.max(itemSpace - nextc.minitem, 0) : 1f;
            float moved = speed * edelta();

            for(int i = len - 1; i >= 0; i--){
                float nextpos = (i == len - 1 ? 100f : ys[i + 1]) - itemSpace;
                float maxmove = Mathf.clamp(nextpos - ys[i], 0, moved);

                ys[i] += maxmove;

                if(ys[i] > nextMax) ys[i] = nextMax;
                if(ys[i] > 0.5 && i > 0) mid = i - 1;
                xs[i] = Mathf.approach(xs[i], 0, moved*2);

                if(ys[i] >= 1f && pass(ids[i])){
                    //align X position if passing forwards
                    if(aligned){
                        nextc.xs[nextc.lastInserted] = xs[i];
                    }
                    //remove last item
                    items.remove(ids[i], len - i);
                    len = Math.min(i, len);
                }else if(ys[i] < minitem){
                    minitem = ys[i];
                }
            }

            if(minitem < itemSpace + (blendbits == 1 ? 0.3f : 0f)){
                clogHeat = Mathf.approachDelta(clogHeat, 1f, 1f / 60f);
            }else{
                clogHeat = 0f;
            }

            noSleep();
        }
        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();

            int[] bits = buildBlending(tile, rotation, null, true);
            blendbits = bits[0];
            blendsclx = bits[1];
            blendscly = bits[2];
            blending = bits[4];

            next = front();
            nextc = next instanceof ConveyorBuild && next.team == team ? (ConveyorBuild)next : null;
            aligned = nextc != null && rotation == next.rotation;
        }
    }
}
