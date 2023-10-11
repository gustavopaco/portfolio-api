package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Project}
 */
public record ProjectDto(Long id,
                         @NotBlank(message = "Name is mandatory") @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String name,
                         @NotBlank(message = "Description is mandatory") String description,
                         String url,
                         @NotNull(message = "Project status is mandatory") ProjectStatus projectStatus,
                         String pictureUrl,
                         String pictureOrientation) implements Serializable {
}
