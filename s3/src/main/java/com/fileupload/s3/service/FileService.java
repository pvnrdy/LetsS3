package com.fileupload.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fileupload.s3.model.FileInfo;
import com.fileupload.s3.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private AmazonS3 amazonS3;

    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Autowired
    private FileInfoRepository fileRepository;

    private String bucketName = "{your-bucket-name}";

    public FileInfo uploadFile(MultipartFile file, String email) throws IOException {
        File convFile = convertMultiPartToFile(file);
        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + file.getOriginalFilename();

        amazonS3.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), convFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setFileURL(fileUrl);
        fileInfo.setUploadedBy(email);

        return fileRepository.save(fileInfo);
    }

    public List<FileInfo> getAllFiles() {
        return fileRepository.findAll();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}