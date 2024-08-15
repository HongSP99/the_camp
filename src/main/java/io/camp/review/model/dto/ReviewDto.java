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
}