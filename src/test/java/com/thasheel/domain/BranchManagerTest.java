package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class BranchManagerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchManager.class);
        BranchManager branchManager1 = new BranchManager();
        branchManager1.setId(1L);
        BranchManager branchManager2 = new BranchManager();
        branchManager2.setId(branchManager1.getId());
        assertThat(branchManager1).isEqualTo(branchManager2);
        branchManager2.setId(2L);
        assertThat(branchManager1).isNotEqualTo(branchManager2);
        branchManager1.setId(null);
        assertThat(branchManager1).isNotEqualTo(branchManager2);
    }
}
