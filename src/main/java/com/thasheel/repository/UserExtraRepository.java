package com.thasheel.repository;

import com.thasheel.domain.User;
import com.thasheel.domain.UserExtra;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

	Optional<UserExtra> findByPhone(String phone);
}
