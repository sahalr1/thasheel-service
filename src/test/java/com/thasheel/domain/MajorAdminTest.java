package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class MajorAdminTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorAdmin.class);
        MajorAdmin majorAdmin1 = new MajorAdmin();
        majorAdmin1.setId(1L);
        MajorAdmin majorAdmin2 = new MajorAdmin();
        majorAdmin2.setId(majorAdmin1.getId());
        assertThat(majorAdmin1).isEqualTo(majorAdmin2);
        majorAdmin2.setId(2L);
        assertThat(majorAdmin1).isNotEqualTo(majorAdmin2);
        majorAdmin1.setId(null);
        assertThat(majorAdmin1).isNotEqualTo(majorAdmin2);
    }
}
