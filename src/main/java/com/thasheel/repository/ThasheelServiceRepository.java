package com.thasheel.repository;

import com.thasheel.domain.ThasheelService;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ThasheelService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThasheelServiceRepository extends JpaRepository<ThasheelService, Long> {
}
