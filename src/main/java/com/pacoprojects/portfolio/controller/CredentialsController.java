package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.dto.AwsConfigurationDto;
import com.pacoprojects.portfolio.service.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialsController {

    private final CredentialsService credentialsService;

    @GetMapping("aws")
    public ResponseEntity<AwsConfigurationDto> getAwsCredentials() {
        return ResponseEntity.ok(credentialsService.getAwsCredentials());
    }
}
