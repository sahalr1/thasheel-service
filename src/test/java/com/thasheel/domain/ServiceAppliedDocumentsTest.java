package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class ServiceAppliedDocumentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAppliedDocuments.class);
        ServiceAppliedDocuments serviceAppliedDocuments1 = new ServiceAppliedDocuments();
        serviceAppliedDocuments1.setId(1L);
        ServiceAppliedDocuments serviceAppliedDocuments2 = new ServiceAppliedDocuments();
        serviceAppliedDocuments2.setId(serviceAppliedDocuments1.getId());
        assertThat(serviceAppliedDocuments1).isEqualTo(serviceAppliedDocuments2);
        serviceAppliedDocuments2.setId(2L);
        assertThat(serviceAppliedDocuments1).isNotEqualTo(serviceAppliedDocuments2);
        serviceAppliedDocuments1.setId(null);
        assertThat(serviceAppliedDocuments1).isNotEqualTo(serviceAppliedDocuments2);
    }
}
