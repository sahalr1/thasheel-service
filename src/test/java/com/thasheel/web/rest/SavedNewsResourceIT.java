package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.SavedNews;
import com.thasheel.repository.SavedNewsRepository;
import com.thasheel.service.SavedNewsService;

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
 * Integration tests for the {@link SavedNewsResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SavedNewsResourceIT {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_NEWS_ID = 1L;
    private static final Long UPDATED_NEWS_ID = 2L;

    @Autowired
    private SavedNewsRepository savedNewsRepository;

    @Autowired
    private SavedNewsService savedNewsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSavedNewsMockMvc;

    private SavedNews savedNews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedNews createEntity(EntityManager em) {
        SavedNews savedNews = new SavedNews()
            .customerId(DEFAULT_CUSTOMER_ID)
            .newsId(DEFAULT_NEWS_ID);
        return savedNews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedNews createUpdatedEntity(EntityManager em) {
        SavedNews savedNews = new SavedNews()
            .customerId(UPDATED_CUSTOMER_ID)
            .newsId(UPDATED_NEWS_ID);
        return savedNews;
    }

    @BeforeEach
    public void initTest() {
        savedNews = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedNews() throws Exception {
        int databaseSizeBeforeCreate = savedNewsRepository.findAll().size();
        // Create the SavedNews
        restSavedNewsMockMvc.perform(post("/api/saved-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedNews)))
            .andExpect(status().isCreated());

        // Validate the SavedNews in the database
        List<SavedNews> savedNewsList = savedNewsRepository.findAll();
        assertThat(savedNewsList).hasSize(databaseSizeBeforeCreate + 1);
        SavedNews testSavedNews = savedNewsList.get(savedNewsList.size() - 1);
        assertThat(testSavedNews.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSavedNews.getNewsId()).isEqualTo(DEFAULT_NEWS_ID);
    }

    @Test
    @Transactional
    public void createSavedNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = savedNewsRepository.findAll().size();

        // Create the SavedNews with an existing ID
        savedNews.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSavedNewsMockMvc.perform(post("/api/saved-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedNews)))
            .andExpect(status().isBadRequest());

        // Validate the SavedNews in the database
        List<SavedNews> savedNewsList = savedNewsRepository.findAll();
        assertThat(savedNewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSavedNews() throws Exception {
        // Initialize the database
        savedNewsRepository.saveAndFlush(savedNews);

        // Get all the savedNewsList
        restSavedNewsMockMvc.perform(get("/api/saved-news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(savedNews.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].newsId").value(hasItem(DEFAULT_NEWS_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSavedNews() throws Exception {
        // Initialize the database
        savedNewsRepository.saveAndFlush(savedNews);

        // Get the savedNews
        restSavedNewsMockMvc.perform(get("/api/saved-news/{id}", savedNews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(savedNews.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.newsId").value(DEFAULT_NEWS_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSavedNews() throws Exception {
        // Get the savedNews
        restSavedNewsMockMvc.perform(get("/api/saved-news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedNews() throws Exception {
        // Initialize the database
        savedNewsService.save(savedNews);

        int databaseSizeBeforeUpdate = savedNewsRepository.findAll().size();

        // Update the savedNews
        SavedNews updatedSavedNews = savedNewsRepository.findById(savedNews.getId()).get();
        // Disconnect from session so that the updates on updatedSavedNews are not directly saved in db
        em.detach(updatedSavedNews);
        updatedSavedNews
            .customerId(UPDATED_CUSTOMER_ID)
            .newsId(UPDATED_NEWS_ID);

        restSavedNewsMockMvc.perform(put("/api/saved-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSavedNews)))
            .andExpect(status().isOk());

        // Validate the SavedNews in the database
        List<SavedNews> savedNewsList = savedNewsRepository.findAll();
        assertThat(savedNewsList).hasSize(databaseSizeBeforeUpdate);
        SavedNews testSavedNews = savedNewsList.get(savedNewsList.size() - 1);
        assertThat(testSavedNews.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSavedNews.getNewsId()).isEqualTo(UPDATED_NEWS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSavedNews() throws Exception {
        int databaseSizeBeforeUpdate = savedNewsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSavedNewsMockMvc.perform(put("/api/saved-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(savedNews)))
            .andExpect(status().isBadRequest());

        // Validate the SavedNews in the database
        List<SavedNews> savedNewsList = savedNewsRepository.findAll();
        assertThat(savedNewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSavedNews() throws Exception {
        // Initialize the database
        savedNewsService.save(savedNews);

        int databaseSizeBeforeDelete = savedNewsRepository.findAll().size();

        // Delete the savedNews
        restSavedNewsMockMvc.perform(delete("/api/saved-news/{id}", savedNews.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SavedNews> savedNewsList = savedNewsRepository.findAll();
        assertThat(savedNewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
