package olupis.world.blocks;

import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.ui.layout.Scl;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.*;

public class PropellerCoreBlock extends CoreBlock {

    public PropellerCoreBlock(String name){
        super(name);
    }

    @Override
    public void drawLanding(CoreBuild build, float x, float y){
        float fout = renderer.getLandTime() / coreLandDuration;

        if(renderer.isLaunching()) fout = 1f - fout;

        float fin = 1f - fout;

        float scl = Scl.scl(4f) / renderer.getDisplayScale();
        float shake = 0f;
        float s = region.width * region.scl() * scl * 3.6f * Interp.pow2Out.apply(fout);
        float rotation = Interp.pow2In.apply(fout) * 135f;
        x += Mathf.range(shake);
        y += Mathf.range(shake);
        float thrustOpen = 0.25f;
        float thrusterFrame = fin >= thrustOpen ? 1f : fin / thrustOpen;

        //when launching, thrusters stay out the entire time.
        if(renderer.isLaunching()){
            Interp i = Interp.pow2Out;
            thrusterFrame = i.apply(Mathf.clamp(fout*13f));
        }

        //TODO spikier heat
        Draw.rect("circle-shadow", x, y, s, s);

        Draw.scl(scl);

        drawLandingThrusters(x, y, rotation, thrusterFrame);

        Drawf.spinSprite(region, x, y, rotation);

        drawLandingThrusters(x, y, rotation, thrusterFrame);
        Draw.alpha(1f);

        if(teamRegions[build.team.id] == teamRegion) Draw.color(build.team.color);

        Drawf.spinSprite(teamRegions[build.team.id], x, y, rotation);

        Draw.color();
        Draw.scl();
        Draw.reset();
    }

    @Override
    protected void drawLandingThrusters(float x, float y, float rotation, float frame){
        /*Renders thrusters/propellers in flight*/
        float length = thrusterLength * (frame - 1f) - 1f/4f;
        float alpha = Draw.getColor().a;

        //two passes for consistent lighting
        for(int j = 0; j < 2; j++){
            for(int i = 0; i < 4; i++){
                var reg = i >= 2 ? thruster2 : thruster1;
                float rot = (i * 90) + rotation % 90f;
                Tmp.v1.trns(rot, length * Draw.xscl);

                //second pass applies extra layer of shading
                if(j == 1){
                    Tmp.v1.rotate(-90f);
                    Draw.alpha((rotation % 90f) / 90f * alpha);
                    rot -= 90f;
                    Draw.rect(reg, x + Tmp.v1.x, y + Tmp.v1.y, rot);
                }else{
                    Draw.alpha(alpha);
                    Draw.rect(reg, x + Tmp.v1.x, y + Tmp.v1.y, rot);
                }
            }
        }
        Draw.alpha(1f);
    }


    public class PropellerCoreBuild extends CoreBuild {

        @Override
        public void updateLandParticles(){
            if(renderer.getLandTime() >= 1f){
                tile.getLinkedTiles(t -> {
                    if(Mathf.chance(0.4f)){
                        Fx.coreLandDust.at(t.worldx(), t.worldy(), angleTo(t.worldx(), t.worldy()) + Mathf.range(30f), Tmp.c1.set(t.floor().mapColor).mul(1.5f + Mathf.range(0.15f)));
                    }
                });

                renderer.setLandPTimer(0f);
            }
        }
    }

}
