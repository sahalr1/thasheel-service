package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.repository.NeedfulServiceDocumentsRepository;
import com.thasheel.service.NeedfulServiceDocumentsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thasheel.domain.enumeration.FILETYPE;
/**
 * Integration tests for the {@link NeedfulServiceDocumentsResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NeedfulServiceDocumentsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FILETYPE DEFAULT_FILE_TYPE = FILETYPE.IMAGE;
    private static final FILETYPE UPDATED_FILE_TYPE = FILETYPE.IMAGE;

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    @Autowired
    private NeedfulServiceDocumentsRepository needfulServiceDocumentsRepository;

    @Autowired
    private NeedfulServiceDocumentsService needfulServiceDocumentsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNeedfulServiceDocumentsMockMvc;

    private NeedfulServiceDocuments needfulServiceDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NeedfulServiceDocuments createEntity(EntityManager em) {
        NeedfulServiceDocuments needfulServiceDocuments = new NeedfulServiceDocuments()
            .name(DEFAULT_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .isRequired(DEFAULT_IS_REQUIRED);
        return needfulServiceDocuments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NeedfulServiceDocuments createUpdatedEntity(EntityManager em) {
        NeedfulServiceDocuments needfulServiceDocuments = new NeedfulServiceDocuments()
            .name(UPDATED_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .isRequired(UPDATED_IS_REQUIRED);
        return needfulServiceDocuments;
    }

    @BeforeEach
    public void initTest() {
        needfulServiceDocuments = createEntity(em);
    }

    @Test
    @Transactional
    public void createNeedfulServiceDocuments() throws Exception {
        int databaseSizeBeforeCreate = needfulServiceDocumentsRepository.findAll().size();
        // Create the NeedfulServiceDocuments
        restNeedfulServiceDocumentsMockMvc.perform(post("/api/needful-service-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needfulServiceDocuments)))
            .andExpect(status().isCreated());

        // Validate the NeedfulServiceDocuments in the database
        List<NeedfulServiceDocuments> needfulServiceDocumentsList = needfulServiceDocumentsRepository.findAll();
        assertThat(needfulServiceDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        NeedfulServiceDocuments testNeedfulServiceDocuments = needfulServiceDocumentsList.get(needfulServiceDocumentsList.size() - 1);
        assertThat(testNeedfulServiceDocuments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNeedfulServiceDocuments.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testNeedfulServiceDocuments.isIsRequired()).isEqualTo(DEFAULT_IS_REQUIRED);
    }

    @Test
    @Transactional
    public void createNeedfulServiceDocumentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = needfulServiceDocumentsRepository.findAll().size();

        // Create the NeedfulServiceDocuments with an existing ID
        needfulServiceDocuments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNeedfulServiceDocumentsMockMvc.perform(post("/api/needful-service-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needfulServiceDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the NeedfulServiceDocuments in the database
        List<NeedfulServiceDocuments> needfulServiceDocumentsList = needfulServiceDocumentsRepository.findAll();
        assertThat(needfulServiceDocumentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNeedfulServiceDocuments() throws Exception {
        // Initialize the database
        needfulServiceDocumentsRepository.saveAndFlush(needfulServiceDocuments);

        // Get all the needfulServiceDocumentsList
        restNeedfulServiceDocumentsMockMvc.perform(get("/api/needful-service-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(needfulServiceDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNeedfulServiceDocuments() throws Exception {
        // Initialize the database
        needfulServiceDocumentsRepository.saveAndFlush(needfulServiceDocuments);

        // Get the needfulServiceDocuments
        restNeedfulServiceDocumentsMockMvc.perform(get("/api/needful-service-documents/{id}", needfulServiceDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(needfulServiceDocuments.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNeedfulServiceDocuments() throws Exception {
        // Get the needfulServiceDocuments
        restNeedfulServiceDocumentsMockMvc.perform(get("/api/needful-service-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNeedfulServiceDocuments() throws Exception {
        // Initialize the database
        needfulServiceDocumentsService.save(needfulServiceDocuments);

        int databaseSizeBeforeUpdate = needfulServiceDocumentsRepository.findAll().size();

        // Update the needfulServiceDocuments
        NeedfulServiceDocuments updatedNeedfulServiceDocuments = needfulServiceDocumentsRepository.findById(needfulServiceDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedNeedfulServiceDocuments are not directly saved in db
        em.detach(updatedNeedfulServiceDocuments);
        updatedNeedfulServiceDocuments
            .name(UPDATED_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .isRequired(UPDATED_IS_REQUIRED);

        restNeedfulServiceDocumentsMockMvc.perform(put("/api/needful-service-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNeedfulServiceDocuments)))
            .andExpect(status().isOk());

        // Validate the NeedfulServiceDocuments in the database
        List<NeedfulServiceDocuments> needfulServiceDocumentsList = needfulServiceDocumentsRepository.findAll();
        assertThat(needfulServiceDocumentsList).hasSize(databaseSizeBeforeUpdate);
        NeedfulServiceDocuments testNeedfulServiceDocuments = needfulServiceDocumentsList.get(needfulServiceDocumentsList.size() - 1);
        assertThat(testNeedfulServiceDocuments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNeedfulServiceDocuments.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testNeedfulServiceDocuments.isIsRequired()).isEqualTo(UPDATED_IS_REQUIRED);
    }

    @Test
    @Transactional
    public void updateNonExistingNeedfulServiceDocuments() throws Exception {
        int databaseSizeBeforeUpdate = needfulServiceDocumentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNeedfulServiceDocumentsMockMvc.perform(put("/api/needful-service-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needfulServiceDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the NeedfulServiceDocuments in the database
        List<NeedfulServiceDocuments> needfulServiceDocumentsList = needfulServiceDocumentsRepository.findAll();
        assertThat(needfulServiceDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNeedfulServiceDocuments() throws Exception {
        // Initialize the database
        needfulServiceDocumentsService.save(needfulServiceDocuments);

        int databaseSizeBeforeDelete = needfulServiceDocumentsRepository.findAll().size();

        // Delete the needfulServiceDocuments
        restNeedfulServiceDocumentsMockMvc.perform(delete("/api/needful-service-documents/{id}", needfulServiceDocuments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NeedfulServiceDocuments> needfulServiceDocumentsList = needfulServiceDocumentsRepository.findAll();
        assertThat(needfulServiceDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
