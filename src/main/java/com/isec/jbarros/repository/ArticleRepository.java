package com.isec.jbarros.repository;

import com.isec.jbarros.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Article entity.
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    @Query("{}")
    Page<Article> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Article> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Article> findOneWithEagerRelationships(String id);
}
