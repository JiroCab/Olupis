package olupis.world.planets;

import arc.struct.Seq;
import mindustry.content.Liquids;
import mindustry.game.Objectives;
import olupis.content.NyfalisSectors;

import static mindustry.content.Items.*;
import static mindustry.content.TechTree.*;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisPlanets.*;
import static olupis.content.NyfalisUnits.*;

public class NyfalisTechTree {

    public static void load(){
        nyfalis.techTree = nodeRoot("olupis", coreRemnant, () -> {
            node(system, () -> {
                node(arthin, () ->{
                    node(NyfalisSectors.sanctuary, () -> {

                    });
                });
                node(nyfalis, Seq.with(
                        new Objectives.SectorComplete(NyfalisSectors.sanctuary)
                ),() ->{
                    node(NyfalisSectors.vakinyaDesert, () -> {
                        node(NyfalisSectors.placeholder2, Seq.with(
                                new Objectives.SectorComplete(NyfalisSectors.vakinyaDesert)
                        ), () ->{

                        });
                    });
                });
                node(spelta, Seq.with(
                        new Objectives.SectorComplete(NyfalisSectors.sanctuary), new  Objectives.Research(groundConstruct)
                ), () ->{
                    node(NyfalisSectors.forestOfHope,  () ->{
                        node(NyfalisSectors.dormantCell,  ()-> {

                        });
                    });

                });
            });

            node(gnat, ()->{
                node(aero, Seq.with(
                    new  Objectives.Research(arialConstruct)
                ), () -> {
                    node(striker, () -> {

                    });
                });

                node(spirit, Seq.with(
                    new  Objectives.Research(construct)
                ), () ->{
                    node(phantom, () ->{
                        node(banshee, () -> {

                        });
                    });
                });
                node(venom, Seq.with(
                        new  Objectives.Research(groundConstruct)
                ), () -> {

                });
                node(porter, Seq.with(
                        new  Objectives.Research(groundConstruct)
                ), () -> {
                    node(zoner, () -> {

                    });
                    node(bay, () -> {
                        node(blitz, () -> {

                        });
                    });
                });
                node(phorid, Seq.with(
                        new Objectives.SectorComplete(NyfalisSectors.sanctuary), new  Objectives.Research(coreRelic)
                ), () -> {
                    node(embryo, () -> {

                    });
                });
            });

            node(mossyBoulder, ()-> {

                node(coreVestige, () ->{
                    node(coreRelic, () -> {
                        node(coreShrine, () -> {
                            node(coreTemple, () -> {

                            });
                        });
                    });
                });

                node(wire, Seq.with(new Objectives.Research(rustyDrill)), () -> {
                    node(windMills, () -> {
                        node(rustElectrolyzer, Seq.with(new Objectives.Research(corroder)), () -> {
                            node(garden,()->{
                                node(bioMatterPress, () ->{

                                });
                            });
                            node(hydrochloricGraphitePress, ()->{
                                node(siliconArcSmelter, ()->{

                                });
                            });
                        });
                        node(hydroMill, ()->{
                            node(hydroElectricGenerator, () ->{

                            });
                        });
                        node(wireBridge, ()-> {
                            node(superConductors, ()->{

                            });
                        });
                        node(taurus, ()->{

                        });
                    });
                });

                node(leadPipe, Seq.with(new Objectives.Research(windMills)), () -> {
                    node(rustyPump, () ->{
                        node(pipeRouter, ()->{
                            node(fortifiedCanister, ()->{
                                node(fortifiedTank, ()->{

                                });
                            });
                            node(pipeJunction, ()->{
                                node(pipeBridge, ()-> {

                                });
                            });
                            node(ironPipe, ()->{

                            });
                            node(steamBoiler, ()->{
                                node(steamAgitator, Seq.with(new Objectives.Research(steam)),()->{

                                });
                                node(broiler, Seq.with(new Objectives.Research(graphite)),()->{

                                });
                            });
                            node(ironPump, () -> {
                                node(displacementPump, () -> {
                                    node(massDisplacementPump, () -> {

                                    });
                                });
                            });
                        });
                    });
                });

                node(rustyIronConveyor, () ->{
                    node(ironRouter, () ->{
                        node(ironJunction, ()->{
                            node(rustedBridge, () -> {

                            });
                        });
                        node(ironConveyor, ()->{
                            node(cobaltConveyor, ()->{

                            });

                            node(ironOverflow, () ->{
                                node(ironUnderflow, () -> {
                                    node(ironDistributor, () -> {

                                    });
                                });
                            });

                            node(ironBridge, ()->{

                            });
                        });
                    });

                    node(rustyDrill, ()->{
                        node(steamDrill, () ->{
                            node(hydroElectricDrill, () ->{

                            });
                        });
                        node(ironSieve, Seq.with(new Objectives.Research(rustElectrolyzer), new Objectives.Produce(sand)),() ->{

                        });
                    });
                });

                node(ironUnloader, () ->{
                    node(fortifiedContainer, () ->{
                        node(fortifiedVault, () ->{

                        });
                    });
                });

                node(corroder, Seq.with(new Objectives.Research(rustyPump)), ()-> {
                    node(avenger, () -> {
                        node(dissolver, ()->{

                        });
                    });
                    node(shredder, ()->{
                        node(hive, ()->{

                        });
                    });
                    node(rustyWall, () ->{
                        node(rustyWallLarge, ()->{
                            node(rustyWallHuge, ()->{

                            });
                        });
                        node(ironWall, ()->{
                            node(ironWallLarge, ()->{

                            });
                        });
                    });
                });

                node(construct, Seq.with(new Objectives.Research(ironRouter)), ()->{
                    node(groundConstruct, () ->{
                        node(arialConstruct, () ->{

                        });
                    });
                    node(unitReplicator, ()->{
                        node(unitReplicatorSmall, ()->{

                        });
                    });
                });

                node(fortifiedMessageBlock, Seq.with(new Objectives.Research(ironRouter)), ()->{
                    node(mechanicalProcessor, () -> {
                        node(mechanicalSwitch, () -> {
                            node(mechanicalRegistry, ()->{

                            });
                        });
                    });
                });


                nodeProduce(rustyIron, () ->{
                    nodeProduce(lead, () ->{
                        nodeProduce(sand, () ->{
                            nodeProduce(quartz, () ->{

                            });
                        });
                    });
                    nodeProduce(iron, () ->{
                        nodeProduce(condensedBiomatter, () ->{

                        });
                        nodeProduce(cobalt, ()->{

                        });
                    });
                    nodeProduce(Liquids.water, ()->{
                        nodeProduce(steam, () ->{

                        });
                        nodeProduce(Liquids.oil,()->{
                            nodeProduce(graphite, ()->{
                                nodeProduce(silicon, ()->{

                                });
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
