package com.thasheel.web.rest;

import com.thasheel.domain.NewsAppliedStatusHistory;
import com.thasheel.service.NewsAppliedStatusHistoryService;
import com.thasheel.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.thasheel.domain.NewsAppliedStatusHistory}.
 */
@RestController
@RequestMapping("/apis")
public class NewsAppliedStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(NewsAppliedStatusHistoryResource.class);

    private static final String ENTITY_NAME = "newsAppliedStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsAppliedStatusHistoryService newsAppliedStatusHistoryService;

    public NewsAppliedStatusHistoryResource(NewsAppliedStatusHistoryService newsAppliedStatusHistoryService) {
        this.newsAppliedStatusHistoryService = newsAppliedStatusHistoryService;
    }

    /**
     * {@code POST  /news-applied-status-histories} : Create a new newsAppliedStatusHistory.
     *
     * @param newsAppliedStatusHistory the newsAppliedStatusHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsAppliedStatusHistory, or with status {@code 400 (Bad Request)} if the newsAppliedStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-applied-status-histories")
    public ResponseEntity<NewsAppliedStatusHistory> createNewsAppliedStatusHistory(@RequestBody NewsAppliedStatusHistory newsAppliedStatusHistory) throws URISyntaxException {
        log.debug("REST request to save NewsAppliedStatusHistory : {}", newsAppliedStatusHistory);
        if (newsAppliedStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new newsAppliedStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsAppliedStatusHistory result = newsAppliedStatusHistoryService.save(newsAppliedStatusHistory);
        return ResponseEntity.created(new URI("/api/news-applied-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-applied-status-histories} : Updates an existing newsAppliedStatusHistory.
     *
     * @param newsAppliedStatusHistory the newsAppliedStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsAppliedStatusHistory,
     * or with status {@code 400 (Bad Request)} if the newsAppliedStatusHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsAppliedStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-applied-status-histories")
    public ResponseEntity<NewsAppliedStatusHistory> updateNewsAppliedStatusHistory(@RequestBody NewsAppliedStatusHistory newsAppliedStatusHistory) throws URISyntaxException {
        log.debug("REST request to update NewsAppliedStatusHistory : {}", newsAppliedStatusHistory);
        if (newsAppliedStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NewsAppliedStatusHistory result = newsAppliedStatusHistoryService.save(newsAppliedStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsAppliedStatusHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /news-applied-status-histories} : get all the newsAppliedStatusHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsAppliedStatusHistories in body.
     */
    @GetMapping("/news-applied-status-histories")
    public List<NewsAppliedStatusHistory> getAllNewsAppliedStatusHistories() {
        log.debug("REST request to get all NewsAppliedStatusHistories");
        return newsAppliedStatusHistoryService.findAll();
    }

    /**
     * {@code GET  /news-applied-status-histories/:id} : get the "id" newsAppliedStatusHistory.
     *
     * @param id the id of the newsAppliedStatusHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsAppliedStatusHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-applied-status-histories/{id}")
    public ResponseEntity<NewsAppliedStatusHistory> getNewsAppliedStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get NewsAppliedStatusHistory : {}", id);
        Optional<NewsAppliedStatusHistory> newsAppliedStatusHistory = newsAppliedStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsAppliedStatusHistory);
    }

    /**
     * {@code DELETE  /news-applied-status-histories/:id} : delete the "id" newsAppliedStatusHistory.
     *
     * @param id the id of the newsAppliedStatusHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-applied-status-histories/{id}")
    public ResponseEntity<Void> deleteNewsAppliedStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete NewsAppliedStatusHistory : {}", id);
        newsAppliedStatusHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
