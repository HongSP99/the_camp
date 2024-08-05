package io.camp.exception.payment;

import io.camp.exception.ExceptionCode;
import lombok.Getter;

public class PaymentException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public PaymentException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}