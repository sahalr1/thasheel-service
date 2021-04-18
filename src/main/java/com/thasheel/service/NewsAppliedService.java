package com.thasheel.service;

import com.thasheel.domain.NewsApplied;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NewsApplied}.
 */
public interface NewsAppliedService {

    /**
     * Save a newsApplied.
     *
     * @param newsApplied the entity to save.
     * @return the persisted entity.
     */
    NewsApplied save(NewsApplied newsApplied);

    /**
     * Get all the newsApplieds.
     *
     * @return the list of entities.
     */
    List<NewsApplied> findAll();


    /**
     * Get the "id" newsApplied.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewsApplied> findOne(Long id);

    /**
     * Delete the "id" newsApplied.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
