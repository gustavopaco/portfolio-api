package com.pacoprojects.portfolio.dto;

import java.util.Set;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public interface UserApplicationProjectsSkillsProjection {
    Long getId();

    String getUrlPicture();

    Set<SkillInfo> getSkills();

    Set<ProjectInfo> getProjects();

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Skill}
     */
    interface SkillInfo {
        Long getId();

        String getName();

        String getDescription();

        Integer getRating();

        String getUrl();
    }

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Project}
     */
    interface ProjectInfo {
        Long getId();

        String getName();

        String getDescription();

        String getUrl();
    }
}
