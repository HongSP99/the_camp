package io.camp.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentPostDto {
    private String paymentId;
    
    //받아야할 예약정보
    private Long campsiteSeq;
    private Long siteSeq;
    private LocalDateTime reserveStartDate;
    private LocalDateTime reserveEndDate;
    private int adults;
    private int children;
    //받아야할 예약정보
}
