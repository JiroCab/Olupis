package olupis.world.planets;

import arc.struct.Seq;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static mindustry.content.Items.*;
import static mindustry.content.TechTree.*;
import static olupis.content.NyfalisBlocks.*;
import static olupis.content.NyfalisItemsLiquid.*;
import static olupis.content.NyfalisPlanets.*;
import static olupis.content.NyfalisSectors.*;
import static olupis.content.NyfalisUnits.*;

public class NyfalisTechTree {

    public static void load(){
        nyfalis.techTree = nodeRoot("olupis", coreRemnant, () -> {
            node(system, () -> {
                node(arthin, () ->{
                    node(sanctuary, () -> {
                        node(mossyCaverns,
                                Seq.with(new Objectives.SectorComplete(sanctuary)),
                        () -> {
                            node(vakinyaDesert, Seq.with(
                                new Objectives.SectorComplete(mossyCaverns),
                                new Objectives.Produce(iron)
                            ),() ->{
                                node(muddyLakes,
                                    Seq.with(new Objectives.SectorComplete(vakinyaDesert)),
                                () -> {
                                    node(lushyRiverComplex,
                                            Seq.with(new Objectives.SectorComplete(muddyLakes)),
                                    () -> {

                                    });
                                });

                                node(kanwadolRuins,
                                    Seq.with(new Objectives.SectorComplete(vakinyaDesert)),
                                    () -> {
                                        node(abandonedTrainStations,
                                            Seq.with(new Objectives.SectorComplete(kanwadolRuins), new Objectives.Research(steamAgitator)),
                                            () -> {
                                                node(citadelOfOasis,
                                                    Seq.with(new Objectives.SectorComplete(abandonedTrainStations)),
                                                    () -> {

                                                });
                                        });
                                });
                                node(snowPisos,
                                        Seq.with(new Objectives.SectorComplete(kanwadolRuins), new Objectives.SectorComplete(muddyLakes)),
                                        () -> {

                                        }
                                );
                            });
                        });
                    });
                });
                node(nyfalis, Seq.with(
                        new Objectives.SectorComplete(citadelOfOasis),
                        new Objectives.SectorComplete(lushyRiverComplex),
                        new Objectives.SectorComplete(snowPisos)
                ),() ->{
                    node(conservatorium, Seq.with(
                            new Objectives.SectorComplete(sanctuary)
                    ), () ->{
                    });
                });
                node(spelta, Seq.with(
                        new Objectives.SectorComplete(sanctuary), new  Objectives.Research(groundConstruct)
                ), () ->{
                    node(forestOfHope,  () ->{
                        node(dormantCell,  ()-> {

                        });
                    });

                });
            });

            node(gnat, ()->{
                node(aero, Seq.with(
                    new  Objectives.Research(arialConstruct)
                ), () -> {
                    node(striker, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {

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
                    node(serpent, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {

                    });
                });
                node(supella, Seq.with(
                        new  Objectives.Research(groundConstruct)
                ), () -> {
                    node(germanica, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {

                    });
                });
                node(porter, Seq.with(
                        new  Objectives.Research(navalConstruct)
                ), () -> {
                    node(zoner, () -> {

                    });
                    node(essex, () -> {
                        node(regioner, () -> {

                        });
                    });
                });
                node(bay, Seq.with(
                    new  Objectives.Research(navalConstruct)
                ), () -> {
                    node(blitz, () -> {

                    });
                });
                node(phorid, Seq.with(
                        new Objectives.SectorComplete(sanctuary), new  Objectives.Research(coreRelic)
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
                            node(garden, Seq.with(new Objectives.Research(sanctuary)),()->{
                                node(bioMatterPress, () ->{

                                });
                            });
                            node(siliconKiln, ()->{
                                node(hydrochloricGraphitePress, Seq.with(new Objectives.SectorComplete(vakinyaDesert)), ()->{
                                    node(siliconArcSmelter , ()->{

                                    });
                                });
                            });
                            node(inductionSmelter, () -> {
                                node(rustEngraver, () ->{

                                });
                            });
                            node(discardDriver, Seq.with(new Objectives.SectorComplete(lushyRiverComplex)),() ->{

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
                        node(taurus ,Seq.with(new Objectives.Research(sanctuary)), ()->{
                            node(ladar, () -> {

                            });
                        });
                    });
                });

                node(leadPipe, Seq.with(new Objectives.Research(windMills)), () -> {
                    node(rustyPump, () ->{
                        node(pipeRouter, ()->{
                            node(fortifiedCanister, ()->{
                                node(fortifiedTank,
                                    Seq.with(new Objectives.OnSector(muddyLakes))
                                 , ()->{

                                });
                            });
                            node(pipeJunction, ()->{
                                node(pipeBridge, ()-> {

                                });
                            });
                            node(ironPipe,
                                Seq.with(new Objectives.OnSector(muddyLakes)),
                            ()->{

                            });
                            node(steamBoiler,
                                    Seq.with(new Objectives.OnSector(mossyCaverns)),
                            ()->{
                                node(steamAgitator, Seq.with(new Objectives.Research(steam)),()->{

                                });
                                node(broiler, Seq.with(new Objectives.Research(graphite)),()->{

                                });

                            });
                            node(ironPump,
                                Seq.with(new Objectives.OnSector(muddyLakes)),
                            () -> {
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
                        node(steamDrill, Seq.with(new Objectives.Produce(steam)), () ->{
                            node(hydroElectricDrill, () ->{

                            });
                        });
                        node(ironSieve, Seq.with(new Objectives.SectorComplete(vakinyaDesert), new Objectives.Produce(sand)),() ->{

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
                        node(aegis, ()->{

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

                node(construct, Seq.with(new Objectives.Research(ironRouter), new Objectives.SectorComplete(vakinyaDesert)), ()->{
                    node(groundConstruct,
                        Seq.with(new Objectives.Research(silicon))
                    , () ->{
                        node(arialConstruct, () ->{
                            node(navalConstruct, () ->{

                            });
                        });
                    });
                    node(unitReplicator, ()->{
                        node(unitReplicatorSmall, ()->{

                        });
                    });
                    node(fortifiePayloadConveyor, () -> {
                        node(fortifiePayloadConveyor, () -> {

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
