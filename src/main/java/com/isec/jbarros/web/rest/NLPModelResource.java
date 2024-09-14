package com.isec.jbarros.web.rest;

import com.isec.jbarros.repository.NLPModelRepository;
import com.isec.jbarros.service.NLPModelService;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.isec.jbarros.domain.NLPModel}.
 */
@RestController
@RequestMapping("/api/nlp-models")
public class NLPModelResource {

    private final Logger log = LoggerFactory.getLogger(NLPModelResource.class);

    private static final String ENTITY_NAME = "nLPModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NLPModelService nLPModelService;

    private final NLPModelRepository nLPModelRepository;

    public NLPModelResource(NLPModelService nLPModelService, NLPModelRepository nLPModelRepository) {
        this.nLPModelService = nLPModelService;
        this.nLPModelRepository = nLPModelRepository;
    }

    /**
     * {@code POST  /nlp-models} : Create a new nLPModel.
     *
     * @param nLPModelDTO the nLPModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nLPModelDTO, or with status {@code 400 (Bad Request)} if the nLPModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NLPModelDTO> createNLPModel(@Valid @RequestBody NLPModelDTO nLPModelDTO) throws URISyntaxException {
        log.debug("REST request to save NLPModel : {}", nLPModelDTO);
        if (nLPModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new nLPModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NLPModelDTO result = nLPModelService.save(nLPModelDTO);
        return ResponseEntity
            .created(new URI("/api/nlp-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /nlp-models/:id} : Updates an existing nLPModel.
     *
     * @param id the id of the nLPModelDTO to save.
     * @param nLPModelDTO the nLPModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nLPModelDTO,
     * or with status {@code 400 (Bad Request)} if the nLPModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nLPModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NLPModelDTO> updateNLPModel(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody NLPModelDTO nLPModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NLPModel : {}, {}", id, nLPModelDTO);
        if (nLPModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nLPModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nLPModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NLPModelDTO result = nLPModelService.update(nLPModelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nLPModelDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /nlp-models/:id} : Partial updates given fields of an existing nLPModel, field will ignore if it is null
     *
     * @param id the id of the nLPModelDTO to save.
     * @param nLPModelDTO the nLPModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nLPModelDTO,
     * or with status {@code 400 (Bad Request)} if the nLPModelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nLPModelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nLPModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NLPModelDTO> partialUpdateNLPModel(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody NLPModelDTO nLPModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NLPModel partially : {}, {}", id, nLPModelDTO);
        if (nLPModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nLPModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nLPModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NLPModelDTO> result = nLPModelService.partialUpdate(nLPModelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nLPModelDTO.getId())
        );
    }

    /**
     * {@code GET  /nlp-models} : get all the nLPModels.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nLPModels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NLPModelDTO>> getAllNLPModels(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of NLPModels");
        Page<NLPModelDTO> page;
        if (eagerload) {
            page = nLPModelService.findAllWithEagerRelationships(pageable);
        } else {
            page = nLPModelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nlp-models/:id} : get the "id" nLPModel.
     *
     * @param id the id of the nLPModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nLPModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NLPModelDTO> getNLPModel(@PathVariable String id) {
        log.debug("REST request to get NLPModel : {}", id);
        Optional<NLPModelDTO> nLPModelDTO = nLPModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nLPModelDTO);
    }

    /**
     * {@code DELETE  /nlp-models/:id} : delete the "id" nLPModel.
     *
     * @param id the id of the nLPModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNLPModel(@PathVariable String id) {
        log.debug("REST request to delete NLPModel : {}", id);
        nLPModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
