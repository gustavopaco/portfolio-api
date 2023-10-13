package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.enums.ProjectStatus;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.Project}
 */
public interface ProjectProjection {
    Long getId();

    String getName();

    String getDescription();

    String getUrl();

    ProjectStatus getStatus();

    String getPictureUrl();

    String getPictureOrientation();
}
