package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.ThasheelService;
import com.thasheel.repository.ThasheelServiceRepository;
import com.thasheel.service.ThasheelServiceService;

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

/**
 * Integration tests for the {@link ThasheelServiceResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ThasheelServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_MAJOR_ADMIN_ID = 1L;
    private static final Long UPDATED_CREATED_MAJOR_ADMIN_ID = 2L;

    @Autowired
    private ThasheelServiceRepository thasheelServiceRepository;

    @Autowired
    private ThasheelServiceService thasheelServiceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThasheelServiceMockMvc;

    private ThasheelService thasheelService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThasheelService createEntity(EntityManager em) {
        ThasheelService thasheelService = new ThasheelService()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .createdMajorAdminId(DEFAULT_CREATED_MAJOR_ADMIN_ID);
        return thasheelService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThasheelService createUpdatedEntity(EntityManager em) {
        ThasheelService thasheelService = new ThasheelService()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdMajorAdminId(UPDATED_CREATED_MAJOR_ADMIN_ID);
        return thasheelService;
    }

    @BeforeEach
    public void initTest() {
        thasheelService = createEntity(em);
    }

    @Test
    @Transactional
    public void createThasheelService() throws Exception {
        int databaseSizeBeforeCreate = thasheelServiceRepository.findAll().size();
        // Create the ThasheelService
        restThasheelServiceMockMvc.perform(post("/api/thasheel-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thasheelService)))
            .andExpect(status().isCreated());

        // Validate the ThasheelService in the database
        List<ThasheelService> thasheelServiceList = thasheelServiceRepository.findAll();
        assertThat(thasheelServiceList).hasSize(databaseSizeBeforeCreate + 1);
        ThasheelService testThasheelService = thasheelServiceList.get(thasheelServiceList.size() - 1);
        assertThat(testThasheelService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThasheelService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testThasheelService.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testThasheelService.getCreatedMajorAdminId()).isEqualTo(DEFAULT_CREATED_MAJOR_ADMIN_ID);
    }

    @Test
    @Transactional
    public void createThasheelServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thasheelServiceRepository.findAll().size();

        // Create the ThasheelService with an existing ID
        thasheelService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThasheelServiceMockMvc.perform(post("/api/thasheel-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thasheelService)))
            .andExpect(status().isBadRequest());

        // Validate the ThasheelService in the database
        List<ThasheelService> thasheelServiceList = thasheelServiceRepository.findAll();
        assertThat(thasheelServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThasheelServices() throws Exception {
        // Initialize the database
        thasheelServiceRepository.saveAndFlush(thasheelService);

        // Get all the thasheelServiceList
        restThasheelServiceMockMvc.perform(get("/api/thasheel-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thasheelService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdMajorAdminId").value(hasItem(DEFAULT_CREATED_MAJOR_ADMIN_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getThasheelService() throws Exception {
        // Initialize the database
        thasheelServiceRepository.saveAndFlush(thasheelService);

        // Get the thasheelService
        restThasheelServiceMockMvc.perform(get("/api/thasheel-services/{id}", thasheelService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thasheelService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdMajorAdminId").value(DEFAULT_CREATED_MAJOR_ADMIN_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingThasheelService() throws Exception {
        // Get the thasheelService
        restThasheelServiceMockMvc.perform(get("/api/thasheel-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThasheelService() throws Exception {
        // Initialize the database
        thasheelServiceService.save(thasheelService);

        int databaseSizeBeforeUpdate = thasheelServiceRepository.findAll().size();

        // Update the thasheelService
        ThasheelService updatedThasheelService = thasheelServiceRepository.findById(thasheelService.getId()).get();
        // Disconnect from session so that the updates on updatedThasheelService are not directly saved in db
        em.detach(updatedThasheelService);
        updatedThasheelService
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdMajorAdminId(UPDATED_CREATED_MAJOR_ADMIN_ID);

        restThasheelServiceMockMvc.perform(put("/api/thasheel-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedThasheelService)))
            .andExpect(status().isOk());

        // Validate the ThasheelService in the database
        List<ThasheelService> thasheelServiceList = thasheelServiceRepository.findAll();
        assertThat(thasheelServiceList).hasSize(databaseSizeBeforeUpdate);
        ThasheelService testThasheelService = thasheelServiceList.get(thasheelServiceList.size() - 1);
        assertThat(testThasheelService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThasheelService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testThasheelService.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testThasheelService.getCreatedMajorAdminId()).isEqualTo(UPDATED_CREATED_MAJOR_ADMIN_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingThasheelService() throws Exception {
        int databaseSizeBeforeUpdate = thasheelServiceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThasheelServiceMockMvc.perform(put("/api/thasheel-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thasheelService)))
            .andExpect(status().isBadRequest());

        // Validate the ThasheelService in the database
        List<ThasheelService> thasheelServiceList = thasheelServiceRepository.findAll();
        assertThat(thasheelServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThasheelService() throws Exception {
        // Initialize the database
        thasheelServiceService.save(thasheelService);

        int databaseSizeBeforeDelete = thasheelServiceRepository.findAll().size();

        // Delete the thasheelService
        restThasheelServiceMockMvc.perform(delete("/api/thasheel-services/{id}", thasheelService.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThasheelService> thasheelServiceList = thasheelServiceRepository.findAll();
        assertThat(thasheelServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
