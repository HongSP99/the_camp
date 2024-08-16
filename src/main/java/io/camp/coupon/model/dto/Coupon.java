package io.camp.coupon.model.dto;

import io.camp.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Coupon")
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String name;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private int discountRate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;
}
