package com.thasheel.web.rest;

import com.thasheel.domain.ThasheelService;
import com.thasheel.service.ThasheelServiceService;
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
 * REST controller for managing {@link com.thasheel.domain.ThasheelService}.
 */
@RestController
@RequestMapping("/api")
public class ThasheelServiceResource {

    private final Logger log = LoggerFactory.getLogger(ThasheelServiceResource.class);

    private static final String ENTITY_NAME = "thasheelService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThasheelServiceService thasheelServiceService;

    public ThasheelServiceResource(ThasheelServiceService thasheelServiceService) {
        this.thasheelServiceService = thasheelServiceService;
    }

    /**
     * {@code POST  /thasheel-services} : Create a new thasheelService.
     *
     * @param thasheelService the thasheelService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thasheelService, or with status {@code 400 (Bad Request)} if the thasheelService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thasheel-services")
    public ResponseEntity<ThasheelService> createThasheelService(@RequestBody ThasheelService thasheelService) throws URISyntaxException {
        log.debug("REST request to save ThasheelService : {}", thasheelService);
        if (thasheelService.getId() != null) {
            throw new BadRequestAlertException("A new thasheelService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThasheelService result = thasheelServiceService.save(thasheelService);
        return ResponseEntity.created(new URI("/api/thasheel-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thasheel-services} : Updates an existing thasheelService.
     *
     * @param thasheelService the thasheelService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thasheelService,
     * or with status {@code 400 (Bad Request)} if the thasheelService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thasheelService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thasheel-services")
    public ResponseEntity<ThasheelService> updateThasheelService(@RequestBody ThasheelService thasheelService) throws URISyntaxException {
        log.debug("REST request to update ThasheelService : {}", thasheelService);
        if (thasheelService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThasheelService result = thasheelServiceService.save(thasheelService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thasheelService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /thasheel-services} : get all the thasheelServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thasheelServices in body.
     */
    @GetMapping("/thasheel-services")
    public List<ThasheelService> getAllThasheelServices() {
        log.debug("REST request to get all ThasheelServices");
        return thasheelServiceService.findAll();
    }

    /**
     * {@code GET  /thasheel-services/:id} : get the "id" thasheelService.
     *
     * @param id the id of the thasheelService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thasheelService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thasheel-services/{id}")
    public ResponseEntity<ThasheelService> getThasheelService(@PathVariable Long id) {
        log.debug("REST request to get ThasheelService : {}", id);
        Optional<ThasheelService> thasheelService = thasheelServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thasheelService);
    }

    /**
     * {@code DELETE  /thasheel-services/:id} : delete the "id" thasheelService.
     *
     * @param id the id of the thasheelService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thasheel-services/{id}")
    public ResponseEntity<Void> deleteThasheelService(@PathVariable Long id) {
        log.debug("REST request to delete ThasheelService : {}", id);
        thasheelServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
