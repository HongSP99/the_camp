package io.camp.review.model.dto;
import io.camp.image.model.dto.ImageDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String content;
    private String userName;
    private int likeCount;
    private String campName;
    private String email;
    private Long userSeq;
    private Long campsiteSeq;
    private List<ImageDTO> images;
    //민형님 코드
    /*private String campsiteUrl;
    public ReviewDto(Long id, String content, String userName, int likeCount, String campName, String email, Long userSeq, Long campsiteSeq, String campsiteUrl) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.likeCount = likeCount;
        this.campName = campName;
        this.email = email;
        this.userSeq = userSeq;
        this.campsiteSeq = campsiteSeq;
        this.campsiteUrl = campsiteUrl;
    } */
}
