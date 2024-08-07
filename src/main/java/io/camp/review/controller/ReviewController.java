package io.camp.review.controller;

import io.camp.review.model.Review;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.service.ReviewService;
import io.camp.user.jwt.JwtUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @PostMapping("/{campsiteId}")
    public ResponseEntity<ReviewDto> createReview(@PathVariable("campsiteId") Long campsiteId,
                                                  @RequestBody CreateReviewDto createReviewDto,
                                                  @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        ReviewDto createdReview = reviewService.createReview(campsiteId, createReviewDto, jwtUserDetails);
        return ResponseEntity.ok(createdReview);
    }


    @GetMapping("/desc/like")
    public ResponseEntity<Page<ReviewDto>> getAllReviewLikeCountDesc(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<ReviewDto> reviewSort = reviewService.getAllReviewLikeCountDesc(page, size);
        return ResponseEntity.ok(reviewSort);
    }

    @GetMapping("/desc")
    public ResponseEntity<Page<ReviewDto>> getAllReviewDesc(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<ReviewDto> reviewSort = reviewService.getAllReviewDesc(page, size);
        return ResponseEntity.ok(reviewSort);
    }

    @GetMapping("/campsite/{campsiteId}")
    public ResponseEntity<Page<ReviewDto>> getReview(@PathVariable("campsiteId") Long campsiteId,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<ReviewDto> review = reviewService.getReview(campsiteId, page, size);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewOne(@PathVariable("reviewId") Long reviewId) {
        ReviewDto review = reviewService.getReviewOne(reviewId);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("reviewId") Long reviewId,
                                                  @RequestBody UpdateReviewDto updateReviewDto,
                                                  @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        ReviewDto reviewDto = reviewService.updateReview(reviewId, updateReviewDto, jwtUserDetails);
        return ResponseEntity.ok(reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId,
                                             @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        reviewService.deleteReview(reviewId, jwtUserDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/like/{reviewId}")
    public ResponseEntity<Void> likeReview(@PathVariable("reviewId") Long reviewId,
                                           @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        reviewService.likeReview(reviewId, jwtUserDetails);
        return ResponseEntity.ok().build();
    }
}
