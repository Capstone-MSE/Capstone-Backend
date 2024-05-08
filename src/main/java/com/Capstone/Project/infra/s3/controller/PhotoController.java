package com.Capstone.Project.infra.s3.controller;

import com.Capstone.Project.infra.s3.service.AmazonS3Service;
import com.amazonaws.services.s3.transfer.Download;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PhotoController {

    private final AmazonS3Service s3Service;

    public PhotoController(AmazonS3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/{memberId}/photo")
    public ResponseEntity<String> profilePhotoUploadAndUpdate(
            // memberId 값 가져오기
            @PathVariable Long   memberId,
            @Valid @RequestPart("profile_photo") MultipartFile multipartFile) {
        final String profilePhotoUrl = s3Service.uploadPhoto(memberId, multipartFile);

        return ResponseEntity.ok().body(profilePhotoUrl);
    }

    @PostMapping("/{memberId}/photos")
    public ResponseEntity<List<String>> profilePhotosUploadAndUpdate(
            @PathVariable Long memberId,
            @RequestPart("profile_photos") List<MultipartFile> files) {
        List<String> profilePhotoUrls = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            final String profilePhotoUrl = s3Service.uploadPhoto(memberId, file);
            profilePhotoUrls.add(profilePhotoUrl);
        }
        return ResponseEntity.ok().body(profilePhotoUrls);
    }

   /* @GetMapping("/csv_download")
    public ResponseEntity<byte[]> download() throws IOException {
        return s3Service.getObject("1.testExcel.xlsx");
    }
*/
/*
    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {}
*/


}
