package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class Ev3UsedMethodCollectorWorker extends AbstractUsedMethodCollectorWorker {
    @Override
    protected ICollectorVisitor getVisitor(CodeGeneratorSetupBean.Builder builder) {
        return new Ev3UsedMethodCollectorVisitor(builder);
    }
}
