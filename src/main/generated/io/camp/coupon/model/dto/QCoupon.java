package io.camp.coupon.model.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = 1075857213L;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> discountRate = createNumber("discountRate", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> expireDate = createDateTime("expireDate", java.time.LocalDateTime.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath type = createString("type");

    public QCoupon(String variable) {
        super(Coupon.class, forVariable(variable));
    }

    public QCoupon(Path<? extends Coupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupon(PathMetadata metadata) {
        super(Coupon.class, metadata);
    }

}

