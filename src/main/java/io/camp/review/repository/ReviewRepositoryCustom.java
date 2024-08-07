package io.camp.review.repository;

import com.querydsl.core.types.OrderSpecifier;
import io.camp.review.model.Review;
import io.camp.review.model.dto.ReviewDto;
import io.camp.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewDto> getAllCampsiteReviewSort(Long campsiteId, Pageable pageable);
    Page<ReviewDto> getAllReviewSort(Pageable pageable);
    ReviewDto getCampsiteReview(Long reviewId);
    long updateReview(Long reviewId, String content);
}
