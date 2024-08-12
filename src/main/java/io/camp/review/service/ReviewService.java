package io.camp.review.service;

import com.amazonaws.services.s3.AmazonS3;
import io.camp.S3.service.S3Service;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.exception.review.ReviewNotFoundException;
import io.camp.like.service.LikeService;
import io.camp.review.model.Review;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.repository.ReviewRepository;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.camp.user.model.QUser.user;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService authService;
    private final CampSiteRepository campSiteRepository;
    private final LikeService likeService;
    private final S3Service s3Service;

    //리뷰 생성
    public ReviewDto createReview(Long campsiteId, CreateReviewDto createReviewDto, JwtUserDetails jwtUserDetails) {
        User uesr = jwtUserDetails.getUser();

        if (user == null) {
            throw new RuntimeException("로그인한 사용자가 아닙니다.");
        }

        Campsite campsite = campSiteRepository.findById(campsiteId)
                .orElseThrow(() -> new RuntimeException("캠핑장을 찾을 수 없습니다."));

        Review review = Review.builder()
                .content(createReviewDto.getContent())
                .user(uesr)
                .campsite(campsite)
                .build();
        Review savedReview = reviewRepository.save(review);
        return convertToDto(savedReview);
    }

    //전체 리뷰 조회 (좋아요 순)
    public Page<ReviewDto> getAllReviewLikeCountDesc() {
        Pageable Pageable = PageRequest.of(0, 6);
        return reviewRepository.getAllReviewLikeCountDesc(Pageable);
    }

    //전체 리뷰 조회 (최신 순)
    public Page<ReviewDto> getAllReviewDesc() {
        Pageable Pageable = PageRequest.of(0, 6);
        return reviewRepository.getAllReviewDesc(Pageable);
    }

    //리뷰 조회
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReview(Long campsiteId) {
        Pageable pageable = PageRequest.of(0, 6, Sort.by("createdAt").descending());
        Page<ReviewDto> reviews = reviewRepository.reviewJoinCampsite(campsiteId, pageable);
        return reviews;
    }

    //리뷰 수정
    @Transactional
    public ReviewDto updateReview(Long reviewId, UpdateReviewDto updateReviewDto, JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인한 사용자가 아닙니다.");
        }

        Review review = reviewRepository.reviewLoginUser(reviewId, user);
        if (review == null) {
            throw new RuntimeException("작성자가 아닙니다.");
        }

        review.setContent(updateReviewDto.getContent());
        reviewRepository.save(review);

        return convertToDto(review);
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId, JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인한 사용자가 아닙니다.");
        }

        Review review = reviewRepository.reviewLoginUser(reviewId, user);
        if (review == null) {
            throw new RuntimeException("작성자가 아닙니다.");
        }

        reviewRepository.deleteById(reviewId);
    }

    //리뷰 좋아요
    @Transactional
    public void likeReview(Long reviewId, JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인 후 이용해주세요");
        }
        likeService.isLike(reviewId, user);
    }

    public List<String> generatePresignedUrls(int count) {
        List<String> fileNames = IntStream.range(0, count)
                .mapToObj(i -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
        return s3Service.generatePresignedUrls("the-camp", fileNames);
    }

    //dto 전환
    public ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setCampName(review.getCampsite().getFacltNm());
        dto.setUserName(review.getUser().getName());
        dto.setLikeCount(review.getLikeCount());
        return dto;
    }
}
