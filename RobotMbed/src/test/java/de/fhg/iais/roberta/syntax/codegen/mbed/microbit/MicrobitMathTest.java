package de.fhg.iais.roberta.syntax.codegen.mbed.microbit;

import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class MicrobitMathTest extends AstTest {

    @Test
    public void mathOnListsTest() throws Exception {
        UnitTestHelper
            .checkGeneratedSourceEquality(
                testFactory,
                "/function/microbit_math_constants_and_functions_test.py",
                "/function/microbit_math_constants_and_functions_test.xml");
    }

    @Test
    public void mathOperationsTest() throws Exception {
        UnitTestHelper.checkGeneratedSourceEquality(testFactory, "/function/microbit_math_operations_test.py", "/function/microbit_math_operations_test.xml");
    }

}
