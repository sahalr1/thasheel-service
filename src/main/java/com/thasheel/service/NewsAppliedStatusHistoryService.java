package com.thasheel.service;

import com.thasheel.domain.NewsAppliedStatusHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NewsAppliedStatusHistory}.
 */
public interface NewsAppliedStatusHistoryService {

    /**
     * Save a newsAppliedStatusHistory.
     *
     * @param newsAppliedStatusHistory the entity to save.
     * @return the persisted entity.
     */
    NewsAppliedStatusHistory save(NewsAppliedStatusHistory newsAppliedStatusHistory);

    /**
     * Get all the newsAppliedStatusHistories.
     *
     * @return the list of entities.
     */
    List<NewsAppliedStatusHistory> findAll();


    /**
     * Get the "id" newsAppliedStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewsAppliedStatusHistory> findOne(Long id);

    /**
     * Delete the "id" newsAppliedStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
