package com.isec.jbarros.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isec.jbarros.IntegrationTest;
import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.repository.NLPModelRepository;
import com.isec.jbarros.service.NLPModelService;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.mapper.NLPModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link NLPModelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NLPModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FRAMEWORK = "AAAAAAAAAA";
    private static final String UPDATED_FRAMEWORK = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nlp-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NLPModelRepository nLPModelRepository;

    @Mock
    private NLPModelRepository nLPModelRepositoryMock;

    @Autowired
    private NLPModelMapper nLPModelMapper;

    @Mock
    private NLPModelService nLPModelServiceMock;

    @Autowired
    private MockMvc restNLPModelMockMvc;

    private NLPModel nLPModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NLPModel createEntity() {
        NLPModel nLPModel = new NLPModel().name(DEFAULT_NAME).framework(DEFAULT_FRAMEWORK).url(DEFAULT_URL).notes(DEFAULT_NOTES);
        return nLPModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NLPModel createUpdatedEntity() {
        NLPModel nLPModel = new NLPModel().name(UPDATED_NAME).framework(UPDATED_FRAMEWORK).url(UPDATED_URL).notes(UPDATED_NOTES);
        return nLPModel;
    }

    @BeforeEach
    public void initTest() {
        nLPModelRepository.deleteAll();
        nLPModel = createEntity();
    }

    @Test
    void createNLPModel() throws Exception {
        int databaseSizeBeforeCreate = nLPModelRepository.findAll().size();
        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);
        restNLPModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nLPModelDTO)))
            .andExpect(status().isCreated());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeCreate + 1);
        NLPModel testNLPModel = nLPModelList.get(nLPModelList.size() - 1);
        assertThat(testNLPModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNLPModel.getFramework()).isEqualTo(DEFAULT_FRAMEWORK);
        assertThat(testNLPModel.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testNLPModel.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    void createNLPModelWithExistingId() throws Exception {
        // Create the NLPModel with an existing ID
        nLPModel.setId("existing_id");
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        int databaseSizeBeforeCreate = nLPModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNLPModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nLPModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nLPModelRepository.findAll().size();
        // set the field null
        nLPModel.setName(null);

        // Create the NLPModel, which fails.
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        restNLPModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nLPModelDTO)))
            .andExpect(status().isBadRequest());

        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllNLPModels() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        // Get all the nLPModelList
        restNLPModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nLPModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].framework").value(hasItem(DEFAULT_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNLPModelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(nLPModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNLPModelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nLPModelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNLPModelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nLPModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNLPModelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nLPModelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getNLPModel() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        // Get the nLPModel
        restNLPModelMockMvc
            .perform(get(ENTITY_API_URL_ID, nLPModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nLPModel.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.framework").value(DEFAULT_FRAMEWORK))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    void getNonExistingNLPModel() throws Exception {
        // Get the nLPModel
        restNLPModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingNLPModel() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();

        // Update the nLPModel
        NLPModel updatedNLPModel = nLPModelRepository.findById(nLPModel.getId()).orElseThrow();
        updatedNLPModel.name(UPDATED_NAME).framework(UPDATED_FRAMEWORK).url(UPDATED_URL).notes(UPDATED_NOTES);
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(updatedNLPModel);

        restNLPModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nLPModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isOk());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
        NLPModel testNLPModel = nLPModelList.get(nLPModelList.size() - 1);
        assertThat(testNLPModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNLPModel.getFramework()).isEqualTo(UPDATED_FRAMEWORK);
        assertThat(testNLPModel.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testNLPModel.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    void putNonExistingNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nLPModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nLPModelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNLPModelWithPatch() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();

        // Update the nLPModel using partial update
        NLPModel partialUpdatedNLPModel = new NLPModel();
        partialUpdatedNLPModel.setId(nLPModel.getId());

        partialUpdatedNLPModel.framework(UPDATED_FRAMEWORK);

        restNLPModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNLPModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNLPModel))
            )
            .andExpect(status().isOk());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
        NLPModel testNLPModel = nLPModelList.get(nLPModelList.size() - 1);
        assertThat(testNLPModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNLPModel.getFramework()).isEqualTo(UPDATED_FRAMEWORK);
        assertThat(testNLPModel.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testNLPModel.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    void fullUpdateNLPModelWithPatch() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();

        // Update the nLPModel using partial update
        NLPModel partialUpdatedNLPModel = new NLPModel();
        partialUpdatedNLPModel.setId(nLPModel.getId());

        partialUpdatedNLPModel.name(UPDATED_NAME).framework(UPDATED_FRAMEWORK).url(UPDATED_URL).notes(UPDATED_NOTES);

        restNLPModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNLPModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNLPModel))
            )
            .andExpect(status().isOk());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
        NLPModel testNLPModel = nLPModelList.get(nLPModelList.size() - 1);
        assertThat(testNLPModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNLPModel.getFramework()).isEqualTo(UPDATED_FRAMEWORK);
        assertThat(testNLPModel.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testNLPModel.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    void patchNonExistingNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nLPModelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNLPModel() throws Exception {
        int databaseSizeBeforeUpdate = nLPModelRepository.findAll().size();
        nLPModel.setId(UUID.randomUUID().toString());

        // Create the NLPModel
        NLPModelDTO nLPModelDTO = nLPModelMapper.toDto(nLPModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNLPModelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nLPModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NLPModel in the database
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNLPModel() throws Exception {
        // Initialize the database
        nLPModelRepository.save(nLPModel);

        int databaseSizeBeforeDelete = nLPModelRepository.findAll().size();

        // Delete the nLPModel
        restNLPModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, nLPModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NLPModel> nLPModelList = nLPModelRepository.findAll();
        assertThat(nLPModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
