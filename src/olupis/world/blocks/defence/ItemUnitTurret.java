package olupis.world.blocks.defence;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.scene.style.Drawable;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.TypeIO;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.ui.*;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.Stat;
import olupis.content.NyfalisFxs;
import olupis.world.entities.units.NyfalisUnitType;

import java.util.Objects;

import static mindustry.Vars.*;

/*The cross bread of a Turret and Unit factory, for the sake of being different*/
public class ItemUnitTurret extends ItemTurret {
    /*common required items for all unit types*/
    public Seq<Item> requiredItems = new Seq<>();
    /*Minimum Items needed*/
    public int requiredItemsCost;
    /*Parameters when failing to make a unit*/
    public Sound failedMakeSound = Sounds.dullExplosion;
    public float failedMakeSoundPitch = 0.7f, getFailedMakeSoundVolume = 1f;
    public Effect failedMakeFx = NyfalisFxs.failedMake;
    public TextureRegion bottomRegion;
    /*Hovering Shows the unit creation*/
    public boolean hoverShowsSpawn = false, payloadExitShow = true;
    /*Aim at the rally point*/
    public boolean rallyAim = false;
    /*Aim for closest liquid*/
    public boolean liquidAim = false;

    public float payloadSpeed = 0.7f;

    /*Todo:  tier/unit switch when a component block is attached (t4/5 erekir) */
    /*TODO: mode to use payload or deploy units*/

    public ItemUnitTurret(String name){
        super(name);
        commandable = configurable = true;
        playerControllable = false;
        shootSound = Sounds.respawn;
        drawer = new DrawDefault();
        requiredItemsCost =Math.round(itemCapacity / 2f);
        fogRadius = -1;
        range = 0f;
    }


    public void setBars(){
        super.setBars();
        addBar("bar.progress", (ItemUnitTurretBuild entity) -> new Bar("bar.progress", Pal.ammo,() -> entity.reloadCounter / reload));

        /*TODO: Takes a bit to update*/
        addBar("units", (ItemUnitTurretBuild e) -> e.peekAmmo()  != null && e.peekAmmo().spawnUnit.useUnitCap ? new Bar(() ->
                e.peekAmmo().spawnUnit == null ? "[lightgray]" + Iconc.cancel :
                Core.bundle.format("bar.unitcap",
                        !Objects.equals(Fonts.getUnicodeStr(e.peekAmmo().spawnUnit.name), "") ? Fonts.getUnicodeStr(e.peekAmmo().spawnUnit.name) : Iconc.units,
                        e.team.data().countType(e.peekAmmo().spawnUnit),
                        Units.getStringCap(e.team)
                ),
            () -> Pal.power,
            () -> e.peekAmmo() == null ? 0f : e.peekAmmo().spawnUnit == null ? 0f : (float)e.team.data().countType(e.peekAmmo().spawnUnit) / Units.getCap(e.team)
        ): null);

    }

    @Override
    public void load(){
        bottomRegion = Core.atlas.find(name + "-bottom");
        super.load();
    }

    @Override
    public TextureRegion[] icons(){
        if(!(drawer instanceof  DrawDefault))return drawer.finalIcons(this);
        else {
            //use team region in vanilla team blocks
            TextureRegion r = variants > 0 ? Core.atlas.find(name + "1") : region;
            return teamRegion.found() && minfo.mod == null ? new TextureRegion[]{bottomRegion, r, teamRegions[Team.sharded.id]} : new TextureRegion[]{bottomRegion, r};
        }
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out){
        if(!(drawer instanceof DrawDefault))drawer.getRegionsToOutline(this, out);
        else {generatedIcons = null;}
    }


    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.remove(Stat.reload);
        stats.remove(Stat.targetsAir);
        stats.remove(Stat.inaccuracy);
        stats.remove(Stat.targetsGround);

        if(range <= 1)stats.remove(Stat.shootRange);
        stats.add(Stat.output, table -> this.ammoTypes.each((item, bul) -> {
            UnitType displayUnit = bul.spawnUnit;
            if(displayUnit == null) return;
            table.row();
            table.table(Styles.grayPanel, b -> {
                if(!displayUnit.isBanned()) b.image(displayUnit.fullIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                else b.image(Icon.cancel.getRegion()).color(Pal.remove).size(40).pad(10f).left().scaling(Scaling.fit);

                b.table(info -> {
                    if(item != null) info.table(title -> {
                        title.image(item.fullIcon).size(3 * 8).left().scaling(Scaling.fit).top();
                        title.add(item.localizedName).left().top();
                    }).left().row();
                    info.add(displayUnit.localizedName).left().row();
                    if(displayUnit instanceof NyfalisUnitType nyf && nyf.tier != -1) info.add("Tier: " + nyf.tier).left().row();
                    if (Core.settings.getBool("console")) info.add(displayUnit.name).left().color(Color.lightGray);
                });
                b.button("?", Styles.flatBordert, () -> ui.content.show(displayUnit)).size(40f).pad(10).right().grow().visible(displayUnit::unlockedNow);
            }).growX().pad(5).row();
        }));
    }

    @Override
    public void init(){
        if(requiredItems.size >= 1) for (Item i : requiredItems) consumeItem(i, requiredItemsCost);
        /*Todo: Hover icon for the unit (UnitFactory)*/
        super.init();
    }

    public class ItemUnitTurretBuild<T extends Payload> extends ItemTurretBuild{
        public @Nullable Vec2 commandPos;
        public float time, speedScl;
        public int direction = -1;
        public @Nullable T payload;
        public Vec2 payVector = new Vec2();

        @Override
        public boolean acceptItem(Building source, Item item){
            return ((ammoTypes.get(item) != null && totalAmmo + ammoTypes.get(item).ammoMultiplier <= maxAmmo && !ammoTypes.get(item).spawnUnit.isBanned())
                     || (requiredItems.contains(item) && items.get(item) < getMaximumAccepted(item)));
        }

        @Override
        public void handleItem(Building source, Item item){
            if(requiredItems.contains(item) && items.get(item) < getMaximumAccepted(item)){
                items.add(item, 1);
            }else super.handleItem(source, item);
        }

        @Override
        public void updateTile(){

            speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
            time += edelta() * speedScl * Vars.state.rules.unitBuildSpeed(team);

            super.updateTile();
        }

        @Override
        public boolean hasAmmo(){
            for (Item req : requiredItems) {
                if(items.get(req) >= requiredItemsCost) continue;
                return false;
            }
            return super.hasAmmo();
        }

        @Override
        protected void shoot(BulletType type){
            boolean creatable = !type.spawnUnit.isBanned() && (type.spawnUnit.unlockedNowHost() && state.isCampaign() || !state.isCampaign());
            if(direction != -1){
                if(type.spawnUnit != null) return;

                int trns = this.block.size / 2 + 1;
                Building front = this.nearby(Geometry.d4(direction).x * trns, Geometry.d4(direction).y * trns);
                boolean canDump = front == null || !front.tile().solid();
                boolean canMove = front != null && (front.block.outputsPayload || front.block.acceptsPayload);

                Vec2 dest = Tmp.v1.trns(direction * 90, size * tilesize/2f);
                payVector.approach(dest,payloadSpeed  * delta());

                if(canDump && !canMove){
                    shootRegular(type, creatable);
                }

                if(payVector.within(dest, 0.001f)){
                    payVector.clamp(-size * tilesize / 2f, -size * tilesize / 2f, size * tilesize / 2f, size * tilesize / 2f);

                    if(canMove){
                        if(movePayloadAlt(payload)){
                            payload = null;
                        }
                    }else if(canDump){
                        dumpPayload(payload);
                    }
                }

            } else{
                shootRegular(type, creatable);
            }

            if(consumeAmmoOnce){
                useAmmo();
            }

        }

        protected void shootRegular(BulletType type, boolean creatable){
            boolean spawn =creatable && (state.rules.waveTeam == this.team || (type.spawnUnit.useUnitCap && this.team.data().countType(type.spawnUnit) < this.team.data().unitCap) || !type.spawnUnit.useUnitCap);
            if(spawn){
                /*don't create the unit if it's banned or at unit cap*/
                consume();
                float
                        bulletX = x + Angles.trnsx(rotation - 90, shootX, shootY),
                        bulletY = y + Angles.trnsy(rotation - 90, shootX, shootY);

                if (shoot.firstShotDelay > 0) {
                    chargeSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                    type.chargeEffect.at(bulletX, bulletY, rotation);
                }

                shoot.shoot(barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
                    queuedBullets++;
                    if (delay > 0f) {
                        Time.run(delay, () -> bullet(type, xOffset, yOffset, angle, mover));
                    } else {
                        bullet(type, xOffset, yOffset, angle, mover);
                    }
                }, () -> barrelCounter++);
            }else {
                failedMakeFx.create(x, y, rotation -90, Pal.plasticSmoke, null);
                failedMakeSound.at(x, y, failedMakeSoundPitch, getFailedMakeSoundVolume);
            }
        }

        public boolean movePayloadAlt(Payload todump) {
            int trns = this.block.size / 2 + 1;
            Tile next = this.tile.nearby(Geometry.d4(direction).x * trns, Geometry.d4(direction).y * trns);
            if (next != null && next.build != null && next.build.team == this.team && next.build.acceptPayload(this, todump)) {
                next.build.handlePayload(this, todump);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void draw(){
            if(!(drawer instanceof  DrawDefault)){
                super.draw();
            }else{
                Draw.rect(bottomRegion, x, y);
                if(peekAmmo() != null && peekAmmo().spawnUnit != null){
                    UnitType unt = peekAmmo().spawnUnit;
                    if(this.team.data().unitCap >= this.team.data().countType(unt) || state.rules.waveTeam == this.team){
                        Draw.draw(Layer.blockOver, () -> Drawf.construct(this, unt, rotation - 90f, reloadCounter /reload,  speedScl, time));
                    } else {
                        Draw.draw(Layer.blockOver, ()->{
                            Draw.alpha(reloadCounter /reload);
                            Draw.rect(unt.fullIcon, x, y, rotation- 90f);

                            Draw.color(Pal.accent);
                            Draw.alpha((reloadCounter /reload) / 1.2f);
                            Lines.lineAngleCenter(this.x + Mathf.sin(this.time, 20f, (this.block.size * tilesize - 4f) / 4f), this.y, 90, this.block.size * tilesize - 4f);
                            Draw.reset();

                            Draw.color(Pal.remove, Math.min(reloadCounter /reload, 0.5f));
                            Draw.rect(Icon.warning.getRegion(), x, y);
                            Draw.reset();
                        });
                    }
                }
                Draw.z(Layer.blockOver + 0.1f);
                Draw.rect(region, x, y);
            }
        }

        @Override
        public void drawSelect(){
            /*instead of dealing/trying to make the block itself rotate, we have this*/
            Lines.stroke(1f, team.color);
            Draw.color(team.color, 0.8f);

            float rot = direction == -1 ? rotation - 90 : direction * 90,
                    squareX = x + Angles.trnsx(rot , shootX, shootY), squareY = y + Angles.trnsy(rot, shootX, shootY);
            if(hoverShowsSpawn && direction == -1){
                Lines.square(squareX, squareY + 0.5f, 3.5f, Time.time * 0.5f);
            } else if(payloadExitShow && direction != -1){
                TextureRegion regionArrow = Core.atlas.find("place-arrow");

                Draw.rect(regionArrow, squareX, squareY -1f, (float) regionArrow.width / size, (float) regionArrow.height / size,direction * 90);
            }
            Draw.reset();

            super.drawSelect();
        }

        @Override
        public Vec2 getCommandPosition(){return commandPos;}

        @Override
        protected void findTarget(){
            if(rallyAim){
                if(commandPos != null) targetPos = commandPos;
                return;
            }
            if(liquidAim){
                target = indexer.findTile(team, x, y, range, t-> !t.checkSolid() && t.floor().isLiquid, false);
            }

            super.findTarget();
        }

        @Override
        public void onCommand(Vec2 tar){commandPos = tar;}

        @Override
        /*Work around for rally point without fully rewriting updateTile*/
        public boolean logicControlled(){return logicControlTime > 0 || commandPos != null;}

        @Override
        public void buildConfiguration(Table table){
            table.background(Styles.black6);

            buildIcon(table, -1, Icon.export);
            buildIcon(table, 0, Icon.up);
            buildIcon(table, 1, Icon.left);
            buildIcon(table, 2, Icon.down);
            buildIcon(table, 3, Icon.right);

        }

        void buildIcon(Table table, int conf, Drawable icon){
            table.button(icon, Styles.clearNoneTogglei, 40f, () -> {
                direction = conf;
                configure(conf);
                deselect();
            }).checked(direction == conf);
        }

        @Override
        public Object senseObject(LAccess sensor){
            if(sensor == LAccess.config) return null;
            if(sensor == LAccess.rotation) return direction == -1 ? rotation : direction * 90f;
            return super.senseObject(sensor);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            TypeIO.writeVecNullable(write, commandPos);
            write.i(direction);
            Payload.write(payload, write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision >= 3){ /*necessary, to prevent map/save corruption */
                commandPos = TypeIO.readVecNullable(read);
            }
            if(revision >=5 ){
                direction = read.i();
                payload = Payload.read(read);
            } else  direction = -1;
        }

        @Override
        public byte version(){
            return 5;
        }
    }
}
