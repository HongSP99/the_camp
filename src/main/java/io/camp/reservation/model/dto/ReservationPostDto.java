package io.camp.reservation.model.dto;

import io.camp.user.model.User;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@ToString(exclude = {"user"})
public class ReservationPostDto {
    private Long siteSeq;
    private LocalDateTime reserveStartDate;
    private LocalDateTime reserveEndDate;
    private int adults;
    private int children;
    private int totalPrice;
}
