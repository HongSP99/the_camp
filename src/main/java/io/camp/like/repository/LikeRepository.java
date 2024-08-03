package io.camp.like.repository;

import io.camp.like.model.Like;
import io.camp.review.model.Review;
import io.camp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{
    Optional<Like> findByUserAndReview(User user, Review review);
}
