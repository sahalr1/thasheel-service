package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.Branch;
import com.thasheel.repository.BranchRepository;
import com.thasheel.service.BranchService;

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
 * Integration tests for the {@link BranchResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BranchResourceIT {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBranchMockMvc;

    private Branch branch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createEntity(EntityManager em) {
        Branch branch = new Branch()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return branch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createUpdatedEntity(EntityManager em) {
        Branch branch = new Branch()
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);
        return branch;
    }

    @BeforeEach
    public void initTest() {
        branch = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();
        // Create the Branch
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testBranch.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testBranch.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBranch.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createBranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch with an existing ID
        branch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList
        restBranchMockMvc.perform(get("/api/branches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)));
    }
    
    @Test
    @Transactional
    public void getBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE));
    }
    @Test
    @Transactional
    public void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findById(branch.getId()).get();
        // Disconnect from session so that the updates on updatedBranch are not directly saved in db
        em.detach(updatedBranch);
        updatedBranch
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);

        restBranchMockMvc.perform(put("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranch)))
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testBranch.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testBranch.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBranch.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc.perform(put("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Delete the branch
        restBranchMockMvc.perform(delete("/api/branches/{id}", branch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
