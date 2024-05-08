package com.Capstone.Project.infra.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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

        //멤버별 디렉토리 생성
        final String directory = memberId.toString() + "/";
        final String s3Filename = directory + originalFilename;
        final ObjectMetadata metadata = getObjectMetadata(multipartFile);

       /* try {
            amazonS3Client.putObject(bucket, s3Filename, multipartFile.getInputStream(), metadata);
            return amazonS3Client.getUrl(bucket, s3Filename).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 picture upload failed");
        }*/
        try {
            // member 디렉토리가 없을 경우 만들기
            if (!amazonS3Client.doesObjectExist(bucket, directory)) {
                amazonS3Client.putObject(bucket, directory, "");
            }
            amazonS3Client.putObject(bucket, s3Filename, multipartFile.getInputStream(), metadata);
            return amazonS3Client.getUrl(bucket, s3Filename).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 picture upload failed");
        }

    }

    //

    public ResponseEntity<byte[]> getObject(Long memberId, String storedFileName) throws IOException {

        final String directory = memberId.toString() + "/";
        String s3FileName = directory + storedFileName;
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, s3FileName));
        S3ObjectInputStream objectInputStream = o.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);

    }

    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }



}

