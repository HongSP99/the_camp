package io.camp.campsite.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSite is a Querydsl query type for Site
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSite extends EntityPathBase<Site> {

    private static final long serialVersionUID = -308258068L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSite site = new QSite("site");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final QZone zone;

    public QSite(String variable) {
        this(Site.class, forVariable(variable), INITS);
    }

    public QSite(Path<? extends Site> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSite(PathMetadata metadata, PathInits inits) {
        this(Site.class, metadata, inits);
    }

    public QSite(Class<? extends Site> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.zone = inits.isInitialized("zone") ? new QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

