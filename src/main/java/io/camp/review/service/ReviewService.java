package io.camp.review.service;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.common.exception.Campsite.CampsiteNotFoundException;
import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.review.ReviewNotAuthorException;
import io.camp.common.exception.user.CustomException;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.repository.ReviewRepository;
import io.camp.image.model.Image;
import io.camp.image.model.dto.ImageDTO;
import io.camp.image.repository.ImageRepository;
import io.camp.image.service.ImageService;
import io.camp.like.service.LikeService;
import io.camp.review.model.Review;
import io.camp.review.model.dto.LikeReviewDto;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService authService;
    private final CampSiteRepository campSiteRepository;
    private final LikeService likeService;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    //리뷰 생성
    @Transactional
    public ReviewDto createReview(Long campsiteId, CreateReviewDto createReviewDto, JwtUserDetails jwtUserDetails) {

        if (jwtUserDetails == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        User user = jwtUserDetails.getUser();

        Campsite campsite = campSiteRepository.findById(campsiteId)
                .orElseThrow(() -> new CampsiteNotFoundException("Campsite not found"));

        Review review = new Review();
        review.setContent(createReviewDto.getContent());
        review.setCampsite(campsite);
        review.setUser(jwtUserDetails.getUser());

        Review savedReview = reviewRepository.save(review);
            log.info("Transaction completed");
            return convertToDto(savedReview);
    }

    //캠핑장 상세 리뷰 목록 조회

    //캠핑장 전체 리뷰 조회(최신순), 이미지 있음
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReview(Long campsiteId, int page, int size, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(type).descending());
        Page<ReviewDto> reviewPage = reviewRepository.getAllCampsiteReviewSort(campsiteId, pageable);

        List<ReviewDto> reviewDtosWithImages = reviewPage.getContent().stream().map(reviewDto -> {
            List<Image> images = imageRepository.findByReviewId(reviewDto.getId());
            List<ImageDTO> imageDTOs = images.stream()
                    .map(this::convertToImageDto)
                    .collect(Collectors.toList());
            reviewDto.setImages(imageDTOs);
            return reviewDto;
        }).collect(Collectors.toList());

        return new PageImpl<>(reviewDtosWithImages, pageable, reviewPage.getTotalElements());
    }

    public Page<ReviewDto> getAllReviewSort(int page, int size, String type) {
        System.out.println("type : " + type);
        Pageable Pageable = PageRequest.of(page, size, Sort.by(type).descending());
        return reviewRepository.getAllReviewSort(Pageable);
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

        reviewDto = reviewRepository.getCampsiteReview(reviewId);
        if (reviewDto.getUserSeq() != user.getSeq()) {
            throw new RuntimeException("작성자가 아닙니다.");
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


    public ReviewDto getReviewOne(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Review not found with id: " + reviewId);
                });
        List<Image> images = imageRepository.findByReviewId(reviewId);
        return convertToDto(review, images);
    }

    public ReviewDto convertToDto(Review review, List<Image> images) {
        List<ImageDTO> imageDtos = images.stream()
                .map(this::convertToImageDto)
                .collect(Collectors.toList());

        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setCampName(review.getCampsite().getFacltNm());
        dto.setUserName(review.getUser().getName());
        dto.setEmail(review.getUser().getEmail());
        dto.setUserSeq(review.getUser().getSeq());
        dto.setImages(imageDtos);

        return dto;
    }

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

    private ImageDTO convertToImageDto(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        return dto;
    }

}
