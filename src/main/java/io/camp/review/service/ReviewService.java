package io.camp.review.service;

import io.camp.review.Exception.ReviewNotFoundException;
import io.camp.review.model.Review;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.model.dto.UpdateReviewDto;
import io.camp.review.repository.ReviewRepository;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    //private final AuthRepository authRepository;
    private final UserService authService;

    //리뷰 생성
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ReviewDto createReview(CreateReviewDto createReviewDto, String username) {

        //User user = authService.getVerifiyLoginCurrentUser();
        Review review = Review.builder()
                .content(createReviewDto.getContent())
                //.auth(auth)
                .build();
        Review savedReview = reviewRepository.save(review);
        return convertToDto(savedReview);
    }

    //리뷰 조회
    @Transactional(readOnly = true)
    public ReviewDto getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        return convertToDto(review);
    }

    //리뷰 수정
    @Transactional
    @PreAuthorize("isAuthenticated() and @reviewSecurity.isReviewAuthor(#id, principal.username)")
    public ReviewDto updateReview(Long id, UpdateReviewDto updateReviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        review.setContent(updateReviewDto.getContent());
        Review updatedReview = reviewRepository.save(review);
        return convertToDto(updatedReview);
    }

    //리뷰 삭제
    @Transactional
    @PreAuthorize("isAuthenticated() and @reviewSecurity.isReviewAuthor(#id, principal.username)")
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }
        reviewRepository.deleteById(id);
    }

    //리뷰 좋아요
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ReviewDto likeReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        review.setLikeCount(review.getLikeCount() + 1);
        Review updatedReview = reviewRepository.save(review);
        return convertToDto(updatedReview);
    }


    //dto 전환
    private ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        //dto.setUserName(review.getAuth().getUsername());
        dto.setLikeCount(review.getLikeCount());
        return dto;
    }
}
