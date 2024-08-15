package io.camp.common.exception.user;

import io.camp.common.exception.ExceptionCode;

public class MailSendFailedException extends CustomException {
    public MailSendFailedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}