package io.camp.user.model;


import io.camp.audit.BaseEntity;
import io.camp.payment.model.Payment;
import io.camp.reservation.model.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
}
