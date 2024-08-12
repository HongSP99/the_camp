package io.camp.S3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String defaultBucketName;


    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(file -> {
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(file.getSize());
                    metadata.setContentType(file.getContentType());

                    try {
                        amazonS3.putObject(defaultBucketName, fileName, file.getInputStream(), metadata);
                        return amazonS3.getUrl(defaultBucketName, fileName).toString();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file", e);
                    }
                })
                .collect(Collectors.toList());
    }

    //삭제
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(defaultBucketName, fileName);
    }


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





}