package com.thasheel.repository;

import com.thasheel.domain.SavedNews;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SavedNews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SavedNewsRepository extends JpaRepository<SavedNews, Long> {

	List<SavedNews> findAllByCustomerId(Long customerId);
}
