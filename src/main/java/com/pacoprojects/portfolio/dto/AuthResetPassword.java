package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthResetPassword(@NotBlank String password, @NotBlank String token) {
}
