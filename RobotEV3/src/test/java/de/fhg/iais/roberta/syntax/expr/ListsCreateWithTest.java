package de.fhg.iais.roberta.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class ListsCreateWithTest extends AstTest {

    @Test
    public void Test() throws Exception {
        String a = "newArrayList<>(Arrays.asList((float)((float)1.0),(float)((float)3.1),(float)2))}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_create_with.xml");
    }

    @Test
    public void Test1() throws Exception {
        String a = "newArrayList<>(Arrays.<String>asList(\"a\", \"b\", \"c\"))}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_create_with1.xml");
    }

    @Test
    public void Test2() throws Exception {
        String a = "newArrayList<>(Arrays.<Boolean>asList(true, true, false))}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_create_with2.xml");
    }

    @Test
    public void Test3() throws Exception {
        String a = "newArrayList<>(Arrays.<Boolean>asList(true, true, true))}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_create_with3.xml");
    }

    @Test
    public void Test4() throws Exception {
        String a = "newArrayList<>(Arrays.<PickColor>asList(PickColor.NONE,PickColor.RED,PickColor.BROWN))}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_create_with4.xml");
    }
}
