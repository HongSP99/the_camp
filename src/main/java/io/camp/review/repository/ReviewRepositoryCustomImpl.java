package io.camp.review.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.review.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static io.camp.campsite.model.entity.QCampsite.campsite;
import static io.camp.like.model.QLike.like;
import static io.camp.review.model.QReview.review;
import static io.camp.user.model.QUser.user;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private OrderSpecifier<?> reviewSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "createdAt":
                        return new OrderSpecifier(direction, review.createdAt);
                    case "likeCount":
                        return new OrderSpecifier(direction, like.isLike.when(true).then(1).otherwise(0).sum());
                }
            }
        }
        return null;
    }

    @Override
    public Page<ReviewDto> getAllCampsiteReviewSort(Long campsiteId, Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory
                .select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        like.isLike.when(true).then(1).otherwise(0).sum().as("likeCount"),
                        review.campsite.facltNm.as("campName"),
                        review.user.email.as("email"),
                        review.user.seq.as("userSeq")
                ))
                .from(campsite)
                .join(review)
                .on(campsite.seq.eq(review.campsite.seq))
                .leftJoin(like)
                .on(review.id.eq(like.review.id))
                .groupBy(campsite.seq, review.id)
                .where(campsite.seq.eq(campsiteId))
                .orderBy(reviewSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReviewDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReviewDto> getAllReviewSort(Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory
                .select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        like.isLike.when(true).then(1).otherwise(0).sum().as("likeCount"),
                        review.campsite.facltNm.as("campName"),
                        review.user.email.as("email"),
                        review.user.seq.as("userSeq")
                ))
                .from(campsite)
                .join(review)
                .on(campsite.seq.eq(review.campsite.seq))
                .leftJoin(like)
                .on(review.id.eq(like.review.id))
                .groupBy(campsite.seq, review.id)
                .orderBy(reviewSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<ReviewDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public ReviewDto getCampsiteReview(Long reviewId) {
        return jpaQueryFactory
                .select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        like.isLike.when(true).then(1).otherwise(0).sum().as("likeCount"),
                        review.campsite.facltNm.as("campName"),
                        review.user.email.as("email"),
                        review.user.seq.as("userSeq")
                ))
                .from(campsite)
                .join(review)
                .on(campsite.seq.eq(review.campsite.seq))
                .join(user)
                .on(review.user.seq.eq(user.seq))
                .leftJoin(like)
                .on(review.id.eq(like.review.id))
                .groupBy(campsite.seq, review.id)
                .where(review.id.eq(reviewId))
                .fetchOne();
    }

    @Override
    public long updateReview(Long reviewId, String content) {
        return jpaQueryFactory
                .update(review)
                .set(review.content, content)
                .where(review.id.eq(reviewId))
                .execute();
    }
}
