package io.camp.user.model.email.response;


import io.camp.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailResponse {
    private boolean success;
    private String message;
    private String exceptionCode;

    public static MailResponse success(String message) {
        return new MailResponse(true, message, null);
    }

    public static MailResponse error(ExceptionCode exceptionCode) {
        return new MailResponse(false, "Error occurred", exceptionCode.toString());
    }
}
