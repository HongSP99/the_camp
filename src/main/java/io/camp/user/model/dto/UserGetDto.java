package io.camp.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {
    private String email;
    private String name;
    private String phoneNumber;
}
