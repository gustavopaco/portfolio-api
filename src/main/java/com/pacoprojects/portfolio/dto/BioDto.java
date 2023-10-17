package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Bio}
 */
public record BioDto(Long id, String avatarUrl,
                     @Size(message = "Presentation must be less than 500 characters", max = 500) String presentation,
                     @Size(message = "Testimonial must be less than 500 characters", max = 500) String testimonial) implements Serializable {
}
