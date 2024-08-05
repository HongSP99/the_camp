package io.camp.user.model.response;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MailResponse {
    private boolean success;
    private String message;
    private ExceptionCode exceptionCode;


    private MailResponse(boolean success, String message, ExceptionCode exceptionCode) {
        this.success = success;
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public static MailResponse success(String message) {
        return new MailResponse(true, message, null);
    }

    public static MailResponse error(ExceptionCode exceptionCode) {
        return new MailResponse(false, null, exceptionCode);
    }

}
