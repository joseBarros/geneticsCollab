package com.isec.jbarros.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isec.jbarros.IntegrationTest;
import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.repository.NamedEntityRepository;
import com.isec.jbarros.service.NamedEntityService;
import com.isec.jbarros.service.dto.NamedEntityDTO;
import com.isec.jbarros.service.mapper.NamedEntityMapper;
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
 * Integration tests for the {@link NamedEntityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NamedEntityResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_START_CHAR = "AAAAAAAAAA";
    private static final String UPDATED_START_CHAR = "BBBBBBBBBB";

    private static final String DEFAULT_END_CHAR = "AAAAAAAAAA";
    private static final String UPDATED_END_CHAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/named-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NamedEntityRepository namedEntityRepository;

    @Mock
    private NamedEntityRepository namedEntityRepositoryMock;

    @Autowired
    private NamedEntityMapper namedEntityMapper;

    @Mock
    private NamedEntityService namedEntityServiceMock;

    @Autowired
    private MockMvc restNamedEntityMockMvc;

    private NamedEntity namedEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NamedEntity createEntity() {
        NamedEntity namedEntity = new NamedEntity().text(DEFAULT_TEXT).startChar(DEFAULT_START_CHAR).endChar(DEFAULT_END_CHAR);
        return namedEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NamedEntity createUpdatedEntity() {
        NamedEntity namedEntity = new NamedEntity().text(UPDATED_TEXT).startChar(UPDATED_START_CHAR).endChar(UPDATED_END_CHAR);
        return namedEntity;
    }

    @BeforeEach
    public void initTest() {
        namedEntityRepository.deleteAll();
        namedEntity = createEntity();
    }

    @Test
    void createNamedEntity() throws Exception {
        int databaseSizeBeforeCreate = namedEntityRepository.findAll().size();
        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);
        restNamedEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeCreate + 1);
        NamedEntity testNamedEntity = namedEntityList.get(namedEntityList.size() - 1);
        assertThat(testNamedEntity.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testNamedEntity.getStartChar()).isEqualTo(DEFAULT_START_CHAR);
        assertThat(testNamedEntity.getEndChar()).isEqualTo(DEFAULT_END_CHAR);
    }

    @Test
    void createNamedEntityWithExistingId() throws Exception {
        // Create the NamedEntity with an existing ID
        namedEntity.setId("existing_id");
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        int databaseSizeBeforeCreate = namedEntityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNamedEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = namedEntityRepository.findAll().size();
        // set the field null
        namedEntity.setText(null);

        // Create the NamedEntity, which fails.
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        restNamedEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllNamedEntities() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        // Get all the namedEntityList
        restNamedEntityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(namedEntity.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].startChar").value(hasItem(DEFAULT_START_CHAR)))
            .andExpect(jsonPath("$.[*].endChar").value(hasItem(DEFAULT_END_CHAR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNamedEntitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(namedEntityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNamedEntityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(namedEntityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNamedEntitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(namedEntityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNamedEntityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(namedEntityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getNamedEntity() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        // Get the namedEntity
        restNamedEntityMockMvc
            .perform(get(ENTITY_API_URL_ID, namedEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(namedEntity.getId()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.startChar").value(DEFAULT_START_CHAR))
            .andExpect(jsonPath("$.endChar").value(DEFAULT_END_CHAR));
    }

    @Test
    void getNonExistingNamedEntity() throws Exception {
        // Get the namedEntity
        restNamedEntityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingNamedEntity() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();

        // Update the namedEntity
        NamedEntity updatedNamedEntity = namedEntityRepository.findById(namedEntity.getId()).orElseThrow();
        updatedNamedEntity.text(UPDATED_TEXT).startChar(UPDATED_START_CHAR).endChar(UPDATED_END_CHAR);
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(updatedNamedEntity);

        restNamedEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, namedEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
        NamedEntity testNamedEntity = namedEntityList.get(namedEntityList.size() - 1);
        assertThat(testNamedEntity.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testNamedEntity.getStartChar()).isEqualTo(UPDATED_START_CHAR);
        assertThat(testNamedEntity.getEndChar()).isEqualTo(UPDATED_END_CHAR);
    }

    @Test
    void putNonExistingNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, namedEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(namedEntityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNamedEntityWithPatch() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();

        // Update the namedEntity using partial update
        NamedEntity partialUpdatedNamedEntity = new NamedEntity();
        partialUpdatedNamedEntity.setId(namedEntity.getId());

        partialUpdatedNamedEntity.text(UPDATED_TEXT).endChar(UPDATED_END_CHAR);

        restNamedEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNamedEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNamedEntity))
            )
            .andExpect(status().isOk());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
        NamedEntity testNamedEntity = namedEntityList.get(namedEntityList.size() - 1);
        assertThat(testNamedEntity.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testNamedEntity.getStartChar()).isEqualTo(DEFAULT_START_CHAR);
        assertThat(testNamedEntity.getEndChar()).isEqualTo(UPDATED_END_CHAR);
    }

    @Test
    void fullUpdateNamedEntityWithPatch() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();

        // Update the namedEntity using partial update
        NamedEntity partialUpdatedNamedEntity = new NamedEntity();
        partialUpdatedNamedEntity.setId(namedEntity.getId());

        partialUpdatedNamedEntity.text(UPDATED_TEXT).startChar(UPDATED_START_CHAR).endChar(UPDATED_END_CHAR);

        restNamedEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNamedEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNamedEntity))
            )
            .andExpect(status().isOk());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
        NamedEntity testNamedEntity = namedEntityList.get(namedEntityList.size() - 1);
        assertThat(testNamedEntity.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testNamedEntity.getStartChar()).isEqualTo(UPDATED_START_CHAR);
        assertThat(testNamedEntity.getEndChar()).isEqualTo(UPDATED_END_CHAR);
    }

    @Test
    void patchNonExistingNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, namedEntityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNamedEntity() throws Exception {
        int databaseSizeBeforeUpdate = namedEntityRepository.findAll().size();
        namedEntity.setId(UUID.randomUUID().toString());

        // Create the NamedEntity
        NamedEntityDTO namedEntityDTO = namedEntityMapper.toDto(namedEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNamedEntityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(namedEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NamedEntity in the database
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNamedEntity() throws Exception {
        // Initialize the database
        namedEntityRepository.save(namedEntity);

        int databaseSizeBeforeDelete = namedEntityRepository.findAll().size();

        // Delete the namedEntity
        restNamedEntityMockMvc
            .perform(delete(ENTITY_API_URL_ID, namedEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NamedEntity> namedEntityList = namedEntityRepository.findAll();
        assertThat(namedEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
