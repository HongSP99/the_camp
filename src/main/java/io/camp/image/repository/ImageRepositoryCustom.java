package io.camp.image.repository;

import io.camp.image.model.Image;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> findImagesByReviewId(Long reviewId);
}
