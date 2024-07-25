package io.camp.reservation.model;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id", nullable = false)
    private Long id;

    @Column(name = "reserve_date", nullable = false)
    private LocalDateTime reserveDate;

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "seq", columnDefinition = "BIGINT", nullable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "campsite_id", referencedColumnName = "campsite_id", columnDefinition = "BIGINT", nullable = false)
//    private Campsite campsite;

    @Builder
    public Reservation(Long id, LocalDateTime reserveDate, int adults, int children, int totalPrice, User user){
        this.id = id;
        this.reserveDate = reserveDate;
        this.adults = adults;
        this.children = children;
        this.totalPrice = totalPrice;
    }
}
