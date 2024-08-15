package io.camp.common.exception.like;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(Long userId, Long reviewId) {
        super("Like not found for user id: " + userId + " and review id: " + reviewId);
    }
}
