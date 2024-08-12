package io.camp.image.service;

import io.camp.S3.service.S3Service;
import io.camp.image.model.Image;
import io.camp.image.repository.ImageRepository;
import io.camp.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public List<String> generatePresignedUrls(int count) {
        List<String> fileNames = IntStream.range(0, count)
                .mapToObj(i -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
        return s3Service.generatePresignedUrls("the-camp", fileNames);
    }

    @Transactional
    public Image createImage(String fileName, String fileUrl, Review review) {
        Image image = Image.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .review(review)
                .build();
        return imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public List<Image> getImagesByReview(Review review) {
        return imageRepository.findByReview(review);
    }

    @Transactional
    public void deleteImage(Image image) {
        s3Service.deleteFile(image.getFileName());
        imageRepository.delete(image);
    }
}