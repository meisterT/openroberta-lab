package de.fhg.iais.roberta.syntax.check;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.bean.UsedHardwareBean;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.display.ClearDisplayAction;
import de.fhg.iais.roberta.syntax.action.display.ShowTextAction;
import de.fhg.iais.roberta.syntax.action.light.LightAction;
import de.fhg.iais.roberta.syntax.action.light.LightStatusAction;
import de.fhg.iais.roberta.syntax.action.sound.PlayFileAction;
import de.fhg.iais.roberta.syntax.action.sound.PlayNoteAction;
import de.fhg.iais.roberta.syntax.action.sound.ToneAction;
import de.fhg.iais.roberta.syntax.action.sound.VolumeAction;
import de.fhg.iais.roberta.syntax.action.speech.SayTextAction;
import de.fhg.iais.roberta.syntax.action.speech.SetLanguageAction;
import de.fhg.iais.roberta.syntax.lang.stmt.StmtTextComment;
import de.fhg.iais.roberta.syntax.sensor.ExternalSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.TemperatureSensor;
import de.fhg.iais.roberta.transformer.Project;
import de.fhg.iais.roberta.util.Util1;
import de.fhg.iais.roberta.util.test.UnitTestHelper;
import de.fhg.iais.roberta.visitor.validate.AbstractProgramValidatorVisitor;

public class CheckVisitorTest extends AstTest {

    class TestProgramCheckVisitor extends AbstractProgramValidatorVisitor {

        public TestProgramCheckVisitor(UsedHardwareBean.Builder builder, ConfigurationAst brickConfiguration) {
            super(builder, brickConfiguration);
        }

        @Override
        public Void visitTemperatureSensor(TemperatureSensor<Void> temperatureSensor) {
            return null;
        }

        @Override
        public Void visitClearDisplayAction(ClearDisplayAction<Void> clearDisplayAction) {
            return null;
        }

        @Override
        public Void visitShowTextAction(ShowTextAction<Void> showTextAction) {
            return null;
        }

        @Override
        public Void visitLightAction(LightAction<Void> lightAction) {
            return null;
        }

        @Override
        public Void visitLightStatusAction(LightStatusAction<Void> lightStatusAction) {
            return null;
        }

        @Override
        public Void visitToneAction(ToneAction<Void> toneAction) {
            return null;
        }

        @Override
        public Void visitPlayNoteAction(PlayNoteAction<Void> playNoteAction) {
            return null;
        }

        @Override
        public Void visitVolumeAction(VolumeAction<Void> volumeAction) {
            return null;
        }

        @Override
        public Void visitSetLanguageAction(SetLanguageAction<Void> setLanguageAction) {
            return null;
        }

        @Override
        public Void visitSayTextAction(SayTextAction<Void> sayTextAction) {
            return null;
        }

        @Override
        public Void visitPlayFileAction(PlayFileAction<Void> playFileAction) {
            return null;
        }

        @Override
        protected void checkSensorPort(ExternalSensor<Void> sensor) {

        }

        @Override
        public Void visitStmtTextComment(StmtTextComment<Void> stmtTextComment) {
            return null;
        }

    }

    @Test
    public void check_noLoops_returnsEmptyMap() throws Exception {
        String programXml = Util1.readResourceContent("/visitors/invalide_use_of_variable.xml");
        Project.Builder builder = UnitTestHelper.setupWithProgramXML(testFactory, programXml);
        Project project = builder.build();
        ArrayList<ArrayList<Phrase<Void>>> phrases = project.getProgramAst().getTree();

        TestProgramCheckVisitor checkVisitor = new TestProgramCheckVisitor(null, null);
        checkVisitor.check(phrases);

        Assert.assertEquals(1, checkVisitor.getErrorCount());
    }

}
