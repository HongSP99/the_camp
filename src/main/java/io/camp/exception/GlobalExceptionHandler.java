package io.camp.exception;


import io.camp.exception.user.UserAnonymousException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
