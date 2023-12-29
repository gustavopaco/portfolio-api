package com.pacoprojects.portfolio.projection;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.Bio}
 */
public interface BioProjection {
    Long getId();

    String getAvatarUrl();

    String getPresentation();

    String getTestimonial();

    String getJobTitle();

    String getFullName();
}
