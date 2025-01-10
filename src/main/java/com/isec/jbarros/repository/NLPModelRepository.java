package com.isec.jbarros.repository;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data MongoDB repository for the NLPModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NLPModelRepository extends MongoRepository<NLPModel, String> {
    Page<NLPModel> findByUserId(String userId, Pageable pageable);

    // Used to find user own models and second role like admin
    Page<NLPModel> findByUserIdOrUserId(String userId, String adminId, Pageable pageable);

    List<NLPModel> findAllByTagsContaining(Set<Tag> tags);
}
