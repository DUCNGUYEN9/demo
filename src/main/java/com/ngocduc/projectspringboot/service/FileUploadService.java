package com.ngocduc.projectspringboot.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileUploadService {
    void init();
    Path uploadFile(MultipartFile multipartFile);
    Resource load(String fileName);
}
