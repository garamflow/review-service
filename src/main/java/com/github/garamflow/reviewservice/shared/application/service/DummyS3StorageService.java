package com.github.garamflow.reviewservice.shared.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class DummyS3StorageService implements S3StorageService {

    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        return "https://dummy-s3-url.com/" + UUID.randomUUID() + "/" + file.getOriginalFilename();
    }
}