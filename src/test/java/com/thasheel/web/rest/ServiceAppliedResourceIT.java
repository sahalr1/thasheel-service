package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.ServiceApplied;
import com.thasheel.repository.ServiceAppliedRepository;
import com.thasheel.service.ServiceAppliedService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thasheel.domain.enumeration.SERVICEAPPLIEDSTATUS;
/**
 * Integration tests for the {@link ServiceAppliedResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceAppliedResourceIT {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_SERVICE_ID = 1L;
    private static final Long UPDATED_SERVICE_ID = 2L;

    private static final Long DEFAULT_BRANCH_MANAGER_ID = 1L;
    private static final Long UPDATED_BRANCH_MANAGER_ID = 2L;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REPROCESS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_REPROCESS_DESCRIPTION = "BBBBBBBBBB";

    private static final SERVICEAPPLIEDSTATUS DEFAULT_CURRENT_STATUS = SERVICEAPPLIEDSTATUS.APPLIED;
    private static final SERVICEAPPLIEDSTATUS UPDATED_CURRENT_STATUS = SERVICEAPPLIEDSTATUS.REPROCESS;

    @Autowired
    private ServiceAppliedRepository serviceAppliedRepository;

    @Autowired
    private ServiceAppliedService serviceAppliedService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceAppliedMockMvc;

    private ServiceApplied serviceApplied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceApplied createEntity(EntityManager em) {
        ServiceApplied serviceApplied = new ServiceApplied()
            .customerId(DEFAULT_CUSTOMER_ID)
            .serviceId(DEFAULT_SERVICE_ID)
            .branchManagerId(DEFAULT_BRANCH_MANAGER_ID)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .reprocessDescription(DEFAULT_REPROCESS_DESCRIPTION)
            .currentStatus(DEFAULT_CURRENT_STATUS);
        return serviceApplied;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceApplied createUpdatedEntity(EntityManager em) {
        ServiceApplied serviceApplied = new ServiceApplied()
            .customerId(UPDATED_CUSTOMER_ID)
            .serviceId(UPDATED_SERVICE_ID)
            .branchManagerId(UPDATED_BRANCH_MANAGER_ID)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME)
            .reprocessDescription(UPDATED_REPROCESS_DESCRIPTION)
            .currentStatus(UPDATED_CURRENT_STATUS);
        return serviceApplied;
    }

    @BeforeEach
    public void initTest() {
        serviceApplied = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceApplied() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedRepository.findAll().size();
        // Create the ServiceApplied
        restServiceAppliedMockMvc.perform(post("/api/service-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceApplied)))
            .andExpect(status().isCreated());

        // Validate the ServiceApplied in the database
        List<ServiceApplied> serviceAppliedList = serviceAppliedRepository.findAll();
        assertThat(serviceAppliedList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceApplied testServiceApplied = serviceAppliedList.get(serviceAppliedList.size() - 1);
        assertThat(testServiceApplied.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testServiceApplied.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testServiceApplied.getBranchManagerId()).isEqualTo(DEFAULT_BRANCH_MANAGER_ID);
        assertThat(testServiceApplied.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testServiceApplied.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testServiceApplied.getReprocessDescription()).isEqualTo(DEFAULT_REPROCESS_DESCRIPTION);
        assertThat(testServiceApplied.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void createServiceAppliedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedRepository.findAll().size();

        // Create the ServiceApplied with an existing ID
        serviceApplied.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAppliedMockMvc.perform(post("/api/service-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceApplied)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceApplied in the database
        List<ServiceApplied> serviceAppliedList = serviceAppliedRepository.findAll();
        assertThat(serviceAppliedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceApplieds() throws Exception {
        // Initialize the database
        serviceAppliedRepository.saveAndFlush(serviceApplied);

        // Get all the serviceAppliedList
        restServiceAppliedMockMvc.perform(get("/api/service-applieds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceApplied.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchManagerId").value(hasItem(DEFAULT_BRANCH_MANAGER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].reprocessDescription").value(hasItem(DEFAULT_REPROCESS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceApplied() throws Exception {
        // Initialize the database
        serviceAppliedRepository.saveAndFlush(serviceApplied);

        // Get the serviceApplied
        restServiceAppliedMockMvc.perform(get("/api/service-applieds/{id}", serviceApplied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceApplied.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID.intValue()))
            .andExpect(jsonPath("$.branchManagerId").value(DEFAULT_BRANCH_MANAGER_ID.intValue()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.reprocessDescription").value(DEFAULT_REPROCESS_DESCRIPTION))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceApplied() throws Exception {
        // Get the serviceApplied
        restServiceAppliedMockMvc.perform(get("/api/service-applieds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceApplied() throws Exception {
        // Initialize the database
        serviceAppliedService.save(serviceApplied);

        int databaseSizeBeforeUpdate = serviceAppliedRepository.findAll().size();

        // Update the serviceApplied
        ServiceApplied updatedServiceApplied = serviceAppliedRepository.findById(serviceApplied.getId()).get();
        // Disconnect from session so that the updates on updatedServiceApplied are not directly saved in db
        em.detach(updatedServiceApplied);
        updatedServiceApplied
            .customerId(UPDATED_CUSTOMER_ID)
            .serviceId(UPDATED_SERVICE_ID)
            .branchManagerId(UPDATED_BRANCH_MANAGER_ID)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME)
            .reprocessDescription(UPDATED_REPROCESS_DESCRIPTION)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restServiceAppliedMockMvc.perform(put("/api/service-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceApplied)))
            .andExpect(status().isOk());

        // Validate the ServiceApplied in the database
        List<ServiceApplied> serviceAppliedList = serviceAppliedRepository.findAll();
        assertThat(serviceAppliedList).hasSize(databaseSizeBeforeUpdate);
        ServiceApplied testServiceApplied = serviceAppliedList.get(serviceAppliedList.size() - 1);
        assertThat(testServiceApplied.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testServiceApplied.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testServiceApplied.getBranchManagerId()).isEqualTo(UPDATED_BRANCH_MANAGER_ID);
        assertThat(testServiceApplied.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testServiceApplied.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testServiceApplied.getReprocessDescription()).isEqualTo(UPDATED_REPROCESS_DESCRIPTION);
        assertThat(testServiceApplied.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceApplied() throws Exception {
        int databaseSizeBeforeUpdate = serviceAppliedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAppliedMockMvc.perform(put("/api/service-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceApplied)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceApplied in the database
        List<ServiceApplied> serviceAppliedList = serviceAppliedRepository.findAll();
        assertThat(serviceAppliedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceApplied() throws Exception {
        // Initialize the database
        serviceAppliedService.save(serviceApplied);

        int databaseSizeBeforeDelete = serviceAppliedRepository.findAll().size();

        // Delete the serviceApplied
        restServiceAppliedMockMvc.perform(delete("/api/service-applieds/{id}", serviceApplied.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceApplied> serviceAppliedList = serviceAppliedRepository.findAll();
        assertThat(serviceAppliedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
