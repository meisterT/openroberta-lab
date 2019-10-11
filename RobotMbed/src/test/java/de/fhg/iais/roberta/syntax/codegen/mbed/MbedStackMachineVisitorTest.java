package de.fhg.iais.roberta.syntax.codegen.mbed;

import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class MbedStackMachineVisitorTest extends AstTest {

    @Test
    public void mbedDisplayTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/display.json", "/stack_machine/display.xml");
    }

    @Test
    public void mbedLightTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/light.json", "/stack_machine/light.xml");
    }

    @Test
    public void mbedMoveTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/move.json", "/stack_machine/move.xml");
    }

    @Test
    public void mbedSoundTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/sound.json", "/stack_machine/sound.xml");
    }

    @Test
    public void mbedPinTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/pin.json", "/stack_machine/pin.xml");
    }

    @Test
    public void mbedSensorsTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/stack_machine/sensors.json", "/stack_machine/sensors.xml");
    }

}
