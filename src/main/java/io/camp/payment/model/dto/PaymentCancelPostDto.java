package io.camp.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCancelPostDto {
    private String paymentId;
    private String reason;
}