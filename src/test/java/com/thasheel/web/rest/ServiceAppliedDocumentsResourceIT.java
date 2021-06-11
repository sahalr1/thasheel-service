package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.ServiceAppliedDocuments;
import com.thasheel.repository.ServiceAppliedDocumentsRepository;
import com.thasheel.service.ServiceAppliedDocumentsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceAppliedDocumentsResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceAppliedDocumentsResourceIT {

    private static final Long DEFAULT_NEEDFUL_SERVICE_DOCMENT_ID = 1L;
    private static final Long UPDATED_NEEDFUL_SERVICE_DOCMENT_ID = 2L;

    private static final byte[] DEFAULT_UPLOAD_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UPLOAD_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UPLOAD_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UPLOAD_DOCUMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private ServiceAppliedDocumentsRepository serviceAppliedDocumentsRepository;

    @Autowired
    private ServiceAppliedDocumentsService serviceAppliedDocumentsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceAppliedDocumentsMockMvc;

    private ServiceAppliedDocuments serviceAppliedDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAppliedDocuments createEntity(EntityManager em) {
        ServiceAppliedDocuments serviceAppliedDocuments = new ServiceAppliedDocuments()
            .needfulServiceDocmentId(DEFAULT_NEEDFUL_SERVICE_DOCMENT_ID)
            .uploadDocument(DEFAULT_UPLOAD_DOCUMENT)
            .uploadDocumentContentType(DEFAULT_UPLOAD_DOCUMENT_CONTENT_TYPE);
        return serviceAppliedDocuments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAppliedDocuments createUpdatedEntity(EntityManager em) {
        ServiceAppliedDocuments serviceAppliedDocuments = new ServiceAppliedDocuments()
            .needfulServiceDocmentId(UPDATED_NEEDFUL_SERVICE_DOCMENT_ID)
            .uploadDocument(UPDATED_UPLOAD_DOCUMENT)
            .uploadDocumentContentType(UPDATED_UPLOAD_DOCUMENT_CONTENT_TYPE);
        return serviceAppliedDocuments;
    }

    @BeforeEach
    public void initTest() {
        serviceAppliedDocuments = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAppliedDocuments() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedDocumentsRepository.findAll().size();
        // Create the ServiceAppliedDocuments
        restServiceAppliedDocumentsMockMvc.perform(post("/api/service-applied-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedDocuments)))
            .andExpect(status().isCreated());

        // Validate the ServiceAppliedDocuments in the database
        List<ServiceAppliedDocuments> serviceAppliedDocumentsList = serviceAppliedDocumentsRepository.findAll();
        assertThat(serviceAppliedDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAppliedDocuments testServiceAppliedDocuments = serviceAppliedDocumentsList.get(serviceAppliedDocumentsList.size() - 1);
        assertThat(testServiceAppliedDocuments.getNeedfulServiceDocmentId()).isEqualTo(DEFAULT_NEEDFUL_SERVICE_DOCMENT_ID);
        assertThat(testServiceAppliedDocuments.getUploadDocument()).isEqualTo(DEFAULT_UPLOAD_DOCUMENT);
        assertThat(testServiceAppliedDocuments.getUploadDocumentContentType()).isEqualTo(DEFAULT_UPLOAD_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createServiceAppliedDocumentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedDocumentsRepository.findAll().size();

        // Create the ServiceAppliedDocuments with an existing ID
        serviceAppliedDocuments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAppliedDocumentsMockMvc.perform(post("/api/service-applied-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAppliedDocuments in the database
        List<ServiceAppliedDocuments> serviceAppliedDocumentsList = serviceAppliedDocumentsRepository.findAll();
        assertThat(serviceAppliedDocumentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceAppliedDocuments() throws Exception {
        // Initialize the database
        serviceAppliedDocumentsRepository.saveAndFlush(serviceAppliedDocuments);

        // Get all the serviceAppliedDocumentsList
        restServiceAppliedDocumentsMockMvc.perform(get("/api/service-applied-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAppliedDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].needfulServiceDocmentId").value(hasItem(DEFAULT_NEEDFUL_SERVICE_DOCMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].uploadDocumentContentType").value(hasItem(DEFAULT_UPLOAD_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD_DOCUMENT))));
    }
    
    @Test
    @Transactional
    public void getServiceAppliedDocuments() throws Exception {
        // Initialize the database
        serviceAppliedDocumentsRepository.saveAndFlush(serviceAppliedDocuments);

        // Get the serviceAppliedDocuments
        restServiceAppliedDocumentsMockMvc.perform(get("/api/service-applied-documents/{id}", serviceAppliedDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAppliedDocuments.getId().intValue()))
            .andExpect(jsonPath("$.needfulServiceDocmentId").value(DEFAULT_NEEDFUL_SERVICE_DOCMENT_ID.intValue()))
            .andExpect(jsonPath("$.uploadDocumentContentType").value(DEFAULT_UPLOAD_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadDocument").value(Base64Utils.encodeToString(DEFAULT_UPLOAD_DOCUMENT)));
    }
    @Test
    @Transactional
    public void getNonExistingServiceAppliedDocuments() throws Exception {
        // Get the serviceAppliedDocuments
        restServiceAppliedDocumentsMockMvc.perform(get("/api/service-applied-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAppliedDocuments() throws Exception {
        // Initialize the database
        serviceAppliedDocumentsService.save(serviceAppliedDocuments);

        int databaseSizeBeforeUpdate = serviceAppliedDocumentsRepository.findAll().size();

        // Update the serviceAppliedDocuments
        ServiceAppliedDocuments updatedServiceAppliedDocuments = serviceAppliedDocumentsRepository.findById(serviceAppliedDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedServiceAppliedDocuments are not directly saved in db
        em.detach(updatedServiceAppliedDocuments);
        updatedServiceAppliedDocuments
            .needfulServiceDocmentId(UPDATED_NEEDFUL_SERVICE_DOCMENT_ID)
            .uploadDocument(UPDATED_UPLOAD_DOCUMENT)
            .uploadDocumentContentType(UPDATED_UPLOAD_DOCUMENT_CONTENT_TYPE);

        restServiceAppliedDocumentsMockMvc.perform(put("/api/service-applied-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceAppliedDocuments)))
            .andExpect(status().isOk());

        // Validate the ServiceAppliedDocuments in the database
        List<ServiceAppliedDocuments> serviceAppliedDocumentsList = serviceAppliedDocumentsRepository.findAll();
        assertThat(serviceAppliedDocumentsList).hasSize(databaseSizeBeforeUpdate);
        ServiceAppliedDocuments testServiceAppliedDocuments = serviceAppliedDocumentsList.get(serviceAppliedDocumentsList.size() - 1);
        assertThat(testServiceAppliedDocuments.getNeedfulServiceDocmentId()).isEqualTo(UPDATED_NEEDFUL_SERVICE_DOCMENT_ID);
        assertThat(testServiceAppliedDocuments.getUploadDocument()).isEqualTo(UPDATED_UPLOAD_DOCUMENT);
        assertThat(testServiceAppliedDocuments.getUploadDocumentContentType()).isEqualTo(UPDATED_UPLOAD_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAppliedDocuments() throws Exception {
        int databaseSizeBeforeUpdate = serviceAppliedDocumentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAppliedDocumentsMockMvc.perform(put("/api/service-applied-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAppliedDocuments in the database
        List<ServiceAppliedDocuments> serviceAppliedDocumentsList = serviceAppliedDocumentsRepository.findAll();
        assertThat(serviceAppliedDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceAppliedDocuments() throws Exception {
        // Initialize the database
        serviceAppliedDocumentsService.save(serviceAppliedDocuments);

        int databaseSizeBeforeDelete = serviceAppliedDocumentsRepository.findAll().size();

        // Delete the serviceAppliedDocuments
        restServiceAppliedDocumentsMockMvc.perform(delete("/api/service-applied-documents/{id}", serviceAppliedDocuments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceAppliedDocuments> serviceAppliedDocumentsList = serviceAppliedDocumentsRepository.findAll();
        assertThat(serviceAppliedDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
