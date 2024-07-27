package io.camp.user.model.dto;

import io.camp.user.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String phoneNumber;
    private String gender;
}
