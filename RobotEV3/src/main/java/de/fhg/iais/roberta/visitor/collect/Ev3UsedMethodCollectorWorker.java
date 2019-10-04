package de.fhg.iais.roberta.visitor.collect;

import java.util.ArrayList;

import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;
import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean.Builder;
import de.fhg.iais.roberta.transformer.Project;
import de.fhg.iais.roberta.visitor.validate.IWorker;

public class Ev3UsedMethodCollectorWorker implements IWorker {

    @Override
    public void execute(Project project) {
        CodeGeneratorSetupBean.Builder builder = new Builder();
        final UsedMethodCollectorVisitor visitor = new UsedMethodCollectorVisitor(builder);
        ArrayList<ArrayList<Phrase<Void>>> tree = project.getProgramAst().getTree();
        for ( ArrayList<Phrase<Void>> phrases : tree ) {
            for ( Phrase<Void> phrase : phrases ) {
                phrase.visit(visitor); // TODO: REALLY REALLY BAD NAME !!!
            }
        }
        builder.setFileExtension(project.getFileExtension());
        builder.setHelperMethodFile(project.getRobotFactory().getPluginProperties().getStringProperty("robot.helperMethods"));
        CodeGeneratorSetupBean bean = builder.build();
        project.addWorkerResult("CodeGeneratorSetup", bean);
    }

}
