package io.camp.common.exception.reservation;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;

public class ReservationException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public ReservationException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
