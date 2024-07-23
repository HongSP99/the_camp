package io.camp.user.model;


import io.camp.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
