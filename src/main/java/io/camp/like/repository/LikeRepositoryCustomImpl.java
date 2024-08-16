package io.camp.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.like.model.Like;
import io.camp.user.model.User;
import lombok.RequiredArgsConstructor;

import static io.camp.like.model.QLike.like;
import static io.camp.review.model.QReview.review;

@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Like reviewLikeUser(Long reviewId, User loginUser) {
        return jpaQueryFactory
                .select(like)
                .from(review)
                .leftJoin(like)
                .on(review.id.eq(like.review.id))
                .where(like.user.seq.eq(loginUser.getSeq()).and(like.review.id.eq(reviewId)))
                .fetchOne();
    }
}
