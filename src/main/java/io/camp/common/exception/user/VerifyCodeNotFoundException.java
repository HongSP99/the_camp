package io.camp.common.exception.user;

import io.camp.common.exception.ExceptionCode;

public class VerifyCodeNotFoundException extends CustomException {
    public VerifyCodeNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}