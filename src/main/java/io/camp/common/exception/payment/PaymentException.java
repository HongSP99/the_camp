package io.camp.common.exception.payment;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;

public class PaymentException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public PaymentException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}