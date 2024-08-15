package io.camp.review.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateReviewDto {
    private String content;
    private List<String> imageUrls;

    public List<String> getImageUrls() {
        return imageUrls == null ? new ArrayList<>() : imageUrls;
    }
}