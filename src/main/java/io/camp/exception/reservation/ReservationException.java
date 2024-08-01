package io.camp.exception.reservation;

import io.camp.exception.ExceptionCode;
import lombok.Getter;

public class ReservationException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public ReservationException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
