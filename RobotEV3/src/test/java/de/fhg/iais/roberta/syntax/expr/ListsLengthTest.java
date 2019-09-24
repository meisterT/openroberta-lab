package de.fhg.iais.roberta.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.ast.AstTest;
import de.fhg.iais.roberta.util.test.UnitTestHelper;

public class ListsLengthTest extends AstTest {

    @Test
    public void Test() throws Exception {
        String a = "newArrayList<>(Arrays.asList((float)((float) 0.1), (float) ((float)0.0), (float) 0)).size()}";

        UnitTestHelper.checkGeneratedSourceEqualityWithSourceAsString(testFactory, a, "/syntax/lists/lists_length.xml");
    }
}
