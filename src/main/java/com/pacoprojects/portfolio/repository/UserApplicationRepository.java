package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.model.UserApplication;
import com.pacoprojects.portfolio.projection.UserApplicationBasicSearch;
import com.pacoprojects.portfolio.projection.UserApplicationBioSocialProjection;
import com.pacoprojects.portfolio.projection.UserApplicationProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    @EntityGraph(attributePaths = {"tokenConfirmations", "bio", "social", "resume"})
    Optional<UserApplication> findByUsername(String username);

    @EntityGraph(attributePaths = {"bio", "social", "resume"})
    Optional<UserApplication> findByNickname(String nickname);

    Optional<UserApplicationProjection> findUserApplicationByUsername(String username);

    @EntityGraph(attributePaths = {"tokenConfirmations", "bio", "social", "skills", "projects", "courses", "certificates", "resume"})
    Optional<UserApplicationProjection> findUserApplicationByNickname(String nickname);

    @Query(value = """
            SELECT DISTINCT ua
            FROM UserApplication ua
            LEFT JOIN FETCH ua.projects p
            LEFT JOIN FETCH ua.bio b
            LEFT JOIN FETCH ua.social so
            LEFT JOIN FETCH ua.certificates ce
            LEFT JOIN FETCH ua.resume r
            WHERE ua.nickname = :nickname
            """)
    Optional<UserApplication> findUserApplicationDataByNickname(String nickname);

    @EntityGraph(attributePaths = {"bio", "social", "resume"})
    @Query("SELECT u FROM UserApplication u WHERE u.nickname = ?1")
    Optional<UserApplicationBioSocialProjection> findUserApplicationBioSocialProjectionByNickname(String nickname);

    @Query(value = """
            SELECT u.nickname as nickname,
            b.fullName as fullName
            FROM UserApplication u
            JOIN Bio b ON u.bio.id = b.id
            WHERE u.nickname ILIKE %?1% OR b.fullName ILIKE %?1%
            """)
    List<UserApplicationBasicSearch> findAllByContainingNicknameOrFullName(String nickOrName);
}
