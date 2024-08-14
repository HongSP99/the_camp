package io.camp.common.exception.review;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;

public class ReviewNotAuthorException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public ReviewNotAuthorException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
