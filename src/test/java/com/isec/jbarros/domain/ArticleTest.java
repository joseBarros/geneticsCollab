package com.isec.jbarros.domain;

import static com.isec.jbarros.domain.ArticleTestSamples.*;
import static com.isec.jbarros.domain.NLPModelTestSamples.*;
import static com.isec.jbarros.domain.NamedEntityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.isec.jbarros.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = getArticleSample1();
        Article article2 = new Article();
        assertThat(article1).isNotEqualTo(article2);

        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);

        article2 = getArticleSample2();
        assertThat(article1).isNotEqualTo(article2);
    }

    @Test
    void entitiesTest() throws Exception {
        Article article = getArticleRandomSampleGenerator();
        NamedEntity namedEntityBack = getNamedEntityRandomSampleGenerator();

        article.addEntities(namedEntityBack);
        assertThat(article.getEntities()).containsOnly(namedEntityBack);

        article.removeEntities(namedEntityBack);
        assertThat(article.getEntities()).doesNotContain(namedEntityBack);

        article.entities(new HashSet<>(Set.of(namedEntityBack)));
        assertThat(article.getEntities()).containsOnly(namedEntityBack);

        article.setEntities(new HashSet<>());
        assertThat(article.getEntities()).doesNotContain(namedEntityBack);
    }

    @Test
    void modelTest() throws Exception {
        Article article = getArticleRandomSampleGenerator();
        NLPModel nLPModelBack = getNLPModelRandomSampleGenerator();

        article.setModel(nLPModelBack);
        assertThat(article.getModel()).isEqualTo(nLPModelBack);

        article.model(null);
        assertThat(article.getModel()).isNull();
    }
}
