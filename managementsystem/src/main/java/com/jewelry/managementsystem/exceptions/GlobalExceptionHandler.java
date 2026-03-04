package com.jewelry.managementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /// Handling empty resources
    @ExceptionHandler( EmptyResourceException.class)
    public ResponseEntity<ErrorResponse> handleEmpty (EmptyResourceException ex) {

        ///  Create the Error Response object and fill with constructor
        ErrorResponse errorResponse = new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.NO_CONTENT.value(),
                        LocalDateTime.now()
                );
        /// Set up the Response Entity and return it
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);

    }
}
