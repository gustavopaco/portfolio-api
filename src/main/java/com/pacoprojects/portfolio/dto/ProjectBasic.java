package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.enums.ProjectStatus;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.Project}
 */
public interface ProjectBasic {
    Long getId();

    String getName();

    String getPictureUrl();

    String getPictureOrientation();

    ProjectStatus getStatus();
}
