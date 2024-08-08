package io.camp.review.repository;

import io.camp.review.model.dto.LikeReviewDto;
import io.camp.review.model.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewDto> getAllCampsiteReviewSort(Long campsiteId, Pageable pageable);
    Page<ReviewDto> getAllReviewSort(Pageable pageable);
    ReviewDto getCampsiteReview(Long reviewId);
    long updateReview(Long reviewId, String content);
    LikeReviewDto getLikeCount(Long reviewId);
}
