package io.camp.reservation.model;

import io.camp.audit.BaseEntity;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.payment.model.Payment;
import io.camp.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "site"})
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id", nullable = false)
    private Long id;

    @Column(name = "reserve_start_date", nullable = false)
    private LocalDate reserveStartDate;

    @Column(name = "reserve_end_date", nullable = false)
    private LocalDate reserveEndDate;

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "total_price")
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_state")
    private ReservationState reservationState = ReservationState.RESERVATION_DONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", referencedColumnName = "seq", columnDefinition = "BIGINT", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_seq", referencedColumnName = "seq", columnDefinition = "BIGINT", nullable = false)
    private Site site;

//    @OneToOne(mappedBy = "reservation")
//    private Payment payment;

    public void setUser(User user){
        this.user = user;
    }

    public void setSite(Site site){
        this.site = site;
    }
}
