package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.config.AwsConfiguration;
import com.pacoprojects.portfolio.constants.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
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

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service implements FileUploadService{

    private final AwsConfiguration awsConfiguration;
    static final String SEPARATOR_UNDERSCORE = "_";
    static final String SEPARATOR_DASH = "-";
    static final String SEPARATOR_SLASH = "/";

    private StaticCredentialsProvider getCredentialsProvider() {
        // get Credentials
        AwsCredentials awsCredentials = awsConfiguration.getAwsCredentials();
        // create Credentials Provider
        return StaticCredentialsProvider.create(awsCredentials);
    }

    private S3Client getS3Client() {
        // get Credentials Provider
        StaticCredentialsProvider credentialsProvider = getCredentialsProvider();
        // get Region
        Region region = awsConfiguration.getAwsRegion();
        // create S3 Client
        return S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Override
    public String upload(@NotNull MultipartFile file, String path) {

        final String DEFAULT_PATH = (path == null || path.isEmpty()) ? "default" : path;

        // create S3 Client
        try (S3Client s3Client = getS3Client()) {

            // prepare file name
            String fileName = prepareFileName(file.getOriginalFilename());

            // PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsConfiguration.getBucketName())
                    .key(DEFAULT_PATH + SEPARATOR_SLASH + fileName)
                    .contentType(file.getContentType())
                    .build();

            // RequestBody
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            // upload file
            s3Client.putObject(putObjectRequest, requestBody);

            // if upload is successful, return object URL
            String objectURL = s3Client.utilities().getUrl(builder -> builder
                    .bucket(awsConfiguration.getBucketName())
                    .key(DEFAULT_PATH + SEPARATOR_SLASH + fileName))
                    .toString();

            // return decoded URL
            return URLDecoder.decode(objectURL, StandardCharsets.UTF_8);

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

    private String prepareFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return UUID.randomUUID() + SEPARATOR_UNDERSCORE + "unknown";
        }

        // if fileName contains SEPARATOR_UNDERSCORE replace with SEPARATOR_DASH
        if (fileName.contains(SEPARATOR_UNDERSCORE)) {
            fileName = fileName.replace(SEPARATOR_UNDERSCORE, SEPARATOR_DASH);
        }
        return UUID.randomUUID() + SEPARATOR_UNDERSCORE + fileName;
    }

    @Override
    @Async
    public void delete(String key) {

        final String OBJECT_KEY = key.split(".com/")[1];

        try (S3Client s3Client = getS3Client()) {

            // delete file
            s3Client.deleteObject(builder -> builder
                    .bucket(awsConfiguration.getBucketName())
                    .key(OBJECT_KEY));

        } catch (AwsServiceException e) {
            String errorMessage = Messages.AWS_SERVICE_EXCEPTION + key;
            throw new ResponseStatusException(HttpStatusCode.valueOf(e.statusCode()), errorMessage, e.getCause());
        } catch (SdkClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.SDK_CLIENT_EXCEPTION, e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR, e.getCause());
        }
    }
}
