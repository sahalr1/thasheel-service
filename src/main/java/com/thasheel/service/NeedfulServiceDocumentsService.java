package com.thasheel.service;

import com.thasheel.domain.NeedfulServiceDocuments;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NeedfulServiceDocuments}.
 */
public interface NeedfulServiceDocumentsService {

    /**
     * Save a needfulServiceDocuments.
     *
     * @param needfulServiceDocuments the entity to save.
     * @return the persisted entity.
     */
    NeedfulServiceDocuments save(NeedfulServiceDocuments needfulServiceDocuments);

    /**
     * Get all the needfulServiceDocuments.
     *
     * @return the list of entities.
     */
    List<NeedfulServiceDocuments> findAll();


    /**
     * Get the "id" needfulServiceDocuments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NeedfulServiceDocuments> findOne(Long id);

    /**
     * Delete the "id" needfulServiceDocuments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<NeedfulServiceDocuments> findAllByServiceId(Long serviceId);

}
