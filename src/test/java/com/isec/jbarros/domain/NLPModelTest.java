package com.isec.jbarros.domain;

import static com.isec.jbarros.domain.NLPModelTestSamples.*;
import static com.isec.jbarros.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NLPModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NLPModel.class);
        NLPModel nLPModel1 = getNLPModelSample1();
        NLPModel nLPModel2 = new NLPModel();
        assertThat(nLPModel1).isNotEqualTo(nLPModel2);

        nLPModel2.setId(nLPModel1.getId());
        assertThat(nLPModel1).isEqualTo(nLPModel2);

        nLPModel2 = getNLPModelSample2();
        assertThat(nLPModel1).isNotEqualTo(nLPModel2);
    }

    @Test
    void tagsTest() throws Exception {
        NLPModel nLPModel = getNLPModelRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        nLPModel.addTags(tagBack);
        assertThat(nLPModel.getTags()).containsOnly(tagBack);

        nLPModel.removeTags(tagBack);
        assertThat(nLPModel.getTags()).doesNotContain(tagBack);

        nLPModel.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(nLPModel.getTags()).containsOnly(tagBack);

        nLPModel.setTags(new HashSet<>());
        assertThat(nLPModel.getTags()).doesNotContain(tagBack);
    }
}
