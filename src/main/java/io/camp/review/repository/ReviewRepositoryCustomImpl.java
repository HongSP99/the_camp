package io.camp.review.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.Campsite;
import io.camp.like.model.Like;
import io.camp.review.model.Review;
import io.camp.review.model.dto.ReviewDto;
import io.camp.user.model.User;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static io.camp.campsite.model.entity.QCampsite.campsite;
import static io.camp.review.model.QReview.review;
import static io.camp.user.model.QUser.user;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReviewDto> reviewJoinCampsite(Long campsiteId, Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory
                .select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        review.likeCount,
                        review.campsite.facltNm.as("campName")))
                .from(review)
                .join(review.campsite, campsite)
                .on(review.campsite.seq.eq(campsite.seq))
                .where(campsite.seq.eq(campsiteId))
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReviewDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReviewDto> getAllReviewLikeCountDesc(Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory.select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        review.likeCount,
                        review.campsite.facltNm.as("campName")))
                .from(review)
                .orderBy(review.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<ReviewDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReviewDto> getAllReviewDesc(Pageable pageable) {
        QueryResults<ReviewDto> results = jpaQueryFactory.select(Projections.bean(ReviewDto.class,
                        review.id,
                        review.content,
                        review.user.name.as("userName"),
                        review.likeCount,
                        review.campsite.facltNm.as("campName")))
                .from(review)
                .orderBy(review.createdAt.desc())
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
