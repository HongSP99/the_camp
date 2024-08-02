package io.camp.payment.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 2052698196L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final StringPath amount = createString("amount");

    public final StringPath amountCancelled = createString("amountCancelled");

    public final StringPath amountCancelledTaxFree = createString("amountCancelledTaxFree");

    public final StringPath amountDiscount = createString("amountDiscount");

    public final StringPath amountPaid = createString("amountPaid");

    public final StringPath amountSupply = createString("amountSupply");

    public final StringPath amountTaxFree = createString("amountTaxFree");

    public final NumberPath<Integer> amountTotal = createNumber("amountTotal", Integer.class);

    public final StringPath amountVat = createString("amountVat");

    public final StringPath channel = createString("channel");

    public final StringPath channelId = createString("channelId");

    public final StringPath channelKey = createString("channelKey");

    public final StringPath channelName = createString("channelName");

    public final StringPath channelPgMerchantId = createString("channelPgMerchantId");

    public final StringPath channelPgProvider = createString("channelPgProvider");

    public final StringPath channelType = createString("channelType");

    public final StringPath currency = createString("currency");

    public final StringPath customer = createString("customer");

    public final StringPath customerEmail = createString("customerEmail");

    public final StringPath customerId = createString("customerId");

    public final StringPath customerName = createString("customerName");

    public final StringPath customerPhoneNumber = createString("customerPhoneNumber");

    public final StringPath id = createString("id");

    public final StringPath isCulturalExpense = createString("isCulturalExpense");

    public final StringPath merchantId = createString("merchantId");

    public final StringPath method = createString("method");

    public final StringPath methodEasyPayMethod = createString("methodEasyPayMethod");

    public final StringPath methodEasyPayMethodApprovalNumber = createString("methodEasyPayMethodApprovalNumber");

    public final StringPath methodEasyPayMethodCard = createString("methodEasyPayMethodCard");

    public final StringPath methodEasyPayMethodCardBin = createString("methodEasyPayMethodCardBin");

    public final StringPath methodEasyPayMethodCardBrand = createString("methodEasyPayMethodCardBrand");

    public final StringPath methodEasyPayMethodCardIssuer = createString("methodEasyPayMethodCardIssuer");

    public final StringPath methodEasyPayMethodCardName = createString("methodEasyPayMethodCardName");

    public final StringPath methodEasyPayMethodCardNumber = createString("methodEasyPayMethodCardNumber");

    public final StringPath methodEasyPayMethodCardOwnerType = createString("methodEasyPayMethodCardOwnerType");

    public final StringPath methodEasyPayMethodCardPublisher = createString("methodEasyPayMethodCardPublisher");

    public final StringPath methodEasyPayMethodCardType = createString("methodEasyPayMethodCardType");

    public final StringPath methodEasyPayMethodInstallment = createString("methodEasyPayMethodInstallment");

    public final StringPath methodEasyPayMethodInstallmentIsInterestFree = createString("methodEasyPayMethodInstallmentIsInterestFree");

    public final StringPath methodEasyPayMethodInstallmentMonth = createString("methodEasyPayMethodInstallmentMonth");

    public final StringPath methodEasyPayMethodType = createString("methodEasyPayMethodType");

    public final StringPath methodProvider = createString("methodProvider");

    public final StringPath methodType = createString("methodType");

    public final StringPath orderName = createString("orderName");

    public final StringPath paidAt = createString("paidAt");

    public final QPaymentCancellation paymentCancellation;

    public final StringPath paymentId = createString("paymentId");

    public final StringPath pgResponse = createString("pgResponse");

    public final StringPath pgTxId = createString("pgTxId");

    public final StringPath promotionId = createString("promotionId");

    public final StringPath receiptUrl = createString("receiptUrl");

    public final StringPath requestedAt = createString("requestedAt");

    public final io.camp.reservation.model.QReservation reservation;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath status = createString("status");

    public final StringPath statusChangedAt = createString("statusChangedAt");

    public final StringPath storeId = createString("storeId");

    public final StringPath transactionId = createString("transactionId");

    public final StringPath updatedAt = createString("updatedAt");

    public final io.camp.user.model.QUser user;

    public final StringPath version = createString("version");

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.paymentCancellation = inits.isInitialized("paymentCancellation") ? new QPaymentCancellation(forProperty("paymentCancellation"), inits.get("paymentCancellation")) : null;
        this.reservation = inits.isInitialized("reservation") ? new io.camp.reservation.model.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
        this.user = inits.isInitialized("user") ? new io.camp.user.model.QUser(forProperty("user")) : null;
    }

}

