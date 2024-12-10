package com.isec.jbarros.service;

import com.isec.jbarros.service.dto.NLPModelDTO;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

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

//    @Async
//    Future processNLPModelAsync(NLPModelDTO nLPModelDTO, MultipartFile file);
//
//    NLPModelDTO uploadNLPModelFile(NLPModelDTO nLPModelDTO, MultipartFile file);
//
//    void extractZipFile(String zipFilePath, String destDir) throws IOException;
}
