package io.camp.image.service;

import io.camp.S3.service.S3Service;
import io.camp.image.model.Image;
import io.camp.image.repository.ImageRepository;
import io.camp.review.model.Review;
import io.camp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    private final ReviewRepository reviewRepository;

    @Transactional
    public List<String> generatePresignedUrls(int count) {
        List<String> fileNames = IntStream.range(0, count)
                .mapToObj(i -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
        return s3Service.generatePresignedUrls("the-camp", fileNames);
    }

    @Transactional
    public Image createImage( String url, Review review) {
        Image image = Image.builder()
                .url(url)
                .review(review)
                .build();
        return imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public List<Image> getImagesByReview(Review review) {
        return imageRepository.findByReview(review);
    }

    @Transactional
    public List<Map<String, Object>> saveImages(List<String> imageUrls, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return imageUrls.stream()
                .map(url -> {
                    Image image = Image.builder()
                            .url(url)
                            .review(review)
                            .build();
                    Image savedImage = imageRepository.save(image);

                    Map<String, Object> result = new HashMap<>();
                    result.put("id", savedImage.getId());
                    result.put("url", savedImage.getUrl());
                    result.put("review_id", reviewId);

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
}