package com.thasheel.repository;

import com.thasheel.domain.NewsAppliedStatusHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NewsAppliedStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsAppliedStatusHistoryRepository extends JpaRepository<NewsAppliedStatusHistory, Long> {
}
