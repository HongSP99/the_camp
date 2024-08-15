package io.camp.common.exception.Campsite;

import lombok.Data;

@Data
public class CampsiteNotFoundException extends RuntimeException {

    private String errorMessage;

    public CampsiteNotFoundException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
