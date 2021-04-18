package com.thasheel.repository;

import com.thasheel.domain.MajorAdmin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MajorAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorAdminRepository extends JpaRepository<MajorAdmin, Long> {
}
