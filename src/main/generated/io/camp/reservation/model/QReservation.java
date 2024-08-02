package io.camp.reservation.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 788763104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final io.camp.audit.QBaseEntity _super = new io.camp.audit.QBaseEntity(this);

    public final NumberPath<Integer> adults = createNumber("adults", Integer.class);

    public final io.camp.campsite.model.entity.QCampsite campsite;

    public final NumberPath<Integer> children = createNumber("children", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final io.camp.payment.model.QPayment payment;

    public final EnumPath<ReservationState> reservationState = createEnum("reservationState", ReservationState.class);

    public final DateTimePath<java.time.LocalDateTime> reserveEndDate = createDateTime("reserveEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> reserveStartDate = createDateTime("reserveStartDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final io.camp.user.model.QUser user;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.campsite = inits.isInitialized("campsite") ? new io.camp.campsite.model.entity.QCampsite(forProperty("campsite")) : null;
        this.payment = inits.isInitialized("payment") ? new io.camp.payment.model.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.user = inits.isInitialized("user") ? new io.camp.user.model.QUser(forProperty("user")) : null;
    }

}
