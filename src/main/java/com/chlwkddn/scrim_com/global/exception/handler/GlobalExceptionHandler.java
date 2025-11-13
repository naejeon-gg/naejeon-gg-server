package com.chlwkddn.scrim_com.global.exception.handler;

import com.chlwkddn.scrim_com.global.exception.error.BusinessException;
import com.chlwkddn.scrim_com.global.exception.error.ErrorCode;
import com.chlwkddn.scrim_com.global.exception.error.ErrorProperty;
import com.chlwkddn.scrim_com.global.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        ErrorProperty error = ex.getErrorProperty();
        HttpStatus status = error.getStatus();

        ErrorResponse body = new ErrorResponse(
                status.value(),
                error.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // @Valid 또는 바인딩 에러
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = ErrorCode.BAD_REQUEST.getStatus();

        String message;

        if (ex instanceof MethodArgumentNotValidException manve) {
            message = manve.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .findFirst()
                    .map(error -> error.getDefaultMessage())
                    .orElse(ErrorCode.BAD_REQUEST.getMessage());
        } else if (ex instanceof BindException be) {
            message = be.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .findFirst()
                    .map(error -> error.getDefaultMessage())
                    .orElse(ErrorCode.BAD_REQUEST.getMessage());
        } else {
            message = ErrorCode.BAD_REQUEST.getMessage();
        }

        ErrorResponse body = new ErrorResponse(
                status.value(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = ErrorCode.INTERNAL_SERVER_ERROR.getStatus();

        ErrorResponse body = new ErrorResponse(
                status.value(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }
}