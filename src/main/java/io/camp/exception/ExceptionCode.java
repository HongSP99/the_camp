package io.camp.exception;

import lombok.Getter;

public enum ExceptionCode {
    // reservation
    RESERVATION_NOT_FOUND(404, "예약을 찾을 수 없습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
