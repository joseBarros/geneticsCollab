package com.isec.jbarros.repository;

import com.isec.jbarros.domain.NLPModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NLPModel entity.
 */
@Repository
public interface NLPModelRepository extends MongoRepository<NLPModel, String> {
    @Query("{}")
    Page<NLPModel> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<NLPModel> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<NLPModel> findOneWithEagerRelationships(String id);
}
