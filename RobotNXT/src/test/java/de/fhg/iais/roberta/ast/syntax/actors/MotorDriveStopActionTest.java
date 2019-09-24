package de.fhg.iais.roberta.ast.syntax.actors;

import org.junit.Test;

public class MotorDriveStopActionTest {
    

    @Test
    public void stop() throws Exception {
        final String a =
            "#defineWHEELDIAMETER5.6#defineTRACKWIDTH11.0#defineMAXLINES8#include\"NEPODefs.h\"//containsNEPOdeclarationsfortheNXCNXTAPIresources"
                + "\nOff(OUT_BC);}";

        this.h.assertWrappedCodeIsOk(a, "/ast/actions/action_Stop.xml");
    }
}