package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class VorwerkUsedMethodCollectorWorker extends AbstractUsedMethodCollectorWorker {
    @Override
    protected ICollectorVisitor getVisitor(CodeGeneratorSetupBean.Builder builder) {
        return new VorwerkUsedMethodCollectorVisitor(builder);
    }
}
