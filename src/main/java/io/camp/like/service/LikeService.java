package io.camp.like.service;

import io.camp.exception.review.ReviewNotFoundException;
import io.camp.exception.user.UserNotFoundException;
import io.camp.like.model.Like;
import io.camp.like.model.dto.LikeRequestDTO;
import io.camp.like.repository.LikeRepository;
import io.camp.review.model.Review;
import io.camp.review.repository.ReviewRepository;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.camp.exception.like.LikeNotFoundException;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public boolean isLike(Long reviewId, User user) {
        Like isLike = likeRepository.reviewLikeUser(reviewId, user);
        Review review = reviewRepository.findById((long) reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요"));

        if (isLike == null) {
            Like like = new Like();
            like.setLike(true);
            like.setReview(review);
            like.setUser(user);
            likeRepository.save(like);

            return false;
        }

        if (isLike.isLike() == true) {
            isLike.setLike(false);
            likeRepository.save(isLike);
            return true;
        } else {
            isLike.setLike(true);
            likeRepository.save(isLike);
            return false;
        }
    }

    public void createLike(LikeRequestDTO likeRequestDTO) throws Exception {
        User user = userRepository.findById(likeRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(likeRequestDTO.getUserId()));
        Review review = reviewRepository.findById(likeRequestDTO.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(likeRequestDTO.getReviewId()));

        Like like = Like.builder().review(review).user(user).build();
        likeRepository.save(like);
    }

    public void deleteLike(LikeRequestDTO likeRequestDTO){
        User user = userRepository.findById(likeRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(likeRequestDTO.getUserId()));
        Review review = reviewRepository.findById(likeRequestDTO.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(likeRequestDTO.getReviewId()));
        Like like = likeRepository.findByUserAndReview(user, review)
                .orElseThrow(() -> new LikeNotFoundException(likeRequestDTO.getUserId(), likeRequestDTO.getReviewId()));

        likeRepository.delete(like);
    }



}
