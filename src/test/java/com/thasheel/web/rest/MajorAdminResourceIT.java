package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.MajorAdmin;
import com.thasheel.repository.MajorAdminRepository;
import com.thasheel.service.MajorAdminService;

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
 * Integration tests for the {@link MajorAdminResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MajorAdminResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IDP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Autowired
    private MajorAdminRepository majorAdminRepository;

    @Autowired
    private MajorAdminService majorAdminService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorAdminMockMvc;

    private MajorAdmin majorAdmin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorAdmin createEntity(EntityManager em) {
        MajorAdmin majorAdmin = new MajorAdmin()
            .name(DEFAULT_NAME)
            .idpCode(DEFAULT_IDP_CODE)
            .isEnabled(DEFAULT_IS_ENABLED);
        return majorAdmin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorAdmin createUpdatedEntity(EntityManager em) {
        MajorAdmin majorAdmin = new MajorAdmin()
            .name(UPDATED_NAME)
            .idpCode(UPDATED_IDP_CODE)
            .isEnabled(UPDATED_IS_ENABLED);
        return majorAdmin;
    }

    @BeforeEach
    public void initTest() {
        majorAdmin = createEntity(em);
    }

    @Test
    @Transactional
    public void createMajorAdmin() throws Exception {
        int databaseSizeBeforeCreate = majorAdminRepository.findAll().size();
        // Create the MajorAdmin
        restMajorAdminMockMvc.perform(post("/api/major-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorAdmin)))
            .andExpect(status().isCreated());

        // Validate the MajorAdmin in the database
        List<MajorAdmin> majorAdminList = majorAdminRepository.findAll();
        assertThat(majorAdminList).hasSize(databaseSizeBeforeCreate + 1);
        MajorAdmin testMajorAdmin = majorAdminList.get(majorAdminList.size() - 1);
        assertThat(testMajorAdmin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMajorAdmin.getIdpCode()).isEqualTo(DEFAULT_IDP_CODE);
        assertThat(testMajorAdmin.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createMajorAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = majorAdminRepository.findAll().size();

        // Create the MajorAdmin with an existing ID
        majorAdmin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorAdminMockMvc.perform(post("/api/major-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the MajorAdmin in the database
        List<MajorAdmin> majorAdminList = majorAdminRepository.findAll();
        assertThat(majorAdminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMajorAdmins() throws Exception {
        // Initialize the database
        majorAdminRepository.saveAndFlush(majorAdmin);

        // Get all the majorAdminList
        restMajorAdminMockMvc.perform(get("/api/major-admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorAdmin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].idpCode").value(hasItem(DEFAULT_IDP_CODE)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMajorAdmin() throws Exception {
        // Initialize the database
        majorAdminRepository.saveAndFlush(majorAdmin);

        // Get the majorAdmin
        restMajorAdminMockMvc.perform(get("/api/major-admins/{id}", majorAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(majorAdmin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.idpCode").value(DEFAULT_IDP_CODE))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMajorAdmin() throws Exception {
        // Get the majorAdmin
        restMajorAdminMockMvc.perform(get("/api/major-admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajorAdmin() throws Exception {
        // Initialize the database
        majorAdminService.save(majorAdmin);

        int databaseSizeBeforeUpdate = majorAdminRepository.findAll().size();

        // Update the majorAdmin
        MajorAdmin updatedMajorAdmin = majorAdminRepository.findById(majorAdmin.getId()).get();
        // Disconnect from session so that the updates on updatedMajorAdmin are not directly saved in db
        em.detach(updatedMajorAdmin);
        updatedMajorAdmin
            .name(UPDATED_NAME)
            .idpCode(UPDATED_IDP_CODE)
            .isEnabled(UPDATED_IS_ENABLED);

        restMajorAdminMockMvc.perform(put("/api/major-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMajorAdmin)))
            .andExpect(status().isOk());

        // Validate the MajorAdmin in the database
        List<MajorAdmin> majorAdminList = majorAdminRepository.findAll();
        assertThat(majorAdminList).hasSize(databaseSizeBeforeUpdate);
        MajorAdmin testMajorAdmin = majorAdminList.get(majorAdminList.size() - 1);
        assertThat(testMajorAdmin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMajorAdmin.getIdpCode()).isEqualTo(UPDATED_IDP_CODE);
        assertThat(testMajorAdmin.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingMajorAdmin() throws Exception {
        int databaseSizeBeforeUpdate = majorAdminRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorAdminMockMvc.perform(put("/api/major-admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(majorAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the MajorAdmin in the database
        List<MajorAdmin> majorAdminList = majorAdminRepository.findAll();
        assertThat(majorAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMajorAdmin() throws Exception {
        // Initialize the database
        majorAdminService.save(majorAdmin);

        int databaseSizeBeforeDelete = majorAdminRepository.findAll().size();

        // Delete the majorAdmin
        restMajorAdminMockMvc.perform(delete("/api/major-admins/{id}", majorAdmin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MajorAdmin> majorAdminList = majorAdminRepository.findAll();
        assertThat(majorAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
