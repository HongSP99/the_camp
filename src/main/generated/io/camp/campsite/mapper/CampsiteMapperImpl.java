package io.camp.campsite.mapper;

import io.camp.campsite.model.dto.CampSiteAllDto;
import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-07T14:12:28+0900",
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

    @Override
    public Campsite toCampsiteEntity(CampSiteDto campSiteDto) {
        if ( campSiteDto == null ) {
            return null;
        }

        Campsite.CampsiteBuilder campsite = Campsite.builder();

        campsite.contentId( campSiteDto.getContentId() );
        campsite.facltNm( campSiteDto.getFacltNm() );
        campsite.lineIntro( campSiteDto.getLineIntro() );
        campsite.intro( campSiteDto.getIntro() );
        campsite.allar( campSiteDto.getAllar() );
        campsite.trsagntNo( campSiteDto.getTrsagntNo() );
        campsite.bizrno( campSiteDto.getBizrno() );
        campsite.featureNm( campSiteDto.getFeatureNm() );
        campsite.induty( campSiteDto.getInduty() );
        campsite.lctCl( campSiteDto.getLctCl() );
        campsite.doNm( campSiteDto.getDoNm() );
        campsite.sigunguNm( campSiteDto.getSigunguNm() );
        campsite.zipcode( campSiteDto.getZipcode() );
        campsite.addr1( campSiteDto.getAddr1() );
        campsite.addr2( campSiteDto.getAddr2() );
        campsite.mapX( campSiteDto.getMapX() );
        campsite.mapY( campSiteDto.getMapY() );
        campsite.direction( campSiteDto.getDirection() );
        campsite.tel( campSiteDto.getTel() );
        campsite.homepage( campSiteDto.getHomepage() );
        campsite.resveUrl( campSiteDto.getResveUrl() );
        campsite.glampSiteCo( campSiteDto.getGlampSiteCo() );
        campsite.caravSiteCo( campSiteDto.getCaravSiteCo() );
        campsite.siteBottomCl1( campSiteDto.getSiteBottomCl1() );
        campsite.siteBottomCl2( campSiteDto.getSiteBottomCl2() );
        campsite.siteBottomCl3( campSiteDto.getSiteBottomCl3() );
        campsite.siteBottomCl4( campSiteDto.getSiteBottomCl4() );
        campsite.siteBottomCl5( campSiteDto.getSiteBottomCl5() );
        campsite.tooltip( campSiteDto.getTooltip() );
        campsite.glampInnerFclty( campSiteDto.getGlampInnerFclty() );
        campsite.caravInnerFclty( campSiteDto.getCaravInnerFclty() );
        campsite.operPdCl( campSiteDto.getOperPdCl() );
        campsite.operDeCl( campSiteDto.getOperDeCl() );
        campsite.toiletCo( campSiteDto.getToiletCo() );
        campsite.swrmCo( campSiteDto.getSwrmCo() );
        campsite.wtrplCo( campSiteDto.getWtrplCo() );
        campsite.brazierCl( campSiteDto.getBrazierCl() );
        campsite.sbrsCl( campSiteDto.getSbrsCl() );
        campsite.sbrsEtc( campSiteDto.getSbrsEtc() );
        campsite.posblFcltyCl( campSiteDto.getPosblFcltyCl() );
        campsite.posblFcltyEtc( campSiteDto.getPosblFcltyEtc() );
        campsite.themaEnyrnCl( campSiteDto.getThemaEnyrnCl() );
        campsite.animalCmgCl( campSiteDto.getAnimalCmgCl() );
        campsite.firstImageUrl( campSiteDto.getFirstImageUrl() );
        campsite.createdtime( campSiteDto.getCreatedtime() );
        campsite.modifiedtime( campSiteDto.getModifiedtime() );

        return campsite.build();
    }

    @Override
    public CampSiteAllDto toCampSiteAllDto(Campsite campsite) {
        if ( campsite == null ) {
            return null;
        }

        CampSiteAllDto.CampSiteAllDtoBuilder campSiteAllDto = CampSiteAllDto.builder();

        campSiteAllDto.contentId( campsite.getContentId() );
        campSiteAllDto.facltNm( campsite.getFacltNm() );
        campSiteAllDto.lineIntro( campsite.getLineIntro() );
        campSiteAllDto.intro( campsite.getIntro() );
        campSiteAllDto.allar( campsite.getAllar() );
        campSiteAllDto.trsagntNo( campsite.getTrsagntNo() );
        campSiteAllDto.bizrno( campsite.getBizrno() );
        campSiteAllDto.featureNm( campsite.getFeatureNm() );
        campSiteAllDto.induty( campsite.getInduty() );
        campSiteAllDto.lctCl( campsite.getLctCl() );
        campSiteAllDto.doNm( campsite.getDoNm() );
        campSiteAllDto.sigunguNm( campsite.getSigunguNm() );
        campSiteAllDto.zipcode( campsite.getZipcode() );
        campSiteAllDto.addr1( campsite.getAddr1() );
        campSiteAllDto.addr2( campsite.getAddr2() );
        campSiteAllDto.mapX( campsite.getMapX() );
        campSiteAllDto.mapY( campsite.getMapY() );
        campSiteAllDto.direction( campsite.getDirection() );
        campSiteAllDto.tel( campsite.getTel() );
        campSiteAllDto.homepage( campsite.getHomepage() );
        campSiteAllDto.resveUrl( campsite.getResveUrl() );
        campSiteAllDto.glampSiteCo( campsite.getGlampSiteCo() );
        campSiteAllDto.caravSiteCo( campsite.getCaravSiteCo() );
        campSiteAllDto.siteBottomCl1( campsite.getSiteBottomCl1() );
        campSiteAllDto.siteBottomCl2( campsite.getSiteBottomCl2() );
        campSiteAllDto.siteBottomCl3( campsite.getSiteBottomCl3() );
        campSiteAllDto.siteBottomCl4( campsite.getSiteBottomCl4() );
        campSiteAllDto.siteBottomCl5( campsite.getSiteBottomCl5() );
        campSiteAllDto.tooltip( campsite.getTooltip() );
        campSiteAllDto.glampInnerFclty( campsite.getGlampInnerFclty() );
        campSiteAllDto.caravInnerFclty( campsite.getCaravInnerFclty() );
        campSiteAllDto.operPdCl( campsite.getOperPdCl() );
        campSiteAllDto.operDeCl( campsite.getOperDeCl() );
        campSiteAllDto.toiletCo( campsite.getToiletCo() );
        campSiteAllDto.swrmCo( campsite.getSwrmCo() );
        campSiteAllDto.wtrplCo( campsite.getWtrplCo() );
        campSiteAllDto.brazierCl( campsite.getBrazierCl() );
        campSiteAllDto.sbrsCl( campsite.getSbrsCl() );
        campSiteAllDto.sbrsEtc( campsite.getSbrsEtc() );
        campSiteAllDto.posblFcltyCl( campsite.getPosblFcltyCl() );
        campSiteAllDto.posblFcltyEtc( campsite.getPosblFcltyEtc() );
        campSiteAllDto.themaEnyrnCl( campsite.getThemaEnyrnCl() );
        campSiteAllDto.animalCmgCl( campsite.getAnimalCmgCl() );
        campSiteAllDto.firstImageUrl( campsite.getFirstImageUrl() );
        campSiteAllDto.createdtime( campsite.getCreatedtime() );
        campSiteAllDto.modifiedtime( campsite.getModifiedtime() );

        return campSiteAllDto.build();
    }
}
