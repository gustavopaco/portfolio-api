package com.pacoprojects.portfolio.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String upload(@NotNull MultipartFile file, String path, boolean isTemporary);
    String moveFile(@NotBlank String oldPath, @NotBlank String newPath);
    void delete(@NotBlank String key);
}
