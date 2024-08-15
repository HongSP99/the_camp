package io.camp.review.service;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.common.exception.Campsite.CampsiteNotFoundException;
import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.review.ReviewNotAuthorException;
import io.camp.common.exception.user.CustomException;
import io.camp.like.service.LikeService;
import io.camp.review.model.Review;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.LikeReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.repository.ReviewRepository;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.camp.user.model.QUser.user;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService authService;
    private final CampSiteRepository campSiteRepository;
    private final LikeService likeService;

    //리뷰 생성
    public ReviewDto createReview(Long campsiteId, CreateReviewDto createReviewDto, JwtUserDetails jwtUserDetails) {
        if (jwtUserDetails == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        User user = jwtUserDetails.getUser();

        Campsite campsite = campSiteRepository.findById(campsiteId)
                .orElseThrow(() -> new CampsiteNotFoundException("Campsite not found"));

        Review review = Review.builder()
                .content(createReviewDto.getContent())
                .user(user)
                .campsite(campsite)
                .build();
        Review savedReview = reviewRepository.save(review);
        return convertToDto(savedReview);
    }

    //전체 리뷰 조회
    public Page<ReviewDto> getAllReviewSort(int page, int size, String type) {
        System.out.println("type : " + type);
        Pageable Pageable = PageRequest.of(page, size, Sort.by(type).descending());
        return reviewRepository.getAllReviewSort(Pageable);
    }

    //캠핑장 전체 리뷰 조회
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReview(Long campsiteId, int page, int size, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(type).descending());
        Page<ReviewDto> reviews = reviewRepository.getAllCampsiteReviewSort(campsiteId, pageable);
        return reviews;
    }

    //리뷰 단건 조회
    public ReviewDto getReviewOne(Long reviewId) {
        return reviewRepository.getCampsiteReview(reviewId);
    }

    //리뷰 수정
    @Transactional
    public ReviewDto updateReview(Long reviewId, UpdateReviewDto updateReviewDto, JwtUserDetails jwtUserDetails) {
        if (jwtUserDetails == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        User user = jwtUserDetails.getUser();

        ReviewDto reviewDto = reviewRepository.getCampsiteReview(reviewId);
        if (reviewDto.getUserSeq() != user.getSeq()) {
            throw new ReviewNotAuthorException(ExceptionCode.REVIEW_NOT_AUTHOR);
        }
        reviewDto.setContent(updateReviewDto.getContent());
        reviewRepository.updateReview(reviewId, updateReviewDto.getContent());

        return reviewDto;
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId, JwtUserDetails jwtUserDetails) {
        if (jwtUserDetails == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        User user = jwtUserDetails.getUser();

        ReviewDto reviewDto = reviewRepository.getCampsiteReview(reviewId);
        if (reviewDto.getUserSeq() != user.getSeq()) {
            throw new ReviewNotAuthorException(ExceptionCode.REVIEW_NOT_AUTHOR);
        }
        reviewRepository.deleteReview(reviewId);
    }

    //리뷰 좋아요
    @Transactional
    public LikeReviewDto likeReview(Long reviewId, JwtUserDetails jwtUserDetails) {
        if (jwtUserDetails == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        User user = jwtUserDetails.getUser();
        likeService.isLike(reviewId, user);
        return reviewRepository.getLikeCount(reviewId);
    }

    //dto 전환
    public ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setCampName(review.getCampsite().getFacltNm());
        dto.setUserName(review.getUser().getName());
        dto.setEmail(review.getUser().getEmail());
        dto.setUserSeq(review.getUser().getSeq());
        return dto;
    }
}
