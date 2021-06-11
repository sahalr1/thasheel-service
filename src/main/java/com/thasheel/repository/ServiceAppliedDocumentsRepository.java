package com.thasheel.repository;

import com.thasheel.domain.ServiceAppliedDocuments;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceAppliedDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAppliedDocumentsRepository extends JpaRepository<ServiceAppliedDocuments, Long> {

	

	//List<ServiceAppliedDocuments> findAllServiceAppliedId(Long serviceId);

	List<ServiceAppliedDocuments> findAllByServiceApplied(Long serviceId);
}
