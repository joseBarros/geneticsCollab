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
    void namedEntitiesTest() throws Exception {
        Article article = getArticleRandomSampleGenerator();
        NamedEntity namedEntityBack = getNamedEntityRandomSampleGenerator();

        article.addNamedEntities(namedEntityBack);
        assertThat(article.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getArticle()).isEqualTo(article);

        article.removeNamedEntities(namedEntityBack);
        assertThat(article.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getArticle()).isNull();

        article.namedEntities(new HashSet<>(Set.of(namedEntityBack)));
        assertThat(article.getNamedEntities()).containsOnly(namedEntityBack);
        assertThat(namedEntityBack.getArticle()).isEqualTo(article);

        article.setNamedEntities(new HashSet<>());
        assertThat(article.getNamedEntities()).doesNotContain(namedEntityBack);
        assertThat(namedEntityBack.getArticle()).isNull();
    }

    @Test
    void nlpModelTest() throws Exception {
        Article article = getArticleRandomSampleGenerator();
        NLPModel nLPModelBack = getNLPModelRandomSampleGenerator();

        article.setNlpModel(nLPModelBack);
        assertThat(article.getNlpModel()).isEqualTo(nLPModelBack);

        article.nlpModel(null);
        assertThat(article.getNlpModel()).isNull();
    }
}
