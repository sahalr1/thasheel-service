package com.thasheel.service;

import com.thasheel.domain.ServiceApplied;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceApplied}.
 */
public interface ServiceAppliedService {

    /**
     * Save a serviceApplied.
     *
     * @param serviceApplied the entity to save.
     * @return the persisted entity.
     */
    ServiceApplied save(ServiceApplied serviceApplied);

    /**
     * Get all the serviceApplieds.
     *
     * @return the list of entities.
     */
    List<ServiceApplied> findAll();


    /**
     * Get the "id" serviceApplied.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceApplied> findOne(Long id);

    /**
     * Delete the "id" serviceApplied.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<ServiceApplied> checkUserAlreadyApplied(Long serviceId, Long customerId);
}
