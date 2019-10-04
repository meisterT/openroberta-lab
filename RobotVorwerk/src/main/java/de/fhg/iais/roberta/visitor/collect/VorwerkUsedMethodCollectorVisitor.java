package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class VorwerkUsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements IVorwerkCollectorVisitor {
    public VorwerkUsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
