package io.camp.review.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateReviewDto {
    private String content;
    private List<String> imageUrls;
}
