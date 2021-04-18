package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class NewsAppliedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsApplied.class);
        NewsApplied newsApplied1 = new NewsApplied();
        newsApplied1.setId(1L);
        NewsApplied newsApplied2 = new NewsApplied();
        newsApplied2.setId(newsApplied1.getId());
        assertThat(newsApplied1).isEqualTo(newsApplied2);
        newsApplied2.setId(2L);
        assertThat(newsApplied1).isNotEqualTo(newsApplied2);
        newsApplied1.setId(null);
        assertThat(newsApplied1).isNotEqualTo(newsApplied2);
    }
}
