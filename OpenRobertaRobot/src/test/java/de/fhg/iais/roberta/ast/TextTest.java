package de.fhg.iais.roberta.ast;

import org.junit.Assert;
import org.junit.Test;

import de.fhg.iais.roberta.util.test.AbstractHelperForXmlTest;
import de.fhg.iais.roberta.util.test.GenericHelperForXmlTest;

public class TextTest {
    AbstractHelperForXmlTest h = new GenericHelperForXmlTest();

    @Test
    public void text1() throws Exception {
        String a = "BlockAST [project=[[Location [x=-616, y=42], TextJoinFunct [StringConst [text1], EmptyExpr [defVal=STRING], StringConst [text2]]]]]";

        Assert.assertEquals(a, this.h.generateTransformerString("/ast/text/text1.xml"));
    }

    @Test
    public void reverseTransformatinText1() throws Exception {
        this.h.assertTransformationIsOk("/ast/text/text1.xml");
    }

    @Test
    public void textAppend() throws Exception {
        String a = "BlockAST [project=[[Location [x=14, y=93], \nexprStmt Binary [TEXT_APPEND, Var [item], StringConst [text]]]]]";

        Assert.assertEquals(a, this.h.generateTransformerString("/ast/text/text_append.xml"));
    }

    @Test
    public void reverseTransformatinAppend() throws Exception {
        this.h.assertTransformationIsOk("/ast/text/text_append.xml");
    }

    @Test
    public void textPrint() throws Exception {
        String a = "BlockAST [project=[[Location [x=-846, y=39], TextPrintFunct [[StringConst []]]]]]";

        Assert.assertEquals(a, this.h.generateTransformerString("/ast/text/text_print.xml"));
    }

    @Test
    public void reverseTransformatinPrint() throws Exception {
        this.h.assertTransformationIsOk("/ast/text/text_print.xml");
    }

}
