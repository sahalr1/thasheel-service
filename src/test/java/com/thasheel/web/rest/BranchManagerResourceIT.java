package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.BranchManager;
import com.thasheel.repository.BranchManagerRepository;
import com.thasheel.service.BranchManagerService;

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

/**
 * Integration tests for the {@link BranchManagerResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BranchManagerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IDP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Autowired
    private BranchManagerRepository branchManagerRepository;

    @Autowired
    private BranchManagerService branchManagerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBranchManagerMockMvc;

    private BranchManager branchManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BranchManager createEntity(EntityManager em) {
        BranchManager branchManager = new BranchManager()
            .name(DEFAULT_NAME)
            .idpCode(DEFAULT_IDP_CODE)
            .isEnabled(DEFAULT_IS_ENABLED);
        return branchManager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BranchManager createUpdatedEntity(EntityManager em) {
        BranchManager branchManager = new BranchManager()
            .name(UPDATED_NAME)
            .idpCode(UPDATED_IDP_CODE)
            .isEnabled(UPDATED_IS_ENABLED);
        return branchManager;
    }

    @BeforeEach
    public void initTest() {
        branchManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchManager() throws Exception {
        int databaseSizeBeforeCreate = branchManagerRepository.findAll().size();
        // Create the BranchManager
        restBranchManagerMockMvc.perform(post("/api/branch-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchManager)))
            .andExpect(status().isCreated());

        // Validate the BranchManager in the database
        List<BranchManager> branchManagerList = branchManagerRepository.findAll();
        assertThat(branchManagerList).hasSize(databaseSizeBeforeCreate + 1);
        BranchManager testBranchManager = branchManagerList.get(branchManagerList.size() - 1);
        assertThat(testBranchManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBranchManager.getIdpCode()).isEqualTo(DEFAULT_IDP_CODE);
        assertThat(testBranchManager.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createBranchManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchManagerRepository.findAll().size();

        // Create the BranchManager with an existing ID
        branchManager.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchManagerMockMvc.perform(post("/api/branch-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchManager)))
            .andExpect(status().isBadRequest());

        // Validate the BranchManager in the database
        List<BranchManager> branchManagerList = branchManagerRepository.findAll();
        assertThat(branchManagerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBranchManagers() throws Exception {
        // Initialize the database
        branchManagerRepository.saveAndFlush(branchManager);

        // Get all the branchManagerList
        restBranchManagerMockMvc.perform(get("/api/branch-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].idpCode").value(hasItem(DEFAULT_IDP_CODE)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBranchManager() throws Exception {
        // Initialize the database
        branchManagerRepository.saveAndFlush(branchManager);

        // Get the branchManager
        restBranchManagerMockMvc.perform(get("/api/branch-managers/{id}", branchManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branchManager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.idpCode").value(DEFAULT_IDP_CODE))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBranchManager() throws Exception {
        // Get the branchManager
        restBranchManagerMockMvc.perform(get("/api/branch-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchManager() throws Exception {
        // Initialize the database
        branchManagerService.save(branchManager);

        int databaseSizeBeforeUpdate = branchManagerRepository.findAll().size();

        // Update the branchManager
        BranchManager updatedBranchManager = branchManagerRepository.findById(branchManager.getId()).get();
        // Disconnect from session so that the updates on updatedBranchManager are not directly saved in db
        em.detach(updatedBranchManager);
        updatedBranchManager
            .name(UPDATED_NAME)
            .idpCode(UPDATED_IDP_CODE)
            .isEnabled(UPDATED_IS_ENABLED);

        restBranchManagerMockMvc.perform(put("/api/branch-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranchManager)))
            .andExpect(status().isOk());

        // Validate the BranchManager in the database
        List<BranchManager> branchManagerList = branchManagerRepository.findAll();
        assertThat(branchManagerList).hasSize(databaseSizeBeforeUpdate);
        BranchManager testBranchManager = branchManagerList.get(branchManagerList.size() - 1);
        assertThat(testBranchManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBranchManager.getIdpCode()).isEqualTo(UPDATED_IDP_CODE);
        assertThat(testBranchManager.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchManager() throws Exception {
        int databaseSizeBeforeUpdate = branchManagerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchManagerMockMvc.perform(put("/api/branch-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchManager)))
            .andExpect(status().isBadRequest());

        // Validate the BranchManager in the database
        List<BranchManager> branchManagerList = branchManagerRepository.findAll();
        assertThat(branchManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranchManager() throws Exception {
        // Initialize the database
        branchManagerService.save(branchManager);

        int databaseSizeBeforeDelete = branchManagerRepository.findAll().size();

        // Delete the branchManager
        restBranchManagerMockMvc.perform(delete("/api/branch-managers/{id}", branchManager.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BranchManager> branchManagerList = branchManagerRepository.findAll();
        assertThat(branchManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
