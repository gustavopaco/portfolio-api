package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.projection.SkillProjection;
import com.pacoprojects.portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<SkillProjection> findAllByUserApplicationId(Long id);

    @Query("""
        SELECT s from Skill s
        JOIN FETCH s.userApplication u
        WHERE u.nickname = ?1
        ORDER BY s.id ASC
    """)
    Set<Skill> findAllByUserApplicationNickname(String nickname);
}
