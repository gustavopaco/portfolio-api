package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.UserApplicationBioSocialProjection;
import com.pacoprojects.portfolio.dto.UserApplicationProjection;
import com.pacoprojects.portfolio.model.UserApplication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    @EntityGraph(attributePaths = {"tokenConfirmations", "bio", "social"})
    Optional<UserApplication> findByUsername(String username);

    @EntityGraph(attributePaths = {"bio", "social"})
    Optional<UserApplication> findByNickname(String nickname);

    Optional<UserApplicationProjection> findUserApplicationByUsername(String username);

    @EntityGraph(attributePaths = {"tokenConfirmations", "bio", "social", "skills", "projects"})
    Optional<UserApplicationProjection> findUserApplicationByNickname(String nickname);

    @EntityGraph(attributePaths = {"bio", "social"})
    @Query("SELECT u FROM UserApplication u WHERE u.nickname = ?1")
    Optional<UserApplicationBioSocialProjection> findUserApplicationBioSocialProjectionByNickname(String nickname);

}
