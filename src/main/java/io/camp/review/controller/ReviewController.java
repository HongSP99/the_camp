package io.camp.review.controller;

import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.service.ReviewService;
import io.camp.review.model.dto.LikeReviewDto;
import io.camp.user.jwt.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "리뷰 생성", description = "캠핑장 마다 리뷰를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰가 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "로그인하지 않은 사용자입니다."),
    })
    @PostMapping("/{campsiteId}")
    public ResponseEntity<ReviewDto> createReview(@PathVariable("campsiteId") Long campsiteId,
                                                  @RequestBody CreateReviewDto createReviewDto,
                                                  @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        ReviewDto createdReview = reviewService.createReview(campsiteId, createReviewDto, jwtUserDetails);
        return ResponseEntity.ok(createdReview);
    }

    @Operation(summary = "모든 리뷰 정렬", description = "모든 캠핑장 리뷰를 정렬합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정렬된 모든 캠핑장 리스트를 반환합니다"),
    })
    @GetMapping("/sort")
    public ResponseEntity<Page<ReviewDto>> getAllReviewSort(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "6") int size,
                                                            @RequestParam(value = "type") String type) {
        Page<ReviewDto> reviewSort = reviewService.getAllReviewSort(page, size, type);
        return ResponseEntity.ok(reviewSort);
    }

    @Operation(summary = "캠핑장 리뷰 정렬", description = "캠핑장 마다 리뷰를 정렬합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정렬된 캠핑장 리뷰를 반환합니다."),
    })
    @GetMapping("/campsite/{campsiteId}")
    public ResponseEntity<Page<ReviewDto>> getReview(@PathVariable("campsiteId") Long campsiteId,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "6") int size,
                                                     @RequestParam(value = "type") String type) {
        Page<ReviewDto> review = reviewService.getReview(campsiteId, page, size, type);
        return ResponseEntity.ok(review);
    }


    @Operation(summary = "리뷰 단 건 조회", description = "리뷰 단건을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 단건 조회에 성공했습니다."),
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewOne(@PathVariable("reviewId") Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewOne(reviewId);
        return ResponseEntity.ok(reviewDto);
    }




    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 수정에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "작성자가 아닙니다."),
            @ApiResponse(responseCode = "404", description = "로그인하지 않은 사용자입니다."),
    })
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("reviewId") Long reviewId,
                                                  @RequestBody UpdateReviewDto updateReviewDto,
                                                  @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        ReviewDto reviewDto = reviewService.updateReview(reviewId, updateReviewDto, jwtUserDetails);
        return ResponseEntity.ok(reviewDto);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 삭제에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "작성자가 아닙니다."),
            @ApiResponse(responseCode = "404", description = "로그인하지 않은 사용자입니다."),
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId,
                                             @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        reviewService.deleteReview(reviewId, jwtUserDetails);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요", description = "좋아요 기능입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요가 정상적으로 실행되었습니다."),
            @ApiResponse(responseCode = "404", description = "로그인하지 않은 사용자입니다."),
    })
    @GetMapping("/like/{reviewId}")
    public ResponseEntity<LikeReviewDto> likeReview(@PathVariable("reviewId") Long reviewId,
                                                    @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        LikeReviewDto likeReviewDto = reviewService.likeReview(reviewId, jwtUserDetails);
        return ResponseEntity.ok(likeReviewDto);
    }
}
