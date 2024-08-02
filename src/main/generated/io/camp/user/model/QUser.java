package io.camp.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1444921326L;

    public static final QUser user = new QUser("user");

    public final io.camp.audit.QBaseEntity _super = new io.camp.audit.QBaseEntity(this);

    public final StringPath birthday = createString("birthday");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<io.camp.payment.model.Payment, io.camp.payment.model.QPayment> payments = this.<io.camp.payment.model.Payment, io.camp.payment.model.QPayment>createList("payments", io.camp.payment.model.Payment.class, io.camp.payment.model.QPayment.class, PathInits.DIRECT2);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<io.camp.reservation.model.Reservation, io.camp.reservation.model.QReservation> reservations = this.<io.camp.reservation.model.Reservation, io.camp.reservation.model.QReservation>createList("reservations", io.camp.reservation.model.Reservation.class, io.camp.reservation.model.QReservation.class, PathInits.DIRECT2);

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

