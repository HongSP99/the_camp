package io.camp.image.controller;

import io.camp.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/presigned-urls")
    public ResponseEntity<List<String>> getPresignedUrls(@RequestParam("count") int count) {
        List<String> presignedUrls = imageService.generatePresignedUrls(count);
        return ResponseEntity.ok(presignedUrls);
    }
}