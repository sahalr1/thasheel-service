package com.thasheel.repository;

import com.thasheel.domain.NeedfulServiceDocuments;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NeedfulServiceDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NeedfulServiceDocumentsRepository extends JpaRepository<NeedfulServiceDocuments, Long> {

	List<NeedfulServiceDocuments> findAllByThasheelServiceId(Long serviceId);
}
