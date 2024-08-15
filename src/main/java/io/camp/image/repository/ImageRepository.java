package io.camp.image.repository;

import io.camp.image.model.Image;
import io.camp.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByReview(Review review);

    @Query("SELECT i FROM Image i WHERE i.review.id = :reviewId")
    List<Image> findImagesByReviewId(@Param("reviewId") Long reviewId);

    List<Image> findByReviewId(Long reviewId);
}
