package com.pacoprojects.portfolio.dto;

import java.util.Set;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public interface UserApplicationSkillsProjection {
    Long getId();

    String getUrlPicture();

    Set<SkillInfo> getSkills();

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Skill}
     */
    interface SkillInfo {
        Long getId();

        String getName();

        String getDescription();

        String getUrl();
    }
}
