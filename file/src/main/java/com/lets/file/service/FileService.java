package com.lets.file.service;

import com.lets.file.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileInfo storeFile(MultipartFile file, String uploadedBy);
    List<FileInfo> getAllFiles();
}

