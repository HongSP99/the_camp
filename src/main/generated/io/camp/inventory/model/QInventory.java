package io.camp.inventory.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventory is a Querydsl query type for Inventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventory extends EntityPathBase<Inventory> {

    private static final long serialVersionUID = -438874944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventory inventory = new QInventory("inventory");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final io.camp.coupon.model.dto.QCoupon coupon;

    public final DatePath<java.time.LocalDate> expireDate = createDate("expireDate", java.time.LocalDate.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final io.camp.user.model.QUser user;

    public QInventory(String variable) {
        this(Inventory.class, forVariable(variable), INITS);
    }

    public QInventory(Path<? extends Inventory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventory(PathMetadata metadata, PathInits inits) {
        this(Inventory.class, metadata, inits);
    }

    public QInventory(Class<? extends Inventory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new io.camp.coupon.model.dto.QCoupon(forProperty("coupon")) : null;
        this.user = inits.isInitialized("user") ? new io.camp.user.model.QUser(forProperty("user")) : null;
    }

}

