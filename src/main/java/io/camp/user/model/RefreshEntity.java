package io.camp.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String username;

    @Column(length = 500)
    private String refresh;

    private String expiration;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;

    private String birthday;

    private String phoneNumber;

    private String gender;
}