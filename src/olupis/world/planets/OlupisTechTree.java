package olupis.world.planets;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.game.Objectives;
import olupis.content.*;

import static mindustry.content.TechTree.*;
import static olupis.content.OlupisPlanets.*;

public class OlupisTechTree {

    public static void load(){
        olupis.techTree = nodeRoot("olupis", OlupisBlocks.coreRemnant, () -> {

            node(OlupisSectors.placeholder1, () -> {
                node(OlupisSectors.placeholder2, Seq.with(
                        new Objectives.SectorComplete(OlupisSectors.placeholder1)
                ), () ->{

                });
            });

            node(OlupisBlocks.mossyBoulder, ()-> {

                node(OlupisBlocks.windMills, Seq.with(new Objectives.Research(OlupisBlocks.rustyDrill)), () -> {
                    node(OlupisBlocks.wire, () -> {
                        node(OlupisBlocks.rustElectrolyzer, () -> {
                            node(OlupisBlocks.garden,()->{
                                node(OlupisBlocks.bioMatterPress, () ->{
                                    node(OlupisBlocks.hydrochloricGraphitePress, ()->{

                                    });
                                });
                            });
                        });
                        node(OlupisBlocks.wireBridge, ()-> {
                            node(OlupisBlocks.hydroMill, ()->{
                                node(OlupisBlocks.hydroElectricGenerator, () ->{

                                 });
                            });
                        });
                        node(OlupisBlocks.taurus, ()->{

                        });
                    });
                });

                node(OlupisBlocks.pipeRouter, Seq.with(new Objectives.Research(OlupisBlocks.rustyDrill)), () -> {
                    node(OlupisBlocks.rustyPump, () ->{
                        node(OlupisBlocks.leadPipe, ()->{
                            node(OlupisBlocks.fortifiedCanister, ()->{
                                node(OlupisBlocks.fortifiedTank, ()->{

                                });
                            });
                            node(OlupisBlocks.pipeJunction, ()->{
                                node(OlupisBlocks.pipeBridge, ()-> {

                                });
                            });
                            node(OlupisBlocks.ironPipe, ()->{

                            });
                        });
                        node(OlupisBlocks.steamBoiler, ()->{
                            node(OlupisBlocks.steamAgitator, ()->{

                            });
                        });
                        node(OlupisBlocks.ironPump, () -> {
                            node(OlupisBlocks.displacementPump, () -> {
                                node(OlupisBlocks.massDisplacementPump, () -> {

                                });
                            });
                        });
                    });
                });

                node(OlupisBlocks.ironRouter, () ->{
                    node(OlupisBlocks.rustyIronConveyor, () ->{
                        node(OlupisBlocks.ironJunction, ()->{
                            node(OlupisBlocks.ironBridge, ()->{
                                node(OlupisBlocks.fortifiedContainer, () ->{
                                    node(OlupisBlocks.fortifiedVault, () ->{

                                    });
                                });
                            });
                        });
                        node(OlupisBlocks.ironConveyor, ()->{

                        });
                    });

                    node(OlupisBlocks.rustyDrill, ()->{
                        node(OlupisBlocks.steamDrill, () ->{
                            node(OlupisBlocks.hydroElectricDrill, () ->{

                            });
                        });
                    });
                });


                node(OlupisBlocks.corroder, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), ()-> {
                    node(OlupisBlocks.dissolver, ()->{

                    });
                    node(OlupisBlocks.shredder, ()->{

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
                    node(OlupisBlocks.unitReplicatorSmall, ()->{

                    });
                });

                node(OlupisBlocks.fortifiedMessageBlock, Seq.with(new Objectives.Research(OlupisBlocks.rustyIronConveyor)), ()->{

                });


                nodeProduce(OlupisItemsLiquid.rustyIron, () ->{
                    nodeProduce(Items.lead, () ->{

                    });
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
        arthin.techTree = olupis.techTree;
        spelta.techTree = olupis.techTree;
    }
}
