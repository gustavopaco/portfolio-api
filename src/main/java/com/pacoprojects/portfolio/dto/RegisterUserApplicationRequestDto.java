package com.pacoprojects.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public record RegisterUserApplicationRequestDto(@NotBlank(message = "Username is mandatory") String username,
                                                @NotBlank(message = "Nickname is mandatory") String nickname) implements Serializable {
}
