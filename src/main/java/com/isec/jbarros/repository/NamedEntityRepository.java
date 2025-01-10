package com.isec.jbarros.repository;

import com.isec.jbarros.domain.NamedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NamedEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NamedEntityRepository extends MongoRepository<NamedEntity, String> {
    Page<NamedEntity> findByUserId(String userId, Pageable pageable);
}
