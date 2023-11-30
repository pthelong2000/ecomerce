package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderServiceExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .errorCode("500")
                        .build());
    }

    @ExceptionHandler(ExternalException.class)
    public ResponseEntity<ExceptionResponse> handleExternalException(ExternalException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .errorCode("400")
                        .build());
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<ExceptionResponse> handleOrderServiceException(OrderServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .errorCode("400")
                        .build());
    }
}
