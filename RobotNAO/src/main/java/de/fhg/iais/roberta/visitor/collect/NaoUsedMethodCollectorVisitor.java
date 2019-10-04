package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class NaoUsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements INaoCollectorVisitor {
    public NaoUsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
