package io.camp.review.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.review.model.Review;
import io.camp.review.model.dto.ReviewDto;
import io.camp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static io.camp.campsite.model.entity.QCampsite.campsite;
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
                        return new OrderSpecifier(direction, review.likeCount);
                }
            }
        }
        return null;
    }

    @Override
    public Page<ReviewDto> reviewJoinCampsite(Long campsiteId, Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory
                .select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        review.likeCount,
                        review.campsite.facltNm.as("campName"),
                        review.user.email.as("email")
                ))
                .from(review)
                .join(review.campsite, campsite)
                .on(review.campsite.seq.eq(campsite.seq))
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
        QueryResults<ReviewDto> results = jpaQueryFactory.select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        review.likeCount,
                        review.campsite.facltNm.as("campName"),
                        review.user.email.as("email")
                ))
                .from(review)
                .orderBy(reviewSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<ReviewDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Review reviewLoginUser(Long reviewId, User loginUser) {
        return jpaQueryFactory
                .select(review)
                .from(review)
                .join(review.user, user)
                .on(review.user.seq.eq(user.seq))
                .where(review.id.eq(reviewId).and(user.seq.eq(loginUser.getSeq())))
                .fetchOne();
    }
}
