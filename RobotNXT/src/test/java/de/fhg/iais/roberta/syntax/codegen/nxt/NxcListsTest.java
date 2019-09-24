package de.fhg.iais.roberta.syntax.codegen.nxt;

import org.junit.Test;

public class NxcListsTest {

    @Test
    public void nxtListsGetSetFindTest() throws Exception {
        this.nxtHelper
            .compareExistingAndGeneratedNxcSource(
                "/ast/lists/nxc_arrays_find_get_set_test.nxc",
                "/ast/lists/nxc_arrays_find_get_set_test.xml",
                this.standardConfiguration);
    }

    @Test
    public void nxtListsGetSetAllIndiciesTest() throws Exception {
        this.nxtHelper
            .compareExistingAndGeneratedNxcSource(
                "/ast/lists/nxc_arrays_get_set_all_indices.nxc",
                "/ast/lists/nxc_arrays_get_set_all_indices.xml",
                this.standardConfiguration);
    }

}
