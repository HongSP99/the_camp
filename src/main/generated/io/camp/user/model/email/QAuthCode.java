package io.camp.user.model.email;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthCode is a Querydsl query type for AuthCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthCode extends EntityPathBase<AuthCode> {

    private static final long serialVersionUID = -1043481046L;

    public static final QAuthCode authCode = new QAuthCode("authCode");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAuthCode(String variable) {
        super(AuthCode.class, forVariable(variable));
    }

    public QAuthCode(Path<? extends AuthCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthCode(PathMetadata metadata) {
        super(AuthCode.class, metadata);
    }

}

