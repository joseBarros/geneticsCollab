package com.isec.jbarros.repository;

import com.isec.jbarros.domain.NLPModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NLPModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NLPModelRepository extends MongoRepository<NLPModel, String> {}
