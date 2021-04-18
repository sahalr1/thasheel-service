package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.NewsApplied;
import com.thasheel.repository.NewsAppliedRepository;
import com.thasheel.service.NewsAppliedService;

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

import com.thasheel.domain.enumeration.NEWSAPPLIEDSTATUS;
/**
 * Integration tests for the {@link NewsAppliedResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NewsAppliedResourceIT {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_NEWS_ID = 1L;
    private static final Long UPDATED_NEWS_ID = 2L;

    private static final Long DEFAULT_BRANCH_MANAGER_ID = 1L;
    private static final Long UPDATED_BRANCH_MANAGER_ID = 2L;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REPROCESS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_REPROCESS_DESCRIPTION = "BBBBBBBBBB";

    private static final NEWSAPPLIEDSTATUS DEFAULT_CURRENT_STATUS = NEWSAPPLIEDSTATUS.APPLIED;
    private static final NEWSAPPLIEDSTATUS UPDATED_CURRENT_STATUS = NEWSAPPLIEDSTATUS.REPROCESS;

    @Autowired
    private NewsAppliedRepository newsAppliedRepository;

    @Autowired
    private NewsAppliedService newsAppliedService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsAppliedMockMvc;

    private NewsApplied newsApplied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsApplied createEntity(EntityManager em) {
        NewsApplied newsApplied = new NewsApplied()
            .customerId(DEFAULT_CUSTOMER_ID)
            .newsId(DEFAULT_NEWS_ID)
            .branchManagerId(DEFAULT_BRANCH_MANAGER_ID)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .reprocessDescription(DEFAULT_REPROCESS_DESCRIPTION)
            .currentStatus(DEFAULT_CURRENT_STATUS);
        return newsApplied;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsApplied createUpdatedEntity(EntityManager em) {
        NewsApplied newsApplied = new NewsApplied()
            .customerId(UPDATED_CUSTOMER_ID)
            .newsId(UPDATED_NEWS_ID)
            .branchManagerId(UPDATED_BRANCH_MANAGER_ID)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME)
            .reprocessDescription(UPDATED_REPROCESS_DESCRIPTION)
            .currentStatus(UPDATED_CURRENT_STATUS);
        return newsApplied;
    }

    @BeforeEach
    public void initTest() {
        newsApplied = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewsApplied() throws Exception {
        int databaseSizeBeforeCreate = newsAppliedRepository.findAll().size();
        // Create the NewsApplied
        restNewsAppliedMockMvc.perform(post("/api/news-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsApplied)))
            .andExpect(status().isCreated());

        // Validate the NewsApplied in the database
        List<NewsApplied> newsAppliedList = newsAppliedRepository.findAll();
        assertThat(newsAppliedList).hasSize(databaseSizeBeforeCreate + 1);
        NewsApplied testNewsApplied = newsAppliedList.get(newsAppliedList.size() - 1);
        assertThat(testNewsApplied.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testNewsApplied.getNewsId()).isEqualTo(DEFAULT_NEWS_ID);
        assertThat(testNewsApplied.getBranchManagerId()).isEqualTo(DEFAULT_BRANCH_MANAGER_ID);
        assertThat(testNewsApplied.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testNewsApplied.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testNewsApplied.getReprocessDescription()).isEqualTo(DEFAULT_REPROCESS_DESCRIPTION);
        assertThat(testNewsApplied.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void createNewsAppliedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsAppliedRepository.findAll().size();

        // Create the NewsApplied with an existing ID
        newsApplied.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsAppliedMockMvc.perform(post("/api/news-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsApplied)))
            .andExpect(status().isBadRequest());

        // Validate the NewsApplied in the database
        List<NewsApplied> newsAppliedList = newsAppliedRepository.findAll();
        assertThat(newsAppliedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNewsApplieds() throws Exception {
        // Initialize the database
        newsAppliedRepository.saveAndFlush(newsApplied);

        // Get all the newsAppliedList
        restNewsAppliedMockMvc.perform(get("/api/news-applieds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsApplied.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].newsId").value(hasItem(DEFAULT_NEWS_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchManagerId").value(hasItem(DEFAULT_BRANCH_MANAGER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].reprocessDescription").value(hasItem(DEFAULT_REPROCESS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getNewsApplied() throws Exception {
        // Initialize the database
        newsAppliedRepository.saveAndFlush(newsApplied);

        // Get the newsApplied
        restNewsAppliedMockMvc.perform(get("/api/news-applieds/{id}", newsApplied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsApplied.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.newsId").value(DEFAULT_NEWS_ID.intValue()))
            .andExpect(jsonPath("$.branchManagerId").value(DEFAULT_BRANCH_MANAGER_ID.intValue()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.reprocessDescription").value(DEFAULT_REPROCESS_DESCRIPTION))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNewsApplied() throws Exception {
        // Get the newsApplied
        restNewsAppliedMockMvc.perform(get("/api/news-applieds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewsApplied() throws Exception {
        // Initialize the database
        newsAppliedService.save(newsApplied);

        int databaseSizeBeforeUpdate = newsAppliedRepository.findAll().size();

        // Update the newsApplied
        NewsApplied updatedNewsApplied = newsAppliedRepository.findById(newsApplied.getId()).get();
        // Disconnect from session so that the updates on updatedNewsApplied are not directly saved in db
        em.detach(updatedNewsApplied);
        updatedNewsApplied
            .customerId(UPDATED_CUSTOMER_ID)
            .newsId(UPDATED_NEWS_ID)
            .branchManagerId(UPDATED_BRANCH_MANAGER_ID)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME)
            .reprocessDescription(UPDATED_REPROCESS_DESCRIPTION)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restNewsAppliedMockMvc.perform(put("/api/news-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewsApplied)))
            .andExpect(status().isOk());

        // Validate the NewsApplied in the database
        List<NewsApplied> newsAppliedList = newsAppliedRepository.findAll();
        assertThat(newsAppliedList).hasSize(databaseSizeBeforeUpdate);
        NewsApplied testNewsApplied = newsAppliedList.get(newsAppliedList.size() - 1);
        assertThat(testNewsApplied.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testNewsApplied.getNewsId()).isEqualTo(UPDATED_NEWS_ID);
        assertThat(testNewsApplied.getBranchManagerId()).isEqualTo(UPDATED_BRANCH_MANAGER_ID);
        assertThat(testNewsApplied.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testNewsApplied.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testNewsApplied.getReprocessDescription()).isEqualTo(UPDATED_REPROCESS_DESCRIPTION);
        assertThat(testNewsApplied.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNewsApplied() throws Exception {
        int databaseSizeBeforeUpdate = newsAppliedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsAppliedMockMvc.perform(put("/api/news-applieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsApplied)))
            .andExpect(status().isBadRequest());

        // Validate the NewsApplied in the database
        List<NewsApplied> newsAppliedList = newsAppliedRepository.findAll();
        assertThat(newsAppliedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNewsApplied() throws Exception {
        // Initialize the database
        newsAppliedService.save(newsApplied);

        int databaseSizeBeforeDelete = newsAppliedRepository.findAll().size();

        // Delete the newsApplied
        restNewsAppliedMockMvc.perform(delete("/api/news-applieds/{id}", newsApplied.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsApplied> newsAppliedList = newsAppliedRepository.findAll();
        assertThat(newsAppliedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
