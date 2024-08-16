package io.camp.common.exception.inventory;

import io.camp.common.exception.ExceptionCode;
import lombok.Getter;

public class InventoryException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public InventoryException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
