package olupis.world.entities.parts;

import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.part.DrawPart;
import mindustry.gen.Building;

public abstract class UnstableDrawPart{
    public static final UnstablePartParams Uparams = new UnstablePartParams();
    public boolean turretShading;
    public boolean under = false;
    public int weaponIndex = 0;
    public int recoilIndex = -1;

    public UnstableDrawPart() {
    }

    public abstract void draw(UnstablePartParams var1, Building build);

    public abstract void load(String var1);

    public void getOutlines(Seq<TextureRegion> out) {
    }

    public static class UnstablePartParams extends DrawPart.PartParams {
        public float warmup;
        public float reload;
        public float smoothReload;
        public float heat;
        public float recoil;
        public float life;
        public float charge;
        public float x;
        public float y;
        public float rotation;
        public int sideOverride = -1;
        public int sideMultiplier = 1;

        public UnstablePartParams() {
        }

        public UnstablePartParams set(float warmup, float reload, float smoothReload, float heat, float recoil, float charge, float x, float y, float rotation) {
            this.warmup = warmup;
            this.reload = reload;
            this.heat = heat;
            this.recoil = recoil;
            this.smoothReload = smoothReload;
            this.charge = charge;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.sideOverride = -1;
            this.life = 0.0F;
            this.sideMultiplier = 1;
            return this;
        }

        public UnstablePartParams setRecoil(float recoils) {
            this.recoil = recoils;
            return this;
        }
    }

    public interface PartFunc {
        float get(float var1, float var2);
    }

    public interface UnstablePartProgress {
        UnstablePartProgress reload = (p) -> {
            return p.reload;
        };
        UnstablePartProgress smoothReload = (p) -> {
            return p.smoothReload;
        };
        UnstablePartProgress warmup = (p) -> {
            return p.warmup;
        };
        UnstablePartProgress charge = (p) -> {
            return p.charge;
        };
        UnstablePartProgress recoil = (p) -> {
            return p.recoil;
        };
        UnstablePartProgress heat = (p) -> {
            return p.heat;
        };
        UnstablePartProgress life = (p) -> {
            return p.life;
        };

        float get(UnstablePartParams var1);

        static UnstablePartProgress constant(float value) {
            return (p) -> {
                return value;
            };
        }

        default float getClamp(UnstablePartParams p) {
            return Mathf.clamp(this.get(p));
        }

        default UnstablePartProgress inv() {
            return (p) -> {
                return 1.0F - this.get(p);
            };
        }

        default UnstablePartProgress slope() {
            return (p) -> {
                return Mathf.slope(this.get(p));
            };
        }

        default UnstablePartProgress clamp() {
            return (p) -> {
                return Mathf.clamp(this.get(p));
            };
        }

        default UnstablePartProgress add(float amount) {
            return (p) -> {
                return this.get(p) + amount;
            };
        }

        default UnstablePartProgress add(UnstablePartProgress other) {
            return (p) -> {
                return this.get(p) + other.get(p);
            };
        }

        default UnstablePartProgress delay(float amount) {
            return (p) -> {
                return (this.get(p) - amount) / (1.0F - amount);
            };
        }

        default UnstablePartProgress curve(float offset, float duration) {
            return (p) -> {
                return (this.get(p) - offset) / duration;
            };
        }

        default UnstablePartProgress sustain(float offset, float grow, float sustain) {
            return (p) -> {
                float val = this.get(p) - offset;
                return Math.min(Math.max(val, 0.0F) / grow, (grow + sustain + grow - val) / grow);
            };
        }

        default UnstablePartProgress shorten(float amount) {
            return (p) -> {
                return this.get(p) / (1.0F - amount);
            };
        }

        default UnstablePartProgress compress(float start, float end) {
            return (p) -> {
                return Mathf.curve(this.get(p), start, end);
            };
        }

        default UnstablePartProgress blend(UnstablePartProgress other, float amount) {
            return (p) -> {
                return Mathf.lerp(this.get(p), other.get(p), amount);
            };
        }

        default UnstablePartProgress mul(UnstablePartProgress other) {
            return (p) -> {
                return this.get(p) * other.get(p);
            };
        }

        default UnstablePartProgress mul(float amount) {
            return (p) -> {
                return this.get(p) * amount;
            };
        }

        default UnstablePartProgress min(UnstablePartProgress other) {
            return (p) -> {
                return Math.min(this.get(p), other.get(p));
            };
        }

        default UnstablePartProgress sin(float offset, float scl, float mag) {
            return (p) -> {
                return this.get(p) + Mathf.sin(Time.time + offset, scl, mag);
            };
        }

        default UnstablePartProgress sin(float scl, float mag) {
            return (p) -> {
                return this.get(p) + Mathf.sin(scl, mag);
            };
        }

        default UnstablePartProgress absin(float scl, float mag) {
            return (p) -> {
                return this.get(p) + Mathf.absin(scl, mag);
            };
        }

        default UnstablePartProgress apply(UnstablePartProgress other, PartFunc func) {
            return (p) -> {
                return func.get(this.get(p), other.get(p));
            };
        }

        default UnstablePartProgress curve(Interp interp) {
            return (p) -> {
                return interp.apply(this.get(p));
            };
        }
    }

    public static class PartMove {
        public UnstablePartProgress progress;
        public float x;
        public float y;
        public float gx;
        public float gy;
        public float rot;

        public PartMove(UnstablePartProgress progress, float x, float y, float gx, float gy, float rot) {
            this.progress = UnstablePartProgress.warmup;
            this.progress = progress;
            this.x = x;
            this.y = y;
            this.gx = gx;
            this.gy = gy;
            this.rot = rot;
        }

        public PartMove(UnstablePartProgress progress, float x, float y, float rot) {
            this(progress, x, y, 0.0F, 0.0F, rot);
        }

        public PartMove() {
            this.progress = UnstablePartProgress.warmup;
        }
    }
}

