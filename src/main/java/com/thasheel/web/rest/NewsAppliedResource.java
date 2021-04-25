package com.thasheel.web.rest;

import com.thasheel.domain.NewsApplied;
import com.thasheel.service.NewsAppliedService;
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
 * REST controller for managing {@link com.thasheel.domain.NewsApplied}.
 */
@RestController
@RequestMapping("/apis")
public class NewsAppliedResource {

    private final Logger log = LoggerFactory.getLogger(NewsAppliedResource.class);

    private static final String ENTITY_NAME = "newsApplied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsAppliedService newsAppliedService;

    public NewsAppliedResource(NewsAppliedService newsAppliedService) {
        this.newsAppliedService = newsAppliedService;
    }

    /**
     * {@code POST  /news-applieds} : Create a new newsApplied.
     *
     * @param newsApplied the newsApplied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsApplied, or with status {@code 400 (Bad Request)} if the newsApplied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-applieds")
    public ResponseEntity<NewsApplied> createNewsApplied(@RequestBody NewsApplied newsApplied) throws URISyntaxException {
        log.debug("REST request to save NewsApplied : {}", newsApplied);
        if (newsApplied.getId() != null) {
            throw new BadRequestAlertException("A new newsApplied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsApplied result = newsAppliedService.save(newsApplied);
        return ResponseEntity.created(new URI("/api/news-applieds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-applieds} : Updates an existing newsApplied.
     *
     * @param newsApplied the newsApplied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsApplied,
     * or with status {@code 400 (Bad Request)} if the newsApplied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsApplied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-applieds")
    public ResponseEntity<NewsApplied> updateNewsApplied(@RequestBody NewsApplied newsApplied) throws URISyntaxException {
        log.debug("REST request to update NewsApplied : {}", newsApplied);
        if (newsApplied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NewsApplied result = newsAppliedService.save(newsApplied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsApplied.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /news-applieds} : get all the newsApplieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsApplieds in body.
     */
    @GetMapping("/news-applieds")
    public List<NewsApplied> getAllNewsApplieds() {
        log.debug("REST request to get all NewsApplieds");
        return newsAppliedService.findAll();
    }

    /**
     * {@code GET  /news-applieds/:id} : get the "id" newsApplied.
     *
     * @param id the id of the newsApplied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsApplied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-applieds/{id}")
    public ResponseEntity<NewsApplied> getNewsApplied(@PathVariable Long id) {
        log.debug("REST request to get NewsApplied : {}", id);
        Optional<NewsApplied> newsApplied = newsAppliedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsApplied);
    }

    /**
     * {@code DELETE  /news-applieds/:id} : delete the "id" newsApplied.
     *
     * @param id the id of the newsApplied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-applieds/{id}")
    public ResponseEntity<Void> deleteNewsApplied(@PathVariable Long id) {
        log.debug("REST request to delete NewsApplied : {}", id);
        newsAppliedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
