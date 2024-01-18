package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.Resume;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link Resume}
 */
public record ResumeDto(Long id, @NotBlank(message = "Url file is required") String url,
                        String contentType) implements Serializable {
}
