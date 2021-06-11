package com.thasheel.web.rest;

import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.service.NeedfulServiceDocumentsService;
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
 * REST controller for managing {@link com.thasheel.domain.NeedfulServiceDocuments}.
 */
@RestController
@RequestMapping("/api")
public class NeedfulServiceDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(NeedfulServiceDocumentsResource.class);

    private static final String ENTITY_NAME = "needfulServiceDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NeedfulServiceDocumentsService needfulServiceDocumentsService;

    public NeedfulServiceDocumentsResource(NeedfulServiceDocumentsService needfulServiceDocumentsService) {
        this.needfulServiceDocumentsService = needfulServiceDocumentsService;
    }

    /**
     * {@code POST  /needful-service-documents} : Create a new needfulServiceDocuments.
     *
     * @param needfulServiceDocuments the needfulServiceDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new needfulServiceDocuments, or with status {@code 400 (Bad Request)} if the needfulServiceDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/needful-service-documents")
    public ResponseEntity<NeedfulServiceDocuments> createNeedfulServiceDocuments(@RequestBody NeedfulServiceDocuments needfulServiceDocuments) throws URISyntaxException {
        log.debug("REST request to save NeedfulServiceDocuments : {}", needfulServiceDocuments);
        if (needfulServiceDocuments.getId() != null) {
            throw new BadRequestAlertException("A new needfulServiceDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NeedfulServiceDocuments result = needfulServiceDocumentsService.save(needfulServiceDocuments);
        return ResponseEntity.created(new URI("/api/needful-service-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /needful-service-documents} : Updates an existing needfulServiceDocuments.
     *
     * @param needfulServiceDocuments the needfulServiceDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated needfulServiceDocuments,
     * or with status {@code 400 (Bad Request)} if the needfulServiceDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the needfulServiceDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/needful-service-documents")
    public ResponseEntity<NeedfulServiceDocuments> updateNeedfulServiceDocuments(@RequestBody NeedfulServiceDocuments needfulServiceDocuments) throws URISyntaxException {
        log.debug("REST request to update NeedfulServiceDocuments : {}", needfulServiceDocuments);
        if (needfulServiceDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NeedfulServiceDocuments result = needfulServiceDocumentsService.save(needfulServiceDocuments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, needfulServiceDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /needful-service-documents} : get all the needfulServiceDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of needfulServiceDocuments in body.
     */
    @GetMapping("/needful-service-documents")
    public List<NeedfulServiceDocuments> getAllNeedfulServiceDocuments() {
        log.debug("REST request to get all NeedfulServiceDocuments");
        return needfulServiceDocumentsService.findAll();
    }

    /**
     * {@code GET  /needful-service-documents/:id} : get the "id" needfulServiceDocuments.
     *
     * @param id the id of the needfulServiceDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the needfulServiceDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/needful-service-documents/{id}")
    public ResponseEntity<NeedfulServiceDocuments> getNeedfulServiceDocuments(@PathVariable Long id) {
        log.debug("REST request to get NeedfulServiceDocuments : {}", id);
        Optional<NeedfulServiceDocuments> needfulServiceDocuments = needfulServiceDocumentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(needfulServiceDocuments);
    }

    /**
     * {@code DELETE  /needful-service-documents/:id} : delete the "id" needfulServiceDocuments.
     *
     * @param id the id of the needfulServiceDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/needful-service-documents/{id}")
    public ResponseEntity<Void> deleteNeedfulServiceDocuments(@PathVariable Long id) {
        log.debug("REST request to delete NeedfulServiceDocuments : {}", id);
        needfulServiceDocumentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
