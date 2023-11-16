package com.pacoprojects.portfolio.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.ProjectSectionBlock}
 */
public record ProjectSectionBlockDto(Long id, String title, String description) implements Serializable {
}
