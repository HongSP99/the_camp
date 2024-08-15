package io.camp.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.payment.model.Payment;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.camp.payment.model.QPayment.payment;

@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Payment qFindByPaymentId(String paymentId) {
        return jpaQueryFactory.selectFrom(payment).where(payment.paymentId.eq(paymentId)).fetchOne();
    }
}
