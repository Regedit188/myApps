package com.example.testovoetask.web;

import com.example.testovoetask.exception.CustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Void> handleRuntimeException(Exception e, WebRequest request) {
        log.error("Unexpected exception", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<String> handleCustomException(CustomException e, WebRequest request) {
        log.trace("Business exception", e);
        return ResponseEntity
                .status(e.getCommonErrorCode().getHttpStatus())
                .body(e.getMessage());
    }
}
