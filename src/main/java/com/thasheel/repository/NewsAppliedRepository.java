package com.thasheel.repository;

import com.thasheel.domain.NewsApplied;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NewsApplied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsAppliedRepository extends JpaRepository<NewsApplied, Long> {

	Optional<NewsApplied> findByNewsIdAndCustomerId(Long newsId, Long customerId);

	List<NewsApplied> findAllByCustomerId(Long customerId);
}
