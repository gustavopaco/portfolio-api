package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthPasswordRecovery(@NotBlank String username) {
}
