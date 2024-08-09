package io.camp.user.model.dto;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class UserGetDto {

    @Schema(description = "사용자 이메일", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "사용자 이름", example = "John Doe", required = true)
    private String name;

    @Schema(description = "사용자 전화번호", example = "+1234567890", required = false)
    private String phoneNumber;
}
