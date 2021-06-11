package com.thasheel.service;

import com.thasheel.domain.ServiceAppliedStatusHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceAppliedStatusHistory}.
 */
public interface ServiceAppliedStatusHistoryService {

    /**
     * Save a serviceAppliedStatusHistory.
     *
     * @param serviceAppliedStatusHistory the entity to save.
     * @return the persisted entity.
     */
    ServiceAppliedStatusHistory save(ServiceAppliedStatusHistory serviceAppliedStatusHistory);

    /**
     * Get all the serviceAppliedStatusHistories.
     *
     * @return the list of entities.
     */
    List<ServiceAppliedStatusHistory> findAll();


    /**
     * Get the "id" serviceAppliedStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceAppliedStatusHistory> findOne(Long id);

    /**
     * Delete the "id" serviceAppliedStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
