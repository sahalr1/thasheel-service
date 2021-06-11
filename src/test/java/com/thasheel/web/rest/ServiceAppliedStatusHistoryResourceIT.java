package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.ServiceAppliedStatusHistory;
import com.thasheel.repository.ServiceAppliedStatusHistoryRepository;
import com.thasheel.service.ServiceAppliedStatusHistoryService;

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
 * Integration tests for the {@link ServiceAppliedStatusHistoryResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceAppliedStatusHistoryResourceIT {

    private static final SERVICEAPPLIEDSTATUS DEFAULT_STATUS = SERVICEAPPLIEDSTATUS.APPLIED;
    private static final SERVICEAPPLIEDSTATUS UPDATED_STATUS = SERVICEAPPLIEDSTATUS.REPROCESS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DONE_BRANCH_MANAGER_ID = "BBBBBBBBBB";

    @Autowired
    private ServiceAppliedStatusHistoryRepository serviceAppliedStatusHistoryRepository;

    @Autowired
    private ServiceAppliedStatusHistoryService serviceAppliedStatusHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceAppliedStatusHistoryMockMvc;

    private ServiceAppliedStatusHistory serviceAppliedStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAppliedStatusHistory createEntity(EntityManager em) {
        ServiceAppliedStatusHistory serviceAppliedStatusHistory = new ServiceAppliedStatusHistory()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .actionDoneBranchManagerId(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID);
        return serviceAppliedStatusHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAppliedStatusHistory createUpdatedEntity(EntityManager em) {
        ServiceAppliedStatusHistory serviceAppliedStatusHistory = new ServiceAppliedStatusHistory()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .updatedTime(UPDATED_UPDATED_TIME)
            .actionDoneBranchManagerId(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);
        return serviceAppliedStatusHistory;
    }

    @BeforeEach
    public void initTest() {
        serviceAppliedStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAppliedStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedStatusHistoryRepository.findAll().size();
        // Create the ServiceAppliedStatusHistory
        restServiceAppliedStatusHistoryMockMvc.perform(post("/api/service-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedStatusHistory)))
            .andExpect(status().isCreated());

        // Validate the ServiceAppliedStatusHistory in the database
        List<ServiceAppliedStatusHistory> serviceAppliedStatusHistoryList = serviceAppliedStatusHistoryRepository.findAll();
        assertThat(serviceAppliedStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAppliedStatusHistory testServiceAppliedStatusHistory = serviceAppliedStatusHistoryList.get(serviceAppliedStatusHistoryList.size() - 1);
        assertThat(testServiceAppliedStatusHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServiceAppliedStatusHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceAppliedStatusHistory.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testServiceAppliedStatusHistory.getActionDoneBranchManagerId()).isEqualTo(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID);
    }

    @Test
    @Transactional
    public void createServiceAppliedStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAppliedStatusHistoryRepository.findAll().size();

        // Create the ServiceAppliedStatusHistory with an existing ID
        serviceAppliedStatusHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAppliedStatusHistoryMockMvc.perform(post("/api/service-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAppliedStatusHistory in the database
        List<ServiceAppliedStatusHistory> serviceAppliedStatusHistoryList = serviceAppliedStatusHistoryRepository.findAll();
        assertThat(serviceAppliedStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceAppliedStatusHistories() throws Exception {
        // Initialize the database
        serviceAppliedStatusHistoryRepository.saveAndFlush(serviceAppliedStatusHistory);

        // Get all the serviceAppliedStatusHistoryList
        restServiceAppliedStatusHistoryMockMvc.perform(get("/api/service-applied-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAppliedStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].actionDoneBranchManagerId").value(hasItem(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID)));
    }
    
    @Test
    @Transactional
    public void getServiceAppliedStatusHistory() throws Exception {
        // Initialize the database
        serviceAppliedStatusHistoryRepository.saveAndFlush(serviceAppliedStatusHistory);

        // Get the serviceAppliedStatusHistory
        restServiceAppliedStatusHistoryMockMvc.perform(get("/api/service-applied-status-histories/{id}", serviceAppliedStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAppliedStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.actionDoneBranchManagerId").value(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingServiceAppliedStatusHistory() throws Exception {
        // Get the serviceAppliedStatusHistory
        restServiceAppliedStatusHistoryMockMvc.perform(get("/api/service-applied-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAppliedStatusHistory() throws Exception {
        // Initialize the database
        serviceAppliedStatusHistoryService.save(serviceAppliedStatusHistory);

        int databaseSizeBeforeUpdate = serviceAppliedStatusHistoryRepository.findAll().size();

        // Update the serviceAppliedStatusHistory
        ServiceAppliedStatusHistory updatedServiceAppliedStatusHistory = serviceAppliedStatusHistoryRepository.findById(serviceAppliedStatusHistory.getId()).get();
        // Disconnect from session so that the updates on updatedServiceAppliedStatusHistory are not directly saved in db
        em.detach(updatedServiceAppliedStatusHistory);
        updatedServiceAppliedStatusHistory
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .updatedTime(UPDATED_UPDATED_TIME)
            .actionDoneBranchManagerId(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);

        restServiceAppliedStatusHistoryMockMvc.perform(put("/api/service-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceAppliedStatusHistory)))
            .andExpect(status().isOk());

        // Validate the ServiceAppliedStatusHistory in the database
        List<ServiceAppliedStatusHistory> serviceAppliedStatusHistoryList = serviceAppliedStatusHistoryRepository.findAll();
        assertThat(serviceAppliedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        ServiceAppliedStatusHistory testServiceAppliedStatusHistory = serviceAppliedStatusHistoryList.get(serviceAppliedStatusHistoryList.size() - 1);
        assertThat(testServiceAppliedStatusHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServiceAppliedStatusHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceAppliedStatusHistory.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testServiceAppliedStatusHistory.getActionDoneBranchManagerId()).isEqualTo(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAppliedStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = serviceAppliedStatusHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAppliedStatusHistoryMockMvc.perform(put("/api/service-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAppliedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAppliedStatusHistory in the database
        List<ServiceAppliedStatusHistory> serviceAppliedStatusHistoryList = serviceAppliedStatusHistoryRepository.findAll();
        assertThat(serviceAppliedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceAppliedStatusHistory() throws Exception {
        // Initialize the database
        serviceAppliedStatusHistoryService.save(serviceAppliedStatusHistory);

        int databaseSizeBeforeDelete = serviceAppliedStatusHistoryRepository.findAll().size();

        // Delete the serviceAppliedStatusHistory
        restServiceAppliedStatusHistoryMockMvc.perform(delete("/api/service-applied-status-histories/{id}", serviceAppliedStatusHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceAppliedStatusHistory> serviceAppliedStatusHistoryList = serviceAppliedStatusHistoryRepository.findAll();
        assertThat(serviceAppliedStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
