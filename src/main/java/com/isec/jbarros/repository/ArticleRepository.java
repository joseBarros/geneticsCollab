package com.isec.jbarros.repository;

import com.isec.jbarros.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {}
