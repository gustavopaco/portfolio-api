package com.pacoprojects.portfolio.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String upload(MultipartFile file, String path, boolean isTemporary);
    void delete(String key);
}
