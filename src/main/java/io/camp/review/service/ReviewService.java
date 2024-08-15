package io.camp.review.service;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.image.model.Image;
import io.camp.image.model.dto.ImageDTO;
import io.camp.image.repository.ImageRepository;
import io.camp.image.service.ImageService;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static io.camp.image.model.QImage.image;
import static io.camp.user.model.QUser.user;

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
        try {
        Campsite campsite = campSiteRepository.findById(campsiteId)
                .orElseThrow(() -> new EntityNotFoundException("Campsite not found"));

        Review review = new Review();
        review.setContent(createReviewDto.getContent());
        review.setCampsite(campsite);
        review.setUser(jwtUserDetails.getUser());

        Review savedReview = reviewRepository.save(review);
            log.info("Transaction completed");
            return convertToDto(savedReview);
        } catch (Exception e) {
            log.error("Error in createReview: {}", e.getMessage());
            throw e;
        }
    }

    //전체 리뷰 조회 (좋아요 순)
    public Page<ReviewDto> getAllReviewLikeCountDesc(int page, int size) {
        Pageable Pageable = PageRequest.of(page, size, Sort.by("likeCount").descending());
        return reviewRepository.getAllReviewSort(Pageable);
    }

    //전체 리뷰 조회 (최신 순)
    public Page<ReviewDto> getAllReviewDesc(int page, int size) {
        Pageable Pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewRepository.getAllReviewSort(Pageable);
    }

    //캠핑장 전체 리뷰 조회(최신순), 이미지 있음
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReview(Long campsiteId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
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

    //리뷰 단건조회
    public ReviewDto getReviewOne(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Review not found with id: " + reviewId);
                });

        List<Image> images = imageRepository.findByReviewId(reviewId);
        log.info("리뷰에 대한 이미지 {} 개 찾음", images.size());

        ReviewDto reviewDto = convertToDto(review, images);

        log.info("종료: 리뷰 조회 - ID: {}", reviewId);
        return reviewDto;
    }




    //리뷰 수정
    @Transactional
    public ReviewDto updateReview(Long reviewId, UpdateReviewDto updateReviewDto, JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인한 사용자가 아닙니다.");
        }

        ReviewDto reviewDto = reviewRepository.getCampsiteReview(reviewId);
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
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인한 사용자가 아닙니다.");
        }

        ReviewDto reviewDto = reviewRepository.getCampsiteReview(reviewId);
        if (reviewDto.getUserSeq() != user.getSeq()) {
            throw new RuntimeException("작성자가 아닙니다.");
        }
        reviewRepository.deleteReview(reviewId);
    }

    //리뷰 좋아요
    @Transactional
    public LikeReviewDto likeReview(Long reviewId, JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            throw new RuntimeException("로그인 후 이용해주세요");
        }
        likeService.isLike(reviewId, user);
        return reviewRepository.getLikeCount(reviewId);
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
