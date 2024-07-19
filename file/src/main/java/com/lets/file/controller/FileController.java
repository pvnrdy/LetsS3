package com.lets.file.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lets.file.model.FileInfo;
import com.lets.file.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileInfo> uploadFile(@RequestParam("file") MultipartFile file,
                                               @RequestParam("uploadedBy") String uploadedBy) {
        FileInfo fileInfo = fileService.storeFile(file, uploadedBy);
        return ResponseEntity.ok().body(fileInfo);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileInfo>> getAllFiles() {
        List<FileInfo> files = fileService.getAllFiles();
        return ResponseEntity.ok().body(files);
    }
}
