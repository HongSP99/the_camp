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
    private String email;
    private Long userSeq;
    private Long campsiteSeq;

    public ReviewDto(Long id, String content, String userName, int likeCount, String campName, String email, Long userSeq, Long campsiteSeq) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.likeCount = likeCount;
        this.campName = campName;
        this.email = email;
        this.userSeq = userSeq;
        this.campsiteSeq = campsiteSeq;
    }
}

