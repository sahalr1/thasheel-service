package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class NewsAppliedStatusHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsAppliedStatusHistory.class);
        NewsAppliedStatusHistory newsAppliedStatusHistory1 = new NewsAppliedStatusHistory();
        newsAppliedStatusHistory1.setId(1L);
        NewsAppliedStatusHistory newsAppliedStatusHistory2 = new NewsAppliedStatusHistory();
        newsAppliedStatusHistory2.setId(newsAppliedStatusHistory1.getId());
        assertThat(newsAppliedStatusHistory1).isEqualTo(newsAppliedStatusHistory2);
        newsAppliedStatusHistory2.setId(2L);
        assertThat(newsAppliedStatusHistory1).isNotEqualTo(newsAppliedStatusHistory2);
        newsAppliedStatusHistory1.setId(null);
        assertThat(newsAppliedStatusHistory1).isNotEqualTo(newsAppliedStatusHistory2);
    }
}
