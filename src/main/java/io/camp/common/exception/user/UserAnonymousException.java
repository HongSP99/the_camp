package io.camp.common.exception.user;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class UserAnonymousException extends RuntimeException {
    private String errorMessage;

    public UserAnonymousException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
