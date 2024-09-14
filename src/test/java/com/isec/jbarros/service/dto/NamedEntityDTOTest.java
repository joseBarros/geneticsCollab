package com.isec.jbarros.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NamedEntityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NamedEntityDTO.class);
        NamedEntityDTO namedEntityDTO1 = new NamedEntityDTO();
        namedEntityDTO1.setId("id1");
        NamedEntityDTO namedEntityDTO2 = new NamedEntityDTO();
        assertThat(namedEntityDTO1).isNotEqualTo(namedEntityDTO2);
        namedEntityDTO2.setId(namedEntityDTO1.getId());
        assertThat(namedEntityDTO1).isEqualTo(namedEntityDTO2);
        namedEntityDTO2.setId("id2");
        assertThat(namedEntityDTO1).isNotEqualTo(namedEntityDTO2);
        namedEntityDTO1.setId(null);
        assertThat(namedEntityDTO1).isNotEqualTo(namedEntityDTO2);
    }
}
