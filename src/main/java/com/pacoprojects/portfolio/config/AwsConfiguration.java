package com.pacoprojects.portfolio.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AwsConfiguration {
    @Value("${aws.s3.credentials.access-key}")
    private String accessKey;
    @Value("${aws.s3.credentials.secret-key}")
    private String secretKey;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
}
