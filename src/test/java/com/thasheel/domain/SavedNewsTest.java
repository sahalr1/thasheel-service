package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class SavedNewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SavedNews.class);
        SavedNews savedNews1 = new SavedNews();
        savedNews1.setId(1L);
        SavedNews savedNews2 = new SavedNews();
        savedNews2.setId(savedNews1.getId());
        assertThat(savedNews1).isEqualTo(savedNews2);
        savedNews2.setId(2L);
        assertThat(savedNews1).isNotEqualTo(savedNews2);
        savedNews1.setId(null);
        assertThat(savedNews1).isNotEqualTo(savedNews2);
    }
}
