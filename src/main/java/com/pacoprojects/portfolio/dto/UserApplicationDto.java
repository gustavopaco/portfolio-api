package com.pacoprojects.portfolio.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public record UserApplicationDto(Long id, Set<SkillDto> skills, Set<ProjectDto> projects, BioDto bio,
                                 SocialDto social, Set<CourseDto> courses, Set<CertificateDto> certificates,
                                 ResumeDto resume) implements Serializable {
}
