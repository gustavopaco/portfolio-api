package com.pacoprojects.portfolio.dto;

import java.time.LocalDate;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.Course}
 */
public interface CourseProjection {
    Long getId();

    String getName();

    String getIssuer();

    LocalDate getEndDate();
}
