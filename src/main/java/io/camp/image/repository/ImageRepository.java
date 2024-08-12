package io.camp.image.repository;

import io.camp.image.model.Image;
import io.camp.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByReview(Review review);
}
