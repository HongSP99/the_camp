package io.camp.image.controller;

import io.camp.image.model.Image;
import io.camp.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


    //클라이언트의 presgined-url 생성 요청
    @GetMapping("/presigned-urls")
    public ResponseEntity<List<String>> getPresignedUrls(@RequestParam("count") int count) {
        List<String> presignedUrls = imageService.generatePresignedUrls(count);
        return ResponseEntity.ok(presignedUrls);
    }

    @PostMapping
    public ResponseEntity<List<Map<String, Object>>> saveImages(@RequestBody Map<String, Object> payload) {
        List<String> imageUrls = (List<String>) payload.get("imageUrls");
        Long reviewId = Long.parseLong(payload.get("reviewId").toString());
        List<Map<String, Object>> savedImages = imageService.saveImages(imageUrls, reviewId);
        return ResponseEntity.ok(savedImages);
    }

    //클라이언트의 이미지 조회 요청
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok(image);
    }
}