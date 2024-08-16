package io.camp.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentCancelPostDto {
    private String paymentId;
    private Long reservationId;
    private LocalDate reserveStartDate;
    private Long invenSeq;
    private String reason = "예약 취소";
}