package io.camp.exception.user;

import io.camp.exception.ExceptionCode;

public class MailSendFailedException extends CustomException {
    public MailSendFailedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}