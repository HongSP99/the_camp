package io.camp.common.exception;


import io.camp.common.exception.Campsite.SeasonAlreadyExsistException;
import io.camp.common.exception.inventory.InventoryException;
import io.camp.common.exception.payment.PaymentException;
import io.camp.common.exception.reservation.ReservationException;
import io.camp.common.exception.user.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAnonymousException.class)
    public ResponseEntity userNotFoundException(UserAnonymousException ex) {
        log.error("message: {}", ex.getErrorMessage());
        return new ResponseEntity(ex.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    //유효성 검사 실패
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ErrorResponse.of(e.getBindingResult());
    }

    //제약 조건 위배
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        return ErrorResponse.of(e.getConstraintViolations());

    }

    //지원되지 않는 HTTP 메서드 요청
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

        return response;
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse response = ErrorResponse.of(ex.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    //JSON 형식에 오류가 있을 경우
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        final ErrorResponse response = ErrorResponse.of("Required request body is missing");

        return response;
    }

    //필수 요청 파라미터가 누락된 경우
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        final ErrorResponse response = ErrorResponse.of(e.getMessage());

        return response;
    }

    @ExceptionHandler
    public ResponseEntity handleReservationException(
            ReservationException e) {
        ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity handleInventoryException(
            InventoryException e) {
        ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }


    // 인증 관련 예외 처리
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException e) {
        ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }

    // 메일 전송 실패 예외 처리
    @ExceptionHandler(MailSendFailedException.class)
    public ResponseEntity<ErrorResponse> handleMailSendFailedException(MailSendFailedException e) {
        ErrorResponse response = ErrorResponse.of(ExceptionCode.MAIL_SEND_FAILED);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 인증 코드 검증 실패 예외 처리
    @ExceptionHandler(VerifyCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVerifyCodeNotFoundException(VerifyCodeNotFoundException e) {
        ErrorResponse response = ErrorResponse.of(ExceptionCode.VERIFY_CODE_NOTFOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }








    //위에 명시된 예외 외의 모든 예외 처리
    @ExceptionHandler
    public ResponseEntity handlePaymentException(
            PaymentException e) {
        ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }



    @ExceptionHandler(SeasonAlreadyExsistException.class)
    public ResponseEntity<String> handleSeasonAlreadyExsistException(SeasonAlreadyExsistException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }

}
