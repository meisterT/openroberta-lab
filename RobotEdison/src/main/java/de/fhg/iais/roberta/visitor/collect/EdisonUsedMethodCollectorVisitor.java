package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class EdisonUsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements IEdisonCollectorVisitor {
    public EdisonUsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
