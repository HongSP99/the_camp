package io.camp.exception.user;

import io.camp.exception.ExceptionCode;

public class AuthorizationException extends CustomException {
    public AuthorizationException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}