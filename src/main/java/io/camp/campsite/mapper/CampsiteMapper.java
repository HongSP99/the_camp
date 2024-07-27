package io.camp.campsite.mapper;

import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampsiteMapper {

    CampSiteDto toCampsiteDto(Campsite campsite);

}
