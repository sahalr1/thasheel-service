package com.thasheel.web.rest;

import com.thasheel.ThasheelApp;
import com.thasheel.domain.NewsAppliedStatusHistory;
import com.thasheel.repository.NewsAppliedStatusHistoryRepository;
import com.thasheel.service.NewsAppliedStatusHistoryService;

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
 * Integration tests for the {@link NewsAppliedStatusHistoryResource} REST controller.
 */
@SpringBootTest(classes = ThasheelApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NewsAppliedStatusHistoryResourceIT {

    private static final NEWSAPPLIEDSTATUS DEFAULT_STATUS = NEWSAPPLIEDSTATUS.APPLIED;
    private static final NEWSAPPLIEDSTATUS UPDATED_STATUS = NEWSAPPLIEDSTATUS.REPROCESS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DONE_BRANCH_MANAGER_ID = "BBBBBBBBBB";

    @Autowired
    private NewsAppliedStatusHistoryRepository newsAppliedStatusHistoryRepository;

    @Autowired
    private NewsAppliedStatusHistoryService newsAppliedStatusHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsAppliedStatusHistoryMockMvc;

    private NewsAppliedStatusHistory newsAppliedStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsAppliedStatusHistory createEntity(EntityManager em) {
        NewsAppliedStatusHistory newsAppliedStatusHistory = new NewsAppliedStatusHistory()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .actionDoneBranchManagerId(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID);
        return newsAppliedStatusHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsAppliedStatusHistory createUpdatedEntity(EntityManager em) {
        NewsAppliedStatusHistory newsAppliedStatusHistory = new NewsAppliedStatusHistory()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .updatedTime(UPDATED_UPDATED_TIME)
            .actionDoneBranchManagerId(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);
        return newsAppliedStatusHistory;
    }

    @BeforeEach
    public void initTest() {
        newsAppliedStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewsAppliedStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = newsAppliedStatusHistoryRepository.findAll().size();
        // Create the NewsAppliedStatusHistory
        restNewsAppliedStatusHistoryMockMvc.perform(post("/api/news-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsAppliedStatusHistory)))
            .andExpect(status().isCreated());

        // Validate the NewsAppliedStatusHistory in the database
        List<NewsAppliedStatusHistory> newsAppliedStatusHistoryList = newsAppliedStatusHistoryRepository.findAll();
        assertThat(newsAppliedStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        NewsAppliedStatusHistory testNewsAppliedStatusHistory = newsAppliedStatusHistoryList.get(newsAppliedStatusHistoryList.size() - 1);
        assertThat(testNewsAppliedStatusHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNewsAppliedStatusHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsAppliedStatusHistory.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testNewsAppliedStatusHistory.getActionDoneBranchManagerId()).isEqualTo(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID);
    }

    @Test
    @Transactional
    public void createNewsAppliedStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsAppliedStatusHistoryRepository.findAll().size();

        // Create the NewsAppliedStatusHistory with an existing ID
        newsAppliedStatusHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsAppliedStatusHistoryMockMvc.perform(post("/api/news-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsAppliedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the NewsAppliedStatusHistory in the database
        List<NewsAppliedStatusHistory> newsAppliedStatusHistoryList = newsAppliedStatusHistoryRepository.findAll();
        assertThat(newsAppliedStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNewsAppliedStatusHistories() throws Exception {
        // Initialize the database
        newsAppliedStatusHistoryRepository.saveAndFlush(newsAppliedStatusHistory);

        // Get all the newsAppliedStatusHistoryList
        restNewsAppliedStatusHistoryMockMvc.perform(get("/api/news-applied-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsAppliedStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].actionDoneBranchManagerId").value(hasItem(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID)));
    }
    
    @Test
    @Transactional
    public void getNewsAppliedStatusHistory() throws Exception {
        // Initialize the database
        newsAppliedStatusHistoryRepository.saveAndFlush(newsAppliedStatusHistory);

        // Get the newsAppliedStatusHistory
        restNewsAppliedStatusHistoryMockMvc.perform(get("/api/news-applied-status-histories/{id}", newsAppliedStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsAppliedStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.actionDoneBranchManagerId").value(DEFAULT_ACTION_DONE_BRANCH_MANAGER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingNewsAppliedStatusHistory() throws Exception {
        // Get the newsAppliedStatusHistory
        restNewsAppliedStatusHistoryMockMvc.perform(get("/api/news-applied-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewsAppliedStatusHistory() throws Exception {
        // Initialize the database
        newsAppliedStatusHistoryService.save(newsAppliedStatusHistory);

        int databaseSizeBeforeUpdate = newsAppliedStatusHistoryRepository.findAll().size();

        // Update the newsAppliedStatusHistory
        NewsAppliedStatusHistory updatedNewsAppliedStatusHistory = newsAppliedStatusHistoryRepository.findById(newsAppliedStatusHistory.getId()).get();
        // Disconnect from session so that the updates on updatedNewsAppliedStatusHistory are not directly saved in db
        em.detach(updatedNewsAppliedStatusHistory);
        updatedNewsAppliedStatusHistory
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .updatedTime(UPDATED_UPDATED_TIME)
            .actionDoneBranchManagerId(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);

        restNewsAppliedStatusHistoryMockMvc.perform(put("/api/news-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewsAppliedStatusHistory)))
            .andExpect(status().isOk());

        // Validate the NewsAppliedStatusHistory in the database
        List<NewsAppliedStatusHistory> newsAppliedStatusHistoryList = newsAppliedStatusHistoryRepository.findAll();
        assertThat(newsAppliedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        NewsAppliedStatusHistory testNewsAppliedStatusHistory = newsAppliedStatusHistoryList.get(newsAppliedStatusHistoryList.size() - 1);
        assertThat(testNewsAppliedStatusHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNewsAppliedStatusHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsAppliedStatusHistory.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testNewsAppliedStatusHistory.getActionDoneBranchManagerId()).isEqualTo(UPDATED_ACTION_DONE_BRANCH_MANAGER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNewsAppliedStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = newsAppliedStatusHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsAppliedStatusHistoryMockMvc.perform(put("/api/news-applied-status-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsAppliedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the NewsAppliedStatusHistory in the database
        List<NewsAppliedStatusHistory> newsAppliedStatusHistoryList = newsAppliedStatusHistoryRepository.findAll();
        assertThat(newsAppliedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNewsAppliedStatusHistory() throws Exception {
        // Initialize the database
        newsAppliedStatusHistoryService.save(newsAppliedStatusHistory);

        int databaseSizeBeforeDelete = newsAppliedStatusHistoryRepository.findAll().size();

        // Delete the newsAppliedStatusHistory
        restNewsAppliedStatusHistoryMockMvc.perform(delete("/api/news-applied-status-histories/{id}", newsAppliedStatusHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsAppliedStatusHistory> newsAppliedStatusHistoryList = newsAppliedStatusHistoryRepository.findAll();
        assertThat(newsAppliedStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
