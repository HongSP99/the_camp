package io.camp.user.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserReservationDto {
    private String campsiteName;
    private String paymentId;
    private Long invenSeq;
    private Long siteSeq;
    private Long reservationId;
    private int totalPrice;
    private int adults;
    private int children;
    private LocalDate createdAt;
    private LocalDate reserveStartDate;
    private LocalDate reserveEndDate;

    public UserReservationDto(String campsiteName, String paymentId, Long invenSeq, Long siteSeq, Long reservationId, Integer totalPrice, Integer adults, Integer children, LocalDateTime createdAt, LocalDate reserveStartDate, LocalDate reserveEndDate) {
        this.campsiteName = campsiteName;
        this.paymentId = paymentId;
        this.invenSeq = invenSeq;
        this.siteSeq = siteSeq;
        this.reservationId = reservationId;
        this.totalPrice = totalPrice;
        this.adults = adults;
        this.children = children;
        this.createdAt = createdAt.toLocalDate();
        this.reserveStartDate = reserveStartDate;
        this.reserveEndDate = reserveEndDate;
    }
}
