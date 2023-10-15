package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.ProjectProjection;
import com.pacoprojects.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<ProjectProjection> findAllByUserApplicationId(Long id);

    Optional<ProjectProjection> findProjectById(Long id);
}
