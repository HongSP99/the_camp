package io.camp.user.model.dto;


import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "회원 가입 정보")
public class JoinDto {
    @Schema(description = "이메일" , example = "user@example.com")
    private String email;
    @Schema(description = "비밀번호", example = "password")
    private String password;

    @Schema(description = "닉네임", example = "홍길동")
    private String name;

    @Schema(description = "생일", example = "2024-08-09")
    private String birthday;

    @Schema(description = "전화번호", example = "010-0000-0000")
    private String phoneNumber;

    @Schema(description = "성별", example = "남자, 여자")
    private String gender;
}
