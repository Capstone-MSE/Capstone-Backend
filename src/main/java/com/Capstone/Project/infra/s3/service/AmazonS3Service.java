package com.Capstone.Project.infra.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public AmazonS3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String uploadPhoto(Long memberId, MultipartFile multipartFile) {
        final String originalFilename = multipartFile.getOriginalFilename();
        final String s3Filename = memberId + "." + originalFilename;
        final ObjectMetadata metadata = getObjectMetadata(multipartFile);

        try {
            amazonS3Client.putObject(bucket, s3Filename, multipartFile.getInputStream(), metadata);
            return amazonS3Client.getUrl(bucket, s3Filename).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 picture upload failed");
        }
    }

    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }
}

