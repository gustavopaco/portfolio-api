package com.pacoprojects.portfolio.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;

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

    public AwsCredentials getAwsCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }

    public Region getAwsRegion() {
        return Region.of(region);
    }
}
