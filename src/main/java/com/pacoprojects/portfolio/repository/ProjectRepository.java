package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.dto.ProjectProjection;
import com.pacoprojects.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<ProjectProjection> findAllByUserApplicationId(Long id);

    Optional<ProjectProjection> findProjectById(Long id);
}
