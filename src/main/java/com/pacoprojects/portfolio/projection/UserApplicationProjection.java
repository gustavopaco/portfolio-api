package com.pacoprojects.portfolio.projection;

import java.util.Set;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public interface UserApplicationProjection {
    Long getId();

    Set<SkillInfo> getSkills();

    Set<ProjectBasic> getProjects();

    BioInfo getBio();

    SocialInfo getSocial();

    Set<CourseProjection> getCourses();

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
     * Projection for {@link com.pacoprojects.portfolio.model.Bio}
     */
    interface BioInfo {
        Long getId();

        String getAvatarUrl();

        String getPresentation();

        String getTestimonial();

        String getJobTitle();

        String getFullName();
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
