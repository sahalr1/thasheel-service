package com.thasheel.repository;

import com.thasheel.domain.ServiceAppliedStatusHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceAppliedStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAppliedStatusHistoryRepository extends JpaRepository<ServiceAppliedStatusHistory, Long> {
}
