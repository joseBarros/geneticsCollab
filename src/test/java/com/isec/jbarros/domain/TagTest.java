package com.isec.jbarros.domain;

import static com.isec.jbarros.domain.NLPModelTestSamples.*;
import static com.isec.jbarros.domain.NamedEntityTestSamples.*;
import static com.isec.jbarros.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void namedEntityTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        NamedEntity namedEntityBack = getNamedEntityRandomSampleGenerator();

        tag.addNamedEntity(namedEntityBack);
        assertThat(tag.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getTags()).containsOnly(tag);

        tag.removeNamedEntity(namedEntityBack);
        assertThat(tag.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getTags()).doesNotContain(tag);

        tag.namedEntities(new HashSet<>(Set.of(namedEntityBack)));
        assertThat(tag.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getTags()).containsOnly(tag);

        tag.setNamedEntities(new HashSet<>());
        assertThat(tag.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getTags()).doesNotContain(tag);
    }

    @Test
    void nLPModelTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        NLPModel nLPModelBack = getNLPModelRandomSampleGenerator();

        tag.addNLPModel(nLPModelBack);
        assertThat(tag.getNLPModels()).containsOnly(nLPModelBack);
        assertThat(nLPModelBack.getTags()).containsOnly(tag);

        tag.removeNLPModel(nLPModelBack);
        assertThat(tag.getNLPModels()).doesNotContain(nLPModelBack);
        assertThat(nLPModelBack.getTags()).doesNotContain(tag);

        tag.nLPModels(new HashSet<>(Set.of(nLPModelBack)));
        assertThat(tag.getNLPModels()).containsOnly(nLPModelBack);
        assertThat(nLPModelBack.getTags()).containsOnly(tag);

        tag.setNLPModels(new HashSet<>());
        assertThat(tag.getNLPModels()).doesNotContain(nLPModelBack);
        assertThat(nLPModelBack.getTags()).doesNotContain(tag);
    }
}
