package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.config.AwsConfiguration;
import com.pacoprojects.portfolio.constants.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AwsConfiguration awsConfiguration;

    public String upload(@NotNull MultipartFile file, String folder) {

        // get Credentials
        AwsCredentials awsCredentials = awsConfiguration.getAwsCredentials();
        // get Region
        Region region = awsConfiguration.getAwsRegion();
        // get Bucket Name
        String bucketName = awsConfiguration.getBucketName();
        // create Credentials Provider
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

        // create S3 Client
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build()) {

            // generate UUID and concat with file name to create a unique file name
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folder + "/" + fileName)
                    .build();

            // RequestBody
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            // upload file
            s3Client.putObject(putObjectRequest, requestBody);

            // if response is 200 OK, then return object URL
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(folder + "/" + fileName)).toString();

        } catch (IOException e) {
            String errorMessage = Messages.IO_EXCEPTION + file.getOriginalFilename();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e.getCause());
        } catch (AwsServiceException e) {
            String errorMessage = Messages.AWS_SERVICE_EXCEPTION + file.getOriginalFilename();
            throw new ResponseStatusException(HttpStatusCode.valueOf(e.statusCode()), errorMessage, e.getCause());
        } catch (SdkClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.SDK_CLIENT_EXCEPTION, e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR, e.getCause());
        }
    }
}
