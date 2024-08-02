package io.camp.campsite.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCampsite is a Querydsl query type for Campsite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCampsite extends EntityPathBase<Campsite> {

    private static final long serialVersionUID = -293907859L;

    public static final QCampsite campsite = new QCampsite("campsite");

    public final StringPath addr1 = createString("addr1");

    public final StringPath addr2 = createString("addr2");

    public final NumberPath<Integer> allar = createNumber("allar", Integer.class);

    public final StringPath animalCmgCl = createString("animalCmgCl");

    public final StringPath bizrno = createString("bizrno");

    public final StringPath brazierCl = createString("brazierCl");

    public final StringPath caravInnerFclty = createString("caravInnerFclty");

    public final NumberPath<Integer> caravSiteCo = createNumber("caravSiteCo", Integer.class);

    public final NumberPath<Integer> contentId = createNumber("contentId", Integer.class);

    public final StringPath createdtime = createString("createdtime");

    public final StringPath direction = createString("direction");

    public final StringPath doNm = createString("doNm");

    public final StringPath facltNm = createString("facltNm");

    public final StringPath featureNm = createString("featureNm");

    public final StringPath firstImageUrl = createString("firstImageUrl");

    public final StringPath glampInnerFclty = createString("glampInnerFclty");

    public final NumberPath<Integer> glampSiteCo = createNumber("glampSiteCo", Integer.class);

    public final StringPath homepage = createString("homepage");

    public final StringPath induty = createString("induty");

    public final StringPath intro = createString("intro");

    public final StringPath lctCl = createString("lctCl");

    public final StringPath lineIntro = createString("lineIntro");

    public final StringPath mapX = createString("mapX");

    public final StringPath mapY = createString("mapY");

    public final StringPath modifiedtime = createString("modifiedtime");

    public final StringPath operDeCl = createString("operDeCl");

    public final StringPath operPdCl = createString("operPdCl");

    public final StringPath posblFcltyCl = createString("posblFcltyCl");

    public final StringPath posblFcltyEtc = createString("posblFcltyEtc");

    public final StringPath resveUrl = createString("resveUrl");

    public final StringPath sbrsCl = createString("sbrsCl");

    public final StringPath sbrsEtc = createString("sbrsEtc");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath sigunguNm = createString("sigunguNm");

    public final StringPath siteBottomCl1 = createString("siteBottomCl1");

    public final StringPath siteBottomCl2 = createString("siteBottomCl2");

    public final StringPath siteBottomCl3 = createString("siteBottomCl3");

    public final StringPath siteBottomCl4 = createString("siteBottomCl4");

    public final StringPath siteBottomCl5 = createString("siteBottomCl5");

    public final NumberPath<Integer> swrmCo = createNumber("swrmCo", Integer.class);

    public final StringPath tel = createString("tel");

    public final StringPath themaEnyrnCl = createString("themaEnyrnCl");

    public final NumberPath<Integer> toiletCo = createNumber("toiletCo", Integer.class);

    public final StringPath tooltip = createString("tooltip");

    public final StringPath trsagntNo = createString("trsagntNo");

    public final NumberPath<Integer> wtrplCo = createNumber("wtrplCo", Integer.class);

    public final StringPath zipcode = createString("zipcode");

    public final ListPath<Zone, QZone> zones = this.<Zone, QZone>createList("zones", Zone.class, QZone.class, PathInits.DIRECT2);

    public QCampsite(String variable) {
        super(Campsite.class, forVariable(variable));
    }

    public QCampsite(Path<? extends Campsite> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCampsite(PathMetadata metadata) {
        super(Campsite.class, metadata);
    }

}

