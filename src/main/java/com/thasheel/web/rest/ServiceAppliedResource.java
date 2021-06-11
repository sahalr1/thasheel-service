package com.thasheel.web.rest;

import com.thasheel.domain.ServiceApplied;
import com.thasheel.service.ServiceAppliedService;
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
 * REST controller for managing {@link com.thasheel.domain.ServiceApplied}.
 */
@RestController
@RequestMapping("/apis")
public class ServiceAppliedResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedResource.class);

    private static final String ENTITY_NAME = "serviceApplied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAppliedService serviceAppliedService;

    public ServiceAppliedResource(ServiceAppliedService serviceAppliedService) {
        this.serviceAppliedService = serviceAppliedService;
    }

    /**
     * {@code POST  /service-applieds} : Create a new serviceApplied.
     *
     * @param serviceApplied the serviceApplied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceApplied, or with status {@code 400 (Bad Request)} if the serviceApplied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-applieds")
    public ResponseEntity<ServiceApplied> createServiceApplied(@RequestBody ServiceApplied serviceApplied) throws URISyntaxException {
        log.debug("REST request to save ServiceApplied : {}", serviceApplied);
        if (serviceApplied.getId() != null) {
            throw new BadRequestAlertException("A new serviceApplied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceApplied result = serviceAppliedService.save(serviceApplied);
        return ResponseEntity.created(new URI("/api/service-applieds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-applieds} : Updates an existing serviceApplied.
     *
     * @param serviceApplied the serviceApplied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceApplied,
     * or with status {@code 400 (Bad Request)} if the serviceApplied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceApplied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-applieds")
    public ResponseEntity<ServiceApplied> updateServiceApplied(@RequestBody ServiceApplied serviceApplied) throws URISyntaxException {
        log.debug("REST request to update ServiceApplied : {}", serviceApplied);
        if (serviceApplied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceApplied result = serviceAppliedService.save(serviceApplied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceApplied.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-applieds} : get all the serviceApplieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceApplieds in body.
     */
    @GetMapping("/service-applieds")
    public List<ServiceApplied> getAllServiceApplieds() {
        log.debug("REST request to get all ServiceApplieds");
        return serviceAppliedService.findAll();
    }

    /**
     * {@code GET  /service-applieds/:id} : get the "id" serviceApplied.
     *
     * @param id the id of the serviceApplied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceApplied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-applieds/{id}")
    public ResponseEntity<ServiceApplied> getServiceApplied(@PathVariable Long id) {
        log.debug("REST request to get ServiceApplied : {}", id);
        Optional<ServiceApplied> serviceApplied = serviceAppliedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceApplied);
    }

    /**
     * {@code DELETE  /service-applieds/:id} : delete the "id" serviceApplied.
     *
     * @param id the id of the serviceApplied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-applieds/{id}")
    public ResponseEntity<Void> deleteServiceApplied(@PathVariable Long id) {
        log.debug("REST request to delete ServiceApplied : {}", id);
        serviceAppliedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
