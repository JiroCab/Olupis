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
                            node(dyingForest, () -> {
                                node(coldFlats, Seq.with(new Objectives.SectorComplete(conciditRuins)),  () -> {
                                    node(glasierSea, () -> {

                                    });
                                });
                                node(muddyLakes, () -> {
                                    node(abandonedPayloadTerminal, () -> {
                                        node(ironCurtain, () -> {

                                        });
                                    });
                                });
                                node(conciditRuins, Seq.with(new Objectives.Research(groundConstruct)), () -> {

                                });
                            });
                        });
                    });
                });
                node(nyfalis, Seq.with(
                        new Objectives.SectorComplete(ironCurtain),
                        new Objectives.SectorComplete(glasierSea),
                        new Objectives.SectorComplete(coldFlats)
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
                node(pteropus, Seq.with(
                    new  Objectives.Research(arialConstruct)
                ), () -> {
                    node(acerodon, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {

                    });
                });

                node(spirit, Seq.with(
                    new  Objectives.Research(construct)
                ), () ->{
                    node(phantom,  Seq.with(
                            new  Objectives.Produce(quartz)
                    ), () ->{
                        node(banshee, Seq.with(
                                new  Objectives.Produce(graphite)
                        ), () -> {
                            node(revenant, Seq.with(
                                    new  Objectives.Produce(silicon)
                            ), () -> {

                            });
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
                    node(essex, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {
                        node(regioner, () -> {

                        });
                    });
                });
                node(bay, Seq.with(
                    new  Objectives.Research(navalConstruct)
                ), () -> {
                    node(blitz, Seq.with(
                            new  Objectives.Research(alternateArticulator)
                    ), () -> {

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
                        node(rustElectrolyzer, Seq.with(new Objectives.Research(corroder), new Objectives.SectorComplete(sanctuary)), () -> {
                            node(garden, Seq.with(new Objectives.Research(dyingForest)),()->{
                                node(bioMatterPress, () ->{

                                });
                            });
                            node(siliconKiln, ()->{
                                node(hydrochloricGraphitePress, Seq.with(new Objectives.OnSector(dyingForest)), ()->{
                                    node(siliconArcSmelter , Seq.with(
                                            new  Objectives.Research(hydrochloricGraphitePress)
                                    ), ()->{

                                    });
                                });
                            });
                            node(inductionSmelter, () -> {
                                node(rustEngraver, () ->{

                                });
                            });
                            node(discardDriver, Seq.with(new Objectives.SectorComplete(glasierSea)),() ->{

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
                            node(ladar, Seq.with(
                                    new  Objectives.Research(hydrochloricGraphitePress)
                            ), () -> {

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
                                node(broiler, Seq.with(new Objectives.Research(graphite), new Objectives.Research(siliconArcSmelter)),()->{

                                });

                            });
                            node(ironPump,
                                Seq.with(new Objectives.OnSector(muddyLakes)),
                            () -> {
                                node(displacementPump, Seq.with(
                                        new  Objectives.Research(hydrochloricGraphitePress)
                                ), () -> {
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
                        node(ironSieve, Seq.with(new Objectives.SectorComplete(dyingForest), new Objectives.Produce(sand)),() ->{

                        });
                    });
                });

                node(ironUnloader, Seq.with(
                        new  Objectives.Research(hydrochloricGraphitePress)
                ), () ->{
                    node(fortifiedContainer, () ->{
                        node(fortifiedVault, () ->{
                            node(deliveryCannon, () ->{

                            });
                        });
                    });
                });

                node(corroder, Seq.with(new Objectives.Research(rustyPump)), ()-> {
                    node(avenger, () -> {
                        node(aegis, ()->{

                        });
                    });
                    node(slash, ()->{
                        node(shredder, ()->{
                            node(hive, ()->{

                            });
                            node(laceration, () -> {

                            });
                        });
                    });
                    node(rustyWall, () ->{
                        node(rustyWallLarge, ()->{
                            node(rustyWallHuge, ()->{

                            });
                        });
                        node(ironWall, ()->{
                            node(ironWallLarge, ()->{
                                node(quartzWall, () -> {
                                    node(quartzWallLarge, () -> {
                                        node(cobaltWall, () -> {
                                            node(cobaltWallLarge, () -> {

                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(construct, Seq.with(new Objectives.Research(ironRouter), new Objectives.SectorComplete(dyingForest)), ()->{
                    node(groundConstruct,
                        Seq.with(new Objectives.Research(silicon))
                    , () ->{
                        node(arialConstruct, () ->{
                            node(navalConstruct, () ->{
                                node(alternateArticulator, () ->{

                                });
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
                    node(alternateArticulator,  Seq.with(
                            new  Objectives.SectorComplete(abandonedPayloadTerminal)
                    ), () -> {

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
                        node(alcoAlloy, ()->{
                            node(aluminum, () -> {

                            });
                            nodeProduce(cobalt, ()->{

                            });
                        });
                        nodeProduce(condensedBiomatter, () ->{

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
