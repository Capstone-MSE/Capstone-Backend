package com.Capstone.Project.infra.s3.controller;

import com.Capstone.Project.infra.s3.service.AmazonS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PhotoController {

    private final AmazonS3Service s3Service;

    public PhotoController(AmazonS3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/photo")
    public ResponseEntity<String> profilePhotoUploadAndUpdate(@RequestPart("profile_photo") MultipartFile multipartFile) {
        final String profilePhotoUrl = s3Service.uploadPhoto(1L, multipartFile);
        return ResponseEntity.ok().body(profilePhotoUrl);
    }

    @PostMapping("/photos")
    public ResponseEntity<List<String>> profilePhotosUploadAndUpdate(@RequestPart("profile_photos") List<MultipartFile> files) {
        List<String> profilePhotoUrls = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            final String profilePhotoUrl = s3Service.uploadPhoto(1L, file);
            profilePhotoUrls.add(profilePhotoUrl);
        }
        return ResponseEntity.ok().body(profilePhotoUrls);
    }
}
