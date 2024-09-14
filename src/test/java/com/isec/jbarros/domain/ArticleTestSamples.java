package com.isec.jbarros.domain;

import java.util.UUID;

public class ArticleTestSamples {

    public static Article getArticleSample1() {
        return new Article().id("id1").title("title1").summary("summary1");
    }

    public static Article getArticleSample2() {
        return new Article().id("id2").title("title2").summary("summary2");
    }

    public static Article getArticleRandomSampleGenerator() {
        return new Article().id(UUID.randomUUID().toString()).title(UUID.randomUUID().toString()).summary(UUID.randomUUID().toString());
    }
}
