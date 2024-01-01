package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.service.FileUploadService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService awsS3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart @NotNull MultipartFile file,
                                         @RequestParam String folder) {
        String objectUrl = awsS3Service.upload(file, folder);
        return ResponseEntity.ok(objectUrl);
    }

}
