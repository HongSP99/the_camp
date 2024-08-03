package io.camp.campsite.model.dto;


import io.camp.campsite.model.entity.Site;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteDto {

    private String title;

    public static SiteDto fromEntity(Site site){
        SiteDto dto = new SiteDto();

        dto.setTitle(site.getTitle());

        return dto;
    }
}
