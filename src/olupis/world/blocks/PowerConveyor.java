package olupis.world.blocks;

import mindustry.world.blocks.distribution.Conveyor;

public class PowerConveyor extends Conveyor {
    public float poweredSpeed = 1f;
    public float unpoweredSpeed = 0.5f;

    public PowerConveyor(String name){
        super(name);

    }
    public class PowerConveyorBuild extends ConveyorBuild {
        @Override
        public void updateTile(){
            float s = speed;
            speed = power.status <=0.5f ? unpoweredSpeed : poweredSpeed;
            super.updateTile();
            speed = s;
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
