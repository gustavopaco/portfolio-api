package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.AuthPasswordRecovery;
import com.pacoprojects.portfolio.dto.AuthRequest;
import com.pacoprojects.portfolio.dto.AuthResponse;
import com.pacoprojects.portfolio.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @NotNull @Valid AuthRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.authenticate(authRequest, response));
    }

    @PostMapping("password-recovery")
    public ResponseEntity<Void> passwordRecovery(@RequestBody @NotNull @Valid AuthPasswordRecovery recovery) {
        authService.passwordRecovery(recovery);
        return ResponseEntity.ok().build();
    }
}
