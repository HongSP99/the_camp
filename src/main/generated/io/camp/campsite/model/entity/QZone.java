package io.camp.campsite.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZone is a Querydsl query type for Zone
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZone extends EntityPathBase<Zone> {

    private static final long serialVersionUID = -308043951L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZone zone = new QZone("zone");

    public final NumberPath<Integer> bestPeakSeasonPrice = createNumber("bestPeakSeasonPrice", Integer.class);

    public final QCampsite campsite;

    public final TimePath<java.time.LocalTime> checkin = createTime("checkin", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> checkout = createTime("checkout", java.time.LocalTime.class);

    public final StringPath intro = createString("intro");

    public final NumberPath<Integer> offSeasonPrice = createNumber("offSeasonPrice", Integer.class);

    public final NumberPath<Integer> peakSeasonPrice = createNumber("peakSeasonPrice", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final ListPath<Site, QSite> sites = this.<Site, QSite>createList("sites", Site.class, QSite.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QZone(String variable) {
        this(Zone.class, forVariable(variable), INITS);
    }

    public QZone(Path<? extends Zone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZone(PathMetadata metadata, PathInits inits) {
        this(Zone.class, metadata, inits);
    }

    public QZone(Class<? extends Zone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.campsite = inits.isInitialized("campsite") ? new QCampsite(forProperty("campsite")) : null;
    }

}

