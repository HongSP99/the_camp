package io.camp.S3.controller;

import io.camp.S3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        List<String> fileUrls = s3Service.uploadFiles(files);
        return ResponseEntity.ok(fileUrls);
    }

    @GetMapping("/presigned-urls")
    public ResponseEntity<List<String>> getPresignedUrls(@RequestParam String bucketName, @RequestParam List<String> objectKeys) {
        List<String> presignedUrls = s3Service.generatePresignedUrls(bucketName, objectKeys);
        return ResponseEntity.ok(presignedUrls);

        
    }


}