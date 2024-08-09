package io.camp.exception.user;

import io.camp.exception.ExceptionCode;

public class VerifyCodeNotFoundException extends CustomException {
    public VerifyCodeNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}