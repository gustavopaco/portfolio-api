package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.config.AwsConfiguration;
import com.pacoprojects.portfolio.dto.AwsConfigurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialsService {

    private final AwsConfiguration awsConfiguration;

    public AwsConfigurationDto getAwsCredentials() {
        return AwsConfigurationDto.builder()
                .accessKey(awsConfiguration.getAccessKey())
                .secretKey(awsConfiguration.getSecretKey())
                .region(awsConfiguration.getRegion())
                .bucketName(awsConfiguration.getBucketName())
                .build();
    }
}
