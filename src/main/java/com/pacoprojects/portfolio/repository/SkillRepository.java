package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.SkillProjection;
import com.pacoprojects.portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<SkillProjection> findAllByUserApplicationId(Long id);
}
