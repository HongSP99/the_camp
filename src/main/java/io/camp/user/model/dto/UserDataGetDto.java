package io.camp.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataGetDto {
    private Long seq;
    private String email;
    private String fullName;
    private String phoneNumber;
}
