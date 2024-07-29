package io.camp.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String paymentId;
    private String status;
    private String id;
    private String pgCancellationId;
    private int totalAmount;
    private int taxFreeAmount;
    private int vatAmount;
    private String reason;
    private String cancelledAt;
    private String requestedAt;

    @OneToOne
    @JoinColumn(name = "payment_seq")
    private Payment payment;
}
