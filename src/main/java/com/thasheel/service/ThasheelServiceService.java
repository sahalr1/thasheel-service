package com.thasheel.service;

import com.thasheel.domain.ThasheelService;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ThasheelService}.
 */
public interface ThasheelServiceService {

    /**
     * Save a thasheelService.
     *
     * @param thasheelService the entity to save.
     * @return the persisted entity.
     */
    ThasheelService save(ThasheelService thasheelService);

    /**
     * Get all the thasheelServices.
     *
     * @return the list of entities.
     */
    List<ThasheelService> findAll();


    /**
     * Get the "id" thasheelService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThasheelService> findOne(Long id);

    /**
     * Delete the "id" thasheelService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
