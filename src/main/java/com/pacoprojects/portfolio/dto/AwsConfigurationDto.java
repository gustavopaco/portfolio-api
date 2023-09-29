package com.pacoprojects.portfolio.dto;

import lombok.Builder;

@Builder
public record AwsConfigurationDto(String accessKey, String secretKey, String region, String bucketName) {
}
