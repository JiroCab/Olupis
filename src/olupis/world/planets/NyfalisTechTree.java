package olupis.world.planets;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.game.Objectives;
import olupis.content.*;

import static mindustry.content.TechTree.*;
import static olupis.content.NyfalisPlanets.*;

public class NyfalisTechTree {

    public static void load(){
        nyfalis.techTree = nodeRoot("olupis", NyfalisBlocks.coreRemnant, () -> {

            node(NyfalisSectors.placeholder1, () -> {
                node(NyfalisSectors.placeholder2, Seq.with(
                        new Objectives.SectorComplete(NyfalisSectors.placeholder1)
                ), () ->{

                });
            });

            node(NyfalisBlocks.mossyBoulder, ()-> {

                node(NyfalisBlocks.windMills, Seq.with(new Objectives.Research(NyfalisBlocks.rustyDrill)), () -> {
                    node(NyfalisBlocks.wire, () -> {
                        node(NyfalisBlocks.rustElectrolyzer, () -> {
                            node(NyfalisBlocks.garden,()->{
                                node(NyfalisBlocks.bioMatterPress, () ->{
                                    node(NyfalisBlocks.hydrochloricGraphitePress, ()->{

                                    });
                                });
                            });
                        });
                        node(NyfalisBlocks.wireBridge, ()-> {
                            node(NyfalisBlocks.hydroMill, ()->{
                                node(NyfalisBlocks.hydroElectricGenerator, () ->{

                                 });
                            });
                        });
                        node(NyfalisBlocks.taurus, ()->{

                        });
                    });
                });

                node(NyfalisBlocks.pipeRouter, Seq.with(new Objectives.Research(NyfalisBlocks.rustyDrill)), () -> {
                    node(NyfalisBlocks.rustyPump, () ->{
                        node(NyfalisBlocks.leadPipe, ()->{
                            node(NyfalisBlocks.fortifiedCanister, ()->{
                                node(NyfalisBlocks.fortifiedTank, ()->{

                                });
                            });
                            node(NyfalisBlocks.pipeJunction, ()->{
                                node(NyfalisBlocks.pipeBridge, ()-> {

                                });
                            });
                            node(NyfalisBlocks.ironPipe, ()->{

                            });
                        });
                        node(NyfalisBlocks.steamBoiler, ()->{
                            node(NyfalisBlocks.steamAgitator, ()->{

                            });
                        });
                        node(NyfalisBlocks.ironPump, () -> {
                            node(NyfalisBlocks.displacementPump, () -> {
                                node(NyfalisBlocks.massDisplacementPump, () -> {

                                });
                            });
                        });
                    });
                });

                node(NyfalisBlocks.ironRouter, () ->{
                    node(NyfalisBlocks.rustyIronConveyor, () ->{
                        node(NyfalisBlocks.ironJunction, ()->{
                            node(NyfalisBlocks.ironBridge, ()->{
                                node(NyfalisBlocks.fortifiedContainer, () ->{
                                    node(NyfalisBlocks.fortifiedVault, () ->{

                                    });
                                });
                            });
                        });
                        node(NyfalisBlocks.ironConveyor, ()->{

                        });
                    });

                    node(NyfalisBlocks.rustyDrill, ()->{
                        node(NyfalisBlocks.steamDrill, () ->{
                            node(NyfalisBlocks.hydroElectricDrill, () ->{

                            });
                        });
                    });
                });


                node(NyfalisBlocks.corroder, Seq.with(new Objectives.Research(NyfalisBlocks.rustyIronConveyor)), ()-> {
                    node(NyfalisBlocks.dissolver, ()->{

                    });
                    node(NyfalisBlocks.shredder, ()->{

                    });
                    node(NyfalisBlocks.rustyWall, () ->{
                        node(NyfalisBlocks.rustyWallLarge, ()->{
                            node(NyfalisBlocks.rustyWallHuge, ()->{

                            });
                        });
                        node(NyfalisBlocks.ironWall, ()->{
                            node(NyfalisBlocks.ironWallLarge, ()->{

                            });
                        });
                    });
                });

                node(NyfalisBlocks.unitReplicator, Seq.with(new Objectives.Research(NyfalisBlocks.rustyIronConveyor)), ()->{
                    node(NyfalisBlocks.unitReplicatorSmall, ()->{

                    });
                });

                node(NyfalisBlocks.fortifiedMessageBlock, Seq.with(new Objectives.Research(NyfalisBlocks.rustyIronConveyor)), ()->{

                });


                nodeProduce(NyfalisItemsLiquid.rustyIron, () ->{
                    nodeProduce(Items.lead, () ->{

                    });
                    nodeProduce(NyfalisItemsLiquid.iron, () ->{
                        nodeProduce(NyfalisItemsLiquid.condensedBiomatter, () ->{

                        });
                        nodeProduce(NyfalisItemsLiquid.cobalt, ()->{

                        });
                    });
                    nodeProduce(Liquids.water, ()->{
                        nodeProduce(NyfalisItemsLiquid.steam, () ->{

                        });
                        nodeProduce(Liquids.oil,()->{
                            nodeProduce(Items.graphite, ()->{

                            });
                        });
                    });
                });

            });
        });
        arthin.techTree = nyfalis.techTree;
        spelta.techTree = nyfalis.techTree;
        system.techTree = nyfalis.techTree;
    }
}
