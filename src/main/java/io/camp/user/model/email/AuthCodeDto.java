package io.camp.user.model.email;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthCodeDto {

    @Schema(description = "이메일 주소", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "인증 번호", example = "123456", required = true)
    private int authNumber;
}