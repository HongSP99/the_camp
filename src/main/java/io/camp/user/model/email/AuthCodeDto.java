package io.camp.user.model.email;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthCodeDto {
    private String email;
    private int authNumber;
}