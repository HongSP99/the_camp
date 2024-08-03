package io.camp.exception;

import lombok.Getter;

public enum ExceptionCode {
    // reservation
    RESERVATION_NOT_FOUND(404, "예약을 찾을 수 없습니다."),
    RESERVATION_CANNOT_BE_CANCELLED(400, "하루 전에는 예약을 할 수 없습니다.");

    //payment
    PAYMENT_NOT_EQUAL_CANCEL(400, "결제 테이블 금액 결제 취소 금액이 일치하지 않습니다."),
    PAYMENT_NOT_EQUAL_RESERVATION(400, "결제 테이블 금액 결제 취소 금액이 일치하지 않습니다."),
    PAYMENT_IMPORT_TYPE(404, "결제 API가 재대로 호출되지 않았습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
