package de.fhg.iais.roberta.syntax.expr;

import org.junit.Test;

public class ListsSubListTest {
    

    @Test
    public void Test() throws Exception {
        String code =
            "ArrayList<Float> Element = new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0));\n"
                + "public void run() throws Exception {"
                + "Element = new ArrayList<>(new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).subList(0, 0));"
                + "Element = new ArrayList<>(new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).subList((new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).size() - 1) - 0, (new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).size() - 1) - 0));"
                + "Element = new ArrayList<>(new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).subList(0, new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).size()));"
                + "Element = new ArrayList<>(new ArrayList<>(Arrays.asList((float) 0, (float) 0, (float) 0)).subList(0, 0));"
                + "}";
        this.h.assertCodeIsOk(code, "/syntax/lists/lists_sub_list.xml");
    }
}
