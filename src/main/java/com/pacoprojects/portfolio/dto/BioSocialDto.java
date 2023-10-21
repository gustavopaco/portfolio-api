package com.pacoprojects.portfolio.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BioSocialDto(@Valid @NotNull(message = "User bio is mandatory") BioDto bio,
                           @NotNull(message = "User social is mandatory") SocialDto social) {
}
