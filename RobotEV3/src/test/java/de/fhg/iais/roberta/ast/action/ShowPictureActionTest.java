package de.fhg.iais.roberta.ast.action;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.ev3.ShowPictureAction;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class ShowPictureActionTest extends AstTest {

    @Test
    public void make() throws Exception {
        String a = "BlockAST [project=[[Location [x=-74, y=64], ShowPictureAction [EYESOPEN, NumConst [0], NumConst [0]]]]]";
        UnitTestHelper.checkProgramAstEquality(testFactory, a, "/ast/actions/action_ShowPicture.xml");
    }

    @Test
    public void getPicture() throws Exception {
        ArrayList<ArrayList<Phrase<Void>>> forest = UnitTestHelper.getAst(testFactory,"/ast/actions/action_ShowPicture.xml");
        ShowPictureAction<Void> spa = (ShowPictureAction<Void>) forest.get(0).get(1);
        Assert.assertEquals("EYESOPEN", spa.getPicture().toString());
    }

    @Test
    public void getX() throws Exception {
        ArrayList<ArrayList<Phrase<Void>>> forest = UnitTestHelper.getAst(testFactory,"/ast/actions/action_ShowPicture.xml");
        ShowPictureAction<Void> spa = (ShowPictureAction<Void>) forest.get(0).get(1);
        Assert.assertEquals("NumConst [0]", spa.getX().toString());
    }

    @Test
    public void getY() throws Exception {
        ArrayList<ArrayList<Phrase<Void>>> forest = UnitTestHelper.getAst(testFactory,"/ast/actions/action_ShowPicture.xml");
        ShowPictureAction<Void> spa = (ShowPictureAction<Void>) forest.get(0).get(1);
        Assert.assertEquals("NumConst [0]", spa.getY().toString());
    }

    @Test
    public void missing() throws Exception {
        String a =
            "BlockAST [project=[[Location [x=-9, y=3], ShowPictureAction [FLOWERS, EmptyExpr [defVal=NUMBER_INT], EmptyExpr [defVal=NUMBER_INT]], ShowPictureAction [OLDGLASSES, NumConst [0], EmptyExpr [defVal=NUMBER_INT]], ShowPictureAction [TACHO, EmptyExpr [defVal=NUMBER_INT], NumConst [0]]]]]";
        UnitTestHelper.checkProgramAstEquality(testFactory, a, "/ast/actions/action_ShowPictureMissing.xml");
    }

    @Test
    public void reverseTransformatin() throws Exception {
        UnitTestHelper.checkProgramReverseTransformation(testFactory, "/ast/actions/action_ShowPicture.xml");
    }

    @Test
    public void reverseTransformatin1() throws Exception {
        UnitTestHelper.checkProgramReverseTransformation(testFactory, "/ast/actions/action_ShowPicture1.xml");
    }

    @Test
    public void reverseTransformatinMissing() throws Exception {
        UnitTestHelper.checkProgramReverseTransformation(testFactory, "/ast/actions/action_ShowPictureMissing.xml");
    }
}
