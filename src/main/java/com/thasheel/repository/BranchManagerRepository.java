package com.thasheel.repository;

import com.thasheel.domain.BranchManager;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BranchManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager, Long> {
}
