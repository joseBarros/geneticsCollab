package com.isec.jbarros.domain;

import static com.isec.jbarros.domain.ArticleTestSamples.*;
import static com.isec.jbarros.domain.NamedEntityTestSamples.*;
import static com.isec.jbarros.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void tagsTest() throws Exception {
        NamedEntity namedEntity = getNamedEntityRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        namedEntity.addTags(tagBack);
        assertThat(namedEntity.getTags()).containsOnly(tagBack);

        namedEntity.removeTags(tagBack);
        assertThat(namedEntity.getTags()).doesNotContain(tagBack);

        namedEntity.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(namedEntity.getTags()).containsOnly(tagBack);

        namedEntity.setTags(new HashSet<>());
        assertThat(namedEntity.getTags()).doesNotContain(tagBack);
    }

    @Test
    void articleTest() throws Exception {
        NamedEntity namedEntity = getNamedEntityRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        namedEntity.addArticle(articleBack);
        assertThat(namedEntity.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getEntities()).containsOnly(namedEntity);

        namedEntity.removeArticle(articleBack);
        assertThat(namedEntity.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getEntities()).doesNotContain(namedEntity);

        namedEntity.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(namedEntity.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getEntities()).containsOnly(namedEntity);

        namedEntity.setArticles(new HashSet<>());
        assertThat(namedEntity.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getEntities()).doesNotContain(namedEntity);
    }
}
