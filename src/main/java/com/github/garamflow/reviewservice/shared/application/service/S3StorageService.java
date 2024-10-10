package com.github.garamflow.reviewservice.shared.application.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {

    String uploadFile(MultipartFile file);
}
