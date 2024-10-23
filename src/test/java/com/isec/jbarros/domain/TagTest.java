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
    void namedEntitiesTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        NamedEntity namedEntityBack = getNamedEntityRandomSampleGenerator();

        tag.addNamedEntities(namedEntityBack);
        assertThat(tag.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getTag()).isEqualTo(tag);

        tag.removeNamedEntities(namedEntityBack);
        assertThat(tag.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getTag()).isNull();

        tag.namedEntities(new HashSet<>(Set.of(namedEntityBack)));
        assertThat(tag.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getTag()).isEqualTo(tag);

        tag.setNamedEntities(new HashSet<>());
        assertThat(tag.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getTag()).isNull();
    }

    @Test
    void nlpModelTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        NLPModel nLPModelBack = getNLPModelRandomSampleGenerator();

        tag.setNlpModel(nLPModelBack);
        assertThat(tag.getNlpModel()).isEqualTo(nLPModelBack);

        tag.nlpModel(null);
        assertThat(tag.getNlpModel()).isNull();
    }
}
