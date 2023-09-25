package com.pacoprojects.portfolio.dto;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.Skill}
 */
public interface SkillProjection {
    Long getId();

    String getName();

    String getDescription();

    Integer getRating();

    String getUrl();
}
