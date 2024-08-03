package io.camp.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPaymentGetDto {
    private String email;
    private String fullName;
    private String phoneNumber;
}
