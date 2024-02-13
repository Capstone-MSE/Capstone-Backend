package com.Capstone.Project.infra.s3.service;

import com.Capstone.Project.infra.s3.model.ImageRequest;
import com.Capstone.Project.infra.s3.model.UploadedImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmazonS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client amazonS3Client;

    public ArrayList<UploadedImage> uploadImages(List<ImageRequest> images) {
        ArrayList<UploadedImage> uploadedImages = new ArrayList<>();
        for (ImageRequest image : images) {
            uploadedImages.add(uploadImage(image));
        }
        return uploadedImages;
    }

    public UploadedImage uploadImage(ImageRequest image) {
        String originName = image.getOriginalImageName();
        if (originName == null) originName = "";

        String ext = originName.substring(originName.lastIndexOf(".") + 1);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(createFileName(originName, ext))
                .contentType(ext)
                .metadata(createObjectMetadata(image.getSize()))
                .build();

        return upload(image, putObjectRequest);
    }

    private UploadedImage upload(ImageRequest image, PutObjectRequest putObjectRequest) {
        try {
            amazonS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
            return new UploadedImage(putObjectRequest.key(), image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build());
    }

    private String createFileName(String fileName, String extension) {
        return makeObjName(fileName, UUID.randomUUID() + "." + extension);
    }

    private String makeObjName(String fileName, String id) {
        return fileName + "-" + id;
    }

    private Map<String, String> createObjectMetadata(long size) {
        return Map.of(
                "Content-Length", String.valueOf(size)
        );
    }
}
