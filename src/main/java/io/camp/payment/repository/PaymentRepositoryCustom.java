package io.camp.payment.repository;

import io.camp.payment.model.Payment;

import java.util.List;

public interface PaymentRepositoryCustom {
    Payment qFindByPaymentId(String paymentId);
}
