package com.jewelry.managementsystem.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


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

    ///  Non existing items
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound (ResourceNotFound ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    LocalDateTime.now()
        );
        return  ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    /// Duplicate items
    @ExceptionHandler ( DuplicateResourceException.class )
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException (DuplicateResourceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    ///  Method Argument Valid Exception @Valid -> DTO LAYER
    @ExceptionHandler ( MethodArgumentNotValidException.class )
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException( MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream().map( e -> e.getField()+" : "+ e.getDefaultMessage() )
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(message));

    }
    ///  HIBERNATE LAYER
    @ExceptionHandler ( ConstraintViolationException.class )
    public ResponseEntity<ErrorResponse> handleConstraintViolationException (ConstraintViolationException ex){
        String message = ex.getConstraintViolations()
                .stream()
                .map( v -> v.getPropertyPath()+ ": "+ v.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(message));
    }
}
