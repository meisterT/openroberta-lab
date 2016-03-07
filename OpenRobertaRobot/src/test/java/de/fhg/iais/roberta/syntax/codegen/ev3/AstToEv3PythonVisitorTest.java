package de.fhg.iais.roberta.syntax.codegen.ev3;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.fhg.iais.roberta.components.ev3.EV3Actor;
import de.fhg.iais.roberta.components.ev3.EV3Actors;
import de.fhg.iais.roberta.components.ev3.EV3Sensor;
import de.fhg.iais.roberta.components.ev3.EV3Sensors;
import de.fhg.iais.roberta.components.ev3.Ev3Configuration;
import de.fhg.iais.roberta.shared.action.ev3.ActorPort;
import de.fhg.iais.roberta.shared.action.ev3.DriveDirection;
import de.fhg.iais.roberta.shared.action.ev3.MotorSide;
import de.fhg.iais.roberta.shared.sensor.ev3.SensorPort;
import de.fhg.iais.roberta.testutil.Helper;

public class AstToEv3PythonVisitorTest {

    private static final String IMPORTS = "" //
        + "#!/usr/bin/python\n\n"
        + "from __future__ import absolute_import\n"
        + "from roberta.ev3 import Hal\n"
        + "from roberta.BlocklyMethods import BlocklyMethods\n"
        + "from sets import Set\n"
        + "from ev3dev import ev3 as ev3dev\n"
        + "import math\n\n"
        + "TRUE = True\n";

    private static final String GLOBALS = "" //
        + "_brickConfiguration = {\n"
        + "    'wheel-diameter': 5.6,\n"
        + "    'track-width': 17.0,\n"
        + "    'actors': {\n"
        + "        'A':Hal.makeMediumMotor(ev3dev.OUTPUT_A, 'on', 'foreward', 'left'),\n"
        + "        'B':Hal.makeLargeMotor(ev3dev.OUTPUT_B, 'on', 'foreward', 'right'),\n"
        + "    },\n"
        + "    'sensors': {\n"
        + "        '1':Hal.makeTouchSensor(ev3dev.INPUT_1),\n"
        + "        '2':Hal.makeUltrasonicSensor(ev3dev.INPUT_2),\n"
        + "    },\n"
        + "}\n"
        + "_usedSensors = Set([])\n"
        + "hal = Hal(_brickConfiguration, _usedSensors)\n\n";

    private static final String MAIN_METHOD = "" //
        + "def main():\n"
        + "    try:\n"
        + "        run()\n"
        + "    except Exception as e:\n"
        + "        hal.drawText('Fehler im EV3', 0, 0)\n"
        + "        hal.drawText(e.__class__.__name, 0, 1)\n"
        + "        if e.message:\n"
        + "            hal.drawText(e.message, 0, 2)\n"
        + "        hal.drawText('Press any key', 0, 4)\n"
        + "        while not hal.isKeyPressed('any'): hal.waitFor(500)\n"
        + "        raise\n\n"
        + "if __name__ == \"__main__\":\n"
        + "    main()";
    private static Ev3Configuration brickConfiguration;

    @BeforeClass
    public static void setupConfigurationForAllTests() {
        Ev3Configuration.Builder builder = new Ev3Configuration.Builder();
        builder.setTrackWidth(17).setWheelDiameter(5.6);
        builder.addActor(ActorPort.A, new EV3Actor(EV3Actors.EV3_MEDIUM_MOTOR, true, DriveDirection.FOREWARD, MotorSide.LEFT)).addActor(
            ActorPort.B,
            new EV3Actor(EV3Actors.EV3_LARGE_MOTOR, true, DriveDirection.FOREWARD, MotorSide.RIGHT));
        builder.addSensor(SensorPort.S1, new EV3Sensor(EV3Sensors.EV3_TOUCH_SENSOR)).addSensor(SensorPort.S2, new EV3Sensor(EV3Sensors.EV3_ULTRASONIC_SENSOR));
        brickConfiguration = builder.build();
    }

    @Test
    public void testSingleStatement() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    hal.drawText(\"Hallo\", 0, 3)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator.xml");
    }

    @Test
    public void testRangeLoop() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    for k0 in xrange(0, 10, 1):\n"
            + "        hal.drawText(\"Hallo\", 0, 3)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator1.xml");
    }

    @Test
    public void testCondition1() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    if hal.isPressed('1'):\n"
            + "        hal.ledOn('green', 'on')\n"
            + "    elif 'red' == hal.getColorSensorColour('3'):\n"
            + "        if TRUE:\n"
            + "            while True:\n"
            + "                hal.drawPicture('eyesopen', 0, 0)\n"
            + "                hal.turnOnRegulatedMotor('B', 30)\n"
            + "    hal.playFile(1)\n"
            + "    hal.setVolume(50)\n"
            + "    for i in xrange(1, 10, 1):\n"
            + "        hal.rotateRegulatedMotor('B', 30, 'rotations', 1)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator2.xml");
    }

    @Test
    public void testCondition2() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    if hal.isPressed('1'):\n"
            + "        hal.ledOn('green', 'on')\n"
            + "    else:\n"
            + "        if hal.isPressed('1'):\n"
            + "            hal.ledOn('green', 'on')\n"
            + "        elif 0 == hal.getUltraSonicSensorDistance('4'):\n"
            + "            hal.drawPicture('flowers', 15, 15)\n"
            + "        else:\n"
            + "            if TRUE:\n"
            + "                while not hal.isKeyPressed('up'):\n"
            + "                    hal.turnOnRegulatedMotor('B', 30)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator3.xml");
    }

    @Test
    public void testCondition3() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    if 5 < hal.getRegulatedMotorSpeed('B'):\n"
            + "        hal.turnOnRegulatedMotor('B', 30)\n"
            + "        hal.rotateRegulatedMotor('B', 30, 'rotations', 1)\n"
            + "        hal.rotateDirectionRegulated('A', 'B', False, 'right', 50)\n"
            + "    if hal.getMotorTachoValue('A', 'rotation') + hal.getInfraredSensorDistance('4') == hal.getUltraSonicSensorDistance('4'):\n"
            + "        hal.ledOff()\n"
            + "    else:\n"
            + "        hal.resetGyroSensor('2')\n"
            + "        if TRUE:\n"
            + "            while hal.isPressed('1'):\n"
            + "                hal.drawPicture('oldglasses', 0, 0)\n"
            + "                hal.clearDisplay()\n"
            + "        hal.ledOn('green', 'on')\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator4.xml");
    }

    @Test
    public void testMultipleStatements() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "def run():\n"
            + "    hal.turnOnRegulatedMotor('B', 0)\n"
            + "    hal.rotateRegulatedMotor('B', 30, 'rotations', 0)\n"
            + "    hal.rotateDirectionRegulated('A', 'B', False, 'right', 0)\n"
            + "    hal.setVolume(50)\n"
            + "    hal.playTone(0, 0)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator5.xml");
    }

    // Skip "{6,7}.xml" since they only test various different statements

    @Test
    public void testVariables() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "item = 10\n"
            + "item2 = \"TTTT\"\n"
            + "item3 = True\n"
            + "def run():\n"
            + "    hal.drawText(str(item), 0, 0)\n"
            + "    hal.drawText(str(item2), 0, 0)\n"
            + "    hal.drawText(str(item3), 0, 0)\n"
            + "    item3 = False\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator8.xml");
    }

    @Test
    public void testUnusedVariable() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "variablenName = 0\n"
            + "def run():\n"
            + "    hal.regulatedDrive('A', 'B', False, 'foreward', 50)\n"
            + "    hal.drawPicture('oldglasses', 0, 0)\n"
            + "    \n\n" // FIXME: where is this whitespace coming from?
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator9.xml");
    }

    // Skip "{6,7}.xml" since it only tests color sensor modes

    @Test
    public void testShadow() throws Exception {

        String a = "" //
            + IMPORTS
            + GLOBALS
            + "item = 0\n"
            + "item2 = \"cc\"\n"
            + "def run():\n"
            + "\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/code_generator/java/java_code_generator11.xml");
    }

    @Test
    public void testExpr1() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "8 + -3 + 5\n"
            + "88 - ( 8 + -3 + 5 )\n"
            + "88 - ( 8 + -3 + 5 ) - ( 88 - ( 8 + -3 + 5 ) )\n"
            + "2 * ( 2 - 2 )\n"
            + "2 - 2 * 2\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/expr/expr1.xml");
    }

    @Test
    public void testLogicExpr() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "False == True\n"
            + "True != False\n"
            + "False == False\n"
            + "5 <= 7 == 8 > 9\n"
            + "( 5 != 7 ) >= ( 8 == 9 )\n"
            + "5 + 7 >= ( 8 + 4 ) / ( 9 + 3 )\n"
            + "( 5 + 7 == 5 + 7 ) >= ( 8 + 4 ) / ( 9 + 3 )\n"
            + "( 5 + 7 == 5 + 7 ) >= ( 5 + 7 == 5 + 7 and 5 + 7 <= 5 + 7 )\n"
            + "not (5 + 7 == 5 + 7) == True\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/expr/logic_expr.xml");
    }

    @Test
    public void testLogicNegate() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "not (0 != 0 and False)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/expr/logic_negate.xml");
    }

    @Test
    public void testLogicNull() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "None\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/expr/logic_null.xml");
    }

    @Test
    public void testLogicTernary() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "False if ( 0 == 0 ) else True\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/expr/logic_ternary.xml");
    }

    // TODO: add tests for files from "/syntax/{functions,lists,math,methods,text}/*.xml"

    @Test
    public void testStmtFlowControl() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "if TRUE:\n"
            + "    while 0 == 0:\n"
            + "        print(\"123\")\n"
            + "        print(\"123\")\n"
            + "        if TRUE:\n"
            + "            while not (0 == 0):\n"
            + "                print(\"123\")\n"
            + "                print(\"123\")\n"
            + "                break\n"
            + "        break\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/flowControl_stmt.xml");
    }

    @Test
    public void testStmtFor() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "for k0 in xrange(0, 10, 1):\n"
            + "    pass\n"
            + "for k1 in xrange(0, 10, 1):\n"
            + "    print(\"15\")\n"
            + "    print(\"15\")\n"
            + "for k2 in xrange(0, 10, 1):\n"
            + "    for k3 in xrange(0, 10, 1):\n"
            + "        print(\"15\")\n"
            + "        print(\"15\")\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/for_stmt.xml");
    }

    @Test
    public void testStmtForCount() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "for i in xrange(1, 10, 15):\n"
            + "    pass\n"
            + "for i in xrange(1, 10, 15):\n"
            + "    print(\"\")\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/forCount_stmt.xml");
    }

    @Ignore
    // https://github.com/OpenRoberta/robertalab/issues/81
    public void testStmtForEach() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "variablenName = BlocklyMethods.createListWith('none', 'red', 'blue')\n"
            + "def run():\n"
            + "    for variablenName2 = in variablenName:\n"
            + "        hal.drawText(str(variablenName2), 0, 0)\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/forEach_stmt.xml");
    }

    @Test
    public void testStmtIf() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "if True:\n"
            + "    pass\n"
            + "if False:\n"
            + "    pass\n"
            + "if True:\n"
            + "    if False:\n"
            + "        pass\n"
            + "if False:\n"
            + "    item = 6 + 8\n"
            + "    item = 6 + 8\n"
            + "else:\n"
            + "    item = 3 * 9\n"
            + "if True:\n"
            + "    item = 6 + 8\n"
            + "    item = 6 + 8\n"
            + "if False:\n"
            + "    item = 6 + 8\n"
            + "    item = 6 + 8\n"
            + "    item = 3 * 9\n"
            + "elif True:\n"
            + "    item = 3 * 9\n"
            + "    item = 3 * 9\n"
            + "else:\n"
            + "    item = 3 * 9\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/if_stmt.xml");
    }

    @Test
    public void testStmtWhileUntil() throws Exception {
        String a = "" //
            + IMPORTS
            + GLOBALS
            + "if TRUE:\n"
            + "    while True:\n"
            + "        pass\n"
            + "if TRUE:\n"
            + "    while not (0 == 0):\n"
            + "        pass\n"
            + "if TRUE:\n"
            + "    while not True:\n"
            + "        pass\n"
            + "if TRUE:\n"
            + "    while not (15 == 20):\n"
            + "        variablenName += 1;\n"
            + "if TRUE:\n"
            + "    while not True:\n"
            + "        if TRUE:\n"
            + "            while not (15 == 20):\n"
            + "                variablenName += 1;\n\n"
            + MAIN_METHOD;

        assertCodeIsOk(a, "/syntax/stmt/whileUntil_stmt.xml");
    }

    // TODO: add tests for files from "/syntax/text/*.xml"

    private void assertCodeIsOk(String a, String fileName) throws Exception {
        String b = Helper.generatePython(fileName, brickConfiguration);
        Assert.assertEquals(a, b);
        //Assert.assertEquals(a.replaceAll("\\s+", ""), b.replaceAll("\\s+", ""));
    }
}
