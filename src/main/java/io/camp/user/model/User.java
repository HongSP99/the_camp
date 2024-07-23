package io.camp.user.model;


import io.camp.audit.BaseEntity;
import jakarta.persistence.*;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;

    private String birthday;

    private String phoneNumber;

    private String gender;
}
