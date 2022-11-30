package com.jirocab.planets.planets;

import com.jirocab.planets.Registry;
import com.jirocab.planets.content.OlupisBlocks;
import mindustry.content.Liquids;

import static mindustry.content.TechTree.*;

public class OlupisTechTree {
    public static void load(){
        Registry.Olupis.techTree = nodeRoot("olupis", OlupisBlocks.mossyBoulder, () -> {

            node(Registry.placeholder1, () -> {
                node(Registry.placeholder2, () ->{

                });
            });

            node(OlupisBlocks.rustElectrolyzer, () -> {
                node(OlupisBlocks.garden,()->{
                    node(OlupisBlocks.bioMatterPress, () ->{

                    });
                });
            });

            node(Registry.condensedBiomatter, () ->{
                node(Registry.rustyIron, () ->{
                    node(Registry.iron, () ->{

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
               // node(Registry.architronito, ()->{

                //});
            });

            node(OlupisBlocks.unitReplicator, ()->{

            });
        });
    }
}
