package org.sudal.responsecore.model.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorObject errorObject;

    public BusinessException(ErrorObject errorObject) {
        super(errorObject.getDescription());
        this.errorObject = errorObject;
    }

    public BusinessException(ErrorObject errorObject, String customMessage) {
        super(customMessage);
        this.errorObject = errorObject;
    }
}
