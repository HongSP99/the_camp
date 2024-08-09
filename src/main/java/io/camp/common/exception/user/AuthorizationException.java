package io.camp.common.exception.user;

import io.camp.common.exception.ExceptionCode;

public class AuthorizationException extends CustomException {
    public AuthorizationException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}