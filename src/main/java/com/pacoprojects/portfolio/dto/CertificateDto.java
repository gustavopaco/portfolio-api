package com.pacoprojects.portfolio.dto;

import com.pacoprojects.portfolio.model.Certificate;

import java.io.Serializable;

/**
 * DTO for {@link Certificate}
 */
public record CertificateDto(Long id, String url) implements Serializable {
}
