package io.camp.like.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDTO {
    private Long userId;
    private Long reviewId;

    public LikeRequestDTO(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
