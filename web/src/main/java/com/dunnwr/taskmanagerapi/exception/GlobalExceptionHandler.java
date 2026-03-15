package com.dunnwr.taskmanagerapi.exception;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(InvalidFieldException e){
        ErrorResponse error = new ErrorResponse(
                e.getField(),
                e.getMessage(),
                e.getStatusCode(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }
}
