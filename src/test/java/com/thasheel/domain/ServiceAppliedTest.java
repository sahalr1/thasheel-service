package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class ServiceAppliedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceApplied.class);
        ServiceApplied serviceApplied1 = new ServiceApplied();
        serviceApplied1.setId(1L);
        ServiceApplied serviceApplied2 = new ServiceApplied();
        serviceApplied2.setId(serviceApplied1.getId());
        assertThat(serviceApplied1).isEqualTo(serviceApplied2);
        serviceApplied2.setId(2L);
        assertThat(serviceApplied1).isNotEqualTo(serviceApplied2);
        serviceApplied1.setId(null);
        assertThat(serviceApplied1).isNotEqualTo(serviceApplied2);
    }
}
