package io.camp.user.model;


import io.camp.audit.BaseEntity;
import io.camp.coupon.model.dto.Coupon;
import io.camp.payment.model.Payment;
import io.camp.reservation.model.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"reservations", "payments"})
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;

    private String birthday;

    private String phoneNumber;

    private String gender;

    @OneToMany(mappedBy = "user")
    List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<Coupon> coupons;
}
