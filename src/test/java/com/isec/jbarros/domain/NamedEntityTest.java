package com.isec.jbarros.domain;

import static com.isec.jbarros.domain.ArticleTestSamples.*;
import static com.isec.jbarros.domain.NamedEntityTestSamples.*;
import static com.isec.jbarros.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NamedEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NamedEntity.class);
        NamedEntity namedEntity1 = getNamedEntitySample1();
        NamedEntity namedEntity2 = new NamedEntity();
        assertThat(namedEntity1).isNotEqualTo(namedEntity2);

        namedEntity2.setId(namedEntity1.getId());
        assertThat(namedEntity1).isEqualTo(namedEntity2);

        namedEntity2 = getNamedEntitySample2();
        assertThat(namedEntity1).isNotEqualTo(namedEntity2);
    }

    @Test
    void articleTest() throws Exception {
        NamedEntity namedEntity = getNamedEntityRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        namedEntity.setArticle(articleBack);
        assertThat(namedEntity.getArticle()).isEqualTo(articleBack);

        namedEntity.article(null);
        assertThat(namedEntity.getArticle()).isNull();
    }

    @Test
    void tagTest() throws Exception {
        NamedEntity namedEntity = getNamedEntityRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        namedEntity.setTag(tagBack);
        assertThat(namedEntity.getTag()).isEqualTo(tagBack);

        namedEntity.tag(null);
        assertThat(namedEntity.getTag()).isNull();
    }
}
