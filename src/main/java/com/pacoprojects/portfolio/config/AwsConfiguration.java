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
    @Value("${aws.s3.bucket.path}")
    private String bucketPath;
    @Value("${aws.s3.bucket.temp.name}")
    private String tempBucketName;
    private static final String SEPARATOR_SLASH = "/";

    public AwsCredentials getAwsCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }

    public Region getAwsRegion() {
        return Region.of(region);
    }

    private String formatPath(String folder) {
        if (bucketPath.endsWith(SEPARATOR_SLASH)) {
            return bucketPath + folder;
        }
        return bucketPath + SEPARATOR_SLASH + folder;
    }

    public String getPathAvatar() {
        return formatPath("avatars");
    }

    public String getPathCertificates() {
        return formatPath("certificates");
    }

    public String getPathProjects() {
        return formatPath("projects");
    }

    public String getPathResume() {
        return formatPath("resumes");
    }

    public String getPathSkills() {
        return formatPath("skills");
    }
}
