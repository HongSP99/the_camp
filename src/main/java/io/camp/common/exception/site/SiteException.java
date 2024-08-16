package io.camp.common.exception.site;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;

public class SiteException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public SiteException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
