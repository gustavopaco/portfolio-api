package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.AuthRequest;
import com.pacoprojects.portfolio.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
    public void authenticate(@RequestBody @NotNull @Valid AuthRequest authRequest, HttpServletResponse response) {
        authService.authenticate(authRequest, response);
    }
}
