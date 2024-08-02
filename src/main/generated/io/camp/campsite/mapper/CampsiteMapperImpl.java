package io.camp.campsite.mapper;

import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T15:32:48+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CampsiteMapperImpl implements CampsiteMapper {

    @Override
    public CampSiteDto toCampsiteDto(Campsite campsite) {
        if ( campsite == null ) {
            return null;
        }

        CampSiteDto.CampSiteDtoBuilder campSiteDto = CampSiteDto.builder();

        campSiteDto.contentId( campsite.getContentId() );
        campSiteDto.facltNm( campsite.getFacltNm() );
        campSiteDto.lineIntro( campsite.getLineIntro() );
        campSiteDto.intro( campsite.getIntro() );
        campSiteDto.allar( campsite.getAllar() );
        campSiteDto.trsagntNo( campsite.getTrsagntNo() );
        campSiteDto.bizrno( campsite.getBizrno() );
        campSiteDto.featureNm( campsite.getFeatureNm() );
        campSiteDto.induty( campsite.getInduty() );
        campSiteDto.lctCl( campsite.getLctCl() );
        campSiteDto.doNm( campsite.getDoNm() );
        campSiteDto.sigunguNm( campsite.getSigunguNm() );
        campSiteDto.zipcode( campsite.getZipcode() );
        campSiteDto.addr1( campsite.getAddr1() );
        campSiteDto.addr2( campsite.getAddr2() );
        campSiteDto.mapX( campsite.getMapX() );
        campSiteDto.mapY( campsite.getMapY() );
        campSiteDto.direction( campsite.getDirection() );
        campSiteDto.tel( campsite.getTel() );
        campSiteDto.homepage( campsite.getHomepage() );
        campSiteDto.resveUrl( campsite.getResveUrl() );
        campSiteDto.glampSiteCo( campsite.getGlampSiteCo() );
        campSiteDto.caravSiteCo( campsite.getCaravSiteCo() );
        campSiteDto.siteBottomCl1( campsite.getSiteBottomCl1() );
        campSiteDto.siteBottomCl2( campsite.getSiteBottomCl2() );
        campSiteDto.siteBottomCl3( campsite.getSiteBottomCl3() );
        campSiteDto.siteBottomCl4( campsite.getSiteBottomCl4() );
        campSiteDto.siteBottomCl5( campsite.getSiteBottomCl5() );
        campSiteDto.tooltip( campsite.getTooltip() );
        campSiteDto.glampInnerFclty( campsite.getGlampInnerFclty() );
        campSiteDto.caravInnerFclty( campsite.getCaravInnerFclty() );
        campSiteDto.operPdCl( campsite.getOperPdCl() );
        campSiteDto.operDeCl( campsite.getOperDeCl() );
        campSiteDto.toiletCo( campsite.getToiletCo() );
        campSiteDto.swrmCo( campsite.getSwrmCo() );
        campSiteDto.wtrplCo( campsite.getWtrplCo() );
        campSiteDto.brazierCl( campsite.getBrazierCl() );
        campSiteDto.sbrsCl( campsite.getSbrsCl() );
        campSiteDto.sbrsEtc( campsite.getSbrsEtc() );
        campSiteDto.posblFcltyCl( campsite.getPosblFcltyCl() );
        campSiteDto.posblFcltyEtc( campsite.getPosblFcltyEtc() );
        campSiteDto.themaEnyrnCl( campsite.getThemaEnyrnCl() );
        campSiteDto.animalCmgCl( campsite.getAnimalCmgCl() );
        campSiteDto.firstImageUrl( campsite.getFirstImageUrl() );
        campSiteDto.createdtime( campsite.getCreatedtime() );
        campSiteDto.modifiedtime( campsite.getModifiedtime() );

        return campSiteDto.build();
    }
}
