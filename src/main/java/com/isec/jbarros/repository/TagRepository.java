package com.isec.jbarros.repository;

import com.isec.jbarros.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    Page<Tag> findByUserId(String userId, Pageable pageable);
}
