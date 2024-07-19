package com.lets.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.lets.file.model.FileInfo;
import com.lets.file.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Value("${aws.s3.bucket-name}")
    private String awsS3BucketName;

    @Override
    public FileInfo storeFile(MultipartFile file, String uploadedBy) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileKey = generateFileKey(fileName);
        String fileUrl = uploadFileToS3(fileKey, file);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileKey); // Using fileKey as fileId
        fileInfo.setUploadedBy(uploadedBy);
        fileInfo.setFileLink(fileUrl);

        return fileInfoRepository.save(fileInfo);
    }

    @Override
    public List<FileInfo> getAllFiles() {
        return fileInfoRepository.findAll();
    }

    private String generateFileKey(String fileName) {
        // Generate a unique key for the file based on your requirement
        return "file_" + System.currentTimeMillis() + "_" + fileName;
    }

    private String uploadFileToS3(String fileKey, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(awsS3BucketName, fileKey, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3.getUrl(awsS3BucketName, fileKey).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file to S3", e);
        }
    }
}


