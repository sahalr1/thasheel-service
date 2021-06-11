package com.thasheel.repository;

import com.thasheel.domain.ServiceApplied;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceApplied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAppliedRepository extends JpaRepository<ServiceApplied, Long> {

	Optional<ServiceApplied> findByServiceIdAndCustomerId(Long serviceId, Long customerId);
}
