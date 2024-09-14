package com.isec.jbarros.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ArticleMapperTest {

    private ArticleMapper articleMapper;

    @BeforeEach
    public void setUp() {
        articleMapper = new ArticleMapperImpl();
    }
}
