package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Bio}
 */
public record BioDto(Long id,
                     String avatarUrl,
                     @Size(message = "Presentation must be less than 250 characters", max = 250) String presentation,
                     @Size(message = "Testimonial must be less than 1000 characters", max = 1000) String testimonial,
                     @Size(max = 50, message = "Job title must be less than 50 characters") String jobTitle,
                     @Size(max = 50, message = "Full name must be less than 50 characters") String fullName) implements Serializable {
}
