package io.camp.payment.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentCancellation is a Querydsl query type for PaymentCancellation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentCancellation extends EntityPathBase<PaymentCancellation> {

    private static final long serialVersionUID = -615796873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentCancellation paymentCancellation = new QPaymentCancellation("paymentCancellation");

    public final StringPath cancelledAt = createString("cancelledAt");

    public final StringPath id = createString("id");

    public final QPayment payment;

    public final StringPath paymentId = createString("paymentId");

    public final StringPath pgCancellationId = createString("pgCancellationId");

    public final StringPath reason = createString("reason");

    public final StringPath requestedAt = createString("requestedAt");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Integer> taxFreeAmount = createNumber("taxFreeAmount", Integer.class);

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final NumberPath<Integer> vatAmount = createNumber("vatAmount", Integer.class);

    public QPaymentCancellation(String variable) {
        this(PaymentCancellation.class, forVariable(variable), INITS);
    }

    public QPaymentCancellation(Path<? extends PaymentCancellation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentCancellation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentCancellation(PathMetadata metadata, PathInits inits) {
        this(PaymentCancellation.class, metadata, inits);
    }

    public QPaymentCancellation(Class<? extends PaymentCancellation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payment = inits.isInitialized("payment") ? new QPayment(forProperty("payment"), inits.get("payment")) : null;
    }

}

