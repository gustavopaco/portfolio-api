package com.pacoprojects.portfolio.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Certification}
 */
public record CertificationDto(Long id, String url) implements Serializable {
}
