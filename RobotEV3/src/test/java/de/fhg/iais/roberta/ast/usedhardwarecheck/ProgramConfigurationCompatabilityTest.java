package de.fhg.iais.roberta.ast.usedhardwarecheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.components.ConfigurationComponent;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.transformer.UsedHardwareBean;
import de.fhg.iais.roberta.util.test.ev3.HelperEv3ForXmlTest;
import de.fhg.iais.roberta.visitor.validate.AbstractBrickValidatorVisitor;
import de.fhg.iais.roberta.visitor.validate.Ev3BrickValidatorVisitor;

public class ProgramConfigurationCompatabilityTest {
    private final HelperEv3ForXmlTest h = new HelperEv3ForXmlTest();

    @Test
    public void ev3program_configuration_compatibility_4_errors() throws Exception {

        Map<String, String> motorAproperties = createMap("MOTOR_REGULATION", "TRUE", "MOTOR_REVERSE", "OFF", "MOTOR_DRIVE", "LEFT");
        ConfigurationComponent motorA = new ConfigurationComponent("LARGE", true, "A", "A", motorAproperties);

        Map<String, String> motorBproperties = createMap("MOTOR_REGULATION", "TRUE", "MOTOR_REVERSE", "OFF", "MOTOR_DRIVE", "RIGHT");
        ConfigurationComponent motorB = new ConfigurationComponent("LARGE", true, "B", "B", motorBproperties);

        ConfigurationComponent touchSensor = new ConfigurationComponent("TOUCH", false, "S1", "1", createMap("TYPE", "TOUCH"));
        ConfigurationComponent ultrasonicSensor = new ConfigurationComponent("ULTRASONIC", false, "S2", "2", createMap("TYPE", "ULTRASONIC"));

        final ConfigurationAst.Builder builder = new ConfigurationAst.Builder();
        builder.setTrackWidth(17f).setWheelDiameter(5.6f).addComponents(Arrays.asList(motorA, motorB, touchSensor, ultrasonicSensor));

        ConfigurationAst brickConfiguration = builder.build();
        ArrayList<ArrayList<Phrase<Void>>> phrases = this.h.generateASTs("/visitors/program_config_compatibility.xml");
        UsedHardwareBean.Builder beanBuilder = new UsedHardwareBean.Builder();
        AbstractBrickValidatorVisitor programChecker = new Ev3BrickValidatorVisitor(beanBuilder, brickConfiguration);
        programChecker.check(phrases);

        Assert.assertEquals(4, programChecker.getErrorCount());

    }

    private static Map<String, String> createMap(String... args) {
        Map<String, String> m = new HashMap<>();
        for ( int i = 0; i < args.length; i += 2 ) {
            m.put(args[i], args[i + 1]);
        }
        return m;
    }

    @Test
    public void ev3program_configuration_compatibility_0_errors() throws Exception {
        ConfigurationAst.Builder builder = new ConfigurationAst.Builder();

        Map<String, String> motorAproperties = createMap("MOTOR_REGULATION", "TRUE", "MOTOR_REVERSE", "OFF", "MOTOR_DRIVE", "LEFT");
        ConfigurationComponent motorA = new ConfigurationComponent("MEDIUM", true, "A", "A", motorAproperties);

        Map<String, String> motorBproperties = createMap("MOTOR_REGULATION", "TRUE", "MOTOR_REVERSE", "OFF", "MOTOR_DRIVE", "RIGHT");
        ConfigurationComponent motorB = new ConfigurationComponent("LARGE", true, "B", "B", motorBproperties);

        ConfigurationComponent touchSensor = new ConfigurationComponent("TOUCH", false, "S1", "1", createMap("TYPE", "TOUCH"));
        ConfigurationComponent colorSensor = new ConfigurationComponent("COLOR", false, "S2", "2", createMap("TYPE", "COLOR"));
        ConfigurationComponent gyroSensor = new ConfigurationComponent("GYRO", false, "S3", "3", createMap("TYPE", "GYRO"));
        ConfigurationComponent ultrasonicSensor = new ConfigurationComponent("ULTRASONIC", false, "S4", "4", createMap("TYPE", "ULTRASONIC"));

        builder.setTrackWidth(17f).setWheelDiameter(5.6f).addComponents(Arrays.asList(motorA, motorB, touchSensor, colorSensor, gyroSensor, ultrasonicSensor));

        ConfigurationAst brickConfiguration = builder.build();
        ArrayList<ArrayList<Phrase<Void>>> phrases = this.h.generateASTs("/visitors/program_config_compatibility_gyro_touch_ultra_color.xml");
        UsedHardwareBean.Builder beanBuilder = new UsedHardwareBean.Builder();
        AbstractBrickValidatorVisitor programChecker = new Ev3BrickValidatorVisitor(beanBuilder, brickConfiguration);
        programChecker.check(phrases);

        Assert.assertEquals(0, programChecker.getErrorCount());

    }
}
