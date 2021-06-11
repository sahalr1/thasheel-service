package com.thasheel.web.rest;

import com.thasheel.domain.ServiceAppliedDocuments;
import com.thasheel.service.ServiceAppliedDocumentsService;
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
 * REST controller for managing {@link com.thasheel.domain.ServiceAppliedDocuments}.
 */
@RestController
@RequestMapping("/apis")
public class ServiceAppliedDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedDocumentsResource.class);

    private static final String ENTITY_NAME = "serviceAppliedDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAppliedDocumentsService serviceAppliedDocumentsService;

    public ServiceAppliedDocumentsResource(ServiceAppliedDocumentsService serviceAppliedDocumentsService) {
        this.serviceAppliedDocumentsService = serviceAppliedDocumentsService;
    }

    /**
     * {@code POST  /service-applied-documents} : Create a new serviceAppliedDocuments.
     *
     * @param serviceAppliedDocuments the serviceAppliedDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAppliedDocuments, or with status {@code 400 (Bad Request)} if the serviceAppliedDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-applied-documents")
    public ResponseEntity<ServiceAppliedDocuments> createServiceAppliedDocuments(@RequestBody ServiceAppliedDocuments serviceAppliedDocuments) throws URISyntaxException {
        log.debug("REST request to save ServiceAppliedDocuments : {}", serviceAppliedDocuments);
        if (serviceAppliedDocuments.getId() != null) {
            throw new BadRequestAlertException("A new serviceAppliedDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAppliedDocuments result = serviceAppliedDocumentsService.save(serviceAppliedDocuments);
        return ResponseEntity.created(new URI("/api/service-applied-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-applied-documents} : Updates an existing serviceAppliedDocuments.
     *
     * @param serviceAppliedDocuments the serviceAppliedDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAppliedDocuments,
     * or with status {@code 400 (Bad Request)} if the serviceAppliedDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAppliedDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-applied-documents")
    public ResponseEntity<ServiceAppliedDocuments> updateServiceAppliedDocuments(@RequestBody ServiceAppliedDocuments serviceAppliedDocuments) throws URISyntaxException {
        log.debug("REST request to update ServiceAppliedDocuments : {}", serviceAppliedDocuments);
        if (serviceAppliedDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAppliedDocuments result = serviceAppliedDocumentsService.save(serviceAppliedDocuments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAppliedDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-applied-documents} : get all the serviceAppliedDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAppliedDocuments in body.
     */
    @GetMapping("/service-applied-documents")
    public List<ServiceAppliedDocuments> getAllServiceAppliedDocuments() {
        log.debug("REST request to get all ServiceAppliedDocuments");
        return serviceAppliedDocumentsService.findAll();
    }

    /**
     * {@code GET  /service-applied-documents/:id} : get the "id" serviceAppliedDocuments.
     *
     * @param id the id of the serviceAppliedDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAppliedDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-applied-documents/{id}")
    public ResponseEntity<ServiceAppliedDocuments> getServiceAppliedDocuments(@PathVariable Long id) {
        log.debug("REST request to get ServiceAppliedDocuments : {}", id);
        Optional<ServiceAppliedDocuments> serviceAppliedDocuments = serviceAppliedDocumentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceAppliedDocuments);
    }

    /**
     * {@code DELETE  /service-applied-documents/:id} : delete the "id" serviceAppliedDocuments.
     *
     * @param id the id of the serviceAppliedDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-applied-documents/{id}")
    public ResponseEntity<Void> deleteServiceAppliedDocuments(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAppliedDocuments : {}", id);
        serviceAppliedDocumentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
