package io.camp.review.model.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String content;
    private String userName;
    private int likeCount;
    private String campName;

    public ReviewDto(Long id, String content, String userName, int likeCount, String campName) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.likeCount = likeCount;
        this.campName = campName;
    }
}

