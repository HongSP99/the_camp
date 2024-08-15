package io.camp.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    // reservation
    RESERVATION_NOT_FOUND(404, "예약을 찾을 수 없습니다."),

    RESERVATION_CANNOT_BE_CANCELLED(400, "하루 전에는 예약을 취소 할 수 없습니다."),
    RESERVATION_ALREADY_EXIST(400, "이미 예약이 있습니다."),

    //payment
    PAYMENT_ALREADY_RESERVATION(409, "이미 결제된 예약입니다."),
    PAYMENT_NOT_EQUAL_CANCEL(400, "결제 테이블 금액 결제 취소 금액이 일치하지 않습니다."),
    PAYMENT_NOT_EQUAL_RESERVATION(400, "결제 테이블 금액 결제 취소 금액이 일치하지 않습니다."),
    PAYMENT_IMPORT_TYPE(404, "결제 API가 재대로 호출되지 않았습니다."),

    //inventory
    INVENTORY_NOT_FOUND(404, "해당 쿠폰을 찾을 수 없습니다."),
    INVENTORY_NOT_USE(400, "쿠폰이 만료되었습니다."),
    INVENTORY_ALREADY_USE(400, "이미 쿠폰을 사용하셨습니다."),

    //공통 부분
    BAD_REQUEST(400, "Invalid request."),


    UNAUTHORIZED_REQUEST(401, "Unauthorized."),


    FORBIDDEN_ACCESS(403, "Forbidden."),


    NOT_FOUND(404, "Not found."),


    METHOD_NOT_ALLOWED(405, "Not allowed method."),


    INTERNAL_SERVER_ERROR(500, "Server error."),



    //jwt 토큰
    AUTHENTICATION_FAILED(401, "인증에 실패했습니다."),
    AUTHORIZATION_TOKEN_EXPIRED(401, "인증 토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_TYPE(401, "지원되지 않는 토큰 유형입니다."),
    INVALID_AUTHORIZATION_TOKEN(401, "유효하지 않은 인증 토큰입니다."),
    TOKEN_NOT_FOUND(404,"토큰이 존재하지 않습니다."),

    //refresh 토큰
    INVALID_REFRESH_TOKEN(400, "유효하지 않은 갱신 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(401, "갱신 토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "갱신 토큰을 찾을 수 없습니다."),

    //메일 인증 코드
    MAIL_SEND_FAILED(500, "메일 전송에 실패했습니다."),
    VERIFY_CODE_EXPIRED(403, "인증 코드가 만료 되었습니다."),
    VERIFY_CODE_NOTFOUND(404, "인증 코드가 맞지 않습니다."),

    //유저
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    LOGIN_FAILED(401,"로그인 실패"),
    EMAIL_ALREADY_EXISTS(409, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(400, "잘못된 비밀번호입니다."),
    UNREGISTERED_EMAIL(404, "등록되지 않은 이메일입니다."),
    USER_INVALID(400,"유효한 사용자가 아닙니다."),


    //리뷰
    REVIEW_NOT_AUTHOR(400, "작성자가 아닙니다");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
