package io.camp.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {

    @Schema(description = "현재 비밀번호", example = "oldPassword123", required = true)
    private String currentPassword;

    @Schema(description = "새 비밀번호", example = "newPassword123", required = true)
    private String newPassword;
}
