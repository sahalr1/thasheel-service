package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class NeedfulServiceDocumentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NeedfulServiceDocuments.class);
        NeedfulServiceDocuments needfulServiceDocuments1 = new NeedfulServiceDocuments();
        needfulServiceDocuments1.setId(1L);
        NeedfulServiceDocuments needfulServiceDocuments2 = new NeedfulServiceDocuments();
        needfulServiceDocuments2.setId(needfulServiceDocuments1.getId());
        assertThat(needfulServiceDocuments1).isEqualTo(needfulServiceDocuments2);
        needfulServiceDocuments2.setId(2L);
        assertThat(needfulServiceDocuments1).isNotEqualTo(needfulServiceDocuments2);
        needfulServiceDocuments1.setId(null);
        assertThat(needfulServiceDocuments1).isNotEqualTo(needfulServiceDocuments2);
    }
}
