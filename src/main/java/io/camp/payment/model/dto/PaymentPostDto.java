package io.camp.payment.model.dto;

import java.time.LocalDate;
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
    private LocalDate reserveStartDate;
    private LocalDate reserveEndDate;
    private int adults;
    private int children;
    private String campsiteName;
    //받아야할 예약정보
    
    //받아야 할 쿠폰정보
    private int count;
    private String couponName;
    private Long couponSeq;
    private String couponType;
    private LocalDate expireDate;
    private Long invenSeq;
    private boolean use;
    //받아야 할 쿠폰정보
}
