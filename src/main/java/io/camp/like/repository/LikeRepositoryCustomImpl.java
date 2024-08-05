package io.camp.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.like.model.Like;
import io.camp.like.model.QLike;
import io.camp.review.model.QReview;
import io.camp.review.model.Review;
import io.camp.user.model.QUser;
import io.camp.user.model.User;
import lombok.RequiredArgsConstructor;

import static io.camp.like.model.QLike.like;
import static io.camp.review.model.QReview.review;
import static io.camp.user.model.QUser.user;

@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Like reviewLikeUser(Long reviewId, User loginUser) {
        Like res = jpaQueryFactory
                .selectFrom(like)
                .join(like.user, user)
                .join(like.review, review)
                .where(review.id.eq(reviewId)
                .and(user.seq.eq(loginUser.getSeq())))
                .fetchOne();
        return res;
    }
}
