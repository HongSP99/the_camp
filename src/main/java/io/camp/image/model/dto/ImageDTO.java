package io.camp.image.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ImageDTO {

    private Long id;
    private String url;

    public ImageDTO(Long id, String url){
        this.id = id;
        this.url = url;
    }
}
