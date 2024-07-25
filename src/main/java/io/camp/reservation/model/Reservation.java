package io.camp.reservation.model;

import io.camp.audit.BaseEntity;
import io.camp.campsite.model.Campsite;
import io.camp.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "campsite"})
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id", nullable = false)
    private Long id;

    @Column(name = "reserve_start_date", nullable = false)
    private LocalDateTime reserveStartDate;

    @Column(name = "reserve_end_date", nullable = false)
    private LocalDateTime reserveEndDate;

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "seq", columnDefinition = "BIGINT", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_id", referencedColumnName = "campsite_id", columnDefinition = "BIGINT", nullable = false)
    private Campsite campsite;

    @Builder
    public Reservation(Long id, LocalDateTime reserveStartDate, LocalDateTime reserveEndDate, int adults, int children, int totalPrice, User user, Campsite campsite){
        this.id = id;
        this.reserveStartDate = reserveStartDate;
        this.reserveEndDate = reserveEndDate;
        this.adults = adults;
        this.children = children;
        this.totalPrice = totalPrice;
        this.user = user;
        this.campsite = campsite;
    }
}
