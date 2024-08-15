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

    @GetMapping("/presigned-urls")
    public ResponseEntity<List<String>> getPresignedUrls(@RequestParam String bucketName, @RequestParam List<String> objectKeys) {
        List<String> presignedUrls = s3Service.generatePresignedUrls(bucketName, objectKeys);
        return ResponseEntity.ok(presignedUrls);
    }


    @GetMapping("/permanent-urls")
    public ResponseEntity<List<String>> getPermanentUrls(@RequestBody List<String> objectKeys) {
        List<String> permanentUrls = s3Service.generatePermanentUrls(objectKeys);
        return ResponseEntity.ok(permanentUrls);
    }
}
