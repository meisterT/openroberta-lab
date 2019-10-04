package de.fhg.iais.roberta.visitor.collect;

import de.fhg.iais.roberta.syntax.lang.functions.FunctionNames;
import de.fhg.iais.roberta.syntax.lang.functions.MathRandomFloatFunct;
import de.fhg.iais.roberta.syntax.lang.functions.MathRandomIntFunct;
import de.fhg.iais.roberta.transformer.CodeGeneratorSetupBean;

public class Ev3UsedMethodCollectorVisitor extends AbstractUsedMethodCollectorVisitor implements IEv3CollectorVisitor {
    public Ev3UsedMethodCollectorVisitor(CodeGeneratorSetupBean.Builder builder) {
        super(builder);
    }
}
