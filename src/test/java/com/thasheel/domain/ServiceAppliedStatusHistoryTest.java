package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class ServiceAppliedStatusHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAppliedStatusHistory.class);
        ServiceAppliedStatusHistory serviceAppliedStatusHistory1 = new ServiceAppliedStatusHistory();
        serviceAppliedStatusHistory1.setId(1L);
        ServiceAppliedStatusHistory serviceAppliedStatusHistory2 = new ServiceAppliedStatusHistory();
        serviceAppliedStatusHistory2.setId(serviceAppliedStatusHistory1.getId());
        assertThat(serviceAppliedStatusHistory1).isEqualTo(serviceAppliedStatusHistory2);
        serviceAppliedStatusHistory2.setId(2L);
        assertThat(serviceAppliedStatusHistory1).isNotEqualTo(serviceAppliedStatusHistory2);
        serviceAppliedStatusHistory1.setId(null);
        assertThat(serviceAppliedStatusHistory1).isNotEqualTo(serviceAppliedStatusHistory2);
    }
}
