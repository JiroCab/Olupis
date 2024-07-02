package olupis.world.entities.units;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

import java.util.Objects;

public class BatHelperUnitType extends UnitType {
    public UnitType main = UnitTypes.alpha;
    public TextureRegion iconOverlay;
    public String overlayString = "";

    public BatHelperUnitType(String name){
        super(name);
        constructor = main.constructor;
        flying = true;
    }

    public BatHelperUnitType(UnitType main){
        this(main.name + "-air");
        this.main = main;
    }


    public BatHelperUnitType(String name, UnitType main){
        this(name);
        this.main = main;
    }

    @Override
    public void load() {
        super.load();
        iconOverlay = Core.atlas.find(Objects.equals(overlayString, "") ? "olupis-air-overlay" : name + overlayString);
        TextureRegion i = main.uiIcon;
        fullIcon = main.fullIcon;
        localizedName = main.localizedName + " " + Core.bundle.get("nyfalis-helper-air");
    }

        @Override
    public void update(Unit unit){
        super.update(unit);
        Unit u = this.main.create(unit.team);
        u.set(unit.x, unit.y);
        if(!Vars.net.client()){
            u.add();
        }
        unit.remove();
    }
}
