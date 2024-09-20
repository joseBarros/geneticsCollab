package com.isec.jbarros.web.rest;

import com.isec.jbarros.repository.ArticleRepository;
import com.isec.jbarros.service.ArticleService;
import com.isec.jbarros.service.NLPService;
import com.isec.jbarros.service.PDFService;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.isec.jbarros.domain.Article}.
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleResource {

    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private static final String ENTITY_NAME = "article";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleService articleService;

    private final ArticleRepository articleRepository;

    private final NLPService nlpService;

    private final PDFService pdfService;

    public ArticleResource(ArticleService articleService, ArticleRepository articleRepository, NLPService nlpService, PDFService pdfService) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
        this.nlpService = nlpService;
        this.pdfService = pdfService;
    }

    /**
     * {@code POST  /articles} : Create a new article.
     *
     * @param articleDTO the articleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleDTO, or with status {@code 400 (Bad Request)} if the article has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody ArticleDTO articleDTO) throws URISyntaxException {
        log.debug("REST request to save Article : {}", articleDTO);
        if (articleDTO.getId() != null) {
            throw new BadRequestAlertException("A new article cannot already have an ID", ENTITY_NAME, "idexists");
        }
        extractText(articleDTO);
        ArticleDTO result = articleService.save(articleDTO);
        extractEntities(result);
        return ResponseEntity
            .created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    private void extractEntities(ArticleDTO result) {
        //Call NLP service responsible for NER
        if(result.getText()!=null && !result.getText().isEmpty() && result.getModel() != null && result.getModel().getId()!=null){
            nlpService.processText(result.getId(), result.getText(), result.getModel().getId());
        }
    }

    private void extractText(ArticleDTO articleDTO) {
        //Extract text from PDF
        try {
            if(articleDTO.getFile() != null) {
                String extractedText = pdfService.extractTextFromPDF(articleDTO.getFile());
                if(extractedText != null) {
                    articleDTO.setText(extractedText);
                }
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("Error extracting article text from file", ENTITY_NAME, "filerror");
        }
    }

    /**
     * {@code PUT  /articles/:id} : Updates an existing article.
     *
     * @param id the id of the articleDTO to save.
     * @param articleDTO the articleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleDTO,
     * or with status {@code 400 (Bad Request)} if the articleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ArticleDTO articleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Article : {}, {}", id, articleDTO);
        if (articleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        extractText(articleDTO);
        ArticleDTO result = articleService.update(articleDTO);
        extractEntities(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /articles/:id} : Partial updates given fields of an existing article, field will ignore if it is null
     *
     * @param id the id of the articleDTO to save.
     * @param articleDTO the articleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleDTO,
     * or with status {@code 400 (Bad Request)} if the articleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the articleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleDTO> partialUpdateArticle(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ArticleDTO articleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Article partially : {}, {}", id, articleDTO);
        if (articleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleDTO> result = articleService.partialUpdate(articleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleDTO.getId())
        );
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleDTO>> getAllArticles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Articles");
        Page<ArticleDTO> page;
        if (eagerload) {
            page = articleService.findAllWithEagerRelationships(pageable);
        } else {
            page = articleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /articles/:id} : get the "id" article.
     *
     * @param id the id of the articleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable String id) {
        log.debug("REST request to get Article : {}", id);
        Optional<ArticleDTO> articleDTO = articleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleDTO);
    }

    /**
     * {@code DELETE  /articles/:id} : delete the "id" article.
     *
     * @param id the id of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String id) {
        log.debug("REST request to delete Article : {}", id);
        articleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @PostMapping("/process-text")
    public ResponseEntity<Map<String, Object>> processText(@RequestBody Map<String, String> request) {
        String articleId = request.get("article_id");
        String text = request.get("text");
        String modelId = request.get("model_id");

        Map<String, Object> result = nlpService.processText(articleId, text, modelId);

        return ResponseEntity.ok(result);
    }
}
