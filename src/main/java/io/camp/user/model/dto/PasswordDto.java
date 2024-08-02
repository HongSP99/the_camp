package io.camp.user.model.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}