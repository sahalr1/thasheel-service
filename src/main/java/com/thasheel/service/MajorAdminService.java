package com.thasheel.service;

import com.thasheel.domain.MajorAdmin;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MajorAdmin}.
 */
public interface MajorAdminService {

    /**
     * Save a majorAdmin.
     *
     * @param majorAdmin the entity to save.
     * @return the persisted entity.
     */
    MajorAdmin save(MajorAdmin majorAdmin);

    /**
     * Get all the majorAdmins.
     *
     * @return the list of entities.
     */
    List<MajorAdmin> findAll();


    /**
     * Get the "id" majorAdmin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MajorAdmin> findOne(Long id);

    /**
     * Delete the "id" majorAdmin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
