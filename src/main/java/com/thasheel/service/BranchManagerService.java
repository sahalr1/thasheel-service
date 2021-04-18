package com.thasheel.service;

import com.thasheel.domain.BranchManager;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BranchManager}.
 */
public interface BranchManagerService {

    /**
     * Save a branchManager.
     *
     * @param branchManager the entity to save.
     * @return the persisted entity.
     */
    BranchManager save(BranchManager branchManager);

    /**
     * Get all the branchManagers.
     *
     * @return the list of entities.
     */
    List<BranchManager> findAll();
    /**
     * Get all the BranchManagerDTO where Branch is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BranchManager> findAllWhereBranchIsNull();


    /**
     * Get the "id" branchManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BranchManager> findOne(Long id);

    /**
     * Delete the "id" branchManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
