package com.jirocab.planets.planets;

import arc.struct.Seq;
import com.jirocab.planets.content.*;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static mindustry.content.Blocks.tankRefabricator;
import static mindustry.content.SectorPresets.frozenForest;
import static mindustry.content.TechTree.*;

public class OlupisTechTree {
    public static void load(){
        OlupisPlanets.olupis.techTree = nodeRoot("olupis", OlupisBlocks.coreRemnant, () -> {
            node(OlupisBlocks.mossyBoulder, ()-> {


                node(OlupisSectors.placeholder1, () -> {
                    node(OlupisSectors.placeholder2, Seq.with(
                            new Objectives.SectorComplete(OlupisSectors.placeholder1)
                    ), () ->{

                    });
                });

                node(OlupisBlocks.windMills, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), () -> {
                    node(OlupisBlocks.wire, () -> {
                        node(OlupisBlocks.rustElectrolyzer, () -> {
                            node(OlupisBlocks.garden,()->{
                                node(OlupisBlocks.bioMatterPress, () ->{
                                    //node(OlupisBlocks.hydrochloricGraphitePress, ()->{

                                    //});
                                });
                            });
                        });
                        node(OlupisBlocks.wireBridge, ()-> {
                            node(OlupisBlocks.hydroMill, ()->{
                                //node(OlupisBlocks.hydroElectricGenerator, () ->{

                                // });
                            });
                        });
                    });
                });

                node(OlupisBlocks.rustyPump, Seq.with(new Objectives.Research(OlupisBlocks.steamDrill)), () -> {
                    node(OlupisBlocks.pipeRouter, () ->{
                        node(OlupisBlocks.rustyPipe, ()->{
                            node(OlupisBlocks.pipeJunction, ()->{
                                node(OlupisBlocks.pipeBridge, ()-> {

                                });
                            });
                            node(OlupisBlocks.ironPipe, ()->{

                            });
                        });
                        node(OlupisBlocks.steamBoilder, ()->{
                            node(OlupisBlocks.steamAgitator, ()->{

                            });
                        });
                        node(OlupisBlocks.ironPump, () -> {
                            //node(OlupisBlocks.displacementPump, () ->{
                               // node(OlupisBlocks.massDisplacementPump, () ->{

                                //});
                            //});
                        });
                    });
                });

                node(OlupisBlocks.ironRouter, () ->{
                    node(OlupisBlocks.rustyIronConveyor, () ->{
                        node(OlupisBlocks.ironBridge, ()->{
                            node(OlupisBlocks.ironJunction, ()->{

                            });
                        });
                        node(OlupisBlocks.ironConveyor, ()->{
                            //node(OlupisBlocks.cobaltConveyor, ()->{

                            //});
                        });
                    });

                    node(OlupisBlocks.steamDrill, ()->{
                        node(OlupisBlocks.hydroElectricDrill, () ->{

                        });
                    });
                });


                node(OlupisBlocks.architonnerre, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), ()-> {
                    node(OlupisBlocks.architronito, ()->{

                    });
                    node(OlupisBlocks.rustyWall, () ->{
                        node(OlupisBlocks.rustyWallLarge, ()->{
                            node(OlupisBlocks.rustyWallHuge, ()->{

                            });
                        });
                        node(OlupisBlocks.ironWall, ()->{
                            node(OlupisBlocks.ironWallLarge, ()->{

                            });
                        });
                    });
                });

                node(OlupisBlocks.unitReplicator, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), ()->{

                });

                node(OlupisBlocks.fortifiedMessageBlock, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), ()->{

                });


                nodeProduce(OlupisItemsLiquid.rustyIron, () ->{
                    nodeProduce(OlupisItemsLiquid.iron, () ->{
                        nodeProduce(OlupisItemsLiquid.condensedBiomatter, () ->{

                        });
                        nodeProduce(OlupisItemsLiquid.cobalt, ()->{

                        });
                    });
                    nodeProduce(Liquids.water, ()->{
                        nodeProduce(OlupisItemsLiquid.steam, () ->{

                        });
                        nodeProduce(Liquids.oil,()->{
                            nodeProduce(Items.graphite, ()->{

                            });
                        });
                    });
                });

            });
        });
    }
}
