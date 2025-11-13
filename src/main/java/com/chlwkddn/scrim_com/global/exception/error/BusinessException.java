package com.chlwkddn.scrim_com.global.exception.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorProperty errorProperty;

    public BusinessException(ErrorProperty errorProperty) {
        super(errorProperty.getMessage());
        this.errorProperty = errorProperty;
    }

    public BusinessException(ErrorProperty errorProperty, String detailMessage) {
        super(detailMessage);
        this.errorProperty = errorProperty;
    }
}