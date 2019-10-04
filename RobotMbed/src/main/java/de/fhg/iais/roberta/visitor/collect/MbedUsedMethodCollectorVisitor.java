package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class MbedUsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements IMbedCollectorVisitor {
    public MbedUsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
