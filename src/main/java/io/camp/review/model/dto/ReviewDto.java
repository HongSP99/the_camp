package io.camp.review.model.dto;
import lombok.Data;
@Data
public class ReviewDto {
    private Long id;
    private String content;
    private String userName;
    private int likeCount;
    private String campName;
}

