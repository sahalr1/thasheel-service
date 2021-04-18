package com.thasheel.web.rest;

import com.thasheel.domain.BranchManager;
import com.thasheel.service.BranchManagerService;
import com.thasheel.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.thasheel.domain.BranchManager}.
 */
@RestController
@RequestMapping("/api")
public class BranchManagerResource {

    private final Logger log = LoggerFactory.getLogger(BranchManagerResource.class);

    private static final String ENTITY_NAME = "branchManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchManagerService branchManagerService;

    public BranchManagerResource(BranchManagerService branchManagerService) {
        this.branchManagerService = branchManagerService;
    }

    /**
     * {@code POST  /branch-managers} : Create a new branchManager.
     *
     * @param branchManager the branchManager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchManager, or with status {@code 400 (Bad Request)} if the branchManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branch-managers")
    public ResponseEntity<BranchManager> createBranchManager(@Valid @RequestBody BranchManager branchManager) throws URISyntaxException {
        log.debug("REST request to save BranchManager : {}", branchManager);
        if (branchManager.getId() != null) {
            throw new BadRequestAlertException("A new branchManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BranchManager result = branchManagerService.save(branchManager);
        return ResponseEntity.created(new URI("/api/branch-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branch-managers} : Updates an existing branchManager.
     *
     * @param branchManager the branchManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchManager,
     * or with status {@code 400 (Bad Request)} if the branchManager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branch-managers")
    public ResponseEntity<BranchManager> updateBranchManager(@Valid @RequestBody BranchManager branchManager) throws URISyntaxException {
        log.debug("REST request to update BranchManager : {}", branchManager);
        if (branchManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchManager result = branchManagerService.save(branchManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branchManager.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /branch-managers} : get all the branchManagers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branchManagers in body.
     */
    @GetMapping("/branch-managers")
    public List<BranchManager> getAllBranchManagers(@RequestParam(required = false) String filter) {
        if ("branch-is-null".equals(filter)) {
            log.debug("REST request to get all BranchManagers where branch is null");
            return branchManagerService.findAllWhereBranchIsNull();
        }
        log.debug("REST request to get all BranchManagers");
        return branchManagerService.findAll();
    }

    /**
     * {@code GET  /branch-managers/:id} : get the "id" branchManager.
     *
     * @param id the id of the branchManager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchManager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branch-managers/{id}")
    public ResponseEntity<BranchManager> getBranchManager(@PathVariable Long id) {
        log.debug("REST request to get BranchManager : {}", id);
        Optional<BranchManager> branchManager = branchManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(branchManager);
    }

    /**
     * {@code DELETE  /branch-managers/:id} : delete the "id" branchManager.
     *
     * @param id the id of the branchManager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branch-managers/{id}")
    public ResponseEntity<Void> deleteBranchManager(@PathVariable Long id) {
        log.debug("REST request to delete BranchManager : {}", id);
        branchManagerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
