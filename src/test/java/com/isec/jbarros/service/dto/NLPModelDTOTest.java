package com.isec.jbarros.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NLPModelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NLPModelDTO.class);
        NLPModelDTO nLPModelDTO1 = new NLPModelDTO();
        nLPModelDTO1.setId("id1");
        NLPModelDTO nLPModelDTO2 = new NLPModelDTO();
        assertThat(nLPModelDTO1).isNotEqualTo(nLPModelDTO2);
        nLPModelDTO2.setId(nLPModelDTO1.getId());
        assertThat(nLPModelDTO1).isEqualTo(nLPModelDTO2);
        nLPModelDTO2.setId("id2");
        assertThat(nLPModelDTO1).isNotEqualTo(nLPModelDTO2);
        nLPModelDTO1.setId(null);
        assertThat(nLPModelDTO1).isNotEqualTo(nLPModelDTO2);
    }
}
