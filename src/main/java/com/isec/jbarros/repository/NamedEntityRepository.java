package com.isec.jbarros.repository;

import com.isec.jbarros.domain.NamedEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NamedEntity entity.
 */
@Repository
public interface NamedEntityRepository extends MongoRepository<NamedEntity, String> {
    @Query("{}")
    Page<NamedEntity> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<NamedEntity> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<NamedEntity> findOneWithEagerRelationships(String id);
}
