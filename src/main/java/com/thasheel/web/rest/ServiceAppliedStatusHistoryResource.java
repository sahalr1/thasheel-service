package com.thasheel.web.rest;

import com.thasheel.domain.ServiceAppliedStatusHistory;
import com.thasheel.service.ServiceAppliedStatusHistoryService;
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
 * REST controller for managing {@link com.thasheel.domain.ServiceAppliedStatusHistory}.
 */
@RestController
@RequestMapping("/apis")
public class ServiceAppliedStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedStatusHistoryResource.class);

    private static final String ENTITY_NAME = "serviceAppliedStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAppliedStatusHistoryService serviceAppliedStatusHistoryService;

    public ServiceAppliedStatusHistoryResource(ServiceAppliedStatusHistoryService serviceAppliedStatusHistoryService) {
        this.serviceAppliedStatusHistoryService = serviceAppliedStatusHistoryService;
    }

    /**
     * {@code POST  /service-applied-status-histories} : Create a new serviceAppliedStatusHistory.
     *
     * @param serviceAppliedStatusHistory the serviceAppliedStatusHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAppliedStatusHistory, or with status {@code 400 (Bad Request)} if the serviceAppliedStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-applied-status-histories")
    public ResponseEntity<ServiceAppliedStatusHistory> createServiceAppliedStatusHistory(@RequestBody ServiceAppliedStatusHistory serviceAppliedStatusHistory) throws URISyntaxException {
        log.debug("REST request to save ServiceAppliedStatusHistory : {}", serviceAppliedStatusHistory);
        if (serviceAppliedStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new serviceAppliedStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAppliedStatusHistory result = serviceAppliedStatusHistoryService.save(serviceAppliedStatusHistory);
        return ResponseEntity.created(new URI("/api/service-applied-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-applied-status-histories} : Updates an existing serviceAppliedStatusHistory.
     *
     * @param serviceAppliedStatusHistory the serviceAppliedStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAppliedStatusHistory,
     * or with status {@code 400 (Bad Request)} if the serviceAppliedStatusHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAppliedStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-applied-status-histories")
    public ResponseEntity<ServiceAppliedStatusHistory> updateServiceAppliedStatusHistory(@RequestBody ServiceAppliedStatusHistory serviceAppliedStatusHistory) throws URISyntaxException {
        log.debug("REST request to update ServiceAppliedStatusHistory : {}", serviceAppliedStatusHistory);
        if (serviceAppliedStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAppliedStatusHistory result = serviceAppliedStatusHistoryService.save(serviceAppliedStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAppliedStatusHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-applied-status-histories} : get all the serviceAppliedStatusHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAppliedStatusHistories in body.
     */
    @GetMapping("/service-applied-status-histories")
    public List<ServiceAppliedStatusHistory> getAllServiceAppliedStatusHistories() {
        log.debug("REST request to get all ServiceAppliedStatusHistories");
        return serviceAppliedStatusHistoryService.findAll();
    }

    /**
     * {@code GET  /service-applied-status-histories/:id} : get the "id" serviceAppliedStatusHistory.
     *
     * @param id the id of the serviceAppliedStatusHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAppliedStatusHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-applied-status-histories/{id}")
    public ResponseEntity<ServiceAppliedStatusHistory> getServiceAppliedStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get ServiceAppliedStatusHistory : {}", id);
        Optional<ServiceAppliedStatusHistory> serviceAppliedStatusHistory = serviceAppliedStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceAppliedStatusHistory);
    }

    /**
     * {@code DELETE  /service-applied-status-histories/:id} : delete the "id" serviceAppliedStatusHistory.
     *
     * @param id the id of the serviceAppliedStatusHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-applied-status-histories/{id}")
    public ResponseEntity<Void> deleteServiceAppliedStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAppliedStatusHistory : {}", id);
        serviceAppliedStatusHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
