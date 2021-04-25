package com.thasheel.service;

import com.thasheel.domain.SavedNews;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SavedNews}.
 */
public interface SavedNewsService {

    /**
     * Save a savedNews.
     *
     * @param savedNews the entity to save.
     * @return the persisted entity.
     */
    SavedNews save(SavedNews savedNews);

    /**
     * Get all the savedNews.
     *
     * @return the list of entities.
     */
    List<SavedNews> findAll();


    /**
     * Get the "id" savedNews.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SavedNews> findOne(Long id);

    /**
     * Delete the "id" savedNews.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<SavedNews> findAllByCustomerId(Long CustomerId);
}
