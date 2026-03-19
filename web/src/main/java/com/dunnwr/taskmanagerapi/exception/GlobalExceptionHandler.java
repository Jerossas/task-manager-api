package com.dunnwr.taskmanagerapi.exception;

import com.dunnwr.taskmanagerapi.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyRegistered(EmailAlreadyRegisteredException e){
        ErrorResponse error = new ErrorResponse(
                null,
                e.getMessage(),
                e.getStatusCode(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSignInCredentials(InvalidCredentialsException e){
        ErrorResponse error = new ErrorResponse(
                null,
                e.getMessage(),
                e.getStatusCode(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        ErrorResponse error = new ErrorResponse(
                null,
                e.getMessage(),
                e.getStatusCode(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException e){
        ErrorResponse error = new ErrorResponse(
                null,
                e.getMessage(),
                e.getStatusCode(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> tokenExpired(ExpiredJwtException e){
        ErrorResponse error = new ErrorResponse(
                null,
                e.getMessage(),
                403,
                LocalDateTime.now()
        );

        return ResponseEntity.status(403).body(error);
    }
}
