package io.camp.S3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;


    @Value("${cloud.aws.s3.bucketName}")
    private String defaultBucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    //presignedurl 생성
    public List<String> generatePresignedUrls(String bucketName, List<String> objectKeys) {
        return objectKeys.stream()
                .map(objectKey -> {
                    Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1시간동안 유효
                    GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.PUT)
                            .withExpiration(expiration);
                    return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
                })
                .collect(Collectors.toList());
    }

    //객체 url 생성
    public List<String> generatePermanentUrls(List<String> objectKeys) {
        return objectKeys.stream()
                .map(objectKey -> String.format("https://%s.s3.%s.amazonaws.com/%s", defaultBucketName, region, objectKey))
                .collect(Collectors.toList());
    }

}