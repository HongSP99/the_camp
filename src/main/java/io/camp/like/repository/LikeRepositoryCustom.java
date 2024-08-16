package io.camp.like.repository;

import io.camp.like.model.Like;
import io.camp.user.model.User;

public interface LikeRepositoryCustom {
    Like reviewLikeUser(Long reviewId, User loginUser);
}
