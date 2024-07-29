package io.camp.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentPostDto {
    private String paymentId;
    private Long reservationId;
    private String reason;
}
