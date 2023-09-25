package olupis.world.blocks.misc;

import arc.func.Prov;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Icon;
import mindustry.logic.LStatement;
import mindustry.logic.LStatements;
import mindustry.ui.Styles;
import mindustry.world.blocks.logic.LogicBlock;
import olupis.NyfalisMain;

public class NyfalisLogicBlock extends LogicBlock {
    /*Credit: https://github.com/TeamViscott/ModProjectViscott/blob/master/src/viscott/world/block/logic/PvLogicBlock.java*/
    public Seq<Prov<LStatement>> allStatements;

    public NyfalisLogicBlock(String name){
        super(name);
        allStatements = Seq.with( new Prov[]{
                LStatements.InvalidStatement::new,
                LStatements.ReadStatement::new,
                LStatements.WriteStatement::new,
                LStatements.DrawStatement::new,
                LStatements.PrintStatement::new,
                LStatements.DrawFlushStatement::new,
                LStatements.PrintFlushStatement::new,
                LStatements.GetLinkStatement::new,
                LStatements.ControlStatement::new,
                LStatements.SensorStatement::new,
                LStatements.RadarStatement::new,
                LStatements.SetStatement::new,
                LStatements.OperationStatement::new,
                LStatements.WaitStatement::new,
                LStatements.StopStatement::new,
                LStatements.LookupStatement::new,
                LStatements.PackColorStatement::new,
                LStatements.EndStatement::new,
                LStatements.JumpStatement::new,
                LStatements.GetBlockStatement::new,
                LStatements.SetBlockStatement::new,
                LStatements.SpawnUnitStatement::new,
                LStatements.ApplyStatusStatement::new,
                LStatements.SpawnWaveStatement::new,
                LStatements.SetRuleStatement::new,
                LStatements.FlushMessageStatement::new,
                LStatements.CutsceneStatement::new,
                LStatements.ExplosionStatement::new,
                LStatements.SetRateStatement::new,
                LStatements.FetchStatement::new,
                LStatements.GetFlagStatement::new,
                LStatements.SetFlagStatement::new,
                LStatements.CommentStatement::new
        });
    }
    public class NyfalisLogicBuild extends LogicBuild {

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.pencil, Styles.cleari, () -> NyfalisMain.logicDialog.show(allStatements, code, executor, privileged, code -> configure(compress(code, relativeConnections())))).size(40);
        }
    }
}
