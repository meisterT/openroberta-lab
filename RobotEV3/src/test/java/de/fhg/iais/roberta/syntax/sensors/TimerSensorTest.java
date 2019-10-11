package de.fhg.iais.roberta.syntax.sensors;

import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class TimerSensorTest extends AstTest {

    @Test
    public void getTimerValue() throws Exception {
        String a = "\nhal.getTimerValue(1)}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/sensors/sensor_getSampleTimer.xml");
    }

    @Test
    public void resetTimer() throws Exception {
        String a = "\nhal.resetTimer(1);}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/sensors/sensor_resetTimer.xml");
    }
}
