package com.isec.jbarros.service;

import com.isec.jbarros.service.dto.NLPModelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isec.jbarros.domain.NLPModel}.
 */
public interface NLPModelService {
    /**
     * Save a nLPModel.
     *
     * @param nLPModelDTO the entity to save.
     * @return the persisted entity.
     */
    NLPModelDTO save(NLPModelDTO nLPModelDTO);

    /**
     * Updates a nLPModel.
     *
     * @param nLPModelDTO the entity to update.
     * @return the persisted entity.
     */
    NLPModelDTO update(NLPModelDTO nLPModelDTO);

    /**
     * Partially updates a nLPModel.
     *
     * @param nLPModelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NLPModelDTO> partialUpdate(NLPModelDTO nLPModelDTO);

    /**
     * Get all the nLPModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NLPModelDTO> findAll(Pageable pageable);

    /**
     * Get all the nLPModels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NLPModelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" nLPModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NLPModelDTO> findOne(String id);

    /**
     * Delete the "id" nLPModel.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
