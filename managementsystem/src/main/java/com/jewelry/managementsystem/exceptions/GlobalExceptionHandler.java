package com.jewelry.managementsystem.exceptions;

import com.jewelry.managementsystem.security.response.MessageResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /// Handling empty resources
    @ExceptionHandler( EmptyResourceException.class)
    public ResponseEntity<ErrorResponse> handleEmpty (EmptyResourceException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                );
        /// Set up the Response Entity and return it
        return  ResponseEntity.status(HttpStatus.OK).body(errorResponse);

    }

    ///  Non-existing items
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound (ResourceNotFound ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    ///  HIBERNATE LAYER
    @ExceptionHandler ( ConstraintViolationException.class )
    public ResponseEntity<Map<String, String>> handleConstraintViolationException (ConstraintViolationException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        MessageResponse response = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShoppingCartException.class)
    public ResponseEntity<ErrorResponse> handleShoppingCartException(ShoppingCartException ex) {
         ErrorResponse  errorResponse = new ErrorResponse(
                 ex.getMessage(),
                 HttpStatus.CONFLICT.value(),
                 LocalDateTime.now()
         );
         return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
