//
//package com.Capstone.Project.infra.s3.controller;
//import com.Capstone.Project.infra.s3.*;
//import com.Capstone.Project.infra.s3.model.ImageFile;
//import com.Capstone.Project.infra.s3.model.ImageRequest;
//import com.Capstone.Project.infra.s3.model.UploadedImage;
//import lombok.RequiredArgsConstructor;
//import lombok.Value;
//import org.apache.coyote.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//
//import java.io.IOException;
//
////@RestController
////@RequestMapping("/boards")
////@RequiredArgsConstructor
////public class BoardController {
////    private final S3Client amazonS3Client;
////
////    @Value("${zoqtmxhs}")
////    private String bucket;
////
////    @PostMapping
////    public ResponseEntity<String> UploadedImage(@RequestParam("file")MultipartFilefile) {
////        try {
////            StringfileName=file.getOriginalFilename();
////            StringfileUrl= "https://" + bucket + "/test" +fileName;
////            ObjectMetadatametadata= new ObjectMetadata();
////            metadata.setContentType(file.getContentType());
////            metadata.setContentLength(file.getSize());
////            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
////            return ResponseEntity.ok(fileUrl);
////        } catch (IOException e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
////        }
////    }
////}
//
//
////
////
////@RestController
////@RequestMapping("/upload")
////@RequiredArgsConstructor
////public class BoardController {
////
////    private final S3Client amazonS3Client;
////
////    @PostMapping
////    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
////        String url = amazonS3Client.upload(file);
////        return Response.success(url);
////    }
////
////
////    private final StorageService service;
////
////    @PostMapping("/{member-id}")
////    public void postMember(
////            @PathVariable("member-id") @Positive long memberId,
////            @RequestParam(value = "file") MultipartFile file) {
////
////        service.deleteFile(Long.toString(memberId));
////        service.uploadFile(file, memberId);
////    }
////}
//
//
//package project.AMS.awsS3;
//
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import project.AMS.awsS3.image.Image;
//import project.AMS.awsS3.image.ImageRepository;
//import project.AMS.awsS3.image.ImageService;
//import project.AMS.awsS3.article.Article;
//import project.AMS.awsS3.article.ArticleRepository;
//import project.AMS.awsS3.article.ArticleService;
//import project.AMS.awsS3.s3.FileService;
//import project.AMS.awsS3.common.CommonUtils;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class BoardController {
//
//    private final ImageFile imageFile;
//    private final ImageRequest imageRequest;
//    private final UploadedImage uploadedImage;
//
//
//    //사진 저장
//    //1. Request : multipart + form-data
//    @PostMapping("/save-form-data")
//    public String saveByFormData(@RequestParam("title") String title,
//                                 @RequestParam("context") String context,
//                                 @RequestParam("imageFileList") List<MultipartFile> lists) {
//
//        //게시물 저장
//        Long saveArticleId = imageFile.(title, context);
//
//        //이미지 저장
//        uploadedImage.saveArticleImages(saveArticleId, lists);
//
//        return "save success";
//    }
//    //2. Request : multipart + json
//    @PostMapping(value = "/save-json", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public String saveByJson(@RequestPart ArticleForm form,
//                             @RequestPart List<MultipartFile> imgFiles) {
//
//        Long saveArticleId = articleService.saveArticle(form.getTitle(), form.getContent());
//        imageService.saveArticleImages(saveArticleId, imgFiles);
//        return "save success";
//    }
//
//    //게시물 단건 조회
//    @GetMapping("/{id}")
//    public ArticleDto find(@PathVariable Long id){
//
//        Article article = articleService.findArticle(id);
//        List<Image> images = imageRepository.findByArticleId(id);
//
//        return new ArticleDto(article, images);
//    }
//
//    //게시물 복수개 조회
//    @GetMapping("/all")
//    public List<ArticleDto> findAll() {
//
//        List<Article> articles = articleRepository.findAll();
//        return articles.stream().map(a -> new ArticleDto(a, imageRepository.findByArticleId(a.getId())))
//                .collect(Collectors.toList());
//    }
//
//    //게시물 삭제
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long articleId){
//
//        imageService.deleteArticleImage(articleId);
//        articleService.deleteArticle(articleId);
//
//        return "delete success";
//    }
//
//    //게시물 수정(정보, 사진 수정)
//    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public String update(@PathVariable("id") Long articleId,
//                         @RequestPart ArticleEditForm form,
//                         @RequestParam List<MultipartFile> images){
//
//        articleService.editArticle(articleId, form.getTitle(), form.getContent());
//        imageService.editArticleImages(articleId, images);
//
//        return "edit success";
//    }
//
//    //파일 다운로드
//    @GetMapping("/download/{id}")
//    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") Long imageId) throws FileNotFoundException {
//
//        Image image = imageRepository.findById(imageId).orElseThrow(() -> new NullPointerException("존재하지 않은 파일 입니다."));
//
//        byte[] bytes = fileService.downloadFile(image.getStorageImageName());
//        ByteArrayResource resource = new ByteArrayResource(bytes);
//        HttpHeaders headers = buildHeaders(image.getOriginalImageName(), bytes);
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .body(resource);
//    }
//
//    private HttpHeaders buildHeaders(String fileName, byte[] data) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentLength(data.length);
//        headers.setContentType(CommonUtils.contentType(fileName));
//        headers.setContentDisposition(CommonUtils.createContentDisposition(fileName));
//        return headers;
//    }
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class ArticleForm {
//        private String title;
//        private String content;
//    }
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class ArticleEditForm {
//        private String title;
//        private String content;
//    }
//    @Data
//    static class ArticleDto {
//        private Long id;
//        private String title;
//        private String content;
//        List<String> images;
//
//        public ArticleDto(Article article, List<Image> images) {
//            this.id = article.getId();
//            this.title = article.getTitle();
//            this.content = article.getContent();
//            this.images = images.stream().map( image -> new String(image.getImagePath())).collect(Collectors.toList());
//        }
//    }
//
//}
