package io.camp.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefreshEntity is a Querydsl query type for RefreshEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshEntity extends EntityPathBase<RefreshEntity> {

    private static final long serialVersionUID = -2067483173L;

    public static final QRefreshEntity refreshEntity = new QRefreshEntity("refreshEntity");

    public final StringPath birthday = createString("birthday");

    public final StringPath expiration = createString("expiration");

    public final StringPath gender = createString("gender");

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath refresh = createString("refresh");

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath username = createString("username");

    public QRefreshEntity(String variable) {
        super(RefreshEntity.class, forVariable(variable));
    }

    public QRefreshEntity(Path<? extends RefreshEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefreshEntity(PathMetadata metadata) {
        super(RefreshEntity.class, metadata);
    }

}

