package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.ProjectProjection;
import com.pacoprojects.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<ProjectProjection> findAllByUserApplicationId(Long id);
}
