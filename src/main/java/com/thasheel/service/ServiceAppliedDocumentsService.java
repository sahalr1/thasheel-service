package com.thasheel.service;

import com.thasheel.domain.ServiceAppliedDocuments;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceAppliedDocuments}.
 */
public interface ServiceAppliedDocumentsService {

    /**
     * Save a serviceAppliedDocuments.
     *
     * @param serviceAppliedDocuments the entity to save.
     * @return the persisted entity.
     */
    ServiceAppliedDocuments save(ServiceAppliedDocuments serviceAppliedDocuments);

    /**
     * Get all the serviceAppliedDocuments.
     *
     * @return the list of entities.
     */
    List<ServiceAppliedDocuments> findAll();


    /**
     * Get the "id" serviceAppliedDocuments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceAppliedDocuments> findOne(Long id);

    /**
     * Delete the "id" serviceAppliedDocuments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<ServiceAppliedDocuments> findAllByAppliedServiceId(Long serviceId);

	ServiceAppliedDocuments upload(ServiceAppliedDocuments serviceAppliedDocuments);
}
