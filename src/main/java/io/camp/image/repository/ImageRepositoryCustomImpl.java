package io.camp.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.image.model.Image;
import io.camp.image.model.QImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@RequiredArgsConstructor
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Image> findImagesByReviewId(Long reviewId) {
        QImage image = QImage.image;

        return queryFactory
                .selectFrom(image)
                .where(image.review.id.eq(reviewId))
                .fetch();
    }
}