package io.camp.like.repository;

import io.camp.like.model.Like;
import io.camp.review.model.Review;
import io.camp.user.model.User;

public interface LikeRepositoryCustom {
    Like reviewLikeUser(Long reviewId, User loginUser);
}
