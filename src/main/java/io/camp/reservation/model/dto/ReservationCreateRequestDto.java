package io.camp.reservation.model.dto;

import io.camp.campsite.model.Campsite;
import io.camp.user.model.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationCreateRequestDto {
    private LocalDateTime reserveStartDate;
    private LocalDateTime reserveEndDate;
    private int adults;
    private int children;
    private User user;
    private Campsite campsite;

    public void setUser(User user){
        this.user = user;
    }

    public void setCampsite(Campsite campsite){
        this.campsite = campsite;
    }
}
