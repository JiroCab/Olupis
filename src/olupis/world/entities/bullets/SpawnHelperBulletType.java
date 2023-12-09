package olupis.world.entities.bullets;

import arc.Events;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.ai.types.MissileAI;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.blocks.ControlBlock;
import olupis.world.ai.AgressiveFlyingAi;
import olupis.world.blocks.defence.ItemUnitTurret;

import static mindustry.Vars.*;

public class SpawnHelperBulletType extends BasicBulletType {
    public boolean hasParent = false;

    @Override
    public @Nullable Bullet create(@Nullable Entityc owner, @Nullable Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY) {
        if (spawnUnit != null) {
            //don't spawn units clientside!
            if (!net.client()) {
                Unit spawned = spawnUnit.create(team);
                spawned.set(x, y);
                spawned.rotation = angle;
                //immediately spawn at top speed, since it was launched
                if (spawnUnit.missileAccelTime <= 0f) spawned.vel.trns(angle, spawnUnit.speed);
                //assign unit owner
                if (spawned.controller() instanceof MissileAI ai) {
                    if (shooter instanceof Unit unit) ai.shooter = unit;
                    if (shooter instanceof ControlBlock control) ai.shooter = control.unit();
                }

                if(owner instanceof ItemUnitTurret.ItemUnitTurretBuild u && spawned.isCommandable() && state.rules.waveTeam != spawned.team){
                    if(u.commandPos != null) spawned.command().commandPosition(u.commandPos);
                    /* 0,0 on create for some reason*/
                    else if(u.targetPos != null && u.targetPos.x != 0 && u.targetPos.y != 0) spawned.command().commandPosition(u.targetPos);

                    if(u.command != null){
                        spawned.command().command(u.command);
                    }
                    Events.fire(new EventType.UnitCreateEvent(spawned, u));
                }
                if(spawned.controller() instanceof AgressiveFlyingAi ai && hasParent) {
                    ai.hasParent = true;
                    if (shooter instanceof Unit unit) ai.parent = unit;
                    if (shooter instanceof ControlBlock control) ai.parent = control.unit();
                }


                spawned.add();
            }
            //Since bullet init is never called, handle killing shooter here
            if (killShooter && owner instanceof Healthc h && !h.dead()) h.kill();
            if (intervalBullet != null){
                for(int i = 0; i < intervalBullets; i++){
                    intervalBullet.create(owner, team, x, y, angle + Mathf.range(intervalRandomSpread) + intervalAngle + ((i - (intervalBullets - 1f)/2f) * intervalSpread));
                }
            }

            //no bullet returned
            return null;
        } return super.create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY);
    }
}
