package io.camp.campsite.model.dto;


import io.camp.campsite.model.entity.Site;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteDto {

    private Long seq;
    private String title;

    public static SiteDto fromEntity(Site site){
        SiteDto dto = new SiteDto();

        dto.setSeq(site.getSeq());
        dto.setTitle(site.getTitle());

        return dto;
    }
}
