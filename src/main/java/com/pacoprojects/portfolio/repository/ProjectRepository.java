package com.pacoprojects.portfolio.repository;

import com.pacoprojects.portfolio.projection.ProjectBasic;
import com.pacoprojects.portfolio.projection.ProjectProjection;
import com.pacoprojects.portfolio.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<ProjectBasic> findAllByUserApplicationId(Long id);

    @EntityGraph(attributePaths = {"projectSectionBlocks" ,"tags"})
    Optional<ProjectProjection> findProjectById(Long id);
}
