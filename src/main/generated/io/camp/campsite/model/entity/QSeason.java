package io.camp.campsite.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeason is a Querydsl query type for Season
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeason extends EntityPathBase<Season> {

    private static final long serialVersionUID = 112496968L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeason season1 = new QSeason("season1");

    public final QCampsite campsite;

    public final DateTimePath<java.time.LocalDateTime> end = createDateTime("end", java.time.LocalDateTime.class);

    public final EnumPath<SeasonType> season = createEnum("season", SeasonType.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> start = createDateTime("start", java.time.LocalDateTime.class);

    public QSeason(String variable) {
        this(Season.class, forVariable(variable), INITS);
    }

    public QSeason(Path<? extends Season> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeason(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeason(PathMetadata metadata, PathInits inits) {
        this(Season.class, metadata, inits);
    }

    public QSeason(Class<? extends Season> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.campsite = inits.isInitialized("campsite") ? new QCampsite(forProperty("campsite")) : null;
    }

}

