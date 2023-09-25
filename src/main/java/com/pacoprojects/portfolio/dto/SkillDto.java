package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Skill}
 */
public record SkillDto(Long id,
                       @NotBlank(message = "Name is mandatory") String name,
                       String description,
                       @NotBlank(message = "Url is mandatory") String url,
                       @NotNull(message = "Rating is mandatory")Integer rating,
                       UserApplicationDto userApplication) implements Serializable {
    /**
     * DTO for {@link com.pacoprojects.portfolio.model.UserApplication}
     */
    public record UserApplicationDto(@NotNull(message = "Id is mandatory") Long id) implements Serializable {
    }
}
