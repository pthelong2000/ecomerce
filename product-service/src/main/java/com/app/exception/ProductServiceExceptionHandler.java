package com.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ExceptionResponse> handleProductServiceException(ProductServiceException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode("404")
                        .build());
    }
}
