package com.thasheel.web.rest;

import com.thasheel.domain.MajorAdmin;
import com.thasheel.service.MajorAdminService;
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

/**
 * REST controller for managing {@link com.thasheel.domain.MajorAdmin}.
 */
@RestController
@RequestMapping("/apis")
public class MajorAdminResource {

    private final Logger log = LoggerFactory.getLogger(MajorAdminResource.class);

    private static final String ENTITY_NAME = "majorAdmin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorAdminService majorAdminService;

    public MajorAdminResource(MajorAdminService majorAdminService) {
        this.majorAdminService = majorAdminService;
    }

    /**
     * {@code POST  /major-admins} : Create a new majorAdmin.
     *
     * @param majorAdmin the majorAdmin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorAdmin, or with status {@code 400 (Bad Request)} if the majorAdmin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/major-admins")
    public ResponseEntity<MajorAdmin> createMajorAdmin(@Valid @RequestBody MajorAdmin majorAdmin) throws URISyntaxException {
        log.debug("REST request to save MajorAdmin : {}", majorAdmin);
        if (majorAdmin.getId() != null) {
            throw new BadRequestAlertException("A new majorAdmin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MajorAdmin result = majorAdminService.save(majorAdmin);
        return ResponseEntity.created(new URI("/api/major-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /major-admins} : Updates an existing majorAdmin.
     *
     * @param majorAdmin the majorAdmin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorAdmin,
     * or with status {@code 400 (Bad Request)} if the majorAdmin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorAdmin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/major-admins")
    public ResponseEntity<MajorAdmin> updateMajorAdmin(@Valid @RequestBody MajorAdmin majorAdmin) throws URISyntaxException {
        log.debug("REST request to update MajorAdmin : {}", majorAdmin);
        if (majorAdmin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MajorAdmin result = majorAdminService.save(majorAdmin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorAdmin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /major-admins} : get all the majorAdmins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorAdmins in body.
     */
    @GetMapping("/major-admins")
    public List<MajorAdmin> getAllMajorAdmins() {
        log.debug("REST request to get all MajorAdmins");
        return majorAdminService.findAll();
    }

    /**
     * {@code GET  /major-admins/:id} : get the "id" majorAdmin.
     *
     * @param id the id of the majorAdmin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorAdmin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/major-admins/{id}")
    public ResponseEntity<MajorAdmin> getMajorAdmin(@PathVariable Long id) {
        log.debug("REST request to get MajorAdmin : {}", id);
        Optional<MajorAdmin> majorAdmin = majorAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(majorAdmin);
    }

    /**
     * {@code DELETE  /major-admins/:id} : delete the "id" majorAdmin.
     *
     * @param id the id of the majorAdmin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/major-admins/{id}")
    public ResponseEntity<Void> deleteMajorAdmin(@PathVariable Long id) {
        log.debug("REST request to delete MajorAdmin : {}", id);
        majorAdminService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
