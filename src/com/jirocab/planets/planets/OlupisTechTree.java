package com.jirocab.planets.planets;

import com.jirocab.planets.content.*;
import mindustry.content.Liquids;

import static mindustry.content.TechTree.*;

public class OlupisTechTree {
    public static void load(){
        OlupisPlanets.olupis.techTree = nodeRoot("olupis", OlupisBlocks.mossyBoulder, () -> {

            node(OlupisSectors.placeholder1, () -> {
                node(OlupisSectors.placeholder2, () ->{

                });
            });

            node(OlupisBlocks.rustElectrolyzer, () -> {
                node(OlupisBlocks.garden,()->{
                    node(OlupisBlocks.bioMatterPress, () ->{

                    });
                });
            });

            node(OlupisItemsLiquid.condensedBiomatter, () ->{
                node(OlupisItemsLiquid.rustyIron, () ->{
                    node(OlupisItemsLiquid.iron, () ->{

                    });
                });
                node(Liquids.water, ()->{

                });
            });


            node(OlupisBlocks.rustyIronConveyor, () ->{
                node(OlupisBlocks.ironConveyor, ()->{

                });
            });

            node(OlupisBlocks.architonnerre, ()-> {
                node(OlupisBlocks.architronito, ()->{

                });
            });

            node(OlupisBlocks.unitReplicator, ()->{

            });
        });
    }
}
