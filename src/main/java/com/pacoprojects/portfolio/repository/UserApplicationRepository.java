package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.UserApplicationBioSocialProjection;
import com.pacoprojects.portfolio.dto.UserApplicationProjection;
import com.pacoprojects.portfolio.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    Optional<UserApplication> findByUsername(String username);

    Optional<UserApplication> findByNickname(String nickname);

    Optional<UserApplicationProjection> findUserApplicationByUsername(String username);

    Optional<UserApplicationProjection> findUserApplicationByNickname(String nickname);

    @Query("SELECT u FROM UserApplication u WHERE u.nickname = ?1")
    Optional<UserApplicationBioSocialProjection> findUserApplicationBioSocialProjectionByNickname(String nickname);

}
