package io.camp.review.repository;

import io.camp.review.model.Review;
import io.camp.review.model.dto.ReviewDto;
import io.camp.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<ReviewDto> reviewJoinCampsite(Long campsiteId, Pageable pageable);
    Review reviewLoginUser(Long reviewId, User loginUser);

}
