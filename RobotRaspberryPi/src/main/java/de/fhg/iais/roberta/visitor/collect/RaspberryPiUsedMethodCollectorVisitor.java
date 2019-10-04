package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class RaspberryPiUsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements IRaspberryPiCollectorVisitor {
    public RaspberryPiUsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
