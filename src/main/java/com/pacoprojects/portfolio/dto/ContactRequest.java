package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotNull;

public record ContactRequest(@NotNull String name,
                             @NotNull String email,
                             @NotNull String message) {
}
