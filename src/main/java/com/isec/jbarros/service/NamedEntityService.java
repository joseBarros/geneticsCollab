package com.isec.jbarros.service;

import com.isec.jbarros.service.dto.NamedEntityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isec.jbarros.domain.NamedEntity}.
 */
public interface NamedEntityService {
    /**
     * Save a namedEntity.
     *
     * @param namedEntityDTO the entity to save.
     * @return the persisted entity.
     */
    NamedEntityDTO save(NamedEntityDTO namedEntityDTO);

    /**
     * Updates a namedEntity.
     *
     * @param namedEntityDTO the entity to update.
     * @return the persisted entity.
     */
    NamedEntityDTO update(NamedEntityDTO namedEntityDTO);

    /**
     * Partially updates a namedEntity.
     *
     * @param namedEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NamedEntityDTO> partialUpdate(NamedEntityDTO namedEntityDTO);

    /**
     * Get all the namedEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NamedEntityDTO> findAll(Pageable pageable);

    /**
     * Get all the namedEntities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NamedEntityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" namedEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NamedEntityDTO> findOne(String id);

    /**
     * Delete the "id" namedEntity.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
