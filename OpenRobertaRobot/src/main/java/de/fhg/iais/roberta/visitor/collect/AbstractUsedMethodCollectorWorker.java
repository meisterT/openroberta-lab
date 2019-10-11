package de.fhg.iais.roberta.visitor.collect;

import java.util.ArrayList;

import de.fhg.iais.roberta.bean.CodeGeneratorSetupBean;
import de.fhg.iais.roberta.bean.CodeGeneratorSetupBean.Builder;
import de.fhg.iais.roberta.bean.UsedHardwareBean;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.transformer.Project;
import de.fhg.iais.roberta.visitor.validate.IWorker;

public abstract class AbstractUsedMethodCollectorWorker implements IWorker {

    protected abstract ICollectorVisitor getVisitor(CodeGeneratorSetupBean.Builder builder);

    @Override
    public void execute(Project project) {
        CodeGeneratorSetupBean.Builder builder = new Builder();
        final ICollectorVisitor visitor = getVisitor(builder);
        ArrayList<ArrayList<Phrase<Void>>> tree = project.getProgramAst().getTree();
        for ( ArrayList<Phrase<Void>> phrases : tree ) {
            for ( Phrase<Void> phrase : phrases ) {
                phrase.visit(visitor); // TODO: REALLY REALLY BAD NAME !!!
            }
        }

        builder.setFileExtension(project.getFileExtension());
        builder.setHelperMethodFile(project.getRobotFactory().getPluginProperties().getStringProperty("robot.helperMethods"));
        CodeGeneratorSetupBean bean = builder.build();
        if (project.getRobot().equals("edison")) { // TODO real solution
            bean.getHelperMethodGenerator().addAdditionalEnum(UsedHardwareBean.EdisonMethods.class);
        }
        project.addWorkerResult("CodeGeneratorSetup", bean);
    }

}
