package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.enums.ProjectStatus;

import java.util.Set;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public interface UserApplicationProjection {
    Long getId();

    Set<SkillInfo> getSkills();

    Set<ProjectInfo> getProjects();

    BioInfo getBio();

    SocialInfo getSocial();

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Skill}
     */
    interface SkillInfo {
        Long getId();

        String getName();

        String getDescription();

        Integer getRating();

        String getPictureUrl();
    }

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Project}
     */
    interface ProjectInfo {
        Long getId();

        String getName();

        String getDescription();

        String getUrl();

        String getPictureUrl();

        String getPictureOrientation();

        ProjectStatus getStatus();
    }

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Bio}
     */
    interface BioInfo {
        Long getId();

        String getAvatarUrl();

        String getPresentation();

        String getTestimonial();
    }

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Social}
     */
    interface SocialInfo {
        Long getId();

        String getGithub();

        String getLinkedin();

        String getTwitter();

        String getInstagram();

        String getFacebook();
    }
}
