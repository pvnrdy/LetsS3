package com.fileupload.s3.controller;

import com.fileupload.s3.model.FileInfo;
import com.fileupload.s3.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public FileInfo uploadFile(@RequestParam("file") MultipartFile file,
                               @RequestParam("email") String email) throws IOException {
        return fileService.uploadFile(file, email);
    }

    @GetMapping
    public List<FileInfo> getAllFiles() {
        return fileService.getAllFiles();
    }
}
