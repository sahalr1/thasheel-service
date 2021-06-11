package com.thasheel.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thasheel.web.rest.TestUtil;

public class ThasheelServiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThasheelService.class);
        ThasheelService thasheelService1 = new ThasheelService();
        thasheelService1.setId(1L);
        ThasheelService thasheelService2 = new ThasheelService();
        thasheelService2.setId(thasheelService1.getId());
        assertThat(thasheelService1).isEqualTo(thasheelService2);
        thasheelService2.setId(2L);
        assertThat(thasheelService1).isNotEqualTo(thasheelService2);
        thasheelService1.setId(null);
        assertThat(thasheelService1).isNotEqualTo(thasheelService2);
    }
}
