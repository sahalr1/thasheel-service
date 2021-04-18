package com.thasheel.web.rest;

import com.thasheel.domain.SavedNews;
import com.thasheel.service.SavedNewsService;
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
 * REST controller for managing {@link com.thasheel.domain.SavedNews}.
 */
@RestController
@RequestMapping("/api")
public class SavedNewsResource {

    private final Logger log = LoggerFactory.getLogger(SavedNewsResource.class);

    private static final String ENTITY_NAME = "savedNews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SavedNewsService savedNewsService;

    public SavedNewsResource(SavedNewsService savedNewsService) {
        this.savedNewsService = savedNewsService;
    }

    /**
     * {@code POST  /saved-news} : Create a new savedNews.
     *
     * @param savedNews the savedNews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new savedNews, or with status {@code 400 (Bad Request)} if the savedNews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saved-news")
    public ResponseEntity<SavedNews> createSavedNews(@RequestBody SavedNews savedNews) throws URISyntaxException {
        log.debug("REST request to save SavedNews : {}", savedNews);
        if (savedNews.getId() != null) {
            throw new BadRequestAlertException("A new savedNews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SavedNews result = savedNewsService.save(savedNews);
        return ResponseEntity.created(new URI("/api/saved-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saved-news} : Updates an existing savedNews.
     *
     * @param savedNews the savedNews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated savedNews,
     * or with status {@code 400 (Bad Request)} if the savedNews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the savedNews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saved-news")
    public ResponseEntity<SavedNews> updateSavedNews(@RequestBody SavedNews savedNews) throws URISyntaxException {
        log.debug("REST request to update SavedNews : {}", savedNews);
        if (savedNews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SavedNews result = savedNewsService.save(savedNews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, savedNews.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saved-news} : get all the savedNews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of savedNews in body.
     */
    @GetMapping("/saved-news")
    public List<SavedNews> getAllSavedNews() {
        log.debug("REST request to get all SavedNews");
        return savedNewsService.findAll();
    }

    /**
     * {@code GET  /saved-news/:id} : get the "id" savedNews.
     *
     * @param id the id of the savedNews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the savedNews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saved-news/{id}")
    public ResponseEntity<SavedNews> getSavedNews(@PathVariable Long id) {
        log.debug("REST request to get SavedNews : {}", id);
        Optional<SavedNews> savedNews = savedNewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(savedNews);
    }

    /**
     * {@code DELETE  /saved-news/:id} : delete the "id" savedNews.
     *
     * @param id the id of the savedNews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saved-news/{id}")
    public ResponseEntity<Void> deleteSavedNews(@PathVariable Long id) {
        log.debug("REST request to delete SavedNews : {}", id);
        savedNewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
