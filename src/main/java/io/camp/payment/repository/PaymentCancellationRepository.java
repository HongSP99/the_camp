package io.camp.payment.repository;

import io.camp.payment.model.PaymentCancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCancellationRepository extends JpaRepository<PaymentCancellation, Long> {
}
