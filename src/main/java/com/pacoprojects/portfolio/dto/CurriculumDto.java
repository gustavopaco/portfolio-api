package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Curriculum}
 */
public record CurriculumDto(Long id, @NotBlank(message = "Url file is required") String url,
                            String contentType) implements Serializable {
}
