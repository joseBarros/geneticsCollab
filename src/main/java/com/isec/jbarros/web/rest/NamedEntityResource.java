package com.isec.jbarros.web.rest;

import com.isec.jbarros.repository.NamedEntityRepository;
import com.isec.jbarros.service.NamedEntityService;
import com.isec.jbarros.service.dto.NamedEntityDTO;
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
 * REST controller for managing {@link com.isec.jbarros.domain.NamedEntity}.
 */
@RestController
@RequestMapping("/api/named-entities")
public class NamedEntityResource {

    private final Logger log = LoggerFactory.getLogger(NamedEntityResource.class);

    private static final String ENTITY_NAME = "namedEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NamedEntityService namedEntityService;

    private final NamedEntityRepository namedEntityRepository;

    public NamedEntityResource(NamedEntityService namedEntityService, NamedEntityRepository namedEntityRepository) {
        this.namedEntityService = namedEntityService;
        this.namedEntityRepository = namedEntityRepository;
    }

    /**
     * {@code POST  /named-entities} : Create a new namedEntity.
     *
     * @param namedEntityDTO the namedEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new namedEntityDTO, or with status {@code 400 (Bad Request)} if the namedEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NamedEntityDTO> createNamedEntity(@Valid @RequestBody NamedEntityDTO namedEntityDTO) throws URISyntaxException {
        log.debug("REST request to save NamedEntity : {}", namedEntityDTO);
        if (namedEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new namedEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NamedEntityDTO result = namedEntityService.save(namedEntityDTO);
        return ResponseEntity
            .created(new URI("/api/named-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /named-entities/:id} : Updates an existing namedEntity.
     *
     * @param id the id of the namedEntityDTO to save.
     * @param namedEntityDTO the namedEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated namedEntityDTO,
     * or with status {@code 400 (Bad Request)} if the namedEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the namedEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NamedEntityDTO> updateNamedEntity(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody NamedEntityDTO namedEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NamedEntity : {}, {}", id, namedEntityDTO);
        if (namedEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, namedEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!namedEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NamedEntityDTO result = namedEntityService.update(namedEntityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, namedEntityDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /named-entities/:id} : Partial updates given fields of an existing namedEntity, field will ignore if it is null
     *
     * @param id the id of the namedEntityDTO to save.
     * @param namedEntityDTO the namedEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated namedEntityDTO,
     * or with status {@code 400 (Bad Request)} if the namedEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the namedEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the namedEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NamedEntityDTO> partialUpdateNamedEntity(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody NamedEntityDTO namedEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NamedEntity partially : {}, {}", id, namedEntityDTO);
        if (namedEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, namedEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!namedEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NamedEntityDTO> result = namedEntityService.partialUpdate(namedEntityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, namedEntityDTO.getId())
        );
    }

    /**
     * {@code GET  /named-entities} : get all the namedEntities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of namedEntities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NamedEntityDTO>> getAllNamedEntities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of NamedEntities");
        Page<NamedEntityDTO> page;
        if (eagerload) {
            page = namedEntityService.findAllWithEagerRelationships(pageable);
        } else {
            page = namedEntityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /named-entities/:id} : get the "id" namedEntity.
     *
     * @param id the id of the namedEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the namedEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NamedEntityDTO> getNamedEntity(@PathVariable String id) {
        log.debug("REST request to get NamedEntity : {}", id);
        Optional<NamedEntityDTO> namedEntityDTO = namedEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(namedEntityDTO);
    }

    /**
     * {@code DELETE  /named-entities/:id} : delete the "id" namedEntity.
     *
     * @param id the id of the namedEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNamedEntity(@PathVariable String id) {
        log.debug("REST request to delete NamedEntity : {}", id);
        namedEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
