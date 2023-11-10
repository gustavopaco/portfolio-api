package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Course}
 */
public record CourseDto(Long id, @Size(message = "Name must be less than 64 characters", max = 64) String name,
                        @Size(message = "Issuer must be less than 64 characters", max = 64) String issuer,
                        @NotNull(message = "End date is mandatory") @Past(message = "End date must be in the past") LocalDate endDate) implements Serializable {
}
